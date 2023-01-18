package com.skachko.shop.payment.service.exceptions;

public class PaymentDetailsException extends RuntimeException{
    public PaymentDetailsException() {
    }

    public PaymentDetailsException(String message) {
        super(message);
    }

    public PaymentDetailsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentDetailsException(Throwable cause) {
        super(cause);
    }

    public PaymentDetailsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
