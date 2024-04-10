package com.pxxy.springboot.webcase.interceptor;

import com.pxxy.springboot.webcase.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        boolean isLoggedIn = getCurrentUser(request) != null;

        if (!isLoggedIn) {
            if (!uri.startsWith("/app/user")) {
                // 重定向到登录页面（这里假设“/app/user/toLogin”是你的登录页面）
                response.sendRedirect("/app/user/toLogin");
                return false;
            }
        }

        return true;  // 允许继续执行后续处理器
    }
    private User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 从session中获取已登录用户对象
            Object loginUser = session.getAttribute("LOGIN_USER");
            if (loginUser instanceof User) {
                return (User) loginUser;
            }
        }


        return null;  // 如果session不存在或session中没有LOGIN_USER属性，则表示未登录
    }
}
