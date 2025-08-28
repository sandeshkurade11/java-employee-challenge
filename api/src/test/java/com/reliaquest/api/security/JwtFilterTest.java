package com.reliaquest.api.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.reliaquest.api.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Tests that the filter is applied to other endpoints.
 */
@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @InjectMocks
    private JwtFilter jwtFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    /**
     * Clears the security context before each test to ensure isolation.
     */
    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    /**
     * Tests that a valid JWT token sets authentication in the security context
     * and allows the request to proceed through the filter chain.
     */
    @Test
    void testValidJwtTokenSetsAuthentication() throws ServletException, IOException {
        String token = JwtUtil.generateToken("admin");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        jwtFilter.doFilterInternal(request, response, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(
                "admin", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        verify(chain, times(1)).doFilter(request, response);
    }

    /**
     * Tests that an invalid JWT token results in a 401 Unauthorized response
     * and does not set authentication or proceed through the filter chain.
     */
    @Test
    void testInvalidJwtTokenReturns401() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidtoken");

        jwtFilter.doFilterInternal(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(chain, never()).doFilter(request, response);
    }

    /**
     * Tests that a missing Authorization header results in a 401 Unauthorized response
     * and does not set authentication or proceed through the filter chain.
     */
    @Test
    void testMissingAuthorizationHeaderReturns401() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(chain, never()).doFilter(request, response);
    }

    /**
     * Tests that the filter is not applied to authentication endpoints.
     */
    @Test
    void testShouldNotFilterAuthEndpoint() {
        when(request.getRequestURI()).thenReturn("/api/v1/auth/token");
        assertTrue(jwtFilter.shouldNotFilter(request));
    }

    /**
     * Tests that the filter is not applied to health check endpoints.
     */
    @Test
    void testShouldNotFilterHealthEndpoint() {
        when(request.getRequestURI()).thenReturn("/api/v1/health");
        assertTrue(jwtFilter.shouldNotFilter(request));
    }

    /**
     * Tests that the filter is applied to other endpoints.
     */
    @Test
    void testShouldFilterOtherEndpoints() {
        when(request.getRequestURI()).thenReturn("/api/v1/employee");
        assertFalse(jwtFilter.shouldNotFilter(request));
    }
}
