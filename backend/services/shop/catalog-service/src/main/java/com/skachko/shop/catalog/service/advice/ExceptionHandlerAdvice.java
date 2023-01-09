package com.skachko.shop.catalog.service.advice;

import com.skachko.shop.catalog.service.libraries.mvc.exceptions.EntityNotFoundException;
import com.skachko.shop.catalog.service.libraries.mvc.exceptions.ServiceException;
import com.skachko.shop.catalog.service.libraries.mvc.exceptions.StructuredError;
import com.skachko.shop.catalog.service.libraries.mvc.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;

/**
 * Exception handler for handle any controller exceptions
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    private final MessageSource messageSource;

    public ExceptionHandlerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String handleNotFoundException(EntityNotFoundException e) {
        return messageSource.getMessage("error.rest.not.found", null, LocaleContextHolder.getLocale());
    }

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleServiceException(ServiceException e) {
        e.printStackTrace();
        return e.getMessage() == null ?
                e.getMessage() :
                messageSource.getMessage("error.rest.bad.request", null, LocaleContextHolder.getLocale());
    }


    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Collection<StructuredError> handleValidationException(ValidationException e) {
        return e.getErrors();
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        e.printStackTrace();
        return messageSource.getMessage("error.rest.internal.server.error", null, LocaleContextHolder.getLocale());
    }


}
