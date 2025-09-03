package com.healthnet.entity;

/**
 * Enumeration for user account status
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
public enum UserStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    SUSPENDED("Suspended"),
    PENDING("Pending");
    
    private final String displayName;
    
    UserStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
