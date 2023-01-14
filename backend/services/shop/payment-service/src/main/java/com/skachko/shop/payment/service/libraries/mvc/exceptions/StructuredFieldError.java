package com.skachko.shop.payment.service.libraries.mvc.exceptions;

public class StructuredFieldError extends StructuredError{
    private final String field;

    public StructuredFieldError(String field, String msg) {
        super(msg);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
