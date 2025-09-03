package com.healthnet.service;

import com.healthnet.entity.HealthReport;
import com.healthnet.entity.ReportStatus;
import com.healthnet.entity.UrgencyLevel;
import com.healthnet.repository.HealthReportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for HealthReport operations
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
@Service
@Transactional
public class HealthReportService {
    
    private final HealthReportRepository healthReportRepository;
    
    public HealthReportService(HealthReportRepository healthReportRepository) {
        this.healthReportRepository = healthReportRepository;
    }
    
    /**
     * Get all health reports with pagination
     */
    public Page<HealthReport> getAllReports(Pageable pageable) {
        return healthReportRepository.findAll(pageable);
    }
    
    /**
     * Get health report by ID
     */
    public HealthReport getReportById(Long id) {
        return healthReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Health report not found with id: " + id));
    }
    
    /**
     * Create a new health report
     */
    public HealthReport createReport(HealthReport report) {
        // Set default values
        if (report.getStatus() == null) {
            report.setStatus(ReportStatus.PENDING);
        }
        
        if (report.getConsentGiven() == null) {
            report.setConsentGiven(false);
        }
        
        return healthReportRepository.save(report);
    }
    
    /**
     * Update an existing health report
     */
    public HealthReport updateReport(Long id, HealthReport reportDetails) {
        HealthReport report = getReportById(id);
        
        // Update fields
        report.setReporterName(reportDetails.getReporterName());
        report.setReporterPhone(reportDetails.getReporterPhone());
        report.setVillage(reportDetails.getVillage());
        report.setDistrict(reportDetails.getDistrict());
        report.setSymptoms(reportDetails.getSymptoms());
        report.setDescription(reportDetails.getDescription());
        report.setUrgency(reportDetails.getUrgency());
        report.setStatus(reportDetails.getStatus());
        report.setPhotoUrls(reportDetails.getPhotoUrls());
        report.setConsentGiven(reportDetails.getConsentGiven());
        report.setProcessedBy(reportDetails.getProcessedBy());
        report.setProcessedAt(reportDetails.getProcessedAt());
        report.setAdminNotes(reportDetails.getAdminNotes());
        
        return healthReportRepository.save(report);
    }
    
    /**
     * Delete a health report
     */
    public void deleteReport(Long id) {
        HealthReport report = getReportById(id);
        healthReportRepository.delete(report);
    }
    
    /**
     * Get reports by district
     */
    public List<HealthReport> getReportsByDistrict(String district) {
        return healthReportRepository.findByDistrict(district);
    }
    
    /**
     * Get reports by village
     */
    public List<HealthReport> getReportsByVillage(String village) {
        return healthReportRepository.findByVillage(village);
    }
    
    /**
     * Get reports by status
     */
    public List<HealthReport> getReportsByStatus(ReportStatus status) {
        return healthReportRepository.findByStatus(status);
    }
    
    /**
     * Get reports by urgency level
     */
    public List<HealthReport> getReportsByUrgency(UrgencyLevel urgency) {
        return healthReportRepository.findByUrgency(urgency);
    }
    
    /**
     * Get reports by district and status
     */
    public List<HealthReport> getReportsByDistrictAndStatus(String district, ReportStatus status) {
        return healthReportRepository.findByDistrictAndStatus(district, status);
    }
    
    /**
     * Get reports by urgency and status
     */
    public List<HealthReport> getReportsByUrgencyAndStatus(UrgencyLevel urgency, ReportStatus status) {
        return healthReportRepository.findByUrgencyAndStatus(urgency, status);
    }
    
    /**
     * Get pending reports (ordered by priority)
     */
    public List<HealthReport> getPendingReports() {
        return healthReportRepository.findPendingReportsOrderedByPriority();
    }
    
    /**
     * Get high priority reports
     */
    public List<HealthReport> getHighPriorityReports() {
        return healthReportRepository.findHighPriorityReports();
    }
    
    /**
     * Update report status
     */
    public HealthReport updateReportStatus(Long id, ReportStatus status) {
        HealthReport report = getReportById(id);
        report.setStatus(status);
        
        // If status is being changed to PROCESSED or RESOLVED, set processed info
        if (status == ReportStatus.PROCESSED || status == ReportStatus.RESOLVED) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof com.healthnet.entity.User) {
                com.healthnet.entity.User currentUser = (com.healthnet.entity.User) authentication.getPrincipal();
                report.setProcessedBy(currentUser.getId());
                report.setProcessedAt(LocalDateTime.now());
            }
        }
        
        return healthReportRepository.save(report);
    }
    
    /**
     * Add admin notes to a report
     */
    public HealthReport addAdminNotes(Long id, String notes) {
        HealthReport report = getReportById(id);
        report.setAdminNotes(notes);
        return healthReportRepository.save(report);
    }
    
    /**
     * Get reports created today
     */
    public List<HealthReport> getTodaysReports() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        return healthReportRepository.findReportsCreatedToday(startOfDay, endOfDay);
    }
    
    /**
     * Get reports created this week
     */
    public List<HealthReport> getThisWeeksReports() {
        LocalDateTime weekStart = LocalDateTime.now().minusWeeks(1);
        return healthReportRepository.findReportsCreatedThisWeek(weekStart);
    }
    
    /**
     * Get reports created this month
     */
    public List<HealthReport> getThisMonthsReports() {
        return healthReportRepository.findReportsCreatedThisMonth();
    }
    
    /**
     * Get reports by date range
     */
    public List<HealthReport> getReportsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return healthReportRepository.findByCreatedAtBetween(startDate, endDate);
    }
    
    /**
     * Get reports by district and date range
     */
    public List<HealthReport> getReportsByDistrictAndDateRange(String district, 
                                                               LocalDateTime startDate, 
                                                               LocalDateTime endDate) {
        return healthReportRepository.findByDistrictAndDateRange(district, startDate, endDate);
    }
    
    /**
     * Get reports by symptoms
     */
    public List<HealthReport> getReportsBySymptoms(List<String> symptoms) {
        return healthReportRepository.findBySymptoms(symptoms);
    }
    
    /**
     * Search reports by reporter name
     */
    public List<HealthReport> searchReportsByReporterName(String name) {
        return healthReportRepository.findByReporterNameContainingIgnoreCase(name);
    }
    
    /**
     * Search reports by village
     */
    public List<HealthReport> searchReportsByVillage(String village) {
        return healthReportRepository.findByVillageContainingIgnoreCase(village);
    }
    
    /**
     * Search reports by district
     */
    public List<HealthReport> searchReportsByDistrict(String district) {
        return healthReportRepository.findByDistrictContainingIgnoreCase(district);
    }
    
    /**
     * Get report statistics
     */
    public com.healthnet.controller.HealthReportController.ReportStatistics getReportStatistics() {
        long totalReports = healthReportRepository.count();
        long pendingReports = healthReportRepository.countByStatus(ReportStatus.PENDING);
        long processedReports = healthReportRepository.countByStatus(ReportStatus.PROCESSED);
        long highUrgencyReports = healthReportRepository.countByUrgency(UrgencyLevel.HIGH);
        long criticalUrgencyReports = healthReportRepository.countByUrgency(UrgencyLevel.CRITICAL);
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        long todaysReports = healthReportRepository.findReportsCreatedToday(startOfDay, endOfDay).size();
        
        return new com.healthnet.controller.HealthReportController.ReportStatistics(
            totalReports, pendingReports, processedReports, 
            highUrgencyReports, criticalUrgencyReports, todaysReports
        );
    }
    
    /**
     * Get reports with pagination by district
     */
    public Page<HealthReport> getReportsByDistrict(String district, Pageable pageable) {
        return healthReportRepository.findByDistrict(district, pageable);
    }
    
    /**
     * Get reports with pagination by status
     */
    public Page<HealthReport> getReportsByStatus(ReportStatus status, Pageable pageable) {
        return healthReportRepository.findByStatus(status, pageable);
    }
    
    /**
     * Get reports with pagination by urgency
     */
    public Page<HealthReport> getReportsByUrgency(UrgencyLevel urgency, Pageable pageable) {
        return healthReportRepository.findByUrgency(urgency, pageable);
    }
    
    /**
     * Get pending reports with pagination
     */
    public Page<HealthReport> getPendingReports(Pageable pageable) {
        return healthReportRepository.findPendingReportsOrderedByPriority(pageable);
    }
    
    /**
     * Get high priority reports with pagination
     */
    public Page<HealthReport> getHighPriorityReports(Pageable pageable) {
        return healthReportRepository.findHighPriorityReports(pageable);
    }
}
