package com.skachko.shop.auth.service.entities.auth.controller;

import com.skachko.shop.auth.service.entities.auth.dto.AuthLoginRequest;
import com.skachko.shop.auth.service.entities.auth.dto.AuthRegisterRequest;
import com.skachko.shop.auth.service.entities.auth.dto.AuthResponse;
import com.skachko.shop.auth.service.entities.auth.service.api.IAuthHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthHandlerService service;


    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthLoginRequest request){
        return service.login(request);
    }


    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRegisterRequest request){
        return service.register(request);
    }

    /**
     * empty because already validated at gateway and just return 200 if ok
     */
    @GetMapping("/validate")
    public ResponseEntity<?> validate() {
        return ResponseEntity.ok().build();
    }

}
