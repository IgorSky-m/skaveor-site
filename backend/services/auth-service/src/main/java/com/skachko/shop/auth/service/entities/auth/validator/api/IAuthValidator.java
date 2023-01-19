package com.skachko.shop.auth.service.entities.auth.validator.api;

import com.skachko.shop.auth.service.entities.auth.dto.AuthLoginRequest;
import com.skachko.shop.auth.service.entities.auth.dto.AuthRegisterRequest;


public interface IAuthValidator {
    void validateLogin(AuthLoginRequest request);
    void validateRegister(AuthRegisterRequest request);
}
