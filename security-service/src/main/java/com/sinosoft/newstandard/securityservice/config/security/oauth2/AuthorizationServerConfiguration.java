package com.sinosoft.newstandard.securityservice.config.security.oauth2;

import com.sinosoft.newstandard.common.util.RSAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // redis 连接工厂
    //@Autowired
    //private RedisConnectionFactory connectionFactory;

    @Value("${authentication.ssl.pfxFileName}")
    private String pfxFileName = "private.pfx";

    @Value("${authentication.ssl.password}")
    private String password = "Aa123456";

    public AuthorizationServerConfiguration() {
        publicKey = RSAUtils.getPublicKey(pfxFileName, password);
        privateKey = RSAUtils.getPrivateKey(pfxFileName, password);
    }

    /*@Bean
    public RedisTokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }*/

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /*@Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }*/

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        logger.info("正在使用公钥初始化JWT,publicKey:{}", publicKey);
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        //对称加密
        //converter.setSigningKey("对称秘要");

        //非对称加密
        converter.setKeyPair(new KeyPair(publicKey, privateKey));

        return converter;
    }

    @Bean
    public TokenEnhancerChain tokenEnhancerChain() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
        return tokenEnhancerChain;
    }

    @Bean
    public CustomDefaultTokenServices customDefaultTokenServices() {
        CustomDefaultTokenServices customDefaultTokenServices = new CustomDefaultTokenServices();
        customDefaultTokenServices.setTokenStore(tokenStore());
        customDefaultTokenServices.setSupportRefreshToken(true);
        customDefaultTokenServices.setReuseRefreshToken(false);//refresh后refresh_token是否继续使用
        customDefaultTokenServices.setClientDetailsService(clientDetailsService);
        customDefaultTokenServices.setTokenEnhancer(tokenEnhancerChain());
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService));
        customDefaultTokenServices.setAuthenticationManager(new ProviderManager(Collections.singletonList(provider)));
        return customDefaultTokenServices;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        OAuth2AuthenticationManager oauthAuthenticationManager = new OAuth2AuthenticationManager();
        oauthAuthenticationManager.setResourceId("WEBS");
        oauthAuthenticationManager.setTokenServices(customDefaultTokenServices());
        oauthAuthenticationManager.setClientDetailsService(clientDetailsService);
        return oauthAuthenticationManager;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                //.tokenStore(tokenStore()) 在customDefaultTokenServices()中设置了该属性
                //.tokenEnhancer(tokenEnhancerChain()) 在customDefaultTokenServices()中设置了该属性
                .tokenServices(customDefaultTokenServices())
                //.tokenGranter(tokenGranter(endpoints)) 暂时用默认的TokenGranter
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .authorizationCodeServices(authorizationCodeServices())
                .accessTokenConverter(jwtAccessTokenConverter())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    /**
     * 自定义 TokenGranter,方便设置token的属性.
     */
    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        return new TokenGranter() {
            private CompositeTokenGranter delegate;

            @Override
            public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
                if (delegate == null) {
                    ClientDetailsService clientDetails = endpoints.getClientDetailsService();
                    AuthorizationServerTokenServices tokenServices = endpoints.getTokenServices();
                    AuthorizationCodeServices authorizationCodeServices = endpoints.getAuthorizationCodeServices();
                    OAuth2RequestFactory requestFactory = endpoints.getOAuth2RequestFactory();
                    delegate = new CompositeTokenGranter(getDefaultTokenGranters(clientDetails, tokenServices, authorizationCodeServices, requestFactory));
                }
                return delegate.grant(grantType, tokenRequest);
            }
        };
    }

    /**
     * 授权码模式会用到,以后再研究.
     */
    private List<TokenGranter> getDefaultTokenGranters(ClientDetailsService clientDetails, AuthorizationServerTokenServices tokenServices, AuthorizationCodeServices authorizationCodeServices, OAuth2RequestFactory requestFactory) {
        List<TokenGranter> tokenGranters = new ArrayList<>();
        AuthorizationCodeTokenGranter authorizationCodeTokenGranter = new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetails, requestFactory);
        tokenGranters.add(authorizationCodeTokenGranter);

        RefreshTokenGranter refreshTokenGranter = new RefreshTokenGranter(tokenServices, clientDetails, requestFactory);
        tokenGranters.add(refreshTokenGranter);

        ImplicitTokenGranter implicit = new ImplicitTokenGranter(tokenServices, clientDetails, requestFactory);
        tokenGranters.add(implicit);

        ClientCredentialsTokenGranter clientCredentialsTokenGranter = new ClientCredentialsTokenGranter(tokenServices, clientDetails, requestFactory);
        clientCredentialsTokenGranter.setAllowRefresh(true);//设置client_credentials模式允许生成refresh_token
        tokenGranters.add(clientCredentialsTokenGranter);

        if (authenticationManager != null) {
            ResourceOwnerPasswordTokenGranter resourceOwnerPasswordTokenGranter = new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, clientDetails, requestFactory);
            tokenGranters.add(resourceOwnerPasswordTokenGranter);
        }
        return tokenGranters;
    }

}
