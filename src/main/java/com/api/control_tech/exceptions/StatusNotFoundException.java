package com.api.control_tech.exceptions;

public class StatusNotFoundException extends RuntimeException{

    public StatusNotFoundException() {
        super();
    }

    public StatusNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatusNotFoundException(final String message) {
        super(message);
    }

    public StatusNotFoundException(final Throwable cause) {
        super(cause);
    }
}
