package com.sinosoft.newstandard.database.druid.multipledatasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class DataSourceContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<String> contextHolder = new InheritableThreadLocal<>();

    /**
     * 设置数据源.
     */
    public static void setDataSource(String dataSourceKey) {
        logger.debug("DataSourceContextHolder.setDataSource() -> threadId:{} -> dataSource:{}", Thread.currentThread().getId(), dataSourceKey);
        contextHolder.set(dataSourceKey);
    }

    /**
     * 取得当前数据源.
     */
    public static String getDataSource() {
        String dataSourceKey = contextHolder.get();
        logger.debug("DataSourceContextHolder.getDataSource() -> threadId:{} -> dataSource:{}", Thread.currentThread().getId(), dataSourceKey);
        return dataSourceKey;
    }

    /**
     * 清除上下文数据.
     */
    public static void clear() {
        logger.debug("DataSourceContextHolder.clear() -> threadId:{}", Thread.currentThread().getId());
        contextHolder.remove();
    }

}
