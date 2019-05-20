package com.sinosoft.newstandard.coreservice.database;


import com.sinosoft.newstandard.database.mybatis.interceptors.PrepareInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Configuration
@EnableTransactionManagement
@MapperScan("com.sinosoft.newstandard.coreservice.web.mapper")
public class MybatisConfiguration {

    @Autowired
    private PrepareInterceptor prepareInterceptor;

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        SqlSessionFactory sqlSessionFactory = factoryBean.getObject();
        if (prepareInterceptor != null) {
            sqlSessionFactory.getConfiguration().addInterceptor(prepareInterceptor);
        }
        return sqlSessionFactory;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
