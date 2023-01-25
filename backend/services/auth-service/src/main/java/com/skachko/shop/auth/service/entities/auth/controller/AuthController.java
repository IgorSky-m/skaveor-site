package com.skachko.shop.auth.service.entities.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skachko.shop.auth.service.entities.auth.dto.AuthLoginRequest;
import com.skachko.shop.auth.service.entities.auth.dto.AuthRegisterRequest;
import com.skachko.shop.auth.service.entities.auth.dto.AuthResponse;
import com.skachko.shop.auth.service.entities.auth.service.api.IAuthHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthHandlerService service;
    private final ObjectMapper mapper;

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

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles(@RequestHeader Map<String, String>headers) {
        return ResponseEntity.ok(service.getRoles(headers));
    }

    @GetMapping("/payload")
    public ResponseEntity<?> getPayload(@RequestHeader Map<String, String>headers) {
        return ResponseEntity.ok(service.getPayload(headers));
    }
}
