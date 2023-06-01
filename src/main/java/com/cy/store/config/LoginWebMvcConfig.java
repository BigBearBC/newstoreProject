package com.cy.store.config;

import com.cy.store.controller.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoginWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor projectInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //设置白名单即不需要拦截的路径
        List<String> patterns = new ArrayList<>();
        //设置静态路径
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");
        patterns.add("/web/login.html");
        patterns.add("/web/product.html");
        patterns.add("index.html");
        //配置请求路径
        patterns.add("/users/login");
        patterns.add("/users/reg");

        //注册拦截器
        registry.addInterceptor(projectInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(patterns);
    }

}
