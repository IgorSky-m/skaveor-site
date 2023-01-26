package com.skachko.gatewayservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping(value = "/account",  method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE})
    public ResponseEntity<String> accountServiceFallback() {
        return ResponseEntity.badRequest().body("Account service taking longer than Expected. Please try again later");
    }
    @RequestMapping(value = "/catalog",  method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE})
    public ResponseEntity<String> catalogServiceFallback() {
        return ResponseEntity.badRequest().body("Catalog service taking longer than Expected. Please try again later");
    }

    @RequestMapping(value = "/order", method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE})
    public ResponseEntity<String> orderServiceFallback() {
        return ResponseEntity.badRequest().body("Order service taking longer than Expected. Please try again later");
    }

    @RequestMapping(value = "/auth" , method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE})
    public ResponseEntity<String> authServiceFallback() {
        return ResponseEntity.badRequest().body("Authentication service taking longer than Expected. Please try again later");
    }

    @RequestMapping(value = "/news" , method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE})
    public ResponseEntity<String> newsServiceFallback() {
        return ResponseEntity.badRequest().body("news service taking longer than Expected. Please try again later");
    }

    @RequestMapping(value = "/msg" , method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE})
    public ResponseEntity<String> msgServiceFallback() {
        return ResponseEntity.badRequest().body("message service taking longer than Expected. Please try again later");
    }
}
