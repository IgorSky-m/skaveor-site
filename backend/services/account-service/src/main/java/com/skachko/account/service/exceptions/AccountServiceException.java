package com.skachko.account.service.exceptions;

public class AccountServiceException extends RuntimeException{

    public AccountServiceException() {
    }

    public AccountServiceException(String message) {
        super(message);
    }

    public AccountServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
