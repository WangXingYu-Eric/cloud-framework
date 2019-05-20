package com.sinosoft.newstandard.redis.refresh;

import com.sinosoft.newstandard.common.SpringContextHolder;
import com.sinosoft.newstandard.common.util.ReflectionUtils;
import com.sinosoft.newstandard.redis.CustomizedRedisCache;
import com.sinosoft.newstandard.redis.expression.CacheOperationExpressionEvaluator;
import com.sinosoft.newstandard.redis.operate.RedisHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MethodInvoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Component
public class RefreshCacheSupportImpl implements RefreshCacheSupport {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String INVOCATION_CACHE_KEY_PREFIX = "invocation_cache_key_prefix";

    @Autowired
    private KeyGenerator keyGenerator;

    @Autowired
    private RedisHandler redisHandler;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void registerInvocation(Object invokedBean, Method invokedMethod, Class[] invocationParamTypes, Object[] invocationArgs, Set<String> cacheNames, String cacheKey) {

        Collection<? extends Cache> caches = getCaches(cacheNames);

        Object key = getKeyFromSpEL(caches, cacheKey, invokedMethod, invocationArgs, invokedBean);

        // 新建一个代理对象(记录了缓存注解的方法类信息)
        final CachedMethodInvocation invocation = new CachedMethodInvocation(key, invokedBean, invokedMethod, invocationParamTypes, invocationArgs);

        for (Cache cache : caches) {
            if (cache instanceof CustomizedRedisCache) {
                CustomizedRedisCache redisCache = ((CustomizedRedisCache) cache);
                // 将方法执行信息放入Redis
                if (redisHandler.get(CustomizedRedisCache.concatKey(INVOCATION_CACHE_KEY_PREFIX, key)) == null) {
                    logger.info("key=[{}],注册缓存方法执行信息,所执行的方法:{}", CustomizedRedisCache.concatKey(String.join("::", cacheNames), key), invokedBean.getClass().getName() + "." + invokedMethod.getName() + "()");
                    redisHandler.set(CustomizedRedisCache.concatKey(INVOCATION_CACHE_KEY_PREFIX, key), invocation, redisCache.getExpirationSecondTime());
                } else {
                    logger.info("缓存方法执行信息已注册");
                }
            }
        }

    }

    @Override
    public void refreshCacheByKey(String cacheName, String cacheKey) {
        //获取方法执行信息
        Object object = redisHandler.get(CustomizedRedisCache.concatKey(INVOCATION_CACHE_KEY_PREFIX, cacheKey));
        logger.info("key=[{}]获取方法执行信息:{}", CustomizedRedisCache.concatKey(cacheName, cacheKey), CustomizedRedisCache.concatKey(INVOCATION_CACHE_KEY_PREFIX, cacheKey));
        if (object instanceof CachedMethodInvocation) {
            CachedMethodInvocation cachedMethodInvocation = (CachedMethodInvocation) object;
            // 刷新操作
            refreshCache(cachedMethodInvocation, cacheName);
        } else {
            logger.error("刷新redis缓存,方法执行信息反序列化异常.");
        }
    }

    /**
     * 获取根据cacheNames获取Cache对象
     */
    private Collection<? extends Cache> getCaches(Set<String> cacheNames) {
        Collection<Cache> caches = new ArrayList<>();
        for (String cacheName : cacheNames) {
            Cache cache = this.cacheManager.getCache(cacheName);
            if (cache == null) {
                throw new IllegalArgumentException("Cannot find cache name :" + cacheName);
            }
            caches.add(cache);
        }
        return caches;
    }

    /**
     * 解析SpEL表达式,获取注解上的key属性值
     */
    private Object getKeyFromSpEL(Collection<? extends Cache> caches, String key, Method method, Object[] args, Object target) {
        // 获取注解上的key属性值
        Class<?> targetClass = getTargetClass(target);
        if (StringUtils.isNotEmpty(key)) {
            CacheOperationExpressionEvaluator evaluator = new CacheOperationExpressionEvaluator();
            EvaluationContext evaluationContext = evaluator.createEvaluationContext(caches, method, args, target, targetClass, method, new Object(), null);
            AnnotatedElementKey methodCacheKey = new AnnotatedElementKey(method, targetClass);
            return evaluator.key(key, methodCacheKey, evaluationContext);
        }
        return this.keyGenerator.generate(target, method, args);
    }

    /**
     * 获取目标类信息
     */
    private Class<?> getTargetClass(Object target) {
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(target);
        if (targetClass == null && target != null) {
            targetClass = target.getClass();
        }
        return targetClass;
    }

    private void refreshCache(CachedMethodInvocation cachedMethodInvocation, String cacheName) {
        try {
            // 通过代理调用方法
            Object computed = invoke(cachedMethodInvocation);

            // 通过cacheManager获取操作缓存的cache对象
            Cache cache = cacheManager.getCache(cacheName);

            //如果刷新的过程中缓存过期,则放弃操作
            if (redisHandler.get(CustomizedRedisCache.concatKey(cacheName, cachedMethodInvocation.getKey())) != null) {
                // 通过Cache对象更新缓存
                cache.put(cachedMethodInvocation.getKey(), computed);
                CustomizedRedisCache redisCache = (CustomizedRedisCache) cache;
                long expireTime = redisCache.getExpirationSecondTime();
                // 刷新redis中缓存方法执行信息的有效时间
                redisHandler.setExpireTime(CustomizedRedisCache.concatKey(INVOCATION_CACHE_KEY_PREFIX, cachedMethodInvocation.getKey()), expireTime);
                logger.info("key=[{}],重新加载数据", CustomizedRedisCache.concatKey(cacheName, cachedMethodInvocation.getKey()));
            } else {
                logger.info("key=[{}],已过期,放弃重新加载数据", CustomizedRedisCache.concatKey(cacheName, cachedMethodInvocation.getKey()));
            }
        } catch (Exception e) {
            logger.info("刷新缓存失败:" + e.getMessage(), e);
        }

    }

    private Object invoke(CachedMethodInvocation invocation) throws Exception {
        final MethodInvoker invoker = new MethodInvoker();
        Object computed;
        try {
            Object target = ReflectionUtils.getTarget(SpringContextHolder.getBean(Class.forName(invocation.getTargetBean())));
            invoker.setTargetObject(target);
            invoker.setTargetMethod(invocation.getTargetMethod());
            Object[] args = null;
            if (!CollectionUtils.isEmpty(invocation.getArguments())) {
                args = invocation.getArguments().toArray();
            }
            invoker.setArguments(args);
            invoker.prepare();
            logger.info("重新执行缓存方法-[开始],所执行的方法:{}.{}()", invocation.getTargetBean(), invocation.getTargetMethod());
            computed = invoker.invoke();
            logger.info("重新执行缓存方法-[完成],所执行的方法:{}.{}()", invocation.getTargetBean(), invocation.getTargetMethod());
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            computed = "重新执行缓存方法-[异常]";
            logger.info("重新执行缓存方法-[异常],所执行的方法:{}.{}()", invocation.getTargetBean(), invocation.getTargetMethod());
        }
        return computed;
    }

}
