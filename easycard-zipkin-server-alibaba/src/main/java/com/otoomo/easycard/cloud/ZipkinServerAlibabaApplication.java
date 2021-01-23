package com.otoomo.easycard.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin2.server.internal.EnableZipkinServer;

@SpringBootApplication
@EnableDiscoveryClient
//开启zipkin服务
@EnableZipkinServer
public class ZipkinServerAlibabaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZipkinServerAlibabaApplication.class, args);
    }
}
