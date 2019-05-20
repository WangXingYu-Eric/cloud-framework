package com.sinosoft.newstandard.database.druid.multipledatasource;

import java.lang.annotation.*;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DataSource {

    /**
     * 用于区分数据源的字段,与数据源实体Bean同名即可.
     */
    String value();

}
