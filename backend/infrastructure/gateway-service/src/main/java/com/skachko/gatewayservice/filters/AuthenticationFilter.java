package com.skachko.gatewayservice.filters;

import com.skachko.gatewayservice.utils.JwtUtil;
import com.skachko.gatewayservice.validators.RouterValidator;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter
        , GatewayFilterFactory<AuthenticationFilter.Config>
{


    private final RouterValidator routerValidator;//custom route validator
    private final JwtUtil jwtUtil;

    public AuthenticationFilter(RouterValidator routerValidator, JwtUtil jwtUtil) {
        this.routerValidator = routerValidator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            if (this.isAuthMissing(request)) {
                return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
            }

            final String token = getToken(request);
            try {
                if (jwtUtil.isInvalid(token)) {
                    throw new IllegalArgumentException();
                }
            } catch (Exception e) {
                return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
            }

            this.populateRequestWithHeaders(exchange, token);

        } else if (!this.isAuthMissing(request)) {
            String token = getToken(request);
            if (!jwtUtil.isInvalid(token)) {
                this.populateRequestWithHeaders(exchange, token);
            }
        }


        return chain.filter(exchange);
    }

    private String getToken(ServerHttpRequest request){
        final String header = this.getAuthHeader(request);

        return header != null && header.startsWith("Bearer") ? header.substring(7) :
                header;
    }


    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("id", String.valueOf(claims.get("id")))
                .header("role", String.valueOf(claims.get("role")))
                .build();
    }

    @Override
    public Config newConfig() {
        return new Config("AuthenticationFilter");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return this;
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }

    static class Config{
        private final String name;

        public Config(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

