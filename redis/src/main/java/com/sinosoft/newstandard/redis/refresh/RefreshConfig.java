package com.sinosoft.newstandard.redis.refresh;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录所有需要刷新的缓存名称,以及过期时间和触发时间.
 *
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Component
@ConfigurationProperties("refresh.config")
public class RefreshConfig {

    private List<RefreshInfo> list = new ArrayList<>();

    public List<RefreshInfo> getList() {
        return list;
    }

    public void setList(List<RefreshInfo> list) {
        this.list = list;
    }

}
