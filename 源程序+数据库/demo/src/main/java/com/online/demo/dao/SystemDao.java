package com.online.demo.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SystemDao {

    @Select("select p.t_p_id,p.t_p_name,s.t_s_id,s.t_s_name,s.t_s_url,t_parent_id from on_template_p p " +
            "left join (select * from on_template_s where is_del=0) s on p.t_p_id=s.t_parent_id" +
            " where p.is_del=0")
    List<Map<String,Object>> getAllModulesInfo();


    @Select("select t_p_id,t_p_name from on_template_p where is_del=0 ")
    List<Map<String,Object>> getParentModules();

    @Insert("insert into on_template_p(t_p_name,is_del) values(#{name},0)")
    boolean addParentModules(@Param("name") String name);

    @Insert("insert into on_template_s(t_s_name,t_s_url,t_parent_id,is_del) values(#{name},#{url},#{p_id},0)")
    boolean addSonModules(@Param("name") String name,@Param("url") String url,@Param("p_id") Integer parent_id);

    @Update("update on_template_s set is_del=1 where t_s_id=#{s_id}")
    boolean deleteSonModules(@Param("s_id") String id);

    @Update("update on_template_p set is_del=1 where t_p_id=#{p_id}")
    boolean deleteParentModules(@Param("p_id") String id);

    @Update("update on_template_p set t_p_name=#{p_name} where t_p_id=#{id}")
    boolean updateParentModule(@Param("id") String id,@Param("p_name") String name);

    @Update("update on_template_s set t_s_name=#{s_name},t_parent_id=#{parent_id},t_s_url=#{url} where t_s_id=#{id}")
    boolean updateSonModule(@Param("id") String id,@Param("s_name") String name,@Param("parent_id") String parent_id,@Param("url") String url);

    @Select("select r.role_id,r.role_name,p.id from on_role r left join (select * from on_role_template where template_id=#{id}) p on r.role_id=p.role_id")
    List<Map<String,Object>> getRoleTemplates(@Param("id") String id);

    @Delete("delete from on_role_template where template_id=#{id}")
    boolean delRolsTemplate(@Param("id") String id);

    @Select("select r.role_id,r.role_name,date_format(r.create_time,'%Y-%m-%d %H:%i:%s') as create_time,case when p.num is null then 0 else p.num end as num,r.is_run as status " +
            "from on_role r left join (select role_id,count(*) as num from on_user group by role_id) p " +
            " on p.role_id=r.role_id")
    List<Map<String,Object>> getRolesInfo();

    @Insert("insert into on_role(role_name,create_time) values(#{name},now())")
    boolean addRoleName(@Param("name") String name);

    @Delete("delete from on_role where role_id=#{id} ")
    boolean delRoleNameById(@Param("id")String id);

    @Update("update on_role set role_name=#{name} where role_id=#{id}")
    boolean updateRoleName(@Param("name") String name,@Param("id") String id);

    @Update("update on_role set is_run=#{status} where role_id=#{id}")
    boolean updateRoleStatus(@Param("status")Integer status,@Param("id") String id);

    @Select("select u.id,u.username,u.email,r.role_id,r.role_name,u.image,date_format(u.create_time,'%Y-%m-%d %H:%i:%s') as create_time " +
            "from on_user u left join on_role r on u.role_id=r.role_id ")
    List<Map<String,Object>> getUserInfoData();

    @Insert("insert into on_user(id,username,password,email,role_id,image,create_time) " +
            "values(#{id},#{username},#{password},#{email},#{role},'default.png',now())")
    int insertUserInfo(Map<String,Object> param);

    @Delete("delete from on_user where id=#{id}")
    int delUserInfo(@Param("id")String id);

    @Select("select id,username,email,role_id,image,create_time,gender,realname,DATE_FORMAT(birthday,'%Y-%m-%d') as birth " +
            "from on_user where id=#{id}")
    Map<String,Object> getUserInfoById(@Param("id")String id);

    @Update("update on_user set password=#{password},role_id=#{role},username=#{username} where id=#{id}")
    int updateUserInfo(Map<String,Object> param);

    @Select("select u.image,u.username,l.email,l.content,date_format(l.time,'%Y-%m-%d %H:%i:%s') as time," +
            "l.ip from on_log l left join on_user u on l.email=u.email order by l.time desc ")
    List<Map<String,Object>> getLogInfoData();
}
