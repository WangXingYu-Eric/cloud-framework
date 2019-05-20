# 认证依赖模块  
**此模块用于提供认证功能,依赖此模块后便具备访问授权认证服务的能力.此模块主要对外接口为:com.sinosoft.newstandard.authenticationclient.service.IAuthService**  
# 相关方法  
**com.sinosoft.newstandard.authenticationclient.service.IAuthService**  
```java
//判断url是否在忽略鉴权范围内.
boolean ignoreAuthentication(String url);

//token有效性验证.
boolean invalidJwtAccessToken(String authentication);

//从token中提取jwt.
Jwt getJwt(String token);

//判断用户是否有权限.
Result<AuthenticationResultEntity> authentication(String authentication, String url, String method);
```
**com.sinosoft.newstandard.authenticationclient.provider.AuthProvider**  
```java
//调用认证服务,校验权限是否可访问.
Result<AuthenticationResultEntity> decide(String authentication, String url, String method);
```
# 相关配置  
**忽略鉴权URL配置:在依赖模块配置文件中添加如下内容:**  
```properties
#鉴权时需要忽略的url.多个url用','隔开.
ignore:
  authentication:
    urls: "/oauth/token,/oauth/check_token,/oauth/token_key,/resources/**"
```
**由于token采用了ssl加密,所以在依赖模块的resources文件夹下需要添加证书文件(public.cer),此证书文件是与授权认证服务的秘钥文件(private.pfx)相关联的.并且需要在依赖模块的配置文件中添加如下配置:**  
```properties
authentication:
  ssl:
    cerFileName: public.cer
```