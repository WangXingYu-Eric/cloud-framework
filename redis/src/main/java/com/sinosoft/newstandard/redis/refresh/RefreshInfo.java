package com.sinosoft.newstandard.redis.refresh;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class RefreshInfo {

    /**
     * 缓存容器名称
     */
    private String cacheName;

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

    public RefreshInfo() {
    }

    public RefreshInfo(String cacheName, long preloadSecondTime, long expirationSecondTime) {
        this.cacheName = cacheName;
        this.preloadSecondTime = preloadSecondTime;
        this.expirationSecondTime = expirationSecondTime;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
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
