package com.sinosoft.newstandard.securityservice.config.security.oauth2;

import com.sinosoft.newstandard.common.web.entity.Resource;
import com.sinosoft.newstandard.common.web.service.IResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Configuration
public class ResourceConfiguration {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private HandlerMappingIntrospector handlerMappingIntrospector;

    @Bean
    public Map<RequestMatcher, ConfigAttribute> resourceConfigAttributes() {
        List<Resource> resources = resourceService.selectAll();
        Map<RequestMatcher, ConfigAttribute> map = resources.stream()
                .collect(Collectors.toMap(
                        resource -> {
                            MvcRequestMatcher mvcRequestMatcher = new MvcRequestMatcher(handlerMappingIntrospector, resource.getUrl());
                            mvcRequestMatcher.setMethod(HttpMethod.resolve(resource.getMethod()));
                            return mvcRequestMatcher;
                        },
                        resource -> new SecurityConfig(resource.getCode())
                        )
                );
        logger.info("所有已注册资源:{}", map);
        return map;
    }

}
