package com.skachko.shop.account.service.advice;

import com.skachko.shop.account.service.exceptions.MsgServiceException;
import com.skachko.shop.account.service.exceptions.MsgServiceValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

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
    @ExceptionHandler(MsgServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAccException(MsgServiceException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MsgServiceValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<MsgServiceValidationException.StructuredError> handleAccValidation(MsgServiceValidationException e) {
        return e.getStructuredErrors();
    }


    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        e.printStackTrace();
        return messageSource.getMessage("error.rest.internal.server.error", null, LocaleContextHolder.getLocale());
    }


}
