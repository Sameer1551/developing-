package com.healthnet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

/**
 * DTO for Medicine Distribution Record
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
public class DistributionRecordDto {
    
    private String id;
    
    @NotBlank(message = "Patient name is required")
    private String patientName;
    
    @NotBlank(message = "Medicine name is required")
    private String medicineName;
    
    @NotBlank(message = "Medicine type is required")
    private String medicineType;
    
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
    
    private String date;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    
    // Constructors
    public DistributionRecordDto() {}
    
    public DistributionRecordDto(String id, String patientName, String medicineName, 
                               String medicineType, Integer quantity, String date, LocalDateTime timestamp) {
        this.id = id;
        this.patientName = patientName;
        this.medicineName = medicineName;
        this.medicineType = medicineType;
        this.quantity = quantity;
        this.date = date;
        this.timestamp = timestamp;
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getPatientName() {
        return patientName;
    }
    
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    
    public String getMedicineName() {
        return medicineName;
    }
    
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
    
    public String getMedicineType() {
        return medicineType;
    }
    
    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
