package com.otoomo.easycard.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OAuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAuthServerApplication.class, args);
    }
}
