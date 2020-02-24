package com.online.demo.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface TqComputerDao {

    @Select("select s.id,s.name,s.remark,s.create_time,u.realname from tq_supplier s , on_user u where u.id = s.user_id and s.is_del=0")
    List<Map<String,Object>> getSupplierInfo();

    @Insert("insert into tq_supplier(id,name,remark,create_time,user_id,is_del) " +
            "values(#{id},#{name},#{remark},now(),#{person},0)")
    int insertSupplierInfo(Map<String,Object> param);

    @Select("select id,realname from on_user")
    List<Map<String,Object>> getRealNameInfo();

    @Update("update tq_supplier set is_del=1 where id=#{id}")
    int delSupplierInfoById(@Param("id") String id);

    @Select("select id,name,remark,user_id as person from tq_supplier where id=#{id}")
    List<Map<String,Object>> getSupplierInfoById(@Param("id") String id);

    @Update("update tq_supplier set name=#{name},remark=#{remark}, user_id=#{person} where id=#{id}")
    int updateSupplierInfo(Map<String,Object> param);

    @Select("select id,name,remark,create_time,place from tq_business where is_del=0")
    List<Map<String,Object>> getBusinessinfo();

    @Insert("insert into tq_business(id,name,create_time,place,remark) values(#{id},#{name},now(),#{place},#{remark})")
    int addBusinessInfo(Map<String,Object> param);

    @Select("select id,name,create_time,remark,place from tq_business where id=#{id}")
    List<Map<String,Object>> getBusinessInfoById(@Param("id") String id);

    @Update("update tq_business set name=#{name},remark=#{remark},place=#{place} where id=#{id}")
    int updateBusinessInfo(Map<String,Object> param);

    @Update("update tq_business set is_del=1 where id=#{id}")
    int delBusinessInfo(@Param("id") String id);

    @Select("<script>select p.id,p.name,CONCAT(m.model_name,'/',g.name) as model_name,s.name as sup_name,p.number,p.price,p.start_time,p.end_time,u.realname " +
            "from tq_purchase p,tq_supplier s,tq_goods g,on_user u,tq_goods_model m where p.sup_id=s.id and p.goods_id=g.id and u.id=p.user_id " +
            "and m.id=g.model_id and is_accept=#{status} " +
            "<if test=\"content!=null and content!=''\">" +
            " and instr(p.name,#{content})>0" +
            "</if>" +
            "<if test=\"startT!=null and startT!=''\">" +
            " and start_time>STR_TO_DATE(#{startT}, '%Y-%m-%d %H:%i:%s')" +
            "</if>" +
            "<if test=\"endT!=null and endT!=''\">" +
            " and start_time&lt;STR_TO_DATE(#{endT}, '%Y-%m-%d %H:%i:%s')" +
            "</if>" +
            "</script>")
    List<Map<String,Object>> getPurchaseNO(Map<String,Object> param);

    @Insert("insert into tq_goods_model(id,model_name,create_time) values(#{id},#{model},now())")
    int addModelInfo(Map<String,Object> param);

    @Select("select id,model_name from tq_goods_model")
    List<Map<String,Object>> getModelInfo();

    @Insert("insert into tq_goods(id,name,model_id,mark) values(#{id},#{name},#{model_id},#{remark})")
    int addGoodsModel(Map<String,Object> param);

    @Select("select g.id,m.model_name,g.name,g.mark from tq_goods g,tq_goods_model m where g.model_id=m.id")
    List<Map<String,Object>> getModelGoods();

    @Select("select id,name,model_id,mark from tq_goods where id=#{id}")
    List<Map<String,Object>> getModelById(@Param("id") String id);

    @Update("update tq_goods set name=#{name},model_id=#{model_id},mark=#{mark} where id=#{id}")
    int updateModelById(Map<String,Object> param);

    @Delete("delete from tq_goods where id=#{id} ")
    int deleteModelById(@Param("id") String id);
}
