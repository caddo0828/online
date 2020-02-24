package com.online.demo.controller;

import com.online.demo.service.TqComputerService;
import com.online.demo.util.ToMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@Controller
@RequestMapping("/tq")
public class TqComputerController {

    @Autowired
    TqComputerService service;

    /**
     * 跳转页面
     * @param request
     * @return
     */
    @RequestMapping(value="/supplier")
    public String toSupplierPage(HttpServletRequest request){
        request.setAttribute("root",request.getContextPath());
        return "service/tq/supplier";
    }

    /**
     * 跳转页面
     * @return
     */
    @RequestMapping(value="/addSupplierPage")
    public String addSupplierPage(HttpServletRequest request){
        request.setAttribute("root",request.getContextPath());
        return "service/tq/addSupplier";
    }

    /**
     * 添加供应商基本信息
     * @param request
     * @return
     */
    @RequestMapping(value="/addSupplierInfo")
    @ResponseBody
    public String addSupplierInfo(HttpServletRequest request){
        Map<String,Object> param= ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");
        System.out.println(param);
        return service.addSupplierInfo(param);
    }

    @ResponseBody
    @RequestMapping(value = "/getRealNameInfo")
    public Callable<List<Map<String,Object>>> getRealNameInfo(){
        return () -> service.getRealNameInfo();
    }

    @ResponseBody
    @RequestMapping(value = "/getSupplierInfo")
    public Callable<Map<String,Object>> getSupplierInfo(HttpServletRequest request){
        return () -> {
            Map<String,Object> param = ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");
            return service.getSupplierInfo(param);
        };
    }

    @RequestMapping(value = "/delSupplierInfo")
    @ResponseBody
    public String delSupplierInfoById(String id){
        return service.delSupplierInfoById(id);
    }


    @RequestMapping(value="/updateSupplier")
    public String toUpdateSupplierPage(HttpServletRequest request){
        request.setAttribute("id",request.getParameter("id"));
        request.setAttribute("root",request.getContextPath());
        return "service/tq/updateSupplier";
    }


    @RequestMapping(value = "/getSupplierInfoById")
    @ResponseBody
    public Callable<Map<String,Object>> getSupplierInfo(String id){
        return () -> {
            return service.getSupplierInfoById(id);
        };
    }

    @RequestMapping(value = "/updateSupplierInfo")
    @ResponseBody
    public String updateSupplierInfo(HttpServletRequest request){
        Map<String,Object> param=ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");
        return service.updateSupplierInfo(param);
    }

    @RequestMapping(value = "/businessInfo")
    public String toBusinessPage(HttpServletRequest request){
        request.setAttribute("root",request.getContextPath());
        return "service/tq/businessInfo";
    }

    @RequestMapping(value = "/getBusinessInfo")
    @ResponseBody
    public Callable<Map<String,Object>> getBusinessInfo(HttpServletRequest request){
        return ()->{
            Map<String,Object> param=ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");
            return service.getBusinessInfo(param);
        };
    }

    @RequestMapping(value = "/addBusinessPage")
    public String addBusinessPage(HttpServletRequest request){
        request.setAttribute("root",request.getContextPath());
        return "service/tq/addBusiness";
    }

    @RequestMapping(value = "/addBusinessInfo")
    @ResponseBody
    public String addBusinessInfo(HttpServletRequest request){
        Map<String,Object> param=ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");
        return service.addBusinessInfo(param);
    }

    @RequestMapping(value = "/updateBusinessPage")
    public String updateBusinessPage(HttpServletRequest request){
        request.setAttribute("id",request.getParameter("id"));
        request.setAttribute("root",request.getContextPath());
        return "service/tq/updateBusiness";
    }


    @RequestMapping(value = "/getBusinessInfoById")
    @ResponseBody
    public Map<String,Object> getBusinessInfoById(String id){
        return service.getBusinessInfoById(id);
    }

    @RequestMapping(value = "/updateBusinessInfo")
    @ResponseBody
    public String updateBusinessInfo(HttpServletRequest request){
        Map<String,Object> param=ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");
        return service.updateBusinessInfo(param);
    }

    @RequestMapping(value = "/delBusinessInfo")
    @ResponseBody
    public String delBusinessInfo(String id){
        return service.delBusinessInfo(id);
    }


    @RequestMapping(value = "/goods")
    public String toGoodsInfoPage(HttpServletRequest request){
        request.setAttribute("root",request.getContextPath());
        return "service/tq/goods";
    }

    @RequestMapping(value = "/purchase")
    public String toPurchasePage(HttpServletRequest request){
        request.setAttribute("root",request.getContextPath());
        return "service/tq/purchase";
    }


    @RequestMapping(value = "/getPurchaseN")
    @ResponseBody
    public Map<String,Object> getPurchaseNO(HttpServletRequest request){
        Map<String,Object> param = ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");
        return service.getPurchaseNO(param);
    }

    @RequestMapping(value = "/addPurchasePage")
    public String addPurchasePage(HttpServletRequest request){
        request.setAttribute("root",request.getContextPath());
        request.setAttribute("id",((Map)request.getSession().getAttribute("user")).get("id"));
        return "service/tq/addPurchase";
    }

    @RequestMapping(value = "/modelInfo")
    public String modelInfoPage(HttpServletRequest request){
        request.setAttribute("root",request.getContextPath());
        return "service/tq/model";
    }

    @RequestMapping(value = "/addModelPage")
    public String addModelPage(HttpServletRequest request){
        request.setAttribute("root",request.getContextPath());
        return "service/tq/addModel";
    }

    @RequestMapping(value = "/addModelInfo")
    @ResponseBody
    public String addModelInfo(HttpServletRequest request){
        Map<String,Object> param=ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");
        return service.addModelInfo(param);
    }

    @RequestMapping(value = "/addGoodsModel")
    @ResponseBody
    public String addGoodsModel(HttpServletRequest request){
        Map<String,Object> param=ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");
        return service.addGoodsModel(param);
    }


    @RequestMapping(value = "/getModelInfo")
    @ResponseBody
    public List<Map<String,Object>> getModelInfo(){
        return service.getModelInfo();
    }


    @RequestMapping(value = "/getModelGoods")
    @ResponseBody
    public Map<String,Object> getModelGoods(HttpServletRequest request){
        Map<String,Object> param=ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");

        return service.getModelGoods(param);
    }

    @RequestMapping(value = "/updateModelPage")
    public String updateModelPage(HttpServletRequest request){
        request.setAttribute("root",request.getContextPath());
        request.setAttribute("id",request.getParameter("id"));
        return "service/tq/updateModel";
    }

    @RequestMapping(value = "/getModelById")
    @ResponseBody
    public Map<String,Object> getModelById(String id){
        return service.getModelById(id);
    }

    @RequestMapping(value = "/updateModelById")
    @ResponseBody
    public String updateModelById(HttpServletRequest request){
        Map<String,Object> map = ToMapUtils.mapStringArrayToObject(request.getParameterMap(),",");
        return service.updateModelById(map);
    }

    @RequestMapping(value = "/deleteModelById")
    @ResponseBody
    public String deleteModelById(String id){
        return service.deleteModelById(id);
    }

}
