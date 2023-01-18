package com.skachko.shop.auth.service.libraries.mvc.exceptions;

public class StructuredError {

    private final String msg;

    public StructuredError(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
