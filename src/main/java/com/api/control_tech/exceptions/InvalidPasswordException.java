package com.api.control_tech.exceptions;


public class InvalidPasswordException extends RuntimeException{

    public InvalidPasswordException() {
        super();
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPasswordException(final String message) {
        super(message);
    }

    public InvalidPasswordException(final Throwable cause) {
        super(cause);
    }
}
