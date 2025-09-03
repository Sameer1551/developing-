package com.healthnet.controller;

import com.healthnet.dto.HealthReportDto;
import com.healthnet.entity.HealthReport;
import com.healthnet.entity.ReportStatus;
import com.healthnet.entity.UrgencyLevel;
import com.healthnet.service.HealthReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Health Report operations
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/health-reports")
@Tag(name = "Health Reports", description = "APIs for managing health reports")
public class HealthReportController {
    
    private final HealthReportService healthReportService;
    
    public HealthReportController(HealthReportService healthReportService) {
        this.healthReportService = healthReportService;
    }
    
    /**
     * Get all health reports with pagination
     */
    @GetMapping
    @Operation(summary = "Get all health reports", description = "Retrieve all health reports with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reports retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('HEALTH_OFFICER')")
    public ResponseEntity<Page<HealthReportDto>> getAllReports(Pageable pageable) {
        Page<HealthReport> reports = healthReportService.getAllReports(pageable);
        Page<HealthReportDto> reportDtos = reports.map(this::convertToDto);
        return ResponseEntity.ok(reportDtos);
    }
    
    /**
     * Get health report by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get health report by ID", description = "Retrieve a specific health report by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Report found"),
        @ApiResponse(responseCode = "404", description = "Report not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('HEALTH_OFFICER')")
    public ResponseEntity<HealthReportDto> getReportById(@PathVariable Long id) {
        HealthReport report = healthReportService.getReportById(id);
        return ResponseEntity.ok(convertToDto(report));
    }
    
    /**
     * Create a new health report (Public endpoint - no authentication required)
     */
    @PostMapping
    @Operation(summary = "Create new health report", description = "Create a new health report (public endpoint)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Report created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<HealthReportDto> createReport(@Valid @RequestBody HealthReportDto reportDto) {
        HealthReport report = convertToEntity(reportDto);
        HealthReport createdReport = healthReportService.createReport(report);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdReport));
    }
    
    /**
     * Update an existing health report
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update health report", description = "Update an existing health report")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Report updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Report not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('HEALTH_OFFICER')")
    public ResponseEntity<HealthReportDto> updateReport(@PathVariable Long id, 
                                                       @Valid @RequestBody HealthReportDto reportDto) {
        HealthReport report = convertToEntity(reportDto);
        HealthReport updatedReport = healthReportService.updateReport(id, report);
        return ResponseEntity.ok(convertToDto(updatedReport));
    }
    
    /**
     * Delete a health report
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete health report", description = "Delete a health report from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Report deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Report not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        healthReportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Get reports by district
     */
    @GetMapping("/district/{district}")
    @Operation(summary = "Get reports by district", description = "Retrieve health reports filtered by district")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reports retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('HEALTH_OFFICER')")
    public ResponseEntity<List<HealthReportDto>> getReportsByDistrict(@PathVariable String district) {
        List<HealthReport> reports = healthReportService.getReportsByDistrict(district);
        List<HealthReportDto> reportDtos = reports.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDtos);
    }
    
    /**
     * Get reports by status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get reports by status", description = "Retrieve health reports filtered by status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reports retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('HEALTH_OFFICER')")
    public ResponseEntity<List<HealthReportDto>> getReportsByStatus(@PathVariable ReportStatus status) {
        List<HealthReport> reports = healthReportService.getReportsByStatus(status);
        List<HealthReportDto> reportDtos = reports.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDtos);
    }
    
    /**
     * Get reports by urgency level
     */
    @GetMapping("/urgency/{urgency}")
    @Operation(summary = "Get reports by urgency", description = "Retrieve health reports filtered by urgency level")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reports retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('HEALTH_OFFICER')")
    public ResponseEntity<List<HealthReportDto>> getReportsByUrgency(@PathVariable UrgencyLevel urgency) {
        List<HealthReport> reports = healthReportService.getReportsByUrgency(urgency);
        List<HealthReportDto> reportDtos = reports.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDtos);
    }
    
    /**
     * Get pending reports (ordered by priority)
     */
    @GetMapping("/pending")
    @Operation(summary = "Get pending reports", description = "Retrieve all pending reports ordered by priority")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pending reports retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('HEALTH_OFFICER')")
    public ResponseEntity<List<HealthReportDto>> getPendingReports() {
        List<HealthReport> reports = healthReportService.getPendingReports();
        List<HealthReportDto> reportDtos = reports.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDtos);
    }
    
    /**
     * Get high priority reports
     */
    @GetMapping("/high-priority")
    @Operation(summary = "Get high priority reports", description = "Retrieve all high priority reports (HIGH and CRITICAL urgency)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "High priority reports retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('HEALTH_OFFICER')")
    public ResponseEntity<List<HealthReportDto>> getHighPriorityReports() {
        List<HealthReport> reports = healthReportService.getHighPriorityReports();
        List<HealthReportDto> reportDtos = reports.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDtos);
    }
    
    /**
     * Update report status
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update report status", description = "Update the status of a health report")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Report status updated successfully"),
        @ApiResponse(responseCode = "404", description = "Report not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('HEALTH_OFFICER')")
    public ResponseEntity<HealthReportDto> updateReportStatus(@PathVariable Long id, 
                                                             @RequestParam ReportStatus status) {
        HealthReport updatedReport = healthReportService.updateReportStatus(id, status);
        return ResponseEntity.ok(convertToDto(updatedReport));
    }
    
    /**
     * Add admin notes to a report
     */
    @PatchMapping("/{id}/notes")
    @Operation(summary = "Add admin notes", description = "Add administrative notes to a health report")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notes added successfully"),
        @ApiResponse(responseCode = "404", description = "Report not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('HEALTH_OFFICER')")
    public ResponseEntity<HealthReportDto> addAdminNotes(@PathVariable Long id, 
                                                        @RequestParam String notes) {
        HealthReport updatedReport = healthReportService.addAdminNotes(id, notes);
        return ResponseEntity.ok(convertToDto(updatedReport));
    }
    
    /**
     * Get reports created today
     */
    @GetMapping("/today")
    @Operation(summary = "Get today's reports", description = "Retrieve all health reports created today")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Today's reports retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('HEALTH_OFFICER')")
    public ResponseEntity<List<HealthReportDto>> getTodaysReports() {
        List<HealthReport> reports = healthReportService.getTodaysReports();
        List<HealthReportDto> reportDtos = reports.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDtos);
    }
    
    /**
     * Get report statistics
     */
    @GetMapping("/statistics")
    @Operation(summary = "Get report statistics", description = "Retrieve statistics about health reports")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('HEALTH_OFFICER')")
    public ResponseEntity<ReportStatistics> getReportStatistics() {
        ReportStatistics stats = healthReportService.getReportStatistics();
        return ResponseEntity.ok(stats);
    }
    
    // Helper methods for conversion
    private HealthReportDto convertToDto(HealthReport report) {
        HealthReportDto dto = new HealthReportDto();
        dto.setId(report.getId());
        dto.setReporterName(report.getReporterName());
        dto.setReporterPhone(report.getReporterPhone());
        dto.setVillage(report.getVillage());
        dto.setDistrict(report.getDistrict());
        dto.setSymptoms(report.getSymptoms());
        dto.setDescription(report.getDescription());
        dto.setUrgency(report.getUrgency());
        dto.setStatus(report.getStatus());
        dto.setPhotoUrls(report.getPhotoUrls());
        dto.setConsentGiven(report.getConsentGiven());
        dto.setCreatedAt(report.getCreatedAt());
        dto.setUpdatedAt(report.getUpdatedAt());
        dto.setProcessedBy(report.getProcessedBy());
        dto.setProcessedAt(report.getProcessedAt());
        dto.setAdminNotes(report.getAdminNotes());
        return dto;
    }
    
    private HealthReport convertToEntity(HealthReportDto dto) {
        HealthReport report = new HealthReport();
        report.setId(dto.getId());
        report.setReporterName(dto.getReporterName());
        report.setReporterPhone(dto.getReporterPhone());
        report.setVillage(dto.getVillage());
        report.setDistrict(dto.getDistrict());
        report.setSymptoms(dto.getSymptoms());
        report.setDescription(dto.getDescription());
        report.setUrgency(dto.getUrgency());
        report.setStatus(dto.getStatus());
        report.setPhotoUrls(dto.getPhotoUrls());
        report.setConsentGiven(dto.getConsentGiven());
        report.setProcessedBy(dto.getProcessedBy());
        report.setProcessedAt(dto.getProcessedAt());
        report.setAdminNotes(dto.getAdminNotes());
        return report;
    }
    
    // Statistics DTO
    public static class ReportStatistics {
        private long totalReports;
        private long pendingReports;
        private long processedReports;
        private long highUrgencyReports;
        private long criticalUrgencyReports;
        private long todaysReports;
        
        // Constructors, getters, and setters
        public ReportStatistics() {}
        
        public ReportStatistics(long totalReports, long pendingReports, long processedReports,
                               long highUrgencyReports, long criticalUrgencyReports, long todaysReports) {
            this.totalReports = totalReports;
            this.pendingReports = pendingReports;
            this.processedReports = processedReports;
            this.highUrgencyReports = highUrgencyReports;
            this.criticalUrgencyReports = criticalUrgencyReports;
            this.todaysReports = todaysReports;
        }
        
        // Getters and setters
        public long getTotalReports() { return totalReports; }
        public void setTotalReports(long totalReports) { this.totalReports = totalReports; }
        
        public long getPendingReports() { return pendingReports; }
        public void setPendingReports(long pendingReports) { this.pendingReports = pendingReports; }
        
        public long getProcessedReports() { return processedReports; }
        public void setProcessedReports(long processedReports) { this.processedReports = processedReports; }
        
        public long getHighUrgencyReports() { return highUrgencyReports; }
        public void setHighUrgencyReports(long highUrgencyReports) { this.highUrgencyReports = highUrgencyReports; }
        
        public long getCriticalUrgencyReports() { return criticalUrgencyReports; }
        public void setCriticalUrgencyReports(long criticalUrgencyReports) { this.criticalUrgencyReports = criticalUrgencyReports; }
        
        public long getTodaysReports() { return todaysReports; }
        public void setTodaysReports(long todaysReports) { this.todaysReports = todaysReports; }
    }
}
