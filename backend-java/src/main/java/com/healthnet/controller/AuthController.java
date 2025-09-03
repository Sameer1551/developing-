package com.healthnet.controller;

import com.healthnet.dto.LoginRequest;
import com.healthnet.dto.LoginResponse;
import com.healthnet.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Authentication operations
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "APIs for user authentication")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    /**
     * User login endpoint
     */
    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "400", description = "Invalid credentials"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Validate JWT token
     */
    @PostMapping("/validate")
    @Operation(summary = "Validate token", description = "Validate JWT token and return user information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token is valid"),
        @ApiResponse(responseCode = "401", description = "Token is invalid or expired")
    })
    public ResponseEntity<LoginResponse> validateToken(@RequestHeader("Authorization") String token) {
        // Remove "Bearer " prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        LoginResponse response = authService.validateToken(token);
        return ResponseEntity.ok(response);
    }
    
    /**
     * User logout endpoint
     */
    @PostMapping("/logout")
    @Operation(summary = "User logout", description = "Logout user (invalidate token)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logout successful")
    })
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        // Remove "Bearer " prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        authService.logout(token);
        return ResponseEntity.ok("Logout successful");
    }
    
    /**
     * Refresh JWT token
     */
    @PostMapping("/refresh")
    @Operation(summary = "Refresh token", description = "Refresh JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
        @ApiResponse(responseCode = "401", description = "Invalid refresh token")
    })
    public ResponseEntity<LoginResponse> refreshToken(@RequestHeader("Authorization") String token) {
        // Remove "Bearer " prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        LoginResponse response = authService.refreshToken(token);
        return ResponseEntity.ok(response);
    }
}
