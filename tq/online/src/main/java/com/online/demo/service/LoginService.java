package com.online.demo.service;

import com.online.demo.config.logInfoContent;
import com.online.demo.dao.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    LoginDao loginDao;

    @logInfoContent(content = "登录")
    public String  doLogin(HttpServletRequest request, HttpServletResponse response, String username, String password, String remeber){
        List<Map<String,Object>> list=loginDao.getUserInfo(username,password);
        if(!list.isEmpty()){
            if (remeber!=null){
                Cookie cookie = new Cookie("username",username);
                cookie.setMaxAge(60*60);
                cookie.setValue(username);
                response.addCookie(cookie);
            }
            request.getSession().setAttribute("user",list.get(0));
            return "redirect:/main";
        }else{
            return "index";
        }
    }


    @logInfoContent(content = "登录",isInsertLog = false)
    public List<Map<String,Object>> getUserInfo(String username,String password){
        return loginDao.getUserInfo(username,password);
    }

    @logInfoContent(content = "更换头像")
    public boolean updateImageName(String newName,String email){
        return loginDao.updateImageName(newName,email);
    }

    @logInfoContent(content = "根据角色ID获取左侧菜单栏")
    public List<Map<String,Object>> getModulesSon(String role_id){
        return loginDao.getModulesSon(role_id);
    }
}
