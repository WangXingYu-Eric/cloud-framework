package com.sinosoft.newstandard.gatewayservice;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.sinosoft.newstandard.authenticationclient.provider"})
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Value("${service.core.timeout}")
    int coreTimeout;

    @Value("${service.security.timeout}")
    int securityTimeout;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("coreServiceHystrixRoute", r -> r.path("/core-service/**")
                        .filters(f -> f.stripPrefix(1).hystrix(config -> {
                            HystrixCommandProperties.Setter originalSetter = HystrixCommandProperties.Setter();
                            originalSetter.withExecutionTimeoutInMilliseconds(coreTimeout);
                            originalSetter.withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE);
                            HystrixCommandGroupKey customHystrixGroupKey = HystrixCommandGroupKey.Factory.asKey("coreServiceHystrixGroupKey");
                            HystrixCommandKey hystrixCommandKey = HystrixCommandKey.Factory.asKey("coreServiceHystrixKey");
                            HystrixObservableCommand.Setter adapterSetter = HystrixObservableCommand.Setter.withGroupKey(customHystrixGroupKey).andCommandKey(hystrixCommandKey).andCommandPropertiesDefaults(originalSetter);
                            config.setSetter(adapterSetter).setName("coreServiceHystrix").setFallbackUri("forward:/hystrixFallback/serviceUnavailable/core-service");
                        })).uri("lb://core-service"))
                .route("securityServiceHystrixRoute", r -> r.path("/security-service/**")
                        .filters(f -> f.stripPrefix(1).hystrix(config -> {
                            HystrixCommandProperties.Setter originalSetter = HystrixCommandProperties.Setter();
                            originalSetter.withExecutionTimeoutInMilliseconds(securityTimeout);
                            originalSetter.withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE);
                            HystrixCommandGroupKey customHystrixGroupKey = HystrixCommandGroupKey.Factory.asKey("securityServiceHystrixGroupKey");
                            HystrixCommandKey hystrixCommandKey = HystrixCommandKey.Factory.asKey("securityServiceHystrixKey");
                            HystrixObservableCommand.Setter adapterSetter = HystrixObservableCommand.Setter.withGroupKey(customHystrixGroupKey).andCommandKey(hystrixCommandKey).andCommandPropertiesDefaults(originalSetter);
                            config.setSetter(adapterSetter).setName("securityServiceHystrix").setFallbackUri("forward:/hystrixFallback/serviceUnavailable/security-service");
                        })).uri("lb://security-service"))
                .build();
    }

}

