package com.online.demo.controller;

import com.online.demo.util.DataSourceTemplate;
import com.online.demo.util.UUIDUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class DemoController {
    @Resource(name = "mysqlDataSourceTemplate")
    private DataSourceTemplate mysqlTemplate;

    @Resource(name = "mysqlOnDataSourceTemplate")
    private DataSourceTemplate mysqlonTemplate;

    @GetMapping("/getMysqlTestData")
    public List<Map<String,Object>> getMysqlTestData(){
        String sql="select * from v_user";
        List<Map<String,Object>> list=mysqlTemplate.queryForList(sql);
        List<Object[]> insert =new ArrayList<>();
        for (Map<String,Object> temp : list){
            insert.add(new Object[]{UUIDUtils.getId(),temp.get("username"),temp.get("upwd"),temp.get("email")});
        }
        sql="insert into on_user(id,username,password,email,role_id) values(?,?,?,?,1)";
        mysqlonTemplate.batchUpdate(sql,insert);
        return list;
    }

    @GetMapping("/getMysqlTestTemplate")
    public List<Map<String,Object>> getMapperTestTemplate(){
        String sql="select * from on_user";
        List<Map<String,Object>> list=mysqlonTemplate.queryForList(sql);
        return list;
    }



}
