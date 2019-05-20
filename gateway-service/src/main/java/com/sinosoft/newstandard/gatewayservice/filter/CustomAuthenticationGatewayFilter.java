package com.sinosoft.newstandard.gatewayservice.filter;

import com.sinosoft.newstandard.authenticationclient.service.IAuthService;
import com.sinosoft.newstandard.common.enumeration.Constant;
import com.sinosoft.newstandard.common.util.JacksonUtils;
import com.sinosoft.newstandard.common.web.entity.AuthenticationResultEntity;
import com.sinosoft.newstandard.common.web.entity.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.jwt.Jwt;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Configuration
@ComponentScan("com.sinosoft.newstandard.authenticationclient") //扫描authentication-client模块
public class CustomAuthenticationGatewayFilter implements GlobalFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 由authentication-client模块提供鉴权功能
     */
    @Autowired
    private IAuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String url = request.getPath().value();
        String method = request.getMethodValue();

        try {

            if (authService.ignoreAuthentication(url)) { //判断是否忽略鉴权
                logger.info("网关忽略鉴权:url:{} -> method:{} -> headers:{}", url, method, request.getHeaders());
                return chain.filter(exchange);
            }

            logger.info("网关鉴权:url:{} -> method:{} -> headers:{}", url, method, request.getHeaders());
            String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (StringUtils.isEmpty(authorization)) {
                return getMono(response, HttpStatus.UNAUTHORIZED, "未获取到token.");
            }

            authorization = authorization.startsWith(Constant.BEARER_VLAUE) ? authorization : Constant.BEARER_VLAUE + authorization;

            if (authService.invalidJwtAccessToken(authorization)) {
                return getMono(response, HttpStatus.UNAUTHORIZED, "token签名错误.");
            }

            Result<AuthenticationResultEntity> result = authService.authentication(authorization, url, method);
            if (result == null) {
                return getMono(response, HttpStatus.UNAUTHORIZED, "未被授权.");
            }
            AuthenticationResultEntity resultEntity = result.getData();
            if (!resultEntity.isPermissive()) {
                return getMono(response, HttpStatus.UNAUTHORIZED, "未被授权.");
            }

            //response中加入自定义信息,供下游服务调取.
            ServerHttpRequest.Builder builder = request.mutate();
            Jwt jwt = authService.getJwt(authorization);
            Map<String, Object> map = JacksonUtils.fromJson(jwt.getClaims(), Map.class);
            Optional.ofNullable(map.get(Constant.CURRENT_USER)).ifPresent(userJson -> builder.header(Constant.CURRENT_USER, userJson.toString()));
            Optional.ofNullable(map.get("authorities")).ifPresent(authorities -> builder.header(Constant.ROLE_CODES, JacksonUtils.toJson(authorities)));
            //access_token过期后通过refresh_token获取的新授权
            String newAuthorization = resultEntity.getNewAuthorization();
            if (StringUtils.isNotBlank(newAuthorization)) {
                builder.header(Constant.NEW_AUTHORIZATION, newAuthorization);
            }
            return chain.filter(exchange.mutate().request(builder.build()).build());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return getMono(response, HttpStatus.INTERNAL_SERVER_ERROR, "网关鉴权异常.");
        }
    }

    /**
     * 封装Mono对象
     */
    private Mono<Void> getMono(ServerHttpResponse response, HttpStatus httpStatus, String message) {
        response.setStatusCode(httpStatus);
        DataBuffer buffer = response.bufferFactory().wrap(JacksonUtils.toJson(Result.fail(httpStatus.value(), message)).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(buffer));
    }

}
