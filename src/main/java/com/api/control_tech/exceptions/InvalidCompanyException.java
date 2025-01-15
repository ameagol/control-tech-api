package com.api.control_tech.exceptions;

public class InvalidCompanyException extends RuntimeException{

    public InvalidCompanyException() {
        super();
    }

    public InvalidCompanyException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCompanyException(final String message) {
        super(message);
    }

    public InvalidCompanyException(final Throwable cause) {
        super(cause);
    }
}
