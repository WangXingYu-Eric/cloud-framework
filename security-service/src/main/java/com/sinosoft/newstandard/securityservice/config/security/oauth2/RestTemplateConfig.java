package com.sinosoft.newstandard.securityservice.config.security.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
//        // 添加转换器
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
//        messageConverters.add(new FormHttpMessageConverter());
//        messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
//        messageConverters.add(new MappingJackson2HttpMessageConverter());
//
//        RestTemplate restTemplate = new RestTemplate(messageConverters);
//        restTemplate.setRequestFactory(factory);
//        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);//单位为ms
        factory.setConnectTimeout(5000);//单位为ms
        return factory;
    }

}
