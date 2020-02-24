package com.online.demo.controller;

import com.online.demo.service.OrderService;
import com.online.demo.util.ToMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    OrderService service;

    @RequestMapping(value = "/getModelInfo")
    @ResponseBody
    public List<Map<String,Object>> getModelInfo(){
        return service.getModelInfo();
    }

    @RequestMapping(value = "/getSupplierInfo")
    @ResponseBody
    public List<Map<String,Object>> getSupplierInfo(){
        return service.getSupplierInfo();
    }

    @RequestMapping(value = "/getUserInfo")
    @ResponseBody
    public List<Map<String,Object>> getUserInfo(){
        return service.getUserInfo();
    }

    @RequestMapping(value = "/addPurchaseInfo")
    @ResponseBody
    public String addPurchaseInfo(HttpServletRequest request){
        Map<String,Object> map = ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");

        return service.addPurchaseInfo(map);
    }

    @RequestMapping(value = "/confirmOrder")
    @ResponseBody
    public String confirmOrderStatus(String id,String status){
        return service.confirmOrderStatus(id,status);
    }

    @RequestMapping(value = "/sales")
    public String toSalePage(HttpServletRequest request){
        request.setAttribute("root",request.getContextPath());
        return "service/order/saleInfo";
    }

    @RequestMapping(value = "/addSalePage")
    public String addSalePage(HttpServletRequest request){
        request.setAttribute("root",request.getContextPath());
        request.setAttribute("id",((Map)request.getSession().getAttribute("user")).get("id"));
        return "service/order/addSale";
    }

    @RequestMapping(value = "/getGoodsInfo")
    @ResponseBody
    public List<Map<String,Object>> getGoodsInfo(){
        return service.getGoodsInfo();
    }

    @RequestMapping(value = "/getBusinessInfo")
    @ResponseBody
    public List<Map<String,Object>> getBusinessInfo(){
        return service.getBusinessInfo();
    }

    @RequestMapping(value = "/getOrderSupplierInfo")
    @ResponseBody
    public List<Map<String,Object>> getOrderSupplierInfo(String goodsId){
        return service.getOrderSupplierInfo(goodsId);
    }

    @RequestMapping(value = "/getOrderNumById")
    @ResponseBody
    public String getOrderNumById(String goodsId,String supId){
        return service.getOrderNumById(goodsId,supId)+"";
    }

    @RequestMapping(value = "/addSaleInfo")
    @ResponseBody
    public String addSaleInfo(HttpServletRequest request){
        Map<String,Object> map = ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");

        return service.addSaleInfo(map);
    }

    @RequestMapping(value = "/getSaleInfoN")
    @ResponseBody
    public Map<String,Object> getSaleInfoN(HttpServletRequest request){
        Map<String,Object> param = ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");
        return service.getSaleInfo(param);
    }

    @RequestMapping(value = "/confirmSaleOrder")
    @ResponseBody
    public String confirmSaleOrder(String id,String status){
        return service.confirmSaleOrder(id,status);
    }

    @RequestMapping(value = "/getRepertoryNumber")
    @ResponseBody
    public List<Map<String,Object>> getRepertoryNumber(){
        return service.getRepertoryNumber();
    }

    @RequestMapping(value = "/getRepertorySup")
    @ResponseBody
    public List<Map<String,Object>> getRepertorySup(){
        return service.getRepertorySup();
    }

}
