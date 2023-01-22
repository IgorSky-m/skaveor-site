package com.skachko.account.service.entities.user.validator;

import com.skachko.account.service.entities.user.dto.CustomUser;
import com.skachko.account.service.entities.user.dto.UserRequest;
import com.skachko.account.service.entities.user.validator.api.IUserValidator;
import com.skachko.account.service.exceptions.AccountServiceValidationException;
import com.skachko.account.service.support.utils.IsEmptyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidator implements IUserValidator {
    private static final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    private final MessageSource messageSource;

    @Override
    public void validateBeforeCreate(CustomUser user) {
        List<AccountServiceValidationException.StructuredError> errors = new ArrayList<>();

        if (user == null) {
            errors.add(
                    new AccountServiceValidationException.StructuredError(
                            messageSource.getMessage("error.main.entity.empty", null, LocaleContextHolder.getLocale()))
            );

            throw new AccountServiceValidationException(errors);
        }

        validateEmail(user.getEmail(), errors);

        validatePassword(user.getPassword(), errors);

        if (IsEmptyUtil.isNullOrEmpty(user.getRoles())) {
            errors.add(
                    new AccountServiceValidationException.StructuredFieldError(
                            "roles",
                            messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale()))
            );
        }


        if (!errors.isEmpty()) {
            throw new AccountServiceValidationException(errors);
        }
    }

    @Override
    public void validateBeforeUpdate(CustomUser user) {
        List<AccountServiceValidationException.StructuredError> errors = new ArrayList<>();

        if (user == null) {
            errors.add(
                    new AccountServiceValidationException.StructuredError(
                            messageSource.getMessage("error.main.entity.empty", null, LocaleContextHolder.getLocale()))
            );

            throw new AccountServiceValidationException(errors);
        }

        validateEmail(user.getEmail(), errors);

        validatePassword(user.getPassword(), errors);

        if (IsEmptyUtil.isNullOrEmpty(user.getRoles())) {
            errors.add(
                    new AccountServiceValidationException.StructuredFieldError(
                            "roles",
                            messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale()))
            );
        }


        if (!errors.isEmpty()) {
            throw new AccountServiceValidationException(errors);
        }
    }


    @Override
    public void validateRequest(UserRequest request) {
        List<AccountServiceValidationException.StructuredError> errors = new ArrayList<>();

        if (request == null) {
            errors.add(
                    new AccountServiceValidationException.StructuredError(
                            messageSource.getMessage("error.main.entity.empty", null, LocaleContextHolder.getLocale()))
            );

            throw new AccountServiceValidationException(errors);
        }

        validateEmail(request.getEmail(), errors);
        validatePassword(request.getPassword(), errors);

        if (!errors.isEmpty()) {
            throw new AccountServiceValidationException(errors);
        }
    }




    private void validatePassword(String password, List<AccountServiceValidationException.StructuredError> errors) {
        if (IsEmptyUtil.isNullOrEmpty(password)) {
            errors.add(
                    new AccountServiceValidationException.StructuredFieldError(
                            "password",
                            messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale()))
            );
        }
    }

    private void validateEmail(String email, List<AccountServiceValidationException.StructuredError> errors) {
        if (IsEmptyUtil.isNullOrEmpty(email)) {
            errors.add(
                    new AccountServiceValidationException.StructuredFieldError(
                            "email",
                            messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale()))
            );
        } else if (!emailPattern.matcher(email).matches()){
            errors.add(
                    new AccountServiceValidationException.StructuredFieldError(
                            "email",
                            messageSource.getMessage("validation.email.invalid", new Object[]{email}, LocaleContextHolder.getLocale()))
            );
        }
    }


}
