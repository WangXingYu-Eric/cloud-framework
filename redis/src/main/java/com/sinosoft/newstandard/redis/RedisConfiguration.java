package com.sinosoft.newstandard.redis;

import com.sinosoft.newstandard.common.util.executor.ExecutorBuilder;
import com.sinosoft.newstandard.redis.operate.RedisHandler;
import com.sinosoft.newstandard.redis.refresh.RefreshConfig;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.Serializable;
import java.util.concurrent.Executor;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Configuration
@EnableCaching
@EnableAsync
@ComponentScan("com.sinosoft.newstandard.redis")
public class RedisConfiguration {

    @Bean
    public RedisTemplate<Serializable, Serializable> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<Serializable, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }

    @Bean
    public RedisHandler redisHandler(RedisTemplate redisTemplate) {
        return new RedisHandler(redisTemplate);
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder stringBuilder = new StringBuilder();
            //类名.方法名#参数
            stringBuilder.append(target.getClass().getSimpleName());
            stringBuilder.append("#");
            stringBuilder.append(method.getName());
            if (params.length > 0) {
                stringBuilder.append("[");
                for (int i = 0; i < params.length; i++) {
                    if (params[i] == null) {
                        stringBuilder.append("null");
                    } else {
                        stringBuilder.append(params[i].toString());
                    }
                    if (i < params.length - 1) {
                        stringBuilder.append(",");
                    }
                }
                stringBuilder.append("]");
            }
            return stringBuilder.toString();
        };
    }

    @Bean
    public Executor cacheExecutor() {
        return ExecutorBuilder.createExecutor(8, 32, 1024, 120, "refreshCacheExecutor-", ExecutorBuilder.ABORTPOLICY);
    }

    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory, RefreshConfig refreshConfig) {
        CustomizedRedisCacheManager cacheManager = new CustomizedRedisCacheManager(lettuceConnectionFactory, cacheExecutor(), redisTemplate(lettuceConnectionFactory), refreshConfig);
        cacheManager.afterPropertiesSet();
        return cacheManager;
    }

}
