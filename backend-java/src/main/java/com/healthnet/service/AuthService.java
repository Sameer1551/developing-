package com.healthnet.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthnet.dto.LoginRequest;
import com.healthnet.dto.LoginResponse;
import com.healthnet.entity.User;
import com.healthnet.entity.UserRole;
import com.healthnet.repository.UserRepository;
import com.healthnet.util.JwtUtil;

/**
 * Service class for Authentication operations
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
@Service
@Transactional
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }
    
    /**
     * Authenticate user and return JWT token
     */
    public LoginResponse login(LoginRequest loginRequest) {
        // Find user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + loginRequest.getEmail()));
        
        // Validate password (using phone number as password in this system)
        if (!user.getPhone().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid password for user: " + loginRequest.getEmail());
        }
        
        // Validate role compatibility
        String requestedRole = loginRequest.getRole().toLowerCase();
        UserRole userRole = user.getRole();
        
        if (userRole == UserRole.ADMIN && !"admin".equals(requestedRole)) {
            throw new RuntimeException("Role mismatch: user is admin but selected role is " + requestedRole);
        }
        
        if ((userRole == UserRole.DISTRICT_HEALTH_OFFICER || userRole == UserRole.HEALTH_STAFF ||
             userRole == UserRole.ASHA_WORKER || userRole == UserRole.ANM ||
             userRole == UserRole.NURSE || userRole == UserRole.GOVERNMENT_OFFICIAL) && !"staff".equals(requestedRole)) {
            throw new RuntimeException("Role mismatch: user is staff but selected role is " + requestedRole);
        }
        
        // Update last active time
        user.setLastActive(LocalDateTime.now());
        userRepository.save(user);
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user);
        
        // Create designation based on role
        String designation = getDesignation(userRole);
        
        // Create permissions list
        List<String> permissions = getPermissions(userRole);
        
        return new LoginResponse(
            token,
            user.getId(),
            user.getName(),
            user.getEmail(),
            userRole,
            designation,
            user.getDistrict(), // Using district as village for now
            user.getDistrict(),
            permissions
        );
    }
    
    /**
     * Validate JWT token and return user information
     */
    public LoginResponse validateToken(String token) {
        try {
            // Validate token with server restart check
            if (!jwtUtil.validateToken(token)) {
                throw new RuntimeException("Invalid or expired token or token issued before server restart");
            }
            
            // Extract user information from token
            String email = jwtUtil.getEmailFromToken(token);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Update last active time
            user.setLastActive(LocalDateTime.now());
            userRepository.save(user);
            
            // Create designation based on role
            String designation = getDesignation(user.getRole());
            
            // Create permissions list
            List<String> permissions = getPermissions(user.getRole());
            
            return new LoginResponse(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                designation,
                user.getDistrict(), // Using district as village for now
                user.getDistrict(),
                permissions
            );
            
        } catch (Exception e) {
            throw new RuntimeException("Token validation failed: " + e.getMessage());
        }
    }
    
    /**
     * Logout user (invalidate token)
     */
    public void logout(String token) {
        // In a more sophisticated implementation, you would add the token to a blacklist
        // For now, we'll just log the logout action
        try {
            String email = jwtUtil.getEmailFromToken(token);
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                user.setLastActive(LocalDateTime.now());
                userRepository.save(user);
            }
        } catch (Exception e) {
            // Log error but don't throw exception for logout
            System.err.println("Error during logout: " + e.getMessage());
        }
    }
    
    /**
     * Refresh JWT token
     */
    public LoginResponse refreshToken(String token) {
        try {
            // Validate current token
            if (!jwtUtil.validateToken(token)) {
                throw new RuntimeException("Invalid or expired token");
            }
            
            // Extract user information from token
            String email = jwtUtil.getEmailFromToken(token);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Generate new token
            String newToken = jwtUtil.generateToken(user);
            
            // Update last active time
            user.setLastActive(LocalDateTime.now());
            userRepository.save(user);
            
            // Create designation based on role
            String designation = getDesignation(user.getRole());
            
            // Create permissions list
            List<String> permissions = getPermissions(user.getRole());
            
            return new LoginResponse(
                newToken,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                designation,
                user.getDistrict(), // Using district as village for now
                user.getDistrict(),
                permissions
            );
            
        } catch (Exception e) {
            throw new RuntimeException("Token refresh failed: " + e.getMessage());
        }
    }
    
    /**
     * Get designation based on user role
     */
    private String getDesignation(UserRole role) {
        return switch (role) {
            case ADMIN -> "Government Official";
            case DISTRICT_HEALTH_OFFICER -> "Health Officer";
            case HEALTH_STAFF, ASHA_WORKER, ANM, NURSE, GOVERNMENT_OFFICIAL -> "Health Worker";
        };
    }
    
    /**
     * Get permissions based on user role
     */
    private List<String> getPermissions(UserRole role) {
        return switch (role) {
            case ADMIN -> List.of(
                "view_all_reports", "manage_users", "view_analytics", 
                "manage_alerts", "view_predictions"
            );
            case DISTRICT_HEALTH_OFFICER, HEALTH_STAFF, ASHA_WORKER, ANM, NURSE, GOVERNMENT_OFFICIAL -> List.of(
                "view_reports", "submit_water_tests", "distribute_medicine"
            );
        };
    }
}
