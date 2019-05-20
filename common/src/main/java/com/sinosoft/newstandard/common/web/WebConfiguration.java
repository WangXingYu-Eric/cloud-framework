package com.sinosoft.newstandard.common.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Eric
 * @Date: 2019-05-13
 **/
@Configuration
@MapperScan("com.sinosoft.newstandard.common.web.mapper")
@ComponentScan(value = "com.sinosoft.newstandard.common.web.service", lazyInit = true)//lazyInit = true防止service比mapper提前加载导致报错
public class WebConfiguration {
}
