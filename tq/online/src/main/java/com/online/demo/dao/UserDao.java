package com.online.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

@Mapper
public interface UserDao {

    @Update("update on_user set gender=#{gender},username=#{username},realname=#{realname},birthday=STR_TO_DATE(#{birth},'%Y-%m-%d') where id=#{id}")
    int udpateUserInfoById(Map<String,Object> map);

    @Select("select count(*) from on_user where id=#{id} and password=#{old_pwd}")
    int verfiyPasswdById(Map<String,Object> map);

    @Update("update on_user set password=#{new_pwd1} where id=#{id}")
    int updatePassWordById(Map<String,Object> map);

}
