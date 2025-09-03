package com.healthnet.dto;

import com.healthnet.entity.ReportStatus;
import com.healthnet.entity.UrgencyLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for HealthReport entity
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
public class HealthReportDto {
    
    private Long id;
    
    @NotBlank(message = "Reporter name is required")
    private String reporterName;
    
    private String reporterPhone;
    
    @NotBlank(message = "Village is required")
    private String village;
    
    @NotBlank(message = "District is required")
    private String district;
    
    private List<String> symptoms;
    private String description;
    
    @NotNull(message = "Urgency level is required")
    private UrgencyLevel urgency;
    
    private ReportStatus status;
    private List<String> photoUrls;
    private Boolean consentGiven;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long processedBy;
    private LocalDateTime processedAt;
    private String adminNotes;
    
    // Constructors
    public HealthReportDto() {}
    
    public HealthReportDto(String reporterName, String village, String district, 
                          List<String> symptoms, UrgencyLevel urgency) {
        this.reporterName = reporterName;
        this.village = village;
        this.district = district;
        this.symptoms = symptoms;
        this.urgency = urgency;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getReporterName() {
        return reporterName;
    }
    
    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }
    
    public String getReporterPhone() {
        return reporterPhone;
    }
    
    public void setReporterPhone(String reporterPhone) {
        this.reporterPhone = reporterPhone;
    }
    
    public String getVillage() {
        return village;
    }
    
    public void setVillage(String village) {
        this.village = village;
    }
    
    public String getDistrict() {
        return district;
    }
    
    public void setDistrict(String district) {
        this.district = district;
    }
    
    public List<String> getSymptoms() {
        return symptoms;
    }
    
    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public UrgencyLevel getUrgency() {
        return urgency;
    }
    
    public void setUrgency(UrgencyLevel urgency) {
        this.urgency = urgency;
    }
    
    public ReportStatus getStatus() {
        return status;
    }
    
    public void setStatus(ReportStatus status) {
        this.status = status;
    }
    
    public List<String> getPhotoUrls() {
        return photoUrls;
    }
    
    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }
    
    public Boolean getConsentGiven() {
        return consentGiven;
    }
    
    public void setConsentGiven(Boolean consentGiven) {
        this.consentGiven = consentGiven;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Long getProcessedBy() {
        return processedBy;
    }
    
    public void setProcessedBy(Long processedBy) {
        this.processedBy = processedBy;
    }
    
    public LocalDateTime getProcessedAt() {
        return processedAt;
    }
    
    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
    
    public String getAdminNotes() {
        return adminNotes;
    }
    
    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }
}
