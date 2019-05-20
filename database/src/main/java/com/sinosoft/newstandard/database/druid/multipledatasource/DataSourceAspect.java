package com.sinosoft.newstandard.database.druid.multipledatasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Aspect
@Component
@Order(0) //确保比管理事物的切面先加载,防止数据源不能切换.
public class DataSourceAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@within(com.sinosoft.newstandard.database.druid.multipledatasource.DataSource) || @annotation(com.sinosoft.newstandard.database.druid.multipledatasource.DataSource)")
    public void pointCut() {
    }

    @Around("pointCut() && @target(dataSource)")
    public Object around(ProceedingJoinPoint invocation, DataSource dataSource) throws Throwable {
        String dataSourceKey = dataSource.value();
        logger.debug("选择数据源---" + dataSourceKey);
        DataSourceContextHolder.setDataSource(dataSourceKey);
        Object result;
        try {
            result = invocation.proceed();
        } finally {
            DataSourceContextHolder.clear();
        }
        return result;
    }

}
