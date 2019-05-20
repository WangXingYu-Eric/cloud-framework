#授权认证服务
**此服务用于授权给其他服务请求和验证他服务请求权限**  
**由于token采用了ssl加密,所以在resources文件夹下需要添秘钥文件(private.pfx),此秘钥文件是与依赖了认证模块的服务中的证书文件(public.cer)相关联的.并且需要在配置文件中添加如下配置:**
```properties
authentication:
  ssl:
    pfxFileName: private.pfx
    password: xxxxxxxx
```