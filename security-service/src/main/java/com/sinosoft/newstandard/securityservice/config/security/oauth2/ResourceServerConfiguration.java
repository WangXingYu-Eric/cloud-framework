package com.sinosoft.newstandard.securityservice.config.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Order(3)
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private CustomDefaultTokenServices customDefaultTokenServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ResourceServerSecurityConfigurer resourceServerSecurityConfigurer) {
        resourceServerSecurityConfigurer
                .tokenStore(tokenStore)
                .tokenServices(customDefaultTokenServices)
                .authenticationManager(authenticationManager)
                .resourceId("WEBS")
                .authenticationEntryPoint(new CustomOAuth2AuthenticationEntryPoint());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //Oauth2拦截除了'/login/**'和'/logout/**'的所有请求
        http
                .requestMatchers()
                .antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers("/login/**", "/logout/**", "/druid/**", "/**/*.css", "/**/*.js", "/**/*.ico").permitAll()
                .anyRequest().authenticated();
        //localhost:5400/oauth/authorize?client_id=code&grant_type=authorization_code&response_type=code
        //http://localhost:5300/security-service/oauth/authorize?client_id=code&grant_type=authorization_code&response_type=code
        //localhost:5400/oauth/token?client_id=code&client_secret=123456&grant_type=authorization_code&code=xp5Lnv
        //localhost:5300/security-service/oauth/token?client_id=code&client_secret=123456&grant_type=authorization_code&code=xp5Lnv
    }

}
