package com.online.demo.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderDao {

    @Select("select g.id,CONCAT(m.model_name,'/',g.name) as name from tq_goods g,tq_goods_model m where g.model_id=m.id")
    List<Map<String,Object>> getModelInfo();

    @Select("select id,name from tq_supplier where is_del=0")
    List<Map<String,Object>> getSupplierInfo();

    @Select("select id,realname from on_user")
    List<Map<String,Object>> getUserInfo();

    @Insert("insert into tq_purchase(id,name,sup_id,goods_id,start_time,end_time,number,price,user_id,is_accept) " +
            "values(#{id},#{name},#{sup_id},#{model_id},now(),null,#{number},#{price},#{user_id},0)")
    int addPurchaseInfo(Map<String,Object> param);

    @Update("update tq_purchase set is_accept=#{status},end_time=NOW() where id=#{id}")
    int confirmOrderStatus(@Param("id") String id,@Param("status") String status);

    @Select("select a.id,a.name,(ifnull(a.n,0)-ifnull(b.y,0)) as 'number' from " +
            "(select g.id,g.name,sum(p.number) as 'n' " +
            "from " +
            "(select g.id,CONCAT(m.model_name,'/',g.name) as name from tq_goods g,tq_goods_model m where g.model_id=m.id) g " +
            "left join tq_purchase p on g.id=p.goods_id where p.is_accept=1 group by g.id,g.name) a " +
            "left join " +
            "(select g.id,g.name,sum(s.number) as 'y' " +
            "from (select g.id,CONCAT(m.model_name,'/',g.name) as name from tq_goods g,tq_goods_model m where g.model_id=m.id) g " +
            "left join tq_sell s on g.id=s.goods_id where s.is_accept in (0,1) group by g.id,g.name) b on a.id=b.id " +
            "where (ifnull(a.n,0)-ifnull(b.y,0))>0")
    List<Map<String,Object>> getGoodsInfo();

    @Select("select id,name from tq_business where is_del=0")
    List<Map<String,Object>> getBusinessInfo();

    @Select("select a.id,a.name,(ifnull(a.number,0)-ifnull(b.number,0)) as 'number' from " +
            "(select p.number,s.id,s.name from tq_purchase p left join tq_supplier s on p.sup_id=s.id where p.goods_id=#{goods_id} and p.is_accept=1) a " +
            "left join  " +
            "(select sum(p.number) as number,s.id,s.name from tq_sell p left join tq_supplier s on p.sup_id=s.id where p.goods_id=#{goods_id} and p.is_accept in (0,1) group by s.id,s.name) b " +
            "on a.id=b.id where (ifnull(a.number,0)-ifnull(b.number,0))>0")
    List<Map<String,Object>> getOrderSupplierInfo(@Param("goods_id") String goodsId);

    @Select("select ifnull(sum(ifnull(number,0)),0) as 'number' from tq_purchase where sup_id=#{supId} and goods_id=#{goodsId} and is_accept=1")
    Integer getNOrderNumber(@Param("goodsId") String goodsId,@Param("supId") String supId);

    @Select("select ifnull(sum(ifnull(number,0)),0) as 'number' from tq_sell where sup_id=#{supId} and goods_id=#{goodsId} and is_accept in (0,1)")
    Integer getYOrderNumber(@Param("goodsId") String goodsId,@Param("supId") String supId);

    @Insert("insert into tq_sell(id,business_id,goods_id,sup_id,number,price,user_id,start_time,end_time,is_accept) " +
            "values(#{id},#{business_id},#{good_id},#{sup_id},#{number},#{price},#{user_id},now(),null,0) ")
    Integer addSaleInfo(Map<String,Object> param);

    @Select("<script>select s.id,b.name as business_name,g.name as model_name,p.name as sup_name,s.number,s.price,u.realname,s.start_time,s.end_time " +
            "from tq_sell s,tq_business b," +
            "(select g.id,CONCAT(m.model_name,'/',g.name) as name from tq_goods g,tq_goods_model m where g.model_id=m.id) g,tq_supplier p ,on_user u " +
            "where s.business_id=b.id and s.goods_id=g.id and s.sup_id=p.id and s.user_id=u.id " +
            "and is_accept=#{status}" +
            "<if test=\"startT!=null and startT!=''\">" +
            " and s.start_time>STR_TO_DATE(#{startT}, '%Y-%m-%d %H:%i:%s')" +
            "</if>" +
            "<if test=\"endT!=null and endT!=''\">" +
            " and s.start_time&lt;STR_TO_DATE(#{endT}, '%Y-%m-%d %H:%i:%s')" +
            "</if>" +
            "</script>")
    List<Map<String,Object>> getSaleInfo(Map<String,Object> param);

    @Update("update tq_sell set is_accept=#{status} where id=#{id}")
    Integer confirmSaleOrder(@Param("id") String id,@Param("status") String status);

    @Select("select a.id,a.name,(ifnull(a.number,0)-ifnull(b.number,0)) as 'value' from (\n" +
            "select g.id,g.name,sum(p.number) as number from tq_purchase p,(select g.id,CONCAT(m.model_name,'/',g.name) as name \n" +
            "from tq_goods g,tq_goods_model m where g.model_id=m.id) g where g.id=p.goods_id and p.is_accept=1 group by g.id,g.name\n" +
            ") a left join \n" +
            "(\n" +
            "select g.id,g.name,sum(s.number) as number from tq_sell s,(select g.id,CONCAT(m.model_name,'/',g.name) as name \n" +
            "from tq_goods g,tq_goods_model m where g.model_id=m.id) g where g.id=s.goods_id and s.is_accept in(0,1) group by g.id,g.name\n" +
            ") b on a.id=b.id")
    List<Map<String,Object>> getRepertoryNumber();

    @Select("select a.id,a.name,(ifnull(a.number,0)-ifnull(b.number,0)) as 'value' from (\n" +
            "select s.id,s.name,sum(p.number) as number from tq_purchase p,tq_supplier s where s.id=p.sup_id and p.is_accept=1 group by s.id,s.name\n" +
            ") a left join \n" +
            "(\n" +
            "select g.id,g.name,sum(s.number) as number from tq_sell s,tq_supplier g where g.id=s.sup_id and s.is_accept in(0,1) group by g.id,g.name\n" +
            ") b on a.id=b.id")
    List<Map<String,Object>> getRepertorySup();
}
