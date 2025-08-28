package com.reliaquest.api.constants;

/**
 * This class holds constant values used across the API.
 * It includes endpoint paths, authentication credentials, and other static values.
 * The class is designed to prevent instantiation.
 *
 * @version 1.0
 * @since 2024-06-17
 */
public class ApiConstants {
    public static final String API_V1 = "/api/v1";
    public static final String ADMIN = "admin";
    public static final String PASSWORD = "password";
    public static final String BASE_URL = "http://localhost:8112/api/v1/employee";
    public static final String SLASH = "/";
    public static final String DATA = "data";

    private ApiConstants() {
        throw new IllegalStateException("Instantiation of this class from outside not allowed");
    }
}
