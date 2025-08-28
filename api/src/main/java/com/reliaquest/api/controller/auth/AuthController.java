package com.reliaquest.api.controller.auth;

import com.reliaquest.api.constants.ApiConstants;
import com.reliaquest.api.exception.EmployeeRuntimeException;
import com.reliaquest.api.security.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for authentication operations.
 * Provides an endpoint to generate JWT tokens for valid credentials.
 *
 * @author skurade
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    /**
     * Generates a JWT token for valid user credentials.
     * Only accepts username "admin" and password "password".
     *
     * @param username the username to authenticate
     * @param password the password to authenticate
     * @return a JWT token if credentials are valid
     * @throws RuntimeException if credentials are invalid
     */
    @PostMapping("/token")
    public String getToken(@RequestParam String username, @RequestParam String password) {
        if (ApiConstants.ADMIN.equals(username) && ApiConstants.PASSWORD.equals(password)) {
            return JwtUtil.generateToken(username);
        } else {
            throw new EmployeeRuntimeException("Invalid credentials");
        }
    }
}
