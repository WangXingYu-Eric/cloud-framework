package com.sinosoft.newstandard.authenticationclient.provider;


import com.sinosoft.newstandard.common.enumeration.CustomStatus;
import com.sinosoft.newstandard.common.web.entity.AuthenticationResultEntity;
import com.sinosoft.newstandard.common.web.entity.Result;
import org.springframework.stereotype.Component;


/**
 * 鉴权服务的熔断处理.
 *
 * @Author: Eric
 * @Date: 2019-04-08
 */
@Component
class AuthProviderFallback implements AuthProvider {

    @Override
    @SuppressWarnings("unchecked")
    public Result<AuthenticationResultEntity> decide(String authentication, String url, String method) {
        AuthenticationResultEntity entity = new AuthenticationResultEntity();
        entity.setPermissive(false);
        entity.setNewAuthorization(null);
        return Result.fail(CustomStatus.AUTH_ERROR, entity);
    }

}
