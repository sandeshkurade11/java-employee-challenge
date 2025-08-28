package com.reliaquest.api.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

/**
 * Utility class for generating and validating JWT tokens.
 * Provides static methods to create and verify JWTs for authentication purposes.
 *
 * @author skurade
 */
public class JwtUtil {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Generates a JWT token for the specified username.
     * The token is valid for 1 hour from the time of issuance.
     *
     * @param username the username to include in the token's subject
     * @return a signed JWT token as a String
     */
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(key)
                .compact();
    }

    /**
     * Validates the provided JWT token and extracts the username (subject).
     * Throws an exception if the token is invalid or expired.
     *
     * @param token the JWT token to validate
     * @return the username contained in the token's subject
     */
    public static String validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
