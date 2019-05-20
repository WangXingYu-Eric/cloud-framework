/**
 * 由于org.springframework.cache.interceptor包中关于解析SpEL的类是非公共的,所以把相关类copy到此包中,以便在项目中可以调用.<br/>
 * <b>用法:</b>
 * <pre>
 * CacheOperationExpressionEvaluator evaluator = new CacheOperationExpressionEvaluator();
 * EvaluationContext evaluationContext = evaluator.createEvaluationContext(caches, method, args, target, targetClass, targetMethod, result, null);
 * AnnotatedElementKey methodCacheKey = new AnnotatedElementKey(method, targetClass);
 * Object key = evaluator.key(key, methodCacheKey, evaluationContext);
 * </pre>
 */
package com.sinosoft.newstandard.redis.expression;