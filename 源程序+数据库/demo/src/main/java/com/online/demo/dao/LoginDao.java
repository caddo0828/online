package com.online.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface LoginDao {
    @Select("select * from on_user where email=#{email} and password=#{password}")
    List<Map<String,Object>> getUserInfo(@Param("email") String email, @Param("password") String password);

    @Update("update on_user set image=#{image} where email=#{email}")
    boolean updateImageName(@Param("image") String newName,@Param("email")String email);

    @Select("select p.t_p_id,p.t_p_name,s.t_s_id,s.t_s_name,s.t_s_url from on_template_p p " +
            "left join on_template_s s on s.t_parent_id=p.t_p_id " +
            "left join on_role_template t on t.template_id=s.t_s_id where role_id=#{role_id} and s.is_del=0")
    List<Map<String,Object>> getModulesSon(@Param("role_id") String role_id);
}
