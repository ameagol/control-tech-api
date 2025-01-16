package com.api.control_tech.exceptions;

public class DeviceIdMismatchException extends RuntimeException {

    public DeviceIdMismatchException() {
        super();
    }

    public DeviceIdMismatchException(final String message, final Throwable cause) {
        super(message, cause);

    }
    
    public DeviceIdMismatchException(final String message) {
        super(message);
    }
    public DeviceIdMismatchException(final Throwable cause) {
        super(cause);
    }

}
