package com.pxxy.springboot.webcase.controller;

import com.pxxy.springboot.webcase.entity.User;
import com.pxxy.springboot.webcase.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    /**
     * 登录请求处理方法
     *
     * @param name      用户名
     * @param pwd       密码
     * @param checkCode 验证码
     * @param model     模型
     * @param session   会话
     * @return 登录成功后重定向到book/list.do，登录失败后返回user/login
     */
    @PostMapping("/login")
    public String login(@RequestParam("name") String name, @RequestParam("pwd") String pwd, @RequestParam("checkcode") String checkCode, Model model, HttpSession session) {
        log.debug("登录请求的三个参数：" + name + "," + pwd + "," + checkCode);

        final User user = this.userService.login(name, null);
        if (user == null) {
            model.addAttribute("USER_NOT_EXIST", "用户不存在");
        } else {
            if (user.getPassword().equals(pwd)) {
                log.debug("登录成功..");
                session.setAttribute("LOGIN_USER", user);

                //return "book/list";
                return "redirect:/book/list.do";
            } else {
                model.addAttribute("PWD_ERROR", "密码不正确");
//                return "forward:/WEB-INF/classes/public/login.jsp";
                return "user/login";
            }
        }

        return null;
    }

    /**
     * 跳转到登录页面
     *
     * @return 登录页面的视图名称
     */
    @GetMapping("/toLogin")
    public String toLogin() {
        return "user/login";
    }   // templates/user/login.html

    /**
     * 用户退出登录
     *
     * @param session HttpSession对象
     * @return 登录页面
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        log.debug("用户退出登录");
        session.removeAttribute("LOGIN_USER");
        return "user/login";
    }

    /**
     * 注册页面的跳转
     *
     * @return 注册页面的视图名称
     */
    @GetMapping("/toRegister")
    public String toRegister() {
        return "user/register";
    }

    /**
     * 用户注册
     *
     * @param userName  用户名
     * @param password  密码
     * @param pwd2      再次输入密码
     * @param checkCode 验证码
     * @param model     模型
     * @return 注册页面或登录页面
     */
    @PostMapping("/register")
    public String register(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("pwd2") String pwd2, @RequestParam(value = "checkcode", required = false) String checkCode, Model model) {
        log.debug("用户进行注册操作，四个参数分别是：userName:" + userName + ",password:" + password + ",pwd2:" + pwd2 + ",checkCode:" + checkCode);
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);

        boolean isExist = userService.judgeUser(user);
        if (isExist) {
            model.addAttribute("USERNAME_EXIST_ERROR", "用户名已存在");
            return "user/register";
        }

        if (!password.equals(pwd2)) {
            model.addAttribute("CHECK_PWD_ERROR", "输入的密码不一致");
            return "user/register";
        }

        userService.register(user);
        log.debug("注册成功");
        model.addAttribute("REGISTER_SUCCESS", "注册成功");
        return "user/login";
    }

}
