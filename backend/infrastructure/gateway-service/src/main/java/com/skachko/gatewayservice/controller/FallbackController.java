package com.skachko.gatewayservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping(value = "/catalog",  method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE})
    public String catalogServiceFallback() {
        return "Catalog service taking longer than Expected. Please try again later";
    }

    @RequestMapping(value = "/order", method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE})
    public String orderServiceFallback() {
        return "Order service taking longer than Expected. Please try again later";
    }

    @RequestMapping(value = "/auth" , method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE})

    public String authServiceFallback() {
        return "Authentication service taking longer than Expected. Please try again later";
    }
}
