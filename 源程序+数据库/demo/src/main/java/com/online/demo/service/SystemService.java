package com.online.demo.service;

import com.online.demo.config.logInfoContent;
import com.online.demo.dao.SystemDao;
import com.online.demo.util.DataSourceTemplate;
import com.online.demo.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SystemService {
    @Autowired
    private SystemDao systemDao;

    @Autowired @Qualifier("mysqlOnDataSourceTemplate")
    private DataSourceTemplate template;

    @logInfoContent(content = "系统管理-模块管理-获取所有未删除的子模块信息")
    public List<Map<String,Object>> getAllModulesInfo(){
        return systemDao.getAllModulesInfo();
    }

    @logInfoContent(content = "系统管理-模块管理-获取所有父模块信息")
    public List<Map<String,Object>> getParentModulesInfo(){
        return systemDao.getParentModules();
    }

    @logInfoContent(content = "系统管理-模块管理-添加父模块")
    public boolean addParentModules(String name){
        return systemDao.addParentModules(name);
    }

    @logInfoContent(content = "系统管理-模块管理-添加子模块")
    public boolean addSonModules(String name,String url,Integer parent_id){
        return systemDao.addSonModules(name,url,parent_id);
    }

    @logInfoContent(content = "系统管理-模块管理-删除子模块")
    public boolean deleteSonModules(String id){
        return systemDao.deleteSonModules(id);
    }

    @logInfoContent(content = "系统管理-模块管理-删除父模块")
    public boolean deleteParentModules(String id){
        return systemDao.deleteParentModules(id);
    }

    @logInfoContent(content = "系统管理-模块管理-修改父模块")
    public boolean updateParentModule(String id,String name){
        return systemDao.updateParentModule(id,name);
    }

    @logInfoContent(content = "系统管理-模块管理-修改子模块")
    public boolean updateSonModule(String id,String name,String parent_id,String url){
        return systemDao.updateSonModule(id,name,parent_id,url);
    }

    @logInfoContent(content = "系统管理-模块管理-获取当前模块所赋权的角色名称")
    public List<Map<String,Object>> getRoleTemplates(String id){
        return systemDao.getRoleTemplates(id);
    }

    @logInfoContent(content = "系统管理-模块管理-模块赋权给角色")
    public String updateRolesTemplate(String id,String list){
        String sql="insert into on_role_template(id,role_id,template_id) values(?,?,?)";
        List<Object[]> l=new ArrayList<>();
        for(String i : list.split(",")){
            l.add(new Object[]{UUIDUtils.getId(),i,id});
        }
        try{
            systemDao.delRolsTemplate(id);
            template.batchUpdate(sql,l);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    @logInfoContent(content = "系统管理-角色管理-获取所有角色信息")
    public List<Map<String,Object>> getRolesInfo(){
        return systemDao.getRolesInfo();
    }

    @logInfoContent(content = "系统管理-角色管理-添加角色")
    public boolean addRoleName(String name) {
        return systemDao.addRoleName(name);
    }

    @logInfoContent(content = "系统管理-角色管理-删除角色")
    public boolean delRoleNameById(String id){
        return systemDao.delRoleNameById(id);
    }

    @logInfoContent(content = "系统管理-角色管理-修改角色信息")
    public boolean updateRoleName(String name,String id){
        return systemDao.updateRoleName(name,id);
    }

    @logInfoContent(content = "系统管理-角色管理-锁定或解锁角色使用")
    public boolean updateRoleStatus(Integer status,String id){
        return systemDao.updateRoleStatus(status,id);
    }

    @logInfoContent(content = "系统管理-用户管理-获取所有用户基本信息")
    public List<Map<String,Object>> getUserInfoData(){
        return systemDao.getUserInfoData();
    }

    @logInfoContent(content = "系统管理-用户管理-新增用户基本信息")
    public int insertUserInfo(Map<String,Object> param){
        param.put("id",UUIDUtils.getId());
        return systemDao.insertUserInfo(param);
    }

    @logInfoContent(content = "系统管理-用户管理-删除用户基本信息")
    public int delUserInfo(String id){
        return systemDao.delUserInfo(id);
    }

    @logInfoContent(content = "系统管理-用户管理-根据用户ID获取用户基本信息",isInsertLog = false)
    public Map<String,Object> getUserInfoById(String id){
        return systemDao.getUserInfoById(id);
    }

    @logInfoContent(content = "系统管理-用户管理-管理员更新用户信息")
    public int updateUserInfo(Map<String,Object> param){
        return systemDao.updateUserInfo(param);
    }

    @logInfoContent(content = "系统管理-查看日志",isInsertLog = false)
    public List<Map<String,Object>> getLogInfoData(){
        return systemDao.getLogInfoData();
    }
}
