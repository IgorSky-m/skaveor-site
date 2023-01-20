package com.skachko.shop.news.service.libraries.mvc.exceptions;

import com.skachko.shop.news.service.advice.ExceptionHandlerAdvice;

/**
 * Exception used when we can't find an entity from database
 * User in ExceptionHandlerAdvice for 204 code
 * @see ExceptionHandlerAdvice
 */
public class EntityNotFoundException extends RuntimeException{

    /**
     * {@inheritDoc}
     */
    public EntityNotFoundException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * {@inheritDoc}
     */
    public EntityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
