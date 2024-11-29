package com.api.control_tech.exceptions;

/**
 * ! Handling a custom exception message-cause class for BooksNotFound
 */
public class DeviceNotFoundException extends RuntimeException{

    public DeviceNotFoundException() {
        super();
    }
    
    public DeviceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeviceNotFoundException(final String message) {
        super(message);
    }

    public DeviceNotFoundException(final Throwable cause) {
        super(cause);
    }
}
