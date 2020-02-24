package com.online.demo.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url=request.getRequestURI();
        System.out.println(url);
        if(url.matches("/backManager/img/")){
            return false;
        }
        else if(url.matches(".*js|.*css|.*jpg|png|.*JPG|.*toLogin|.*Timeout|.*error|.*main.do") || url.replaceAll("/","").equals(request.getContextPath().replaceAll("/",""))){
            return true;
        }else{
            Map<String,Object> userInfo= (Map<String, Object>) request.getSession().getAttribute("user");
            if(userInfo==null) {
                response.sendRedirect(request.getContextPath()+"/Timeout");
                return false;
            }else{
                return true;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("postHandle......");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //System.out.println("afterCompletion......");
    }
}
