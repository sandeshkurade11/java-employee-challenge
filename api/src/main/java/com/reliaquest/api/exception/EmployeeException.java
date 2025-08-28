package com.reliaquest.api.exception;

import java.util.HashSet;
import java.util.Set;

/**
 * Custom exception for handling employee-related business flows.
 * Supports storing multiple error messages.
 *
 * @author skurade
 */
public class EmployeeException extends Exception {

    private Set<String> errors = new HashSet<>();

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message
     */
    public EmployeeException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public EmployeeException(String message, Throwable cause) {
        super(message, cause);
    }
}
