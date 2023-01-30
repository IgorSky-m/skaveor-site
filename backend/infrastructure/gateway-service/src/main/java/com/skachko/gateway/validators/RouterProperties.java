package com.skachko.gateway.validators;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "gateway.service.authentication.filter")
public class RouterProperties {

    private Endpoints endpoints;

    public Endpoints getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Endpoints endpoints) {
        this.endpoints = endpoints;
    }

    public static class Endpoints {
        private List<String> open;

        public List<String> getOpen() {
            return open;
        }

        public void setOpen(List<String> open) {
            this.open = open;
        }
    }
}

