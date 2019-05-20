package com.sinosoft.newstandard.gatewayservice.filter;


import com.sinosoft.newstandard.common.enumeration.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Configuration
public class CustomCrossFilter implements WebFilter {

    @Override
    @SuppressWarnings("NullableProblems")
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String[] allowDomain = {"http://localhost:8888", "http://127.0.0.1:8888"};
        List<String> allowedOrigins = Arrays.asList(allowDomain);
        ServerHttpRequest request = exchange.getRequest();
        List<String> originHeaderList = request.getHeaders().get(HttpHeaders.ORIGIN);
        String originHeader = null;
        if (!CollectionUtils.isEmpty(originHeaderList)) {
            originHeader = originHeaderList.get(0);
        }
        if (StringUtils.isNotEmpty(originHeader) && allowedOrigins.contains(originHeader)) {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders headers = response.getHeaders();
            headers.add("Access-Control-Allow-Origin", originHeader);//Access-Control-Allow-Origin 头中携带了服务器端验证后的允许的跨域请求域名,可以是一个具体的域名或是一个 *(表示任意域名).简单请求时,浏览器会根据此响应头的内容决定是否给脚本返回相应内容,预先验证请求时,浏览器会根据此响应头决定是否发送实际的跨域请求.
            headers.add("Access-Control-Expose-Headers", "*");//Access-Control-Expose-Headers 头用于允许返回给跨域请求的响应头列表,在列表中的响应头的内容,才可以被浏览器访问
            headers.add("Access-Control-Max-Age", "3600");//Access-Control-Max-Age 用于告知浏览器可以将预先检查请求返回结果缓存的时间,在缓存有效期内,浏览器会使用缓存的预先检查结果判断是否发送跨域请求.
            headers.add("Access-Control-Allow-Credentials", "true");//Access-Control-Allow-Credentials 用于告知浏览器当 withCredentials 属性设置为 true 时,是否可以显示跨域请求返回的内容.简单请求时,浏览器会根据此响应头决定是否显示响应的内容.预先验证请求时,浏览器会根据此响应头决定在发送实际跨域请求时,是否携带认证信息.
            headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTION, HEAD");//Access-Control-Allow-Methods 用于告知浏览器可以在实际发送跨域请求时,可以支持的请求方法,可以是一个具体的方法列表或是一个 *(表示任意方法).简单请求时,浏览器会根据此响应头的内容决定是否给脚本返回相应内容,预先验证请求时,浏览器会根据此响应头决定是否发送实际的跨域请求.
            List<String> customHttpHeaders = new ArrayList<>();
            customHttpHeaders.add("Accept");
            customHttpHeaders.add("Authorization");
            customHttpHeaders.add("X-Requested-With");
            customHttpHeaders.add("Content-Type");
            customHttpHeaders.add("Origin");
            customHttpHeaders.add("User-Agent");
            customHttpHeaders.add(Constant.NEW_AUTHORIZATION);
            headers.add("Access-Control-Allow-Headers", StringUtils.join(customHttpHeaders.toArray(), ","));//Access-Control-Allow-Headers 用于告知浏览器可以在实际发送跨域请求时,可以支持的请求头,可以是一个具体的请求头列表或是一个 *(表示任意请求头).简单请求时,浏览器会根据此响应头的内容决定是否给脚本返回相应内容,预先验证请求时,浏览器会根据此响应头决定是否发送实际的跨域请求.
            HttpMethod requestMethod = request.getHeaders().getAccessControlRequestMethod();
            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }
        }
        return chain.filter(exchange);
    }

}
