package com.skachko.shop.auth.service.entities.auth.validator.api;

import com.skachko.shop.auth.service.entities.auth.dto.AuthRequest;

@FunctionalInterface
public interface IAuthValidator {
    void validate(AuthRequest request);
}
