package com.healthnet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Health report entity for storing community health reports
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
@Entity
@Table(name = "health_reports")
@EntityListeners(AuditingEntityListener.class)
public class HealthReport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Reporter name is required")
    @Column(name = "reporter_name", nullable = false)
    private String reporterName;
    
    @Column(name = "reporter_phone")
    private String reporterPhone;
    
    @NotBlank(message = "Village is required")
    @Column(nullable = false)
    private String village;
    
    @NotBlank(message = "District is required")
    @Column(nullable = false)
    private String district;
    
    @ElementCollection
    @CollectionTable(name = "report_symptoms", joinColumns = @JoinColumn(name = "report_id"))
    @Column(name = "symptom")
    private List<String> symptoms;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @NotNull(message = "Urgency level is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UrgencyLevel urgency;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status = ReportStatus.PENDING;
    
    @ElementCollection
    @CollectionTable(name = "report_photos", joinColumns = @JoinColumn(name = "report_id"))
    @Column(name = "photo_url")
    private List<String> photoUrls;
    
    @Column(name = "consent_given", nullable = false)
    private Boolean consentGiven = false;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "processed_by")
    private Long processedBy;
    
    @Column(name = "processed_at")
    private LocalDateTime processedAt;
    
    @Column(name = "admin_notes", columnDefinition = "TEXT")
    private String adminNotes;
    
    // Constructors
    public HealthReport() {}
    
    public HealthReport(String reporterName, String village, String district, 
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
    
    // Utility methods
    public boolean isHighUrgency() {
        return urgency == UrgencyLevel.HIGH;
    }
    
    public boolean isProcessed() {
        return status == ReportStatus.PROCESSED;
    }
    
    @Override
    public String toString() {
        return "HealthReport{" +
                "id=" + id +
                ", reporterName='" + reporterName + '\'' +
                ", village='" + village + '\'' +
                ", district='" + district + '\'' +
                ", urgency=" + urgency +
                ", status=" + status +
                '}';
    }
}
