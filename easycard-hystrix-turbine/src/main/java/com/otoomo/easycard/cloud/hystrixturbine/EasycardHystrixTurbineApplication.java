package com.otoomo.easycard.cloud.hystrixturbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

//开启聚合功能
@EnableTurbine
@EnableDiscoveryClient
@SpringBootApplication
public class EasycardHystrixTurbineApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasycardHystrixTurbineApplication.class, args);
    }
}
