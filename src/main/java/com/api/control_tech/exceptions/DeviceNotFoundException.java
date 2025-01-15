package com.api.control_tech.exceptions;

public class DeviceNotFoundException extends RuntimeException{

    public DeviceNotFoundException() {
        super("Device Not Found");
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
