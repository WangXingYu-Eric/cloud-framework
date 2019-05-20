package com.sinosoft.newstandard.securityservice.config.security.oauth2;

import com.sinosoft.newstandard.common.enumeration.Constant;
import com.sinosoft.newstandard.common.util.JacksonUtils;
import com.sinosoft.newstandard.common.web.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义的TokenEnhancer,用于access_token的扩展.
 *
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class CustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    private IUserService userService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();
        Authentication userAuthentication = authentication.getUserAuthentication();
        if (userAuthentication != null) {
            User user = (User) userAuthentication.getPrincipal();
            com.sinosoft.newstandard.common.web.entity.User userWithDetailsProxySource = userService.getUserWithDetails(user.getUsername());
            userWithDetailsProxySource.setPassword(null);//脱敏
            com.sinosoft.newstandard.common.web.entity.User userWithDetailsTarget = new com.sinosoft.newstandard.common.web.entity.User();
            BeanUtils.copyProperties(userWithDetailsProxySource, userWithDetailsTarget);
            additionalInfo.put(Constant.CURRENT_USER, JacksonUtils.toJson(userWithDetailsTarget));
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        }
        return accessToken;
    }

}
