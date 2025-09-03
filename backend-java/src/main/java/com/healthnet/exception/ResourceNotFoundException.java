package com.healthnet.exception;

/**
 * Custom exception for resource not found scenarios
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
