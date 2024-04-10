package com.pxxy.springboot.webcase.controller;

import com.pxxy.springboot.webcase.entity.LogMessage;
import com.pxxy.springboot.webcase.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/log")
public class LogController {
    private static final Logger log = LoggerFactory.getLogger(LogController.class);

    @Resource
    private LogService logService;

    @GetMapping("/list.do")
    public String listAll(Model model) {
        List<LogMessage> logList = logService.findAll();
        model.addAttribute("LOG_LIST", logList);
        return "log/loglist";
    }

    @GetMapping("/search.do")
    public String searchLog(@RequestParam("search") String keyword, Model model) {
        List<LogMessage> logs;
        if (keyword == null) {
            log.info("管理员未输入关键词，展示所有日志");
            return "redirect:/log/list.do";
        } else if (keyword != null && StringUtils.isEmpty(keyword.trim())) {
            log.info("管理员未输入关键词，展示所有日志");
            return "redirect:/log/list.do";
        } else {
            logs = this.logService.findByKeyword(keyword.trim());
            log.info("管理员输入关键词【" + keyword + "】，展示结果【" + logs.size() + "】个");
        }
        model.addAttribute("search", keyword);
        model.addAttribute("LOG_LIST", logService.findByKeyword(keyword));
        return "log/loglist";
    }

    @PostMapping("/delete.do")
    @ResponseBody
    public String deleteLog(@RequestParam("id") Integer id) {
        if (id != null) {
            logService.delete(id);
            log.debug("删除单条日志成功");
        } else {
            log.error("删除单条日志失败");
        }
        return "删除成功";
    }

    @PostMapping("deleteSelect.do")
    @ResponseBody
    public String deleteLogs(@RequestBody List<Integer> idList) {
        if (idList != null && idList.size() > 0) {
            logService.deleteSelected(idList);
            log.info("批量删除日志成功");
        } else {
            log.error("批量删除日志失败");
        }
        return "删除成功";
    }

}
