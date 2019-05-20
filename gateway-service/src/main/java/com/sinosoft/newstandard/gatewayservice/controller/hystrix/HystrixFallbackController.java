package com.sinosoft.newstandard.gatewayservice.controller.hystrix;

import com.sinosoft.newstandard.common.web.entity.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@RestController
@RequestMapping("/hystrixFallback")
public class HystrixFallbackController {

    @RequestMapping("/serviceUnavailable/{serviceName}")
    public Result hystrixFallback(ServerWebExchange exchange, @PathVariable String serviceName) {
        exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
        return Result.fail(HttpStatus.SERVICE_UNAVAILABLE.value(), (serviceName + "不可用."));
    }

}
