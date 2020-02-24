package com.online.demo.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.online.demo.entity.ParentModule;
import com.online.demo.service.SystemService;
import com.online.demo.util.MapUtils;
import com.online.demo.util.ToMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "/moduleManager")
    public String getModulesInfo(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setAttribute("root",request.getContextPath());
        List<Map<String,Object>> list=systemService.getAllModulesInfo();
        List<ParentModule> roleName=MapUtils.getModilesInfo(list,request);
        request.setAttribute("roles",java.net.URLEncoder.encode(JSON.toJSONString(roleName),"UTF-8"));
        return "service/system/modules";
    }

    @RequestMapping(value="/getParentModules")
    @ResponseBody
    public List<Map<String,Object>> getParentModules(){
        return systemService.getParentModulesInfo();
    }

    @RequestMapping(value = "/addmodule")
    public String addModuleHtml(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setAttribute("root",request.getContextPath());
        List<Map<String,Object>> list=systemService.getParentModulesInfo();
        request.setAttribute("modules",java.net.URLEncoder.encode(JSON.toJSONString(list),"UTF-8"));
        return "service/system/addModule";
    }

    @ResponseBody
    @RequestMapping(value = "/addmodulesInfo")
    public String addModulesInfo(HttpServletRequest request){
        String isP=request.getParameter("switch");
        Map map=request.getParameterMap();
        System.out.println(map);
        if(isP!=null && !isP.equals("")){
            String p_name=request.getParameter("p_name");
            if(systemService.addParentModules(p_name))
                return "success";
            else
                return "error";
        }else{
            String s_name=request.getParameter("s_name");
            String url=request.getParameter("url");
            Integer parent_id=Integer.parseInt(request.getParameter("pmodules"));
            if(systemService.addSonModules(s_name,url,parent_id))
                return "success";
            else
                return "error";
        }
    }

    @RequestMapping(value = "/deleteSonModule")
    @ResponseBody
    public String deleteSonModules(String id){
        if(id.contains("children"))
            if(systemService.deleteSonModules(id.split("_")[0]))
                return "success";
            else
                return "error";
        else if(id.contains("parent"))
            if(systemService.deleteParentModules(id.split("_")[0]))
                return "success";
            else
                return "error";
        else
            return "error";
    }

    @RequestMapping(value = "/updateModule")
    @ResponseBody
    public String updateModules(String id,String p_name,String s_name,String pmodules,String url){
        if(id.contains("parent")){
            if(systemService.updateParentModule(id.split("_")[0],p_name))
                return "success";
            else
                return "error";
        }else if(id.contains("children")){
            if(systemService.updateSonModule(id.split("_")[0],s_name,pmodules,url))
                return "success";
            else
                return "error";
        }else{
            return "error";
        }
    }

    @RequestMapping(value = "/getRolesById")
    @ResponseBody
    public List<Map<String,Object>> getRoleTemplateById(String id){
        return systemService.getRoleTemplates(id.split("_")[0]);
    }

    @RequestMapping(value = "/addRoleById")
    @ResponseBody
    public String addRolesByTemplateId(String id,String list){
        return systemService.updateRolesTemplate(id.split("_")[0],list);
    }

    @RequestMapping(value = "/roles")
    public String rolesInfo(HttpServletRequest request){
        request.setAttribute("root",request.getContextPath());
        return "service/system/roles";
    }

    @ResponseBody
    @RequestMapping(value = "/getRolesInfo")
    public Map<String,Object> getRolesInfo(HttpServletRequest request){
        String limit=request.getParameter("limit");
        String page=request.getParameter("page");
        PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(limit));
        List<Map<String,Object>> list=systemService.getRolesInfo();
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<Map<String,Object>>(list);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("total",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    /**
     * 新增角色名称
     * @param name
     * @return
     */
    @RequestMapping(value = "/addRole")
    @ResponseBody
    public String addRoleName(String name){
        if(systemService.addRoleName(name))
            return "success";
        else
            return "fail";
    }

    /**
     * 删除角色名称
     * @param id
     * @return
     */
    @RequestMapping(value = "/delRole")
    @ResponseBody
    public String delRoleById(String id){
        if(systemService.delRoleNameById(id))
            return "success";
        else
            return "fail";
    }

    /**
     * 更新角色名称
     * @param newName
     * @param id
     * @return
     */
    @RequestMapping(value = "/updateRole")
    @ResponseBody
    public String updateRoleInfo(String newName,String id){
        if(systemService.updateRoleName(newName,id)){
            return "success";
        }else{
            return "fail";
        }
    }

    /**
     * 更改角色状态
     * @param status
     * @param id
     * @return
     */
    @RequestMapping(value = "/updateRoleStatus")
    @ResponseBody
    public String updateRoleStatus(Integer status,String id){
        if(systemService.updateRoleStatus(status,id)){
            return "success";
        }else{
            return "fail";
        }
    }

    /**
     * 跳转用户管理页面,并传输角色数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/userInfo")
    public String redirectUserInfoHtml(HttpServletRequest request) {
        request.setAttribute("root",request.getContextPath());
        return "service/system/userInfo";
    }

    /**
     * 获取用户的基本信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserInfoData")
    public Map<String,Object> getUserInfoData(HttpServletRequest request){
        String limit=request.getParameter("limit");
        String page=request.getParameter("page");
        PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(limit));
        List<Map<String,Object>> list=systemService.getUserInfoData();
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<Map<String,Object>>(list);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("total",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    /**
     * 加载新增用户页面
     * @return
     */
    @RequestMapping(value = "/addUserInfo")
    public String addUserInfo(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setAttribute("root",request.getContextPath());
        List<Map<String,Object>> list=systemService.getRolesInfo();
        request.setAttribute("roles",java.net.URLEncoder.encode(JSON.toJSONString(list),"utf-8"));
        return "service/system/addUser";
    }

    /**
     * 添加用户信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addUserInfoData")
    public String addUserInfoData(HttpServletRequest request){
        Map<String,Object> param=ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");
        if(systemService.insertUserInfo(param)>0)
            return "success";
        else
            return "fail";
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/delUser")
    @ResponseBody
    public String delUserInfo(String id){
        if(systemService.delUserInfo(id)>0)
            return "success";
        else
            return "fail";
    }

    /**
     * 跳转修改用户信息页面
     * @return
     */
    @RequestMapping(value = "/updateUser")
    public String updateUser(String id,HttpServletRequest request) throws UnsupportedEncodingException {
        request.setAttribute("root",request.getContextPath());
        Map<String,Object> userInfo=systemService.getUserInfoById(id);
        List<Map<String,Object>> list=systemService.getRolesInfo();
        request.setAttribute("roles",java.net.URLEncoder.encode(JSON.toJSONString(list),"utf-8"));
        request.setAttribute("userInfo",java.net.URLEncoder.encode(JSON.toJSONString(userInfo),"utf-8"));
        return "service/system/updateUser";
    }

    /**
     * 修改用户基本信息
     * @return
     */
    @RequestMapping(value = "/updateUserInfoData")
    @ResponseBody
    public String updateUserInfoData(HttpServletRequest request){
        Map<String,Object> param=ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");
        if(systemService.updateUserInfo(param)>1)
            return "success";
        else
            return "fail";
    }

    /**
     * 跳转至日志页面
     * @return
     */
    @RequestMapping(value = "/logInfo")
    public String toLogHtml(HttpServletRequest request){
        request.setAttribute("root",request.getContextPath());
        return "service/system/log";
    }

    /**
     * 查看日志记录
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getLogInfo")
    public Map<String,Object> getLogInfo(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        String limit=request.getParameter("limit");
        String page=request.getParameter("page");
        PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(limit));
        List<Map<String,Object>> list=systemService.getLogInfoData();
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<Map<String,Object>>(list);
        map.put("total",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }
}
