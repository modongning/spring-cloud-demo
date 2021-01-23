package com.otoomo.easycard.cloud.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局日志拦截器
 */
@Component
@Slf4j
public class GlobalLogFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        long start = System.currentTimeMillis();
        ServerHttpRequest request = exchange.getRequest();

        log.info("start request:{}", request.getURI());

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long end = System.currentTimeMillis();
            log.info("请求耗时：{}", (end - start));
        }));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
