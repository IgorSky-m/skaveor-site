package com.skachko.shop.order.service.exceptions;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OrderValidationException extends RuntimeException {

    private final List<StructuredError> structuredErrors;


    public OrderValidationException(List<StructuredError> structuredErrors) {
        this.structuredErrors = new ArrayList<>(structuredErrors);
    }

    public OrderValidationException(StructuredError... errors) {
        structuredErrors = new ArrayList<>(Arrays.asList(errors));
    }

    public List<StructuredError> getStructuredErrors() {
        return structuredErrors;
    }

    public static class StructuredError {
        private final String field;
        private final String msg;

        public StructuredError(String field, String msg) {
            this.field = field;
            this.msg = msg;
        }

        public String getField() {
            return field;
        }


        public String getMsg() {
            return msg;
        }

    }


    public static class ValueStructuredError extends StructuredError {
        private final Object value;

        public ValueStructuredError(String field, String msg, Object value) {
            super(field, msg);
            this.value = value;
        }

        public Object getValue() {
            return value;
        }
    }
}


