package com.online.demo.util;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class DataSourceTemplate {
    private DataSource dataSource;
    private SqlSessionFactoryBean sqlSessionFactoryBean;
    private SqlSessionTemplate sqlSessionTemplate;
    private JdbcTemplate jdbcTemplate;

    public DataSourceTemplate(SqlSessionFactoryBean bean,DataSource dataSource) throws Exception {
        this.dataSource=dataSource;
        this.sqlSessionFactoryBean=bean;
        this.jdbcTemplate=new JdbcTemplate(this.dataSource);
        this.sqlSessionTemplate=new SqlSessionTemplate(this.sqlSessionFactoryBean.getObject());
    }

    @Override
    public String toString() {
        return "DataSourceTemplate{" +
                "dataSource=" + dataSource +
                ", sqlSessionFactoryBean=" + sqlSessionFactoryBean +
                ", sqlSessionTemplate=" + sqlSessionTemplate +
                ", jdbcTemplate=" + jdbcTemplate +
                '}';
    }

    //**********SqlSessionTemplate************//

    public <T> List<T> selectList(String statement){
        return sqlSessionTemplate.selectList(statement);
    }

    //**********JdbcTemplate***********//

    public List<Map<String,Object>> queryForList(String sql,Object[] obj){
        return jdbcTemplate.queryForList(sql,obj);
    }
    public List<Map<String,Object>> queryForList(String sql){
        return jdbcTemplate.queryForList(sql);
    }
    public int [] batchUpdate(String sql,List<Object[]> args){
        return jdbcTemplate.batchUpdate(sql,args);
    }
    public int update(String sql,Object[] obj){
        return jdbcTemplate.update(sql,obj);
    }
}
