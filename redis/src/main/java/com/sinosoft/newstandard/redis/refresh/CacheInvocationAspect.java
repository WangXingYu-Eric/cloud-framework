package com.sinosoft.newstandard.redis.refresh;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户拦截@Cacheable编著的方法,注册那些配置了主动刷新策略的缓存所执行的方法信息.
 *
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Aspect
@Component
public class CacheInvocationAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefreshCacheSupport refreshCacheSupport;

    @Autowired
    private RefreshConfig refreshConfig;

    @Pointcut("@annotation(org.springframework.cache.annotation.Cacheable)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Method method = getSpecificMethod(joinPoint);

        Cacheable cacheable = method.getAnnotation(Cacheable.class);

        if (cacheable != null) {

            Set<String> cacheNames = getCacheName(joinPoint.getTarget(), method);
            boolean satisfied = refreshConfig.getList().stream().map(RefreshInfo::getCacheName).anyMatch(cacheNames::contains);
            if (!satisfied) {
                logger.info("cacheName={},没有相应的刷新缓存配置,所以不用注册方法执行信息", cacheNames);
                return joinPoint.proceed();
            }

            String key = cacheable.key();
            Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
            refreshCacheSupport.registerInvocation(joinPoint.getTarget(), method, parameterTypes, joinPoint.getArgs(), cacheNames, key);
        }

        return joinPoint.proceed();
    }

    private Method getSpecificMethod(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        // The method may be on an interface, but we need attributes from the
        // target class. If the target class is null, the method will be
        // unchanged.
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(pjp.getTarget());
        if (targetClass == null && pjp.getTarget() != null) {
            targetClass = pjp.getTarget().getClass();
        }
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        // If we are dealing with method with generic parameters, find the
        // original method.
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
        return specificMethod;
    }

    /**
     * 获取cacheName,
     */
    private Set<String> getCacheName(Object target, Method method) {

        //获取@Cacheable的value,如果@Cacheable的value有值则忽略@CacheConfig的cacheNames.
        Cacheable cacheable = method.getAnnotation(Cacheable.class);
        String[] value = cacheable.value();
        if (value.length > 0) {
            return new HashSet<>(Arrays.asList(value));
        }

        //获取@CacheConfig的cacheNames
        CacheConfig cacheConfig = target.getClass().getAnnotation(CacheConfig.class);
        String[] cacheNames = cacheConfig.cacheNames();
        if (cacheNames.length > 0) {
            return new HashSet<>(Arrays.asList(cacheNames));
        }

        return new HashSet<>();
    }

}
