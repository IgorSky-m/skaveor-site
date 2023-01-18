package com.skachko.shop.auth.service.entities.auth.controller;

import com.skachko.shop.auth.service.entities.auth.dto.AuthRequest;
import com.skachko.shop.auth.service.entities.auth.dto.AuthResponse;
import com.skachko.shop.auth.service.entities.auth.service.api.IAuthHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthHandlerService service;


    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request){
        return service.login(request);
    }

    
    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request){
        return service.register(request);
    }

}
