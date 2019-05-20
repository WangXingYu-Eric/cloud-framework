package com.sinosoft.newstandard.database.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.core.env.Environment;

import java.sql.SQLException;
import java.util.Properties;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class DruidUtils {

    /**
     * 设置Druid数据源属性
     */
    public static DruidDataSource setDruidProperties(Environment environment, DruidDataSource dataSource) {
        //连接池初始化大小、最小和最大值
        dataSource.setInitialSize(environment.getProperty("spring.datasource.druid.initialSize", Integer.class));
        dataSource.setMinIdle(environment.getProperty("spring.datasource.druid.minIdle", Integer.class));
        dataSource.setMaxActive(environment.getProperty("spring.datasource.druid.maxActive", Integer.class));

        //获取连接等待超时的时间
        dataSource.setMaxWait(environment.getProperty("spring.datasource.druid.maxWait", Long.class));
        //间隔多久才进行一次检测,检测需要关闭的空闲连接
        dataSource.setTimeBetweenEvictionRunsMillis(environment.getProperty("spring.datasource.druid.timeBetweenEvictionRunsMillis", Long.class));
        //连接在池中最小生存的时间
        dataSource.setMinEvictableIdleTimeMillis(environment.getProperty("spring.datasource.druid.minEvictableIdleTimeMillis", Long.class));

        String validationQuery = environment.getProperty("spring.datasource.druid.validationQuery");
        if (validationQuery != null && !"".equals(validationQuery)) {
            dataSource.setValidationQuery(validationQuery);
        }
        dataSource.setTestWhileIdle(Boolean.parseBoolean(environment.getProperty("spring.datasource.druid.testWhileIdle")));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(environment.getProperty("spring.datasource.druid.testOnBorrow")));
        dataSource.setTestOnReturn(Boolean.parseBoolean(environment.getProperty("spring.datasource.druid.testOnReturn")));

        boolean opened = Boolean.parseBoolean(environment.getProperty("spring.datasource.druid.poolPreparedStatements"));
        if (opened) {
            //打开PSCache,并且指定每个连接上PSCache的大小
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(environment.getProperty("spring.datasource.druid.maxPoolPreparedStatementPerConnectionSize", Integer.class));
        }

        //监控统计拦截的filters
        try {
            dataSource.setFilters(environment.getProperty("spring.datasource.druid.filters"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //通过connectProperties属性来打开mergeSql功能,慢SQL记录
        String connectionPropertiesStr = environment.getProperty("spring.datasource.druid.connectionProperties");
        if (connectionPropertiesStr != null && !"".equals(connectionPropertiesStr)) {
            Properties connectProperties = new Properties();
            String[] propertiesList = connectionPropertiesStr.split(";");
            for (String propertiesTmp : propertiesList) {
                String[] obj = propertiesTmp.split("=");
                String key = obj[0];
                String value = obj[1];
                connectProperties.put(key, value);
            }
            dataSource.setConnectProperties(connectProperties);
        }
        //合并多个DruidDataSource的监控数据
        dataSource.setUseGlobalDataSourceStat(Boolean.parseBoolean(environment.getProperty("spring.datasource.druid.useGlobalDataSourceStat")));
        return dataSource;
    }

    /**
     * 设置Druid监控Servlet的参数
     */
    public static ServletRegistrationBean setServletInitParameter(ServletRegistrationBean<StatViewServlet> servletRegistrationBean) {
        // IP白名单
        servletRegistrationBean.addInitParameter(StatViewServlet.PARAM_NAME_ALLOW, "192.168.74.1");
        // IP黑名单(共同存在时,deny优先于allow)
        servletRegistrationBean.addInitParameter(StatViewServlet.PARAM_NAME_DENY, "");
        //控制台管理用户
        servletRegistrationBean.addInitParameter(StatViewServlet.PARAM_NAME_USERNAME, "admin");
        servletRegistrationBean.addInitParameter(StatViewServlet.PARAM_NAME_PASSWORD, "admin");
        //是否能够重置数据
        servletRegistrationBean.addInitParameter(StatViewServlet.PARAM_NAME_RESET_ENABLE, "false");
        return servletRegistrationBean;
    }

}
