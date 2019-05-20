package com.sinosoft.newstandard.common.util.snowflake;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * @author Eric
 */
@Configuration
@ConditionalOnProperty("snowflakeIdWorker.workerId")
public class SnowflakeIdWorkerConfiguration {

    @Resource
    private Environment environment;

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker() {
        long workerId = environment.getProperty("snowflakeIdWorker.workerId", Long.class);
        long dataCenterId = environment.getProperty("snowflakeIdWorker.dataCenterId", Long.class);
        return new SnowflakeIdWorker(workerId, dataCenterId);
    }

}
