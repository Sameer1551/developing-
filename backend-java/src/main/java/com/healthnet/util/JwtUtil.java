package com.healthnet.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.healthnet.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

/**
 * Utility class for JWT token operations
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret:mySecretKey}")
    private String secret;

    @Value("${jwt.expiration:86400000}") // 24 hours in milliseconds
    private Long expiration;

    // Server restart timestamp to invalidate tokens issued before restart
    private long serverRestartTimestamp;

    @PostConstruct
    public void init() {
        // Initialize server restart timestamp on bean creation (server start)
        serverRestartTimestamp = System.currentTimeMillis();
    }

    /**
     * Generate JWT token for user
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole().name());
        claims.put("district", user.getDistrict());
        claims.put("state", user.getState());
        claims.put("serverRestartTimestamp", serverRestartTimestamp); // Add server restart timestamp claim
        return createToken(claims, user.getEmail());
    }

    /**
     * Create JWT token with claims
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Extract email from token
     */
    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Extract expiration date from token
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Extract claim from token
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from token
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Check if token is expired
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Validate token
     */
    public Boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

            // Check server restart timestamp claim
            Long tokenServerRestartTimestamp = claims.get("serverRestartTimestamp", Long.class);
            if (tokenServerRestartTimestamp == null || tokenServerRestartTimestamp < serverRestartTimestamp) {
                // Token was issued before server restart, invalidate it
                return false;
            }

            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Validate token with user details
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = getEmailFromToken(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Get signing key
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extract user ID from token
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("userId", Long.class);
    }

    /**
     * Extract role from token
     */
    public String getRoleFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("role", String.class);
    }

    /**
     * Extract district from token
     */
    public String getDistrictFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("district", String.class);
    }

    /**
     * Extract state from token
     */
    public String getStateFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("state", String.class);
    }

    /**
     * Get token expiration time in milliseconds
     */
    public Long getExpirationTime() {
        return expiration;
    }
}
