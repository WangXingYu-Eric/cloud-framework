package com.sinosoft.newstandard.securityservice.web.controller;

import com.sinosoft.newstandard.common.enumeration.Constant;
import com.sinosoft.newstandard.common.web.CustomHttpServletRequestWrapper;
import com.sinosoft.newstandard.common.web.controller.BaseController;
import com.sinosoft.newstandard.common.web.entity.AuthenticationResultEntity;
import com.sinosoft.newstandard.common.web.entity.Result;
import com.sinosoft.newstandard.common.web.service.IAuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@RestController
@RequestMapping("/oauth")
public class AuthenticationController extends BaseController {

    @Autowired
    private IAuthenticationService authenticationService;

    @Value("${server.port}")
    private String port;

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/decide")
    public Result<AuthenticationResultEntity> decide(@RequestParam String url, @RequestParam String method) {
        boolean permissive = authenticationService.decide(new CustomHttpServletRequestWrapper(request, url, method));
        String newAuthorization = request.getHeader(Constant.NEW_AUTHORIZATION);
        AuthenticationResultEntity entity = new AuthenticationResultEntity();
        entity.setPermissive(permissive);
        if (StringUtils.isNotEmpty(newAuthorization)) {
            entity.setNewAuthorization(newAuthorization);
        }
        return Result.success(entity);
    }

}
