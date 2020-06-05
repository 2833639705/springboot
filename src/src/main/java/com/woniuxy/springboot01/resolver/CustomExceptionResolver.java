package com.woniuxy.springboot01.resolver;

import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

// 统一异常处理
public class CustomExceptionResolver {

    @ExceptionHandler
    public String exceptionHandler(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        return "redirect:/errorDfault.html";
    }
}
