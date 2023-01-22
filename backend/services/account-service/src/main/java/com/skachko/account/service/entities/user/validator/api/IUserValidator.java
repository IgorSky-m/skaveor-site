package com.skachko.account.service.entities.user.validator.api;

import com.skachko.account.service.entities.user.dto.CustomUser;
import com.skachko.account.service.entities.user.dto.UserRequest;

public interface IUserValidator {
    void validateBeforeCreate(CustomUser user);

    void validateBeforeUpdate(CustomUser newUser);
    void validateRequest(UserRequest request);
}
