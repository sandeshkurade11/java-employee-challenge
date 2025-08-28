package com.reliaquest.api.controller.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for service availability health checks.
 * Implements the {@link IServiceAvailabilityController} interface and exposes a health check endpoint.
 *
 * @author skurade
 */
@RestController
@RequestMapping("/api/v1/health")
public class ServiceAvailabilityController {

    /**
     * Health check endpoint to verify if the service is running.
     *
     * @return a {@link ResponseEntity} containing a status message indicating service availability
     */
    @GetMapping
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok().body("Service is up and running");
    }
}
