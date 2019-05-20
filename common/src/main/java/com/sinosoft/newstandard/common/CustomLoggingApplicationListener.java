package com.sinosoft.newstandard.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 扩展log4j2初始化监听,为%X添加信息
 *
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class CustomLoggingApplicationListener extends LoggingApplicationListener {

    protected void initialize(ConfigurableEnvironment environment, ClassLoader classLoader) {
        // 为日志提供自定义信息
        String ip = environment.getProperty("spring.cloud.client.ip-address");
        String appPort = environment.getProperty("server.port");
        String appName = environment.getProperty("spring.application.name");
        if (!StringUtils.isEmpty(ip)) {
            System.setProperty("IP", StringUtils.isNotEmpty(ip) ? ip : "????");
        }
        if (!StringUtils.isEmpty(appPort)) {
            System.setProperty("APPPORT", StringUtils.isNotEmpty(appPort) ? appPort : "????");
        }
        if (!StringUtils.isEmpty(appName)) {
            System.setProperty("APPNAME", StringUtils.isNotEmpty(appName) ? appName : "????");
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 21;
    }

}
