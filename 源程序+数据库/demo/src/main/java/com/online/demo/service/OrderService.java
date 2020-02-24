package com.online.demo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.online.demo.dao.OrderDao;
import com.online.demo.util.MapUtils;
import com.online.demo.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    OrderDao dao;

    public List<Map<String,Object>> getModelInfo(){
        return dao.getModelInfo();
    }

    public List<Map<String,Object>> getSupplierInfo(){
        return dao.getSupplierInfo();
    }

    public List<Map<String,Object>> getUserInfo(){
        return dao.getUserInfo();
    }

    public String addPurchaseInfo(Map<String,Object> param){
        param.put("id",UUIDUtils.getId());
        if(dao.addPurchaseInfo(param)>0){
            return "success";
        }else{
            return "fail";
        }
    }

    public String confirmOrderStatus(String id,String status){
        if(dao.confirmOrderStatus(id,status)>0)
            return "success";
        else
            return "fail";
    }

    public List<Map<String,Object>> getGoodsInfo(){
        return dao.getGoodsInfo();
    }

    public List<Map<String,Object>> getBusinessInfo(){
        return dao.getBusinessInfo();
    }

    public List<Map<String,Object>> getOrderSupplierInfo(String goodsId){
        return dao.getOrderSupplierInfo(goodsId);
    }

    public Integer getOrderNumById(String goodsId,String supId){
        Integer number1=dao.getNOrderNumber(goodsId,supId);
        Integer number2=dao.getYOrderNumber(goodsId,supId);
        return number1-number2;
    }

    public String addSaleInfo(Map<String,Object> param){
        param.put("id",UUIDUtils.getId());
        if(dao.addSaleInfo(param)>0){
            return "success";
        }else{
            return "fail";
        }
    }

    public Map<String,Object> getSaleInfo(Map<String,Object> param){
        String limit= MapUtils.getMapNullString(param,"limit");
        String page=MapUtils.getMapNullString(param,"page");
        PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(limit));
        List<Map<String,Object>> list = dao.getSaleInfo(param);
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<Map<String,Object>>(list);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("total",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    public String confirmSaleOrder(String id,String status){
        if(dao.confirmSaleOrder(id,status)>0){
            return "success";
        }else{
            return "fail";
        }
    }

    public List<Map<String,Object>> getRepertoryNumber(){
        return dao.getRepertoryNumber();
    }

    public List<Map<String,Object>> getRepertorySup(){
        return dao.getRepertorySup();
    }
}
