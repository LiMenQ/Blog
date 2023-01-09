package com.lmq.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/30 12:26
 */
public class LoginInterceptor implements HandlerInterceptor {
    //拦截器 拦截不登录直接访问后台的
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("/admin");  //重定向
            return false;
        }

        return true;

    }
}
