package com.skachko.shop.auth.service.entities.auth.validator;

import com.skachko.shop.auth.service.entities.auth.validator.api.IAuthValidator;
import com.skachko.shop.auth.service.entities.auth.dto.AuthRequest;
import com.skachko.shop.auth.service.exceptions.AuthValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthValidator implements IAuthValidator {

    private final MessageSource messageSource;

    @Override
    public void validate(AuthRequest request) {
        List<AuthValidationException.StructuredError> errors = new ArrayList<>();
        if (isEmpty(request.getEmail())) {
            errors.add(
                    new AuthValidationException.StructuredError(
                            "email",
                            messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale()))
            );
        }

        if (isEmpty(request.getPassword())) {
            errors.add(
                    new AuthValidationException.StructuredError(
                            "password",
                            messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale()))
            );
        }


        if (!errors.isEmpty()) {
            throw new AuthValidationException();
        }
    }



    boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }
}
