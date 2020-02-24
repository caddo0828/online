package com.online.demo.service;

import com.online.demo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public int udpateUserInfoById(Map<String,Object> map){
        return userDao.udpateUserInfoById(map);
    }

    public int verfiyPasswdById(Map<String,Object> param){
        return userDao.verfiyPasswdById(param);
    }

    public int updateUserPassWordById(Map<String,Object> param){
        return userDao.updatePassWordById(param);
    }

}
