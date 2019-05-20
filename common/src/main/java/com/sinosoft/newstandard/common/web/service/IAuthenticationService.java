package com.sinosoft.newstandard.common.web.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public interface IAuthenticationService {

    /**
     * 校验权限.
     *
     * @param authRequest 请求信息
     * @return true:有权限,false:没有权限
     */
    boolean decide(HttpServletRequest authRequest);

}
