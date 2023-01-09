package com.skachko.libraries.mvc.exceptions;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ValidationException extends RuntimeException {

    private final Collection<StructuredError> errors;

    public ValidationException(StructuredError...errors) {
        this.errors = List.of(errors);
    }

    public ValidationException(Collection<StructuredError> errors) {
        this.errors = errors;
    }


    public ValidationException() {
        errors = Collections.EMPTY_LIST;
    }

    public ValidationException(String message) {
        super(message);
        errors = Collections.EMPTY_LIST;
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        errors = Collections.EMPTY_LIST;
    }

    public ValidationException(Throwable cause) {
        super(cause);
        errors = Collections.EMPTY_LIST;
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        errors = Collections.EMPTY_LIST;
    }
}
