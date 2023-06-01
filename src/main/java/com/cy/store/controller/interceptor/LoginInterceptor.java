package com.cy.store.controller.interceptor;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session对象，判断用户是否已经登录
        Object user = request.getSession().getAttribute("user");
        if (ObjectUtils.isNull(user)){
            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;
    }
}
