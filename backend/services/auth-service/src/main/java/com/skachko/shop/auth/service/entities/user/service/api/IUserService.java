package com.skachko.shop.auth.service.entities.user.service.api;

import com.skachko.shop.auth.service.entities.user.dto.CustomUser;

public interface IUserService {
    CustomUser getUserByCredentials(String email, String password);
    Boolean isEmailExist(String email);

    CustomUser save(CustomUser customUser);
}
