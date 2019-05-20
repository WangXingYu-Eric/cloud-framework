package com.sinosoft.newstandard.securityservice.config.database;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.sinosoft.newstandard.database.druid.DruidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

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

    @Bean
    public ServletRegistrationBean statViewServlet() {
        return DruidUtils.setServletInitParameter(new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*"));
    }

}
