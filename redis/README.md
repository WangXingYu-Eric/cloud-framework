#缓存模块
**Redis相关属性配置:**  
```properties
redis:
host: 127.0.0.1
port: 6379
#哨兵模式:去掉host属性,打开sentinel属性
#    sentinel:
#      master:  mymaster
#      nodes: 127.0.0.1:26377,127.0.0.1:26378,127.0.0.1:26379
password: newStandard
timeout: 3000ms
database: 0
lettuce:
  pool:
    max-wait: 1000ms
    max-active: 8
    max-idle: 8
    min-idle: 0
```
**Redis主动刷新配置:**  
```properties
refresh:
  config:
    list:
      - cacheName: "cacheName"
        expiryTimeSecond: 120 #缓存有效时间
        preLoadTimeSecond: 10 #缓存有效时间小于此值时,主动刷新该缓存
      - cacheName: "cacheName"
        expiryTimeSecond: 120
        preLoadTimeSecond: 10
```