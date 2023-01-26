package com.skachko.shop.auth.service.entities.auth.service;

import com.skachko.shop.auth.service.entities.auth.dto.AuthHistory;
import com.skachko.shop.auth.service.entities.auth.dto.AuthLoginRequest;
import com.skachko.shop.auth.service.entities.auth.dto.AuthRegisterRequest;
import com.skachko.shop.auth.service.entities.auth.dto.AuthResponse;
import com.skachko.shop.auth.service.entities.auth.dto.api.EAuthAction;
import com.skachko.shop.auth.service.entities.auth.service.api.IAuthHandlerService;
import com.skachko.shop.auth.service.entities.auth.service.api.IAuthHistoryService;
import com.skachko.shop.auth.service.entities.auth.validator.api.IAuthValidator;
import com.skachko.shop.auth.service.entities.user.api.EUserRole;
import com.skachko.shop.auth.service.entities.user.dto.CustomUser;
import com.skachko.shop.auth.service.entities.user.service.api.IUserService;
import com.skachko.shop.auth.service.exceptions.AuthValidationException;
import com.skachko.shop.auth.service.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthHandlerService implements IAuthHandlerService {

    private final IUserService userService;
    private final IAuthValidator validator;
    private final MessageSource messageSource;

    private final JwtUtil jwtUtil;
    private final IAuthHistoryService historyService;

    @Override
    public AuthResponse login(AuthLoginRequest request) {
        validator.validateLogin(request);
        CustomUser userByEmail = userService.getUserByCredentials(request.getEmail(), request.getPassword());

        createHistory(userByEmail.getId(), new Date(), EAuthAction.LOGIN);

        return new AuthResponse(jwtUtil.generateToken(userByEmail));
    }


    @Override
    public AuthResponse register(AuthRegisterRequest request) {
        validator.validateRegister(request);

        Boolean emailExist = userService.isEmailExist(request.getEmail());

        if (Boolean.TRUE.equals(emailExist)) {
            throw new AuthValidationException(
                    new AuthValidationException.ValueStructuredError(
                            "email",
                            messageSource.getMessage("auth.exist", null, LocaleContextHolder.getLocale()),
                            request.getEmail()
                    ));
        }

        CustomUser build = CustomUser.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .roles(Collections.singleton(EUserRole.USER))
                .name(request.getName())
                .build();
        CustomUser savedUser = userService.save(build);

        createHistory(savedUser.getId(), savedUser.getDtCreate(), EAuthAction.REGISTER);


        return new AuthResponse(jwtUtil.generateToken(savedUser));
    }

    @Override
    public String[] getRoles(Map<String, String> headers) {
        String role = headers.getOrDefault("roles", null);
        String[] substring;
        if (role != null) {
            substring = role.substring(1, role.length() - 1).split(", ");
        } else {
            substring = new String[0];
        }

        return substring;
    }

    @Override
    public Map<String, Object> getPayload(Map<String, String> headers) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("roles", getRoles(headers));
        payload.put("id", headers.getOrDefault("id", null));
        payload.put("name", headers.getOrDefault("name", null));
        return payload;
    }

    private void createHistory(UUID userId, Date date, EAuthAction action) {
        try {
            historyService.save(
                    AuthHistory.builder()
                            .action(action)
                            .dtAction(date)
                            .userId(userId)
                            .build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
