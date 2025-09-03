package com.healthnet.entity;

/**
 * Enumeration for user roles in the system
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
public enum UserRole {
    ADMIN("Administrator"),
    ASHA_WORKER("ASHA Workers"),
    ANM("ANM"),
    NURSE("Nurses"),
    HEALTH_STAFF("Health Staff"),
    GOVERNMENT_OFFICIAL("Government Officials"),
    DISTRICT_HEALTH_OFFICER("District Health Officer");
    
    private final String displayName;
    
    UserRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Check if the role has admin privileges
     */
    public boolean isAdmin() {
        return this == ADMIN;
    }
    
    /**
     * Check if the role has staff privileges
     */
    public boolean isStaff() {
        return this == ASHA_WORKER || this == ANM || this == NURSE || 
               this == HEALTH_STAFF || this == GOVERNMENT_OFFICIAL || 
               this == DISTRICT_HEALTH_OFFICER;
    }
}
