package com.skachko.gateway.validators;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouterValidator {

    private final RouterProperties routerProperties;

    public RouterValidator(@NotNull RouterProperties routerProperties) {
       this.routerProperties = routerProperties;
    }

    public boolean isSecured(ServerHttpRequest request) {
        return routerProperties.getEndpoints().getOpen()
                .stream()
                .noneMatch(uri -> request.getURI().getPath().contains(uri));
    }

    public boolean isOpen(ServerHttpRequest request){
        return routerProperties.getEndpoints().getOpen()
                .stream()
                .anyMatch(uri -> request.getURI().getPath().contains(uri));
    }

}
