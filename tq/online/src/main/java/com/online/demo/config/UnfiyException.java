package com.online.demo.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class UnfiyException {

    @ExceptionHandler(Throwable.class)
    public String handlerException(HttpServletRequest request,Throwable e){
        String url=request.getRequestURI();
        request.setAttribute("message",e.getMessage());
        request.setAttribute("url",url);
        System.out.println("error url : " + url);
        e.printStackTrace();
        return "error";
    }
}
