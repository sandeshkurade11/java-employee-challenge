package com.reliaquest.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for API-related beans.
 * Provides a {@link RestTemplate} bean for HTTP requests.
 *
 * @author skurade
 */
@Configuration
public class ApiConfig {

    /**
     * Creates a {@link RestTemplate} bean to facilitate HTTP requests.
     *
     * @return a new instance of {@link RestTemplate}
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
