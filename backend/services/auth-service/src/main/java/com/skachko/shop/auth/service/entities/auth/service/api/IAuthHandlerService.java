package com.skachko.shop.auth.service.entities.auth.service.api;

import com.skachko.shop.auth.service.entities.auth.dto.AuthLoginRequest;
import com.skachko.shop.auth.service.entities.auth.dto.AuthRegisterRequest;
import com.skachko.shop.auth.service.entities.auth.dto.AuthResponse;

public interface IAuthHandlerService {
    AuthResponse login(AuthLoginRequest request);
    AuthResponse register(AuthRegisterRequest request);
}
