package com.skachko.shop.account.service.exceptions;

public class MsgServiceException extends RuntimeException{

    public MsgServiceException() {
    }

    public MsgServiceException(String message) {
        super(message);
    }

    public MsgServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
