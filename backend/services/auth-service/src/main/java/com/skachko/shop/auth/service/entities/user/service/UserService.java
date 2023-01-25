package com.skachko.shop.auth.service.entities.user.service;


import com.skachko.shop.auth.service.entities.user.client.api.IUserFeignClient;
import com.skachko.shop.auth.service.entities.user.dto.CustomUser;
import com.skachko.shop.auth.service.entities.user.dto.UserRequest;
import com.skachko.shop.auth.service.entities.user.service.api.IUserService;
import com.skachko.shop.auth.service.exceptions.AuthValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {

    private final IUserFeignClient client;
    private final MessageSource messageSource;

    @Override
    public CustomUser getUserByCredentials(String email, String password) {

        CustomUser user = client.getByEmailAndPassword(new UserRequest(email, password));

        if (user == null) {
            Boolean emailExist = client.isEmailExist(email).getBody();
            String msg;
            String field;
            if (Boolean.TRUE.equals(emailExist)) {
                msg = messageSource.getMessage("auth.password.not.match", null, LocaleContextHolder.getLocale());
                field = "password";
            } else  {
                msg = messageSource.getMessage("auth.not.exist", null, LocaleContextHolder.getLocale());
                field = "email";
            }

            throw new AuthValidationException(List.of(new AuthValidationException.StructuredError(field, msg)));
        }

        return user;
    }

    @Override
    public Boolean isEmailExist(String email) {
        return client.isEmailExist(email)
                .getBody();
    }


    @Override
    public CustomUser save(CustomUser customUser) {
        return client.create(customUser);
    }
}
