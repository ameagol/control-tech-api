package com.api.control_tech.exceptions;

import com.api.control_tech.error.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Centralized error handling using @ControllerAdvice
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DeviceNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleDeviceNotFound(DeviceNotFoundException ex) {
        return buildErrorResponse("Could not locate device Provided", "Device Not Found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleUserNotFound(UsernameNotFoundException ex) {
        return buildErrorResponse("Could not locate user Provided", "User Not Found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleRoleNotFound(RoleNotFoundException ex) {
        return buildErrorResponse("Could not locate Role Provided", "Role Not Found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StatusNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleStatusNotFound(StatusNotFoundException ex) {
        return buildErrorResponse("Could not locate Status Provided", "Role Not Found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return buildErrorResponse("User Already Exists", "Conflict", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(StatusAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(StatusAlreadyExistsException ex) {
        return buildErrorResponse("Status Already Exists", "Conflict", HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            DeviceIdMismatchException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex) {
        return buildErrorResponse("Failed to handle Request", "Bad Request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        return buildErrorResponse("Invalid credentials", "Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPassword(InvalidPasswordException ex) {
        return buildErrorResponse("Invalid Password", "Bad Request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCompanyException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCompany(InvalidCompanyException ex) {
        return buildErrorResponse("Invalid Company Request", "Bad Request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return buildErrorResponse("Illegal Argument Provided", "Bad Request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, WebRequest request) {
        return buildErrorResponse("Internal Server Error","Host error",  HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Utility method to create consistent error responses.
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, String error, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(message, error);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
