package com.sinosoft.newstandard.redis.refresh;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 用于支持刷新缓存的接口类
 *
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public interface RefreshCacheSupport {

    /**
     * 注册缓存方法执行信息
     *
     * @param invokedBean          代理Bean
     * @param invokedMethod        代理方法名称
     * @param invocationParamTypes 代理方法参数类型
     * @param invocationArgs       代理方法参数
     * @param cacheNames           缓存名称(@Cacheable注解的value)
     * @param cacheKey             缓存key(@Cacheable注解的key)
     */
    void registerInvocation(Object invokedBean, Method invokedMethod, Class[] invocationParamTypes, Object[] invocationArgs, Set<String> cacheNames, String cacheKey);

    /**
     * 按 缓存名称以及指定键更新缓存
     *
     * @param cacheName 缓存名称
     * @param cacheKey  指定键
     */
    void refreshCacheByKey(String cacheName, String cacheKey);

}
