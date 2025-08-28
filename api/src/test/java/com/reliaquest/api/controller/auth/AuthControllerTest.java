package com.reliaquest.api.controller.auth;

import static org.junit.jupiter.api.Assertions.*;

import com.reliaquest.api.constants.ApiConstants;
import com.reliaquest.api.exception.EmployeeRuntimeException;
import com.reliaquest.api.security.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link AuthController}.
 * <p>
 * Verifies JWT token generation and error handling for authentication.
 *
 * @author skurade
 */
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    /**
     * Tests token generation with valid credentials.
     * Asserts that a non-empty JWT token is returned and contains the correct subject.
     */
    @Test
    void testGetTokenWithValidCredentials() {
        String token = authController.getToken(ApiConstants.ADMIN, ApiConstants.PASSWORD);
        assertNotNull(token);
        assertFalse(token.isEmpty());
        String subject = JwtUtil.validateToken(token);
        assertEquals(ApiConstants.ADMIN, subject);
    }

    /**
     * Tests token generation with an invalid username.
     * Expects a RuntimeException with message "Invalid credentials".
     */
    @Test
    void testGetTokenWithInvalidUsername() {
        assertThrows(EmployeeRuntimeException.class, () -> authController.getToken("wrongUser", ApiConstants.PASSWORD));
    }

    /**
     * Tests token generation with an invalid password.
     * Expects a RuntimeException with message "Invalid credentials".
     */
    @Test
    void testGetTokenWithInvalidPassword() {
        assertThrows(
                EmployeeRuntimeException.class, () -> authController.getToken(ApiConstants.ADMIN, "wrongPassword"));
    }

    /**
     * Tests token generation with both username and password invalid.
     * Expects a RuntimeException with message "Invalid credentials".
     */
    @Test
    void testGetTokenWithInvalidCredentials() {
        assertThrows(EmployeeRuntimeException.class, () -> authController.getToken("user", "pass"));
    }
}
