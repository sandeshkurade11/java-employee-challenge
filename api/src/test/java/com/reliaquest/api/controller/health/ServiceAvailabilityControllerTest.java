package com.reliaquest.api.controller.health;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

/**
 * Unit tests for {@link ServiceAvailabilityController}.
 * <p>
 * Verifies the health check endpoint returns the correct status message.
 *
 * @author skurade
 */
@ExtendWith(MockitoExtension.class)
class ServiceAvailabilityControllerTest {

    @InjectMocks
    private ServiceAvailabilityController controller;

    /**
     * Tests the health check endpoint.
     *
     * @author skurade
     * @return void
     */
    @Test
    void testHealthCheck() {
        ResponseEntity<String> response = controller.healthCheck();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Service is up and running", response.getBody());
    }
}
