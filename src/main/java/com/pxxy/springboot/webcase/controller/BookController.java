package com.pxxy.springboot.webcase.controller;

import com.pxxy.springboot.webcase.dto.ErrorMessage;
import com.pxxy.springboot.webcase.entity.Book;
import com.pxxy.springboot.webcase.entity.BookOperationResponse;
import com.pxxy.springboot.webcase.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/book")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private static final String COVER_UPLOAD_DIR = "/images/book_cover_img";

    @Resource
    private BookService bookService;

    /**
     * 获取所有图书列表
     * @param model 模型对象
     * @return 返回图书列表页面
     */
    @GetMapping("/list.do")
    public String listAll(Model model) {
        List<Book> books = this.bookService.findAll();
        model.addAttribute("BOOK_LIST", books);
        log.debug("展示所有图书");
        return "book/list";
    }

    /**
     * 添加图书
     * @param book 图书对象
     * @param bindingResult Bean验证结果
     * @param coverUrl 图书封面文件
     * @return 返回添加图书结果
     */
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    @ResponseBody
    public Object addBook(@Valid Book book, BindingResult bindingResult, @RequestParam("coverUrlFile") MultipartFile coverUrl) {

        //判断bindingResult是否存在bean验证后的错误信息
        if (bindingResult.hasErrors()) {
            log.error("添加图书失败，验证bean错误");
            return getErrorData(bindingResult);
        }

        String fileName = null;
        System.out.println(coverUrl);
        if (coverUrl != null) {
            fileName = uploadImg(coverUrl);
        }

        if (book != null) {
            book.setCoverUrl(fileName);
            bookService.save(book);
            log.debug("添加图书成功");
        } else {
            log.error("添加图书失败");
        }
        return new BookOperationResponse("success", null);
    }

    /**
     * 根据关键词搜索图书
     * @param keyword 搜索关键词
     * @param model 模型
     * @return 返回图书列表页面
     */
    @GetMapping("/search.do")
    public String searchBook(@RequestParam("search") String keyword, Model model) {
        List<Book> books;
        if (keyword == null) {
            log.debug("用户未输入关键词，展示所有图书");
            return "redirect:/book/list.do";
        } else if (keyword != null && StringUtils.isEmpty(keyword.trim())){
            log.debug("用户未输入关键词，展示所有图书");
            return "redirect:/book/list.do";
        } else {
            books = this.bookService.findByKeyword(keyword.trim());
            log.debug("用户输入关键词【" + keyword + "】，展示结果【" + books.size() + "】个");
        }
        model.addAttribute("search",keyword);
        model.addAttribute("BOOK_LIST", books);
        return "book/list";
    }

    /**
     * 更新图书操作前获取要更新的图书对象
     * @param id 图书ID
     * @return 要更新的图书对象
     */
    @PostMapping("/toUpdate.do")
    @ResponseBody
    public Book toUpdateBook(@RequestParam("id") Integer id) {

        if (id != null) {
            Book book = bookService.findById(id);
            if (book != null) {
                log.debug("更新页面获取图书信息成功");
                return book;
            }
        } else {
            log.error("更新页面获取图书信息失败");
        }
        return null;
    }

    /**
     * 更新图书
     * @param book 图书实体对象
     * @param bindingResult Bean验证结果
     * @param coverUrl 图书封面文件
     * @return 更新结果
     */
    @PostMapping("/update.do")
    @ResponseBody
    public Object updateBook(@Valid Book book, BindingResult bindingResult, @RequestParam("coverUrlFile") MultipartFile coverUrl) {

        //判断bindingResult是否存在bean验证后的错误信息
        if (bindingResult.hasErrors()) {
            log.error("更新图书失败，验证bean错误");

            return getErrorData(bindingResult);
        }
        String fileName = null;
        if (coverUrl != null) {
            fileName = uploadImg(coverUrl);
        }

        if (book != null) {
            if (fileName != null) {
                book.setCoverUrl(fileName);
            }

            bookService.update(book);
            log.debug("更新图书成功");
            return new BookOperationResponse("success", null);
        }
        log.error("更新图书失败");

        return new BookOperationResponse("failure", null);
    }

    /**
     * 获取错误数据
     * @param bindingResult 绑定结果
     * @return 错误数据
     */
    private Object getErrorData(BindingResult bindingResult) {
        List<ErrorMessage> errorData = new ArrayList<>();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            ErrorMessage em = null;
            if (fieldError.getField().equals("price") && String.valueOf(fieldError.getRejectedValue()).isEmpty()) {
                em = new ErrorMessage(fieldError.getField(), "单价不允许为空");
            } else {
                em = new ErrorMessage(fieldError.getField(), fieldError.getDefaultMessage());
            }
            errorData.add(em);
        }
        return new BookOperationResponse("failure", errorData);
    }

    /**
     * 上传图片
     * @param coverUrl 图片文件
     * @return 图片文件名
     */
    private String uploadImg(MultipartFile coverUrl) {
        log.debug("上传图片");
        //获取当前jar包的绝对路径
        ApplicationHome applicationHome = new ApplicationHome(getClass());
        log.debug(">>>> source: {}", applicationHome.getSource());
        log.debug(">>> dir: {}", applicationHome.getDir()); //就是当前jar包所在的目录
        String realPath = applicationHome.getDir().getAbsolutePath() + File.separatorChar + COVER_UPLOAD_DIR;
        log.debug(realPath);
        String fileName = null;
        //上传图片
        final String originalFilename = coverUrl.getOriginalFilename();
        if (!originalFilename.isEmpty()) {
            fileName = UUID.randomUUID().toString();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            fileName += suffix;
            try {
                coverUrl.transferTo(new File(realPath + File.separatorChar + fileName));
            } catch (IOException e) {
                log.error("上传图片失败", e);
            }
        }
        return fileName;
    }

    /**
     * 删除单个图书
     * @param id 图书ID
     * @return 删除结果
     */
    @PostMapping("/delete.do")
    @ResponseBody
    public String deleteBook(@RequestParam("id") Integer id) {
        if (id != null) {
            bookService.delete(id);
            log.debug("删除单个图书成功");
        } else {
            log.error("删除单个图书失败");
        }
        return "删除成功";
    }

    /**
     * 批量删除图书
     * @param idList 图书ID列表
     * @return 删除结果
     */
    @PostMapping("/deleteSelect.do")
    @ResponseBody
    public String deleteSelected(@RequestBody List<Integer> idList) {
        if (idList != null && idList.size() > 0) {
            bookService.deleteSelected(idList);
            log.debug("批量删除图书成功");
        } else {
            log.error("批量删除图书失败");
        }
        return "删除成功";
    }

}
