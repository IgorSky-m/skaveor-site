package com.skachko.gateway.filters;

import com.skachko.gateway.foreign.auth.ValidateTokenResult;
import com.skachko.gateway.foreign.auth.client.IAuthForeignClient;
import com.skachko.gateway.validators.RouterValidator;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RefreshScope
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouterValidator routerValidator;
    private final IAuthForeignClient authClient;

    public AuthenticationFilter(RouterValidator routerValidator, @Lazy IAuthForeignClient authClient) {
        super(Config.class);
        this.routerValidator = routerValidator;
        this.authClient = authClient;

    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            boolean secured = routerValidator.isSecured(request);
            boolean authMissing = isAuthMissing(request);

            //1 check is auth missing
            if (authMissing) {
                //if url is secured - return error
                if (secured) {
                    return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
                }
                //if not just go to next filter
                return chain.filter(exchange);
            }

            //2 get validate result for token with auth service
            ValidateTokenResult result;
            try {
                result = authClient.validateToken(getToken(request));
            } catch (Exception e) {
                //if catch some exception - can't proceed
                return this.onError(exchange, "Something wrong, please try again later", HttpStatus.UNAUTHORIZED);
            }

            //3 if token invalid - return unauthorized error
            if (ValidateTokenResult.EValidateTokenResult.FAIL.equals(result.getResult())) {
                //if secured - error
                if (secured) {
                    return this.onError(exchange, result.getMessage(), HttpStatus.UNAUTHORIZED);
                    //if unsecured - go to next filter
                } else {
                    return chain.filter(exchange);
                }
            }

            //4 if url secured and is not allowed for current user roles - return error
            if (secured && isNotAllowed(result.getPayload().getRoles(), config.allowedRoles)) {
                return this.onError(exchange, "Not Allowed", HttpStatus.FORBIDDEN);
            }

            //5 populate request with payload headers
            this.populateRequestWithHeaders(exchange, result.getPayload());

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getToken(ServerHttpRequest request) {
        final String header = this.getAuthHeader(request);

        return header != null && header.startsWith("Bearer") ? header.substring(7) :
                header;
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, ValidateTokenResult.TokenPayload payload) {
        exchange.getRequest().mutate()
                .header("id", payload.getId().toString())
                .header("version", payload.getVersion().toString())
                .header("name", payload.getName())
                .header("roles", String.join(",", payload.getRoles()))
                .build();
    }

    private boolean isNotAllowed(List<String> roles, Set<String> allowedRoles) {
        if (roles == null || roles.isEmpty()) {
            return true;
        }

        return roles.stream()
                .noneMatch(allowedRoles::contains);
    }

    static class Config {
        private String name;
        private Set<String> allowedRoles = new HashSet<>();

        public Config() {
        }

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