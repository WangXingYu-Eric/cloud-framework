package com.sinosoft.newstandard.authenticationclient.service;

import com.sinosoft.newstandard.common.web.entity.AuthenticationResultEntity;
import com.sinosoft.newstandard.common.web.entity.Result;
import org.springframework.security.jwt.Jwt;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 */
public interface IAuthService {

    /**
     * 判断url是否在忽略鉴权范围内.
     *
     * @param url 请求地址
     * @return true:忽略不鉴权,false:需要鉴权
     */
    boolean ignoreAuthentication(String url);

    /**
     * token有效性验证.
     *
     * @param authentication token
     * @return true:无效,false:有效
     */
    boolean invalidJwtAccessToken(String authentication);

    /**
     * 从token中提取jwt.
     *
     * @param token token
     * @return jwt
     */
    Jwt getJwt(String token);

    /**
     * 判断用户是否有权限.
     *
     * @param authentication token
     * @param url            请求地址
     * @param method         请求方式
     * @return 鉴权结果
     */
    Result<AuthenticationResultEntity> authentication(String authentication, String url, String method);

}
