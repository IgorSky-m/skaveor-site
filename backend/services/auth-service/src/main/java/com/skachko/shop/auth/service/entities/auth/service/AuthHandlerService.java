package com.skachko.shop.auth.service.entities.auth.service;

import com.skachko.shop.auth.service.entities.auth.dto.AuthLoginRequest;
import com.skachko.shop.auth.service.entities.auth.dto.AuthRegisterRequest;
import com.skachko.shop.auth.service.entities.auth.dto.AuthResponse;
import com.skachko.shop.auth.service.entities.auth.service.api.IAuthHandlerService;
import com.skachko.shop.auth.service.entities.auth.validator.api.IAuthValidator;
import com.skachko.shop.auth.service.entities.user.api.EUserRole;
import com.skachko.shop.auth.service.entities.user.dto.CustomUser;
import com.skachko.shop.auth.service.entities.user.service.api.IUserService;
import com.skachko.shop.auth.service.exceptions.AuthValidationException;
import com.skachko.shop.auth.service.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthHandlerService implements IAuthHandlerService {

    private final IUserService userService;
    private final IAuthValidator validator;
    private final MessageSource messageSource;

    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    @Override
    public AuthResponse login(AuthLoginRequest request) {
        validator.validateLogin(request);

        CustomUser userByEmail = userService.getUserByEmail(request.getEmail(), encodePassword(request.getPassword()));

        if (userByEmail == null) {

            throw new AuthValidationException(
                    new AuthValidationException.ValueStructuredError(
                            "email",
                            messageSource.getMessage("auth.not.exist", null, LocaleContextHolder.getLocale()),
                            request.getEmail()
                    ));
        }



        if (!Objects.equals(userByEmail.getPassword(), encodePassword(request.getPassword()))) {
            throw new AuthValidationException(
                    new AuthValidationException.StructuredError(
                            "password",
                            messageSource.getMessage("auth.password.not.match", null, LocaleContextHolder.getLocale())
                    ));
        }


        return new AuthResponse(jwtUtil.generateToken(userByEmail));
    }

    @Transactional
    @Override
    public AuthResponse register(AuthRegisterRequest request) {
        validator.validateRegister(request);

        if (!userService.isEmailExist(request.getEmail())) {

            throw new AuthValidationException(
                    new AuthValidationException.ValueStructuredError(
                            "email",
                            messageSource.getMessage("auth.exist", null, LocaleContextHolder.getLocale()),
                            request.getEmail()
                    ));
        }

        CustomUser savedUser = userService.save(CustomUser.builder()
                .email(request.getEmail())
                .password(encodePassword(request.getPassword()))
                .roles(Collections.singleton(EUserRole.USER))
                .name(request.getName())
                .build());

        return new AuthResponse(jwtUtil.generateToken(savedUser));
    }


    private String encodePassword(String password) {
        return DigestUtils.sha3_256Hex(password);
    }
}
