package com.skachko.libraries.mvc.exceptions;

import lombok.*;

public class StructuredError {

    private final String msg;

    public StructuredError(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
