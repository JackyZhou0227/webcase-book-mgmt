package com.pxxy.springboot.webcase.interceptor;

import com.pxxy.springboot.webcase.entity.LogMessage;
import com.pxxy.springboot.webcase.entity.User;
import com.pxxy.springboot.webcase.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/************
 * 请求URL执行的性能日志拦截器
 */
@Component
public class PerformanceLogInterceptor extends HandlerInterceptorAdapter {

    //此属性是基于线程ID来绑定对象的,ThreadLocal相当于是Key是线程ID的Map,所以，它是一个容器类
    private NamedThreadLocal<Long> timer = new NamedThreadLocal<>("ptime");
    private static final Logger log = LoggerFactory.getLogger(PerformanceLogInterceptor.class);

    @Resource
    private LogService logService;

    /**
     * 此方法为拦截器的预处理方法，在当前Controller方法执行前调用。主要功能为记录系统时间并将其绑定到ThreadLocal中，
     * 以便于后续操作中可以随时获取到方法开始执行的时间。
     *
     * @param request  HttpServletRequest对象，代表客户端的请求。
     * @param response HttpServletResponse对象，用于向客户端发送响应。
     * @param handler  将要执行的处理器对象。
     * @return 返回true表示继续执行拦截器链中的下一个拦截器，返回false则中断执行链。
     * @throws Exception 可能抛出的异常。
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // 记录请求开始时间
        long startTime = System.currentTimeMillis();
        // 将开始时间绑定到ThreadLocal中，以便在同一个线程中的其他地方访问
        timer.set(startTime);

        // 继续执行拦截器链
        return true;
    }

    /**
     * 请求处理完成后执行的回调方法。
     * 用于记录请求耗时和登录用户的信息以及请求的URI，如果请求耗时超过300ms，则记录日志。
     * 对于静态资源的请求，不进行处理。
     *
     * @param request  本次请求的HttpServletRequest对象
     * @param response 本次请求的HttpServletResponse对象
     * @param handler  处理本次请求的处理器对象
     * @param ex       处理本次请求时抛出的异常，如果无异常则为null
     * @throws Exception 如果处理过程中发生异常，则抛出
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        //1. 记录当前系统时间
        long endTime = System.currentTimeMillis();
        //2. 通过ThreadLocal来取出当前线程的起始时间
        long startTime = timer.get();
        //3. 两个时间差
        long cost = endTime - startTime;
        //
        String uri = request.getRequestURI();
        //
        if (uri.startsWith(request.getContextPath() + "/static")) {
            return;
        }
        //如果请求耗时超过300ms，则记录日志
        if (cost > 300) { // 这个阀值是可以根据需求自己设定的
            log.debug("请求：【" + request.getRequestURI() + "】所费时间是：" + cost + "ms.\n");
        } else {
            // 请求耗时在性能要求的阀值之内，记录日志
            log.debug("请求：【" + request.getRequestURI() + "】所费时间是：" + cost + "ms.\n" + "所花时间是在性能要求的阀值之内的\n");
        }

        // 尝试从Session中获取登录用户信息
        HttpSession session = request.getSession(false);
        String userName = "未登录";

        try {
            Object userObj = session.getAttribute("LOGIN_USER");
            if (userObj instanceof User) {
                User user = (User) userObj;
                userName = user.getUserName();
            } else {
                // 如果LOGIN_USER不是User类型，记录错误日志
                log.error("USER_LOGIN 不是 User类型");
            }
        } catch (NullPointerException e) {
            // 如果Session中没有USER_LOGIN，记录未登录信息
            log.info("session 中没有 USER_LOGIN,用户未登录");
        } finally {
            // 记录日志，包括用户名、请求URI、时间戳和请求耗时，并保存日志信息
            log.info("userName:" + userName + " url:" + request.getRequestURI() + " time:" + LocalDateTime.now() + " cost:" + cost + "ms");
            LogMessage logMessage = new LogMessage(userName, request.getRequestURI(), LocalDateTime.now(), cost);
            logService.save(logMessage);
        }
       /* log.info("userName:"+userName+" url:"+request.getRequestURI()+" time:"+LocalDateTime.now()+" cost:"+cost+"ms");
        LogMessage logMessage = new LogMessage(userName,request.getRequestURI(), LocalDateTime.now(),cost);
        logService.save(logMessage);*/

    }
}
