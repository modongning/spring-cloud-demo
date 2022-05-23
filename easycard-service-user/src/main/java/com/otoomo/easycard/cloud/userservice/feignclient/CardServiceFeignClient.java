package com.otoomo.easycard.cloud.userservice.feignclient;

import com.otoomo.easycard.cloud.userservice.feignclient.fallback.CardServiceFallback;
import com.otoomo.easycard.common.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        //配置当前feign客户端请求的接口的服务提供者serviceId
        //feign会结合ribbon负载均衡将serviceId替换成对应服务的真实链接发起请求
        name = "easycard-service-card-alibaba",
        //当前接口的前置请求路径，这个就是对应在Controller类上注解的@RquestMapping("/api/card")
        //这里使用FeignClient，使用path声明前置请求路径
        path = "/card",
        fallback = CardServiceFallback.class
)
//添加到容器管理中
@Component
public interface CardServiceFeignClient {

    //@PathVariable("userId")value必须设置，否则会抛出异常
    @GetMapping("/getUserCardList/{userId}")
    Result getUserCardList(@PathVariable("userId") Long userId);
}
