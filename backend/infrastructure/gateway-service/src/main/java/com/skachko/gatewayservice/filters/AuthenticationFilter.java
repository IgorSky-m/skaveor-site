package com.skachko.gatewayservice.filters;

import com.skachko.gatewayservice.utils.JwtUtil;
import com.skachko.gatewayservice.validators.RouterValidator;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;

@RefreshScope
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouterValidator routerValidator;//custom route validator
    private final JwtUtil jwtUtil;


    public AuthenticationFilter(RouterValidator routerValidator, JwtUtil jwtUtil) {
        super(Config.class);
        this.routerValidator = routerValidator;
        this.jwtUtil = jwtUtil;

    }


    private String getToken(ServerHttpRequest request) {
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

    private Map<String, Object> getPayload(String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        return new HashMap<>() {{
            put("id", claims.get("id"));
            put("roles", claims.get("roles"));
            put("name", claims.get("name"));
        }};
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, Map<String, Object> payload) {

        ServerHttpRequest.Builder mutate = exchange.getRequest().mutate();
        payload.forEach((k, v) -> mutate.header(k, String.valueOf(v)));
        mutate.build();
    }



    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
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

                Map<String, Object> payload = getPayload(token);
                if (isNotAllowed((List<String>)payload.get("roles"), config.allowedRoles)) {
                    return this.onError(exchange, "Not Allowed", HttpStatus.FORBIDDEN);
                }
                this.populateRequestWithHeaders(exchange, payload);

            } else if (!this.isAuthMissing(request)) {
                String token = getToken(request);

                if (!jwtUtil.isInvalid(token)) {
                    Map<String, Object> payload = getPayload(token);
                    if (isNotAllowed((List<String>)payload.get("roles"), config.allowedRoles)) {
                        return this.onError(exchange, "Not Allowed", HttpStatus.FORBIDDEN);
                    }
                    this.populateRequestWithHeaders(exchange, payload);
                }

            }

            return chain.filter(exchange);
        };
    }

    private boolean isNotAllowed(List<String> roles, Set<String> allowedRoles) {
        if (roles == null || roles.isEmpty()) {
            return false;
        }
        return roles.stream()
                .noneMatch(allowedRoles::contains);
    }


    static class Config {
        private String name;
        private Set<String> allowedRoles = new HashSet<>();

        public Config(){}

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Set<String> getAllowedRoles() {
            return allowedRoles;
        }

        public void setAllowedRoles(Set<String> allowedRoles) {
            this.allowedRoles = allowedRoles;
        }
    }
}

