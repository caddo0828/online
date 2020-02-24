package com.online.demo.controller;

import com.alibaba.fastjson.JSON;
import com.online.demo.dao.SystemDao;
import com.online.demo.service.LoginService;
import com.online.demo.service.SystemService;
import com.online.demo.service.UserService;
import com.online.demo.util.MapUtils;
import com.online.demo.util.ToMapUtils;
import com.online.demo.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@RequestMapping(value = "/user")
@Controller
public class UserInfoController {

    @GetMapping(value = "/toUserInfo")
    public String redirectToUserInfo(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setAttribute("root",request.getContextPath());
        Map<String,Object> userInfo=systemService.getUserInfoById(((Map) request.getSession().getAttribute("user")).get("id").toString());
        request.setAttribute("userInfo",URLEncoder.encode(JSON.toJSONString(userInfo),"UTF-8"));
        return "service/userInfo";
    }

    @Value("${spring.servlet.multipart.location}")
    private String path;

    @Autowired
    private LoginService loginService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private UserService userService;
    /**
     * 上传头像图片
     * @param multipartFile
     * @param request
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/fileUpload")
    public Map<String,Object> FileUpload(@RequestParam("file") MultipartFile multipartFile,HttpServletRequest request) throws IOException {
        String name=multipartFile.getOriginalFilename();
        String lastType=name.substring(name.lastIndexOf(".")+1,name.length());
        String newName=UUIDUtils.getId()+"."+lastType;
        String email= request.getParameter("email");
        //首先获取根目录：
        File rootPath = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!rootPath.exists()) {
            rootPath = new File("");
        }
        //将文件存放在与jar包同级的static的img目录下
        File upload = new File(rootPath.getAbsolutePath(),path);
        if(!upload.exists()) {
            upload.mkdirs();
        }
        //创建文件
        File file=new File(rootPath.getAbsolutePath()+path+newName);
        if (!file.exists())
            file.createNewFile();
        //向该文件中输入数据
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        out.write(multipartFile.getBytes());
        out.flush();
        out.close();
        Map<String,Object> json=new HashMap<String,Object>();
        if (loginService.updateImageName(newName,email)){
            ((Map)request.getSession().getAttribute("user")).put("image",newName);
            json.put("code","200");
            json.put("msg","success");
            json.put("img",newName);
            return json;
        }else {
            json.put("code","500");
            json.put("msg","fail");
            return json;
        }
    }

    /**
     * 更新个人信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateUserInfoById")
    @ResponseBody
    public String updateUserInfoById(HttpServletRequest request){
        Map<String,Object> param=ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");
        if(userService.udpateUserInfoById(param)>0)
            return "success";
        else
            return "fail";
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping(value = "/updatePwd")
    @ResponseBody
    public String updatePasswordById(HttpServletRequest request){
        Map<String,Object> param=ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");
        System.out.println(param);
        if(userService.verfiyPasswdById(param)==0){
            return "{\"status\":400,\"msg\":\"原密码错误\"}";
        }else if(userService.updateUserPassWordById(param)>0){
            return "{\"status\":200,\"msg\":\"修改成功\"}";
        }else{
            return "{\"status\":401,\"msg\":\"密码修改失败，联系管理员\"}";
        }
    }


}
