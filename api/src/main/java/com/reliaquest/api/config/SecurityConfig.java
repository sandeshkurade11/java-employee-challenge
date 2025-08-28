package com.reliaquest.api.config;

import com.reliaquest.api.security.JwtFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configures security settings for the API, including authentication and JWT filter.
 * Public endpoints are defined, and all other requests require authentication.
 *
 * @author skurade
 */
@Configuration
public class SecurityConfig {

    /**
     * Defines the security filter chain for HTTP requests.
     * Disables CSRF protection, sets public endpoints, and adds JWT authentication filter.
     *
     * @param http the {@link HttpSecurity} to configure
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during security configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/**", "/api/v1/health")
                        .permitAll()
                        .dispatcherTypeMatchers(DispatcherType.REQUEST)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(
                        new JwtFilter(),
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
