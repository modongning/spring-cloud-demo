package com.otoomo.easycard.cloud.cardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//开启服务发现
@EnableDiscoveryClient
public class CardServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CardServiceApplication.class, args);
    }
}
