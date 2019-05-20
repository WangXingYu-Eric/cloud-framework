package com.sinosoft.newstandard.authenticationclient.service.impl;

import com.sinosoft.newstandard.authenticationclient.provider.AuthProvider;
import com.sinosoft.newstandard.authenticationclient.service.IAuthService;
import com.sinosoft.newstandard.common.enumeration.Constant;
import com.sinosoft.newstandard.common.util.RSAUtils;
import com.sinosoft.newstandard.common.web.entity.AuthenticationResultEntity;
import com.sinosoft.newstandard.common.web.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.stream.Stream;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 */
@Service
public class AuthService implements IAuthService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private PublicKey publicKey;

    @Value("${ignore.authentication.urls}")
    private String ignoreUrls = "/oauth/token,/oauth/check_token,/oauth/token_key";

    @Value("${authentication.ssl.cerFileName}")
    private String cerFileName = "public.cer";

    @Autowired
    private AuthProvider authProvider;

    public AuthService() {
        publicKey = RSAUtils.getPublicKey(cerFileName);
    }

    @Override
    public boolean ignoreAuthentication(String url) {
        return Stream.of(this.ignoreUrls.split(",")).anyMatch(ignoreUrl -> new AntPathMatcher().match(ignoreUrl, url));
    }

    @Override
    public boolean invalidJwtAccessToken(String authorization) {
        boolean invalid = true;

        //对称加密
        //MacSigner verifier = new MacSigner("对称秘钥");

        //非对称加密
        RsaVerifier verifier = new RsaVerifier((RSAPublicKey) publicKey);
        try {
            Jwt jwt = getJwt(authorization);
            jwt.verifySignature(verifier);
            invalid = Boolean.FALSE;
        } catch (InvalidSignatureException | IllegalArgumentException e) {
            logger.error("Jwt签名错误.");
        }
        return invalid;
    }

    @Override
    public Jwt getJwt(String authorization) {
        authorization = authorization.startsWith(Constant.BEARER_VLAUE) ? authorization.substring(Constant.BEARER_BEGIN_INDEX) : authorization;
        return JwtHelper.decode(authorization);
    }

    @Override
    public Result<AuthenticationResultEntity> authentication(String authorization, String url, String method) {
        return authProvider.decide(authorization, url, method);
    }

}
