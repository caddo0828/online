package com.online.demo.controller;

import com.online.demo.entity.ParentModule;
import com.online.demo.service.LoginService;
import com.online.demo.util.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @Value("${spring.servlet.multipart.location}")
    private String path;

    @RequestMapping(value="/toLogin")
    public String toLogin(HttpServletRequest request){
        request.setAttribute("root",request.getContextPath());
        return "index";
    }

    @PostMapping(value = "/main.do")
    public String toMain(HttpServletRequest request, HttpServletResponse response, String username, String password,String remeber){
        request.setAttribute("root",request.getContextPath());
        String result=loginService.doLogin(request,response,username,password,remeber);
        if(result.equals("index")){
            request.setAttribute("message","用户名或密码错误！");
            return result;
        }else{
            return result;
        }
    }

    @RequestMapping(value = "/main")
    public String mainHtml(HttpServletRequest request){
        request.setAttribute("root",request.getContextPath());
        request.setAttribute("userInfo",request.getSession().getAttribute("user"));
        Map<String,Object> userInfo= (Map<String, Object>) request.getSession().getAttribute("user");
        List<Map<String,Object>> list=loginService.getModulesSon(MapUtils.getMapNullString(userInfo,"role_id"));
        List<ParentModule> roleName=MapUtils.getModilesInfo(list,request);
        request.setAttribute("roles",roleName);
        return "service/main";
    }

    @RequestMapping(value = "/Timeout")
    public String Timeout(){
        return "timeout";
    }

    @RequestMapping(value = "logout")
    public String Logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "redirect:/toLogin";
    }

}
