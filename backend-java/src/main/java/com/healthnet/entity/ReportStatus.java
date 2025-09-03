package com.healthnet.entity;

/**
 * Enumeration for health report processing status
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
public enum ReportStatus {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    PROCESSED("Processed"),
    RESOLVED("Resolved"),
    CANCELLED("Cancelled");
    
    private final String displayName;
    
    ReportStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
