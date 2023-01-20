package com.skachko.shop.news.service.exceptions;

public class NewsServiceException extends RuntimeException{

    public NewsServiceException() {
    }

    public NewsServiceException(String message) {
        super(message);
    }

    public NewsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
