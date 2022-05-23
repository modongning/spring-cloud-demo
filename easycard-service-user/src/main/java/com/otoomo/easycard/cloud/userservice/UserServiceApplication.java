package com.otoomo.easycard.cloud.userservice;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//Eureka注册中心，也可以使用这个开启服务发现，为了通用性，建议使用@EnableDiscoveryClient即可
//@EnableEurekaClient
//开启服务发现
@EnableDiscoveryClient
//开启熔断器
//启用@EnableFeignClients后，@EnableCircuitBreaker就不需要了，Feign会自动引入熔断功能
//@EnableCircuitBreaker
//开启Feign客户端
@EnableFeignClients
public class UserServiceApplication {
    public static void main(String[] args) {
//        SpringApplication.run(UserServiceApplication.class, args);

    }

    //使用Feign后，Feign会自动引入RestTemplate到容器中进行使用，所有也不需要单独添加
//    @Bean
//    //开启负载均衡
//    @LoadBalanced
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }

    /**
     * 对外发布一个监控接口，hystrix dashboard会通过这个接口获取对应的数据，展示到仪表盘上
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
