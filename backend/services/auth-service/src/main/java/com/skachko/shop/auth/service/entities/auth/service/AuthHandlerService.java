package com.skachko.shop.auth.service.entities.auth.service;

import com.skachko.shop.auth.service.entities.auth.dto.*;
import com.skachko.shop.auth.service.entities.auth.dto.api.EAuthAction;
import com.skachko.shop.auth.service.entities.auth.service.api.IAuthHandlerService;
import com.skachko.shop.auth.service.entities.auth.service.api.IAuthHistoryService;
import com.skachko.shop.auth.service.entities.auth.validator.api.IAuthValidator;
import com.skachko.shop.auth.service.entities.user.api.EUserRole;
import com.skachko.shop.auth.service.entities.user.dto.CustomUser;
import com.skachko.shop.auth.service.entities.user.service.api.IUserService;
import com.skachko.shop.auth.service.exceptions.AuthValidationException;
import com.skachko.shop.auth.service.exceptions.UnauthorizedException;
import com.skachko.shop.auth.service.support.utils.IsEmptyUtil;
import com.skachko.shop.auth.service.utils.JwtUtil;
import io.jsonwebtoken.Claims;
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

        createHistory(savedUser.getId(), new Date(savedUser.getDtCreate()), EAuthAction.REGISTER);


        return new AuthResponse(jwtUtil.generateToken(savedUser));
    }

    @Override
    public String[] getRoles(Map<String, String> headers) {
        String roles = headers.getOrDefault("roles", null);
        String[] substring;
        if (roles != null) {
            substring = roles.split(",");
        } else {
            substring = new String[0];
        }

        return substring;
    }

    @Override
    public Map<String, Object> getPayload(Map<String, String> headers) {
        isValid(headers);
        Map<String, Object> payload = new HashMap<>();
        payload.put("roles", getRoles(headers));
        payload.put("id", headers.getOrDefault("id", null));
        payload.put("name", headers.getOrDefault("name", null));
        payload.put("version", headers.getOrDefault("version", null));
        return payload;
    }

    @Override
    public ValidateTokenResult validateToken(String token) {
        ValidateTokenResult.EValidateTokenResult result = ValidateTokenResult.EValidateTokenResult.SUCCESS;
        String msg = null;
        ValidateTokenResult.TokenPayload payload = null;

        if (jwtUtil.isInvalid(token)) {
            result = ValidateTokenResult.EValidateTokenResult.FAIL;
            msg = messageSource.getMessage("unauthorized.error.invalid.header", null, LocaleContextHolder.getLocale());
        } else {
            Claims claims = jwtUtil.getAllClaimsFromToken(token);

            try {
                UUID id = UUID.fromString(claims.get("id", String.class));

                CustomUser user = userService.getById(id);

                Long version = claims.get("version", Long.class);

                if (!Objects.equals(version, user.getDtUpdate())) {
                    throw new UnauthorizedException();
                }

                payload = ValidateTokenResult.TokenPayload.builder()
                        .id(id)
                        .version(version)
                        .name(claims.get("name", String.class))
                        .roles((List<String>)claims.get("roles"))
                        .build();

            } catch (Exception e) {
                result = ValidateTokenResult.EValidateTokenResult.FAIL;
                msg = messageSource.getMessage("unauthorized.error.invalid.header", null, LocaleContextHolder.getLocale());
            }
        }

        return ValidateTokenResult.builder()
                .message(msg)
                .result(result)
                .payload(payload)
                .build();
    }

    @Override
    public void isValid(Map<String, String> headers) {

        if (IsEmptyUtil.isNotNullOrEmpty(headers)) {
            String id = headers.getOrDefault("id", null);
            String date = headers.getOrDefault("version", null);
            if (IsEmptyUtil.isNotNullOrEmpty(id) && IsEmptyUtil.isNotNullOrEmpty(date)) {
                CustomUser user;
                try {
                    user = userService.getById(UUID.fromString(id));
                } catch (Exception e) {
                    throw new UnauthorizedException();
                }
                if (Long.parseLong(date) != user.getDtUpdate()) {
                    throw new UnauthorizedException();
                }
            }
        }
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
