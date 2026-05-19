package com.ecoomrce.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.ecoomrce.service.JwtService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory
        extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final JwtService jwtService;

    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Data
    public static class Config {
        private boolean enabled;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }

            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || authHeader.isBlank()) {
                log.warn("Missing Authorization header");
                return writeUnauthorized(exchange, "Missing Authorization header");
            }

            String token = authHeader.startsWith("Bearer ")
                    ? authHeader.substring(7)
                    : authHeader;

            Long userId;
            try {
                userId = jwtService.getUserIdFromToken(token);
            } catch (Exception e) {
                log.warn("Invalid token: {}", e.getMessage());
                return writeUnauthorized(exchange, "Invalid or expired token");
            }

            log.info("Authenticated userId: {}", userId);

            ServerHttpRequest mutatedRequest = exchange.getRequest()
                    .mutate()
                    .header("X-User-Id", String.valueOf(userId))
                    .build();

            return chain.filter(exchange.mutate()
                    .request(mutatedRequest)
                    .build());
        };
    }

    private Mono<Void> writeUnauthorized(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String body = "{\"status\": 401, \"error\": \"Unauthorized\", \"message\": \"" + message + "\"}";
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        return response.writeWith(Mono.just(buffer));
    }
}