package com.sinosoft.newstandard.database.druid.multipledatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class MultipleDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }

}
