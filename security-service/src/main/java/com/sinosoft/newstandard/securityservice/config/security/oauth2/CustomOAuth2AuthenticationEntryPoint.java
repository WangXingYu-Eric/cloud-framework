package com.sinosoft.newstandard.securityservice.config.security.oauth2;

import com.sinosoft.newstandard.common.enumeration.Constant;
import com.sinosoft.newstandard.common.web.CustomHttpServletRequestWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetailsSource;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class CustomOAuth2AuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        try {
            WebResponseExceptionTranslator exceptionTranslator = new DefaultWebResponseExceptionTranslator();
            ResponseEntity result = exceptionTranslator.translate(authException);
            Object body = result.getBody();

            //如果不是InvalidTokenException类型,则执行默认操作
            if (!(body instanceof InvalidTokenException)) {
                super.commence(request, response, authException);
                return;
            }

            InvalidTokenException invalidTokenException = ((InvalidTokenException) body);
            boolean condition1 = result.getStatusCode() == HttpStatus.UNAUTHORIZED; //响应码为403
            boolean condition2 = "invalid_token".equals(invalidTokenException.getOAuth2ErrorCode()); //token失效
            boolean condition3 = invalidTokenException.getLocalizedMessage().startsWith("Access token expired:"); //access_token失效
            if (condition1 && condition2 && condition3) {
                logger.info("access_token过期,准备刷新token.");
                String tokenValue = request.getHeader("authorization").substring(Constant.BEARER_BEGIN_INDEX);
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                //删除过期的accessToken
                tokenStore.removeAccessToken(accessToken);
                OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
                //refresh_token剩余有效期
                long remaining = ((ExpiringOAuth2RefreshToken) refreshToken).getExpiration().getTime() - System.currentTimeMillis();
                //如果refresh_token过期,则执行默认操作
                if (remaining < 5000) {
                    logger.info("refresh_token过期.");
                    //删除过期的refreshToken
                    tokenStore.removeRefreshToken(refreshToken);
                    super.commence(request, response, authException);
                    return;
                }
                //刷新操作
                Map map = refreshTokenAction(refreshToken.getValue());
                //如果刷新成功则重新装填request,并转发目标链接
                String newAccessToken = map.get("access_token") == null ? "" : map.get("access_token").toString();
                if (StringUtils.isNotEmpty(newAccessToken)) {
                    refilledAndForward(request, response, newAccessToken);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求刷新
     *
     * @param refreshToken refreshToken
     * @return 刷新结果
     */
    private Map refreshTokenAction(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", Constant.BASE_AUTH);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("refresh_token", refreshToken);
        return restTemplate.exchange("http://localhost:5300/security-service/oauth/token", HttpMethod.POST, new HttpEntity<>(formData, headers), Map.class).getBody();
    }

    /**
     * 重新装填request,并转发目标链接
     */
    private void refilledAndForward(HttpServletRequest request, HttpServletResponse response, String accessToken) throws ServletException, IOException {
        logger.info("新的access_token:" + accessToken);
        CustomHttpServletRequestWrapper customHeaderHttpServletRequestWrapper = new CustomHttpServletRequestWrapper(request);
        customHeaderHttpServletRequestWrapper.putHeader("authorization", Constant.BEARER_VLAUE + accessToken);
        customHeaderHttpServletRequestWrapper.putHeader(Constant.NEW_AUTHORIZATION, accessToken);
        Authentication authentication = new BearerTokenExtractor().extract(customHeaderHttpServletRequestWrapper);
        if (authentication instanceof AbstractAuthenticationToken) {
            AbstractAuthenticationToken needsDetails = (AbstractAuthenticationToken) authentication;
            needsDetails.setDetails(new OAuth2AuthenticationDetailsSource().buildDetails(customHeaderHttpServletRequestWrapper));
        }
        Authentication authenticate = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        customHeaderHttpServletRequestWrapper.getRequestDispatcher(customHeaderHttpServletRequestWrapper.getRequestURI()).forward(customHeaderHttpServletRequestWrapper, response);
    }

}
