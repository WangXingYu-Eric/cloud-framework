package com.sinosoft.newstandard.redis;

import com.sinosoft.newstandard.common.SpringContextHolder;
import com.sinosoft.newstandard.redis.operate.RedisLock;
import com.sinosoft.newstandard.redis.refresh.RefreshCacheSupport;
import com.sinosoft.newstandard.redis.refresh.RefreshCacheSupportImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisOperations;

import java.util.concurrent.Executor;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class CustomizedRedisCache extends RedisCache {

    private static final Logger logger = LoggerFactory.getLogger(CustomizedRedisCache.class);

    private Executor cacheExecutor;

    private RedisOperations redisOperations;

    /**
     * 缓存有效时间小于此值则触发主动刷新
     * 单位:秒
     */
    private long preloadSecondTime;

    /**
     * 缓存有效时间
     * 单位:秒
     */
    private long expirationSecondTime;

    private RefreshCacheSupport getRefreshCacheSupport() {
        return SpringContextHolder.getBean(RefreshCacheSupportImpl.class);
    }

    public CustomizedRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig, Executor cacheExecutor, RedisOperations redisOperations, long expirationSecondTime, long preloadSecondTime) {
        super(name, cacheWriter, cacheConfig);
        this.cacheExecutor = cacheExecutor;
        this.redisOperations = redisOperations;
        this.expirationSecondTime = expirationSecondTime;
        this.preloadSecondTime = preloadSecondTime;
    }


    @Override
    @SuppressWarnings("NullableProblems")
    public ValueWrapper get(final Object key) {
        ValueWrapper valueWrapper = toValueWrapper(lookup(key));
        if (null != valueWrapper) {
            // 刷新缓存数据
            refreshCache(key.toString());
        }
        return valueWrapper;
    }

    @SuppressWarnings("unchecked")
    private void refreshCache(String key) {
        Long ttl = this.redisOperations.getExpire(concatKey(getName(), key));
        //判断有效期是否满足条件
        if (null != ttl && ttl <= preloadSecondTime) {
            cacheExecutor.execute(() -> {
                // 加一个分布式锁,只放一个请求去刷新缓存
                RedisLock redisLock = new RedisLock(redisOperations, key);
                try {
                    if (redisLock.tryLock()) {
                        // 获取锁之后再判断一下有效期否满足条件
                        Long ttl1 = this.redisOperations.getExpire(concatKey(getName(), key));
                        if (null != ttl1 && ttl1 <= preloadSecondTime) {
                            // 通过获取代理方法信息重新加载缓存数据
                            getRefreshCacheSupport().refreshCacheByKey(CustomizedRedisCache.super.getName(), key);
                        } else {
                            logger.info("剩余有效期不满足刷新条件.");
                        }
                    } else {
                        logger.info("重复的缓存刷新请求.");
                    }
                } catch (Exception e) {
                    logger.info(e.getMessage(), e);
                } finally {
                    redisLock.releaseLock();
                }
            });
        } else {
            logger.info("剩余有效期不满足刷新条件.");
        }
    }

    /**
     * 根据cacheName和simpleKey拼接redis的key
     */
    public static String concatKey(String cacheName, Object simpleKey) {
        return CacheKeyPrefix.simple().compute(cacheName) + simpleKey;
    }

    public long getPreloadSecondTime() {
        return preloadSecondTime;
    }

    public void setPreloadSecondTime(long preloadSecondTime) {
        this.preloadSecondTime = preloadSecondTime;
    }

    public long getExpirationSecondTime() {
        return expirationSecondTime;
    }

    public void setExpirationSecondTime(long expirationSecondTime) {
        this.expirationSecondTime = expirationSecondTime;
    }

}
