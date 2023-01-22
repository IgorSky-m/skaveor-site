package com.skachko.account.service.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AccountServiceValidationException extends RuntimeException {

    private final List<StructuredError> structuredErrors;

    public AccountServiceValidationException(List<StructuredError> structuredErrors) {
        this.structuredErrors = new ArrayList<>(structuredErrors);
    }

    public AccountServiceValidationException(StructuredError... errors) {
        structuredErrors = new ArrayList<>(Arrays.asList(errors));
    }

    public List<StructuredError> getStructuredErrors() {
        return structuredErrors;
    }

    public static class StructuredError {

        private final String msg;

        public StructuredError(String msg) {

            this.msg = msg;
        }


        public String getMsg() {
            return msg;
        }

    }

    public static class StructuredFieldError extends StructuredError{
        private final String field;

        public StructuredFieldError(String field, String msg) {
            super(msg);
            this.field = field;
        }

        public String getField() {
            return field;
        }
    }


    public static class ValueStructuredError extends StructuredFieldError {
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


