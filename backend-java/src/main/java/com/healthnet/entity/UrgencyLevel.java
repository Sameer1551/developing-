package com.healthnet.entity;

/**
 * Enumeration for health report urgency levels
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
public enum UrgencyLevel {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    CRITICAL("Critical");
    
    private final String displayName;
    
    UrgencyLevel(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Get priority score for sorting (higher number = higher priority)
     */
    public int getPriorityScore() {
        return switch (this) {
            case CRITICAL -> 4;
            case HIGH -> 3;
            case MEDIUM -> 2;
            case LOW -> 1;
        };
    }
}
