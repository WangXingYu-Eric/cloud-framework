package com.sinosoft.newstandard.common.util.executor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class ExecutorBuilder {

    //AbortPolicy,用于被拒绝任务的处理程序,它将抛出 RejectedExecutionException.
    public static final String ABORTPOLICY = "AbortPolicy";
    //CallerRunsPolicy,用于被拒绝任务的处理程序,它直接在 execute 方法的调用线程中运行被拒绝的任务.
    public static final String CALLERRUNSPOLICY = "CallerRunsPolicy";
    //DiscardOldestPolicy,用于被拒绝任务的处理程序,它放弃最旧的未处理请求,然后重试execute.
    public static final String DISCARDOLDESTPOLICY = "DiscardOldestPolicy";
    //DiscardPolicy,用于被拒绝任务的处理程序,默认情况下它将丢弃被拒绝的任务.
    public static final String DISCARDPOLICY = "DiscardPolicy";

    /**
     * @param corePoolSize             核心线程数
     * @param maxPoolSize              最大线程数
     * @param queueCapacity            队列最大长度
     * @param keepAliveSeconds         线程池维护线程所允许的空闲时间(单位秒)
     * @param threadNamePrefix         配置线程池中的线程的名称前缀
     * @param rejectedExecutionHandler 线程池对拒绝任务(无线程可用)的处理策略
     */
    public static ThreadPoolTaskExecutor createExecutor(int corePoolSize, int maxPoolSize, int queueCapacity, int keepAliveSeconds, String threadNamePrefix, String rejectedExecutionHandler) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(threadNamePrefix);
        RejectedExecutionHandler handler;
        switch (rejectedExecutionHandler) {
            case ABORTPOLICY:
                handler = new ThreadPoolExecutor.AbortPolicy();
                break;
            case CALLERRUNSPOLICY:
                handler = new ThreadPoolExecutor.CallerRunsPolicy();
                break;
            case DISCARDOLDESTPOLICY:
                handler = new ThreadPoolExecutor.DiscardOldestPolicy();
                break;
            case DISCARDPOLICY:
                handler = new ThreadPoolExecutor.DiscardPolicy();
                break;
            default:
                handler = new ThreadPoolExecutor.AbortPolicy();
                break;
        }
        executor.setRejectedExecutionHandler(handler);
        //执行初始化
        executor.initialize();
        return executor;
    }

    public static ThreadPoolTaskExecutor defaultExecutor(String threadNamePrefix) {
        return createExecutor(8, 32, 1024, 120, threadNamePrefix, ABORTPOLICY);
    }

}
