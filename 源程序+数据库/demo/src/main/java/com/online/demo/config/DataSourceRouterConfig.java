package com.online.demo.config;

import com.online.demo.util.DataSourceTemplate;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;

@Configuration
public class DataSourceRouterConfig {


    @Bean(name="mysqlProperties")
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSourceProperties mysqlDataSource(){
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name="mysqlonProperties")
    @ConfigurationProperties(prefix = "spring.datasource.mysqlon")
    public DataSourceProperties mysqlOnDataSource(){
        return new DataSourceProperties();
    }


    @Bean(name = "mysqlDataSourceTemplate")
    public DataSourceTemplate mysqlTemplateData(@Qualifier("mysqlProperties") DataSourceProperties dataSourceProperties,@Value("${mybatis.mapper-locations}") String mapperLocations,@Value("${mybatis.config-location}") String configPath) throws Exception {
        return createDatSourceTemplate(dataSourceProperties,configPath,mapperLocations);
    }

    @Primary
    @Bean(name = "mysqlOnDataSourceTemplate")
    public DataSourceTemplate mysqlOnTemplateData(@Qualifier("mysqlonProperties") DataSourceProperties dataSourceProperties,@Value("${mybatis.mapper-locations}") String mapperLocations,@Value("${mybatis.config-location}") String configPath) throws Exception {
        return createDatSourceTemplate(dataSourceProperties,configPath,mapperLocations);
    }

    private DataSourceTemplate createDatSourceTemplate(DataSourceProperties properties,String config,String mappers) throws Exception {
        SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
        bean.setDataSource(properties.initializeDataSourceBuilder().build());
        bean.setConfigLocation(new ClassPathResource(config.replace("classpath:","")));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mappers));
        return new DataSourceTemplate(bean,properties.initializeDataSourceBuilder().build());
    }

}
