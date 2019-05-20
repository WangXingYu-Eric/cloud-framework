#数据库模块
**此模块用于提供数据库功能,依赖此模块后便具备数据层能力.**  
**数据源配置如下:**  
```properties
spring:
  datasource:
    druid:
      base:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost/base?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false
        username: root
        password: root
      other: #可选配置
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost/other?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false
        username: root
        password: root
      initialSize: 10 #连接池初始化连接数
      minIdle: 20 #连接池最小连接数
      maxActive: 50 #连接池最大连接数
      maxWait: 60000 #获取连接等待超时的时间,单位是毫秒
      timeBetweenEvictionRunsMillis: 60000 #间隔多久才进行一次检测,检测需要关闭的空闲连接,单位是毫秒
      minEvictableIdleTimeMillis: 300000 #连接在池中最小生存的时间,单位是毫秒
      validationQuery: SELECT 1 FROM DUAL #validationQuery: SELECT 1
      testWhileIdle: true
      testOnBorrow: false
      testOnReturn: false
      poolPreparedStatements: true #打开PSCache,并且指定每个连接上PSCache的大小
      maxPoolPreparedStatementPerConnectionSize: 20
      filters: stat,wall,log4j2 #监控统计拦截的filters,去掉后监控界面sql无法统计,'wall'用于防火墙
      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000 #通过connectProperties属性来打开mergeSql功能；慢SQL记录
      useGlobalDataSourceStat: true #合并多个DruidDataSource的监控数据
```  
#多数据源
```java
@Configuration
public class DruidConfiguration {

    @Autowired
    private Environment environment;

    @Bean(name = "base")
    @ConfigurationProperties(prefix = "spring.datasource.druid.base")
    public DataSource base() {
        return DruidUtils.setDruidProperties(environment, DruidDataSourceBuilder.create().build());
    }

    @Bean(name = "other")
    @ConfigurationProperties(prefix = "spring.datasource.druid.other")
    public DataSource other() {
        return DruidUtils.setDruidProperties(environment, DruidDataSourceBuilder.create().build());
    }

    /**
     * 动态数据源配置.
     */
    @Bean
    @Primary
    public DataSource multipleDataSource() {
        MultipleDataSource multipleDataSource = new MultipleDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("base", base());
        targetDataSources.put("other", other());
        //添加数据源
        multipleDataSource.setTargetDataSources(targetDataSources);
        //设置默认数据源
        multipleDataSource.setDefaultTargetDataSource(base());
        return multipleDataSource;
    }

    @Bean
    public ServletRegistrationBean statViewServlet() {
        return DruidUtils.setServletInitParameter(new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*"));
    }

}

```
#事物
```java
@Configuration
@EnableTransactionManagement
@MapperScan({"com.sinosoft.newstandard.common.web.mapper","com.sinosoft.newstandard.具体模块所在包.web.mapper"})
public class MybatisConfiguration {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
```
**在service层的类中添加@Transactional和@DataSource("base")/@DataSource("other"),以开启事物和多数据源.@DataSource注解中的值表示了类中要使用哪个数据源.


