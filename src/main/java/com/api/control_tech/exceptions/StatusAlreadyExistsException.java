package com.api.control_tech.exceptions;

public class StatusAlreadyExistsException extends RuntimeException{

    public StatusAlreadyExistsException() {
        super();
    }

    public StatusAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatusAlreadyExistsException(final String message) {
        super(message);
    }

    public StatusAlreadyExistsException(final Throwable cause) {
        super(cause);
    }
}
