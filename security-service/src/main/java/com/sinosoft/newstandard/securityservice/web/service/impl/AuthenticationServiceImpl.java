package com.sinosoft.newstandard.securityservice.web.service.impl;

import com.sinosoft.newstandard.common.web.entity.Resource;
import com.sinosoft.newstandard.common.web.service.IAuthenticationService;
import com.sinosoft.newstandard.common.web.service.IResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String NONEXISTENT_URL = "NONEXISTENT_URL";

    @Autowired
    private IResourceService resourceService;

    /**
     * 系统中所有资源权限集合.
     * key是url适配器,用于匹配url.
     * value是Resource对象中code属性的封装.
     */
    private Map<RequestMatcher, ConfigAttribute> resourceConfigAttributes;

    @Autowired
    public AuthenticationServiceImpl(Map<RequestMatcher, ConfigAttribute> resourceConfigAttributes) {
        this.resourceConfigAttributes = resourceConfigAttributes;
    }

    /**
     * 校验权限.
     *
     * @param authRequest 请求信息
     * @return true:有权限,false:没有权限
     */
    @Override
    public boolean decide(HttpServletRequest authRequest) {

        logger.info("正在访问的url是:{},method:{}", authRequest.getServletPath(), authRequest.getMethod());

        //获取已认证用户的相关信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //获取此url、method访问对应的权限资源信息
        ConfigAttribute securityConfig = findConfigAttributesByUrl(authRequest);

        if (NONEXISTENT_URL.equals(securityConfig.getAttribute())) {
            logger.info("url未在资源池中找到,拒绝访问.");
        }

        //获取此访问用户所有角色拥有的权限资源
        Set<Resource> resources = findResourcesByAuthorityRoles(authentication.getAuthorities());

        //用户拥有权限资源与url要求的资源进行对比
        return isMatch(securityConfig, resources);
    }

    /**
     * 根据url和method查询对应的权限信息.
     */
    private ConfigAttribute findConfigAttributesByUrl(HttpServletRequest authRequest) {
        return this.resourceConfigAttributes.keySet().stream()
                .filter(requestMatcher -> requestMatcher.matches(authRequest))
                .map(requestMatcher -> this.resourceConfigAttributes.get(requestMatcher))
                .peek(securityConfig -> logger.info("url在资源池中对应的code:{}", securityConfig.getAttribute()))
                .findFirst()
                .orElse(new SecurityConfig(NONEXISTENT_URL));
    }

    /**
     * 根据用户所被授予的角色,查询到用户所拥有的资源.
     */
    private Set<Resource> findResourcesByAuthorityRoles(Collection<? extends GrantedAuthority> authorityRoles) {

        logger.info("用户的授权角色集合信息为:{}", authorityRoles);

        List<String> authorityRoleCodes = authorityRoles.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Set<Resource> resources = resourceService.listByRoleCodes(authorityRoleCodes);

        logger.info("用户被授予角色的资源数量是:{}, 资源集合信息为:{}", resources.size(), resources.stream().map(Resource::getCode).collect(Collectors.toSet()));
        return resources;
    }

    /**
     * url对应资源与用户拥有资源进行匹配.
     */
    private boolean isMatch(ConfigAttribute urlConfigAttribute, Set<Resource> userResources) {
        return userResources.stream().anyMatch(resource -> resource.getCode().equals(urlConfigAttribute.getAttribute()));
    }

}
