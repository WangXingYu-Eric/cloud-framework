package com.sinosoft.newstandard.authenticationclient.provider;

import com.sinosoft.newstandard.common.web.entity.AuthenticationResultEntity;
import com.sinosoft.newstandard.common.web.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 */
@Component
@FeignClient(name = "security-service", fallback = AuthProviderFallback.class)
public interface AuthProvider {

    /**
     * 调用认证服务,校验权限是否可访问.
     *
     * @param authentication authentication
     * @param url            请求地址
     * @param method         请求方式,GET/POST
     * @return Result
     */
    @PostMapping(value = "/oauth/decide")
    Result<AuthenticationResultEntity> decide(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @RequestParam("url") String url, @RequestParam("method") String method);

}
