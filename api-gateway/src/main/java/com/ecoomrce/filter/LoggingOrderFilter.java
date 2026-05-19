package com.ecoomrce.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoggingOrderFilter extends AbstractGatewayFilterFactory<LoggingOrderFilter.Config>{

	public LoggingOrderFilter() {
        super(Config.class);
    }
    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("Order Filter pre: {}" + exchange.getRequest().getURI());
            log.info("Order Filter pre: {}" + exchange.getRequest().getMethod());

            return chain.filter(exchange);
//            .then(Mono.fromRunnable(() -> {
//                System.out.println("🟢 Response Status: " + exchange.getResponse().getStatusCode());
//            }));
        };
    }
}
