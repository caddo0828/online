package com.online.demo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.online.demo.config.logInfoContent;
import com.online.demo.dao.TqComputerDao;
import com.online.demo.util.MapUtils;
import com.online.demo.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class TqComputerService {

    @Autowired
    TqComputerDao dao;

    public String addSupplierInfo(Map<String,Object> param){
        String id = UUIDUtils.getId();
        param.put("id",id);
        if(dao.insertSupplierInfo(param)>0)
            return "success";
        else
            return "fail";
    }

    public List<Map<String,Object>> getRealNameInfo(){
        return dao.getRealNameInfo();
    }


    public Map<String,Object> getSupplierInfo(Map<String,Object> param){
        String limit= MapUtils.getMapNullString(param,"limit");
        String page=MapUtils.getMapNullString(param,"page");
        PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(limit));
        List<Map<String,Object>> list = dao.getSupplierInfo();
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<Map<String,Object>>(list);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("total",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    public String delSupplierInfoById(String id){
        if(dao.delSupplierInfoById(id)>0)
            return "success";
        else
            return "fail";
    }

    public Map<String,Object> getSupplierInfoById(String id){
        List<Map<String,Object>> list = dao.getSupplierInfoById(id);
        if(list.isEmpty())
            return new HashMap<String,Object>();
        else
            return list.get(0);
    };

    public String updateSupplierInfo(Map<String,Object> param){
        if(dao.updateSupplierInfo(param)>0)
            return "success";
        else
            return "fail";
    }

    public Map<String,Object> getBusinessInfo(Map<String,Object> param){
        String limit= MapUtils.getMapNullString(param,"limit");
        String page=MapUtils.getMapNullString(param,"page");
        Map<String,Object> map = new HashMap<String,Object>();
        PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(limit));
        List<Map<String,Object>> list = dao.getBusinessinfo();
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<Map<String,Object>>(list);
        map.put("total",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    public String addBusinessInfo(Map<String,Object> param){
        param.put("id",UUIDUtils.getId());
        if(dao.addBusinessInfo(param)>0)
            return "success";
        else
            return "fail";
    }


    public Map<String,Object> getBusinessInfoById(String id){
        List<Map<String,Object>> list = dao.getBusinessInfoById(id);
        if(list.isEmpty())
            return new HashMap<String,Object>();
        else
            return list.get(0);
    }

    public String updateBusinessInfo(Map<String,Object> param){
        if(dao.updateBusinessInfo(param)>0)
            return "success";
        else
            return "fail";
    }

    public String delBusinessInfo(String id){
        if(dao.delBusinessInfo(id)>0)
            return "success";
        else
            return "fail";
    }

    @logInfoContent(content = "查看订单")
    public Map<String,Object> getPurchaseNO(Map<String,Object> param){
        String limit= MapUtils.getMapNullString(param,"limit");
        String page=MapUtils.getMapNullString(param,"page");
        Map<String,Object> map = new HashMap<String,Object>();
        PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(limit));
        List<Map<String,Object>> list = dao.getPurchaseNO(param);
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<Map<String,Object>>(list);
        map.put("total",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }


    public String addModelInfo(Map<String,Object> param){
        param.put("id",UUIDUtils.getId());
        if(dao.addModelInfo(param)>0)
            return "success";
        else
            return "fail";

    }

    public List<Map<String,Object>> getModelInfo(){
        return dao.getModelInfo();
    }

    public String addGoodsModel(Map<String,Object> param){
        param.put("id",UUIDUtils.getId());
        if(dao.addGoodsModel(param)>0)
            return "success";
        else
            return "fail";
    }

    public Map<String,Object> getModelGoods(Map<String,Object> param){
        String limit= MapUtils.getMapNullString(param,"limit");
        String page=MapUtils.getMapNullString(param,"page");
        PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(limit));
        List<Map<String,Object>> list = dao.getModelGoods();
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<Map<String,Object>>(list);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("total",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    public Map<String,Object> getModelById(String id){
        List<Map<String,Object>> list=dao.getModelById(id);
        if(list.isEmpty())
            return new HashMap<String,Object>(0);
        else
            return list.get(0);
    }

    public String updateModelById(Map<String,Object> param){
        if(dao.updateModelById(param)>0)
            return "success";
        else
            return "fail";
    }

    public String deleteModelById(String id){
        if(dao.deleteModelById(id)>0)
            return "success";
        else
            return "fail";
    }

}
