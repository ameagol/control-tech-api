package com.api.control_tech.exceptions;


public class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException() {
        super();
    }

    public RoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNotFoundException(final String message) {
        super(message);
    }

    public RoleNotFoundException(final Throwable cause) {
        super(cause);
    }
}
