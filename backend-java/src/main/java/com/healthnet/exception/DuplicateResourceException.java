package com.healthnet.exception;

/**
 * Custom exception for duplicate resource scenarios
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
public class DuplicateResourceException extends RuntimeException {
    
    public DuplicateResourceException(String message) {
        super(message);
    }
    
    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
