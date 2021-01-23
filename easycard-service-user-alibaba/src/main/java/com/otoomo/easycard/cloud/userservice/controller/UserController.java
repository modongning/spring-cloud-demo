package com.otoomo.easycard.cloud.userservice.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.otoomo.commonbase.pojo.Result;
import com.otoomo.easycard.cloud.userservice.feignclient.CardServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

    @Value("${redis.ip}")
    private String redisIp;

    @Autowired
    private CardServiceFeignClient cardServiceFeignClient;

    @GetMapping("/getUserCardList/{userId}")
    public Result getUserCardList(@PathVariable Long userId) {
        System.out.println("----->"+redisIp);
        Result userCardListResult = cardServiceFeignClient.getUserCardList(userId);
        return userCardListResult;
    }
}
