package com.sinosoft.newstandard.database.mybatis.interceptors;

import com.sinosoft.newstandard.common.util.snowflake.SnowflakeIdWorkerConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Configuration
@AutoConfigureAfter(SnowflakeIdWorkerConfiguration.class)
public class MybatisSupportConfiguration {

    @Bean
    @ConditionalOnProperty("snowflakeIdWorker.workerId")
    public PrepareInterceptor prepareInterceptor() {
        return new PrepareInterceptor();
    }

}
