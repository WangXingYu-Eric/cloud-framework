package com.sinosoft.newstandard.redis;

import com.sinosoft.newstandard.redis.refresh.RefreshConfig;
import com.sinosoft.newstandard.redis.refresh.RefreshInfo;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class CustomizedRedisCacheManager extends RedisCacheManager {

    private RedisCacheWriter redisCacheWriter;
    private Map<String, RedisCacheConfiguration> initialCacheConfigurations;
    private Map<String, RefreshInfo> refreshInfoMap;
    private Executor cacheExecutor;
    private RedisOperations redisOperations;

    CustomizedRedisCacheManager(RedisConnectionFactory redisConnectionFactory, Executor cacheExecutor, RedisOperations redisOperations, RefreshConfig refreshConfig) {
        this(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                //缓存默认配置
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(60L)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())),
                //把refreshInfoList转换Map<String, RedisCacheConfiguration>的结构
                refreshConfig.getList().stream().collect(Collectors.toMap(RefreshInfo::getCacheName,
                        refreshInfo -> RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(refreshInfo.getExpirationSecondTime())).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))))
        );
        //把refreshInfoList转换Map<String, RefreshInfo>的结构
        this.refreshInfoMap = refreshConfig.getList().stream().collect(Collectors.toMap(RefreshInfo::getCacheName, refreshInfo -> refreshInfo));
        this.cacheExecutor = cacheExecutor;
        this.redisOperations = redisOperations;
    }

    /**
     * @param redisCacheWriter           用于写入缓存的对象
     * @param defaultCacheConfiguration  缓存默认配置
     * @param initialCacheConfigurations 根据cacheName设置的缓存配置
     */
    private CustomizedRedisCacheManager(RedisCacheWriter redisCacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations) {
        super(redisCacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
        this.redisCacheWriter = redisCacheWriter;
        this.initialCacheConfigurations = initialCacheConfigurations;
    }

    @Override
    public Cache getCache(@Nullable String name) {
        Cache cache = super.getCache(name);
        if (cache == null) {
            return null;
        }
        //获取cacheName对应的自定义的刷新缓存配置
        RedisCacheConfiguration redisCacheConfiguration = this.initialCacheConfigurations.get(cache.getName());
        if (redisCacheConfiguration != null) {
            RefreshInfo refreshInfo = this.refreshInfoMap.get(cache.getName());
            long expirationSecondTime = refreshInfo != null ? refreshInfo.getExpirationSecondTime() : 120L;
            long preloadSecondTime = refreshInfo != null ? refreshInfo.getPreloadSecondTime() : 119L;
            return new CustomizedRedisCache(name, this.redisCacheWriter, redisCacheConfiguration, this.cacheExecutor, this.redisOperations, expirationSecondTime, preloadSecondTime);
        }
        return cache;
    }

}
