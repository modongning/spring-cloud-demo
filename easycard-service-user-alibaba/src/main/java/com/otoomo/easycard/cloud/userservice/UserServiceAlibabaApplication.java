package com.otoomo.easycard.cloud.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//开启服务发现
@EnableDiscoveryClient
//开启fegin客户端
@EnableFeignClients
public class UserServiceAlibabaApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceAlibabaApplication.class, args);
    }
}
