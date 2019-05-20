package com.sinosoft.newstandard.coreservice.database;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.sinosoft.newstandard.database.druid.DruidUtils;
import com.sinosoft.newstandard.database.druid.multipledatasource.MultipleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Configuration
public class DruidConfiguration {

    @Autowired
    private Environment environment;

    @Bean(name = "base")
    @ConfigurationProperties(prefix = "spring.datasource.druid.base")
    public DataSource base() {
        return DruidUtils.setDruidProperties(environment, DruidDataSourceBuilder.create().build());
    }

    @Bean(name = "other")
    @ConfigurationProperties(prefix = "spring.datasource.druid.other")
    public DataSource other() {
        return DruidUtils.setDruidProperties(environment, DruidDataSourceBuilder.create().build());
    }

    /**
     * 动态数据源配置.
     */
    @Bean
    @Primary
    public DataSource multipleDataSource() {
        MultipleDataSource multipleDataSource = new MultipleDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("base", base());
        targetDataSources.put("other", other());
        //添加数据源
        multipleDataSource.setTargetDataSources(targetDataSources);
        //设置默认数据源
        multipleDataSource.setDefaultTargetDataSource(base());
        return multipleDataSource;
    }

    @Bean
    public ServletRegistrationBean statViewServlet() {
        return DruidUtils.setServletInitParameter(new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*"));
    }

}
