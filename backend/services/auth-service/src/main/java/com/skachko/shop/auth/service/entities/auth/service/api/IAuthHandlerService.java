package com.skachko.shop.auth.service.entities.auth.service.api;

import com.skachko.shop.auth.service.entities.auth.dto.AuthRequest;
import com.skachko.shop.auth.service.entities.auth.dto.AuthResponse;

public interface IAuthHandlerService {
    AuthResponse login(AuthRequest request);
    AuthResponse register(AuthRequest request);
}
