package com.otoomo.easycard.cloud.userservice.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Feign的⽇志级别（Feign请求过程信息）
 *
 * NONE：默认的，不显示任何⽇志：性能最好
 * BASIC：仅记录请求⽅法、URL、响应状态码以及执⾏时间: ⽣产问题追踪
 * HEADERS：在BASIC级别的基础上，记录请求和响应的header
 * FULL：记录请求和响应的header、body和元数据：适⽤于开发及测试环境定位问题
 */
@Configuration
public class FeignLogConfig {
    @Bean
    Logger.Level feignLeven() {
        return Logger.Level.BASIC;
    }
}
