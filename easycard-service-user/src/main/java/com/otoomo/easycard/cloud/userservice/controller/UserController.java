package com.otoomo.easycard.cloud.userservice.controller;

import com.google.common.collect.ImmutableList;
import com.otoomo.easycard.cloud.userservice.feignclient.CardServiceFeignClient;
import com.otoomo.easycard.common.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserController {
//    @Autowired
//    private RestTemplate restTemplate;


    /**
     * hystrix熔断回退方法
     */
    public Result hystrixFallback(Long userId) {
        return Result.success(new ArrayList<>());
    }

    @Autowired
    private CardServiceFeignClient cardServiceFeignClient;

    //引入Feign后，这里就不需要这样配置了，具体配置可以在application.yal中配置
//    @HystrixCommand(
//            //发生溶解降级的回退方法
//            fallbackMethod = "hystrixFallback",
//            //commandProperties用来配置一些细节属性
//            commandProperties = {
//                    //配置超时时间
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
//            }
//    )
    @GetMapping("/getUserCardList/{userId}")
    public Result getUserCardList(@PathVariable Long userId) {
        String serviceId = "EASYCARD-SERVICE-CARD";
        //没有开启负载均衡前的请求方式
//        List cardList = restTemplate.getForObject("http://127.0.0.1:8080/api/card/"+userId, Integer.class);
        //开启了负载均衡的请求方式，使用serviceId请求即可
//        List cardList = restTemplate.getForObject("http://" + serviceId + "/api/card/" + userId, List.class);

        Result userCardListResult = cardServiceFeignClient.getUserCardList(1L);
        return userCardListResult;
    }
}
