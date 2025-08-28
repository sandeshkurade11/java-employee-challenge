package com.reliaquest.api.exception;

/**
 * Custom Runtime exception for unchecked handling during business flow.
 * Used for employee-related errors in the API.
 *
 * @author skurade
 */
public class EmployeeRuntimeException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message
     */
    public EmployeeRuntimeException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public EmployeeRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
