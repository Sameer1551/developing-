package com.healthnet.repository;

import com.healthnet.entity.HealthReport;
import com.healthnet.entity.ReportStatus;
import com.healthnet.entity.UrgencyLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for HealthReport entity operations
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
@Repository
public interface HealthReportRepository extends JpaRepository<HealthReport, Long> {
    
    /**
     * Find reports by district
     */
    List<HealthReport> findByDistrict(String district);
    
    /**
     * Find reports by village
     */
    List<HealthReport> findByVillage(String village);
    
    /**
     * Find reports by status
     */
    List<HealthReport> findByStatus(ReportStatus status);
    
    /**
     * Find reports by urgency level
     */
    List<HealthReport> findByUrgency(UrgencyLevel urgency);
    
    /**
     * Find reports by district and status
     */
    List<HealthReport> findByDistrictAndStatus(String district, ReportStatus status);
    
    /**
     * Find reports by urgency and status
     */
    List<HealthReport> findByUrgencyAndStatus(UrgencyLevel urgency, ReportStatus status);
    
    /**
     * Find reports created after a specific date
     */
    List<HealthReport> findByCreatedAtAfter(LocalDateTime date);
    
    /**
     * Find reports created between two dates
     */
    List<HealthReport> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find reports by reporter name
     */
    List<HealthReport> findByReporterName(String reporterName);
    
    /**
     * Find reports by reporter phone
     */
    List<HealthReport> findByReporterPhone(String reporterPhone);
    
    /**
     * Find reports processed by a specific user
     */
    List<HealthReport> findByProcessedBy(Long processedBy);
    
    /**
     * Find reports with pagination
     */
    Page<HealthReport> findAll(Pageable pageable);
    
    /**
     * Find reports by district with pagination
     */
    Page<HealthReport> findByDistrict(String district, Pageable pageable);
    
    /**
     * Find reports by status with pagination
     */
    Page<HealthReport> findByStatus(ReportStatus status, Pageable pageable);
    
    /**
     * Find reports by urgency with pagination
     */
    Page<HealthReport> findByUrgency(UrgencyLevel urgency, Pageable pageable);
    
    /**
     * Find reports by district and status with pagination
     */
    Page<HealthReport> findByDistrictAndStatus(String district, ReportStatus status, Pageable pageable);
    
    /**
     * Find reports by urgency and status with pagination
     */
    Page<HealthReport> findByUrgencyAndStatus(UrgencyLevel urgency, ReportStatus status, Pageable pageable);
    
    /**
     * Find reports created after a specific date with pagination
     */
    Page<HealthReport> findByCreatedAtAfter(LocalDateTime date, Pageable pageable);
    
    /**
     * Find reports created between two dates with pagination
     */
    Page<HealthReport> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    /**
     * Find reports by multiple districts
     */
    @Query("SELECT hr FROM HealthReport hr WHERE hr.district IN :districts")
    List<HealthReport> findByDistricts(@Param("districts") List<String> districts);
    
    /**
     * Find reports by multiple villages
     */
    @Query("SELECT hr FROM HealthReport hr WHERE hr.village IN :villages")
    List<HealthReport> findByVillages(@Param("villages") List<String> villages);
    
    /**
     * Find reports by multiple statuses
     */
    @Query("SELECT hr FROM HealthReport hr WHERE hr.status IN :statuses")
    List<HealthReport> findByStatuses(@Param("statuses") List<ReportStatus> statuses);
    
    /**
     * Find reports by multiple urgency levels
     */
    @Query("SELECT hr FROM HealthReport hr WHERE hr.urgency IN :urgencyLevels")
    List<HealthReport> findByUrgencyLevels(@Param("urgencyLevels") List<UrgencyLevel> urgencyLevels);
    
    /**
     * Find reports containing specific symptoms
     */
    @Query("SELECT hr FROM HealthReport hr JOIN hr.symptoms s WHERE s IN :symptoms")
    List<HealthReport> findBySymptoms(@Param("symptoms") List<String> symptoms);
    
    /**
     * Find reports by reporter name containing (case insensitive)
     */
    @Query("SELECT hr FROM HealthReport hr WHERE LOWER(hr.reporterName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<HealthReport> findByReporterNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Find reports by village containing (case insensitive)
     */
    @Query("SELECT hr FROM HealthReport hr WHERE LOWER(hr.village) LIKE LOWER(CONCAT('%', :village, '%'))")
    List<HealthReport> findByVillageContainingIgnoreCase(@Param("village") String village);
    
    /**
     * Find reports by district containing (case insensitive)
     */
    @Query("SELECT hr FROM HealthReport hr WHERE LOWER(hr.district) LIKE LOWER(CONCAT('%', :district, '%'))")
    List<HealthReport> findByDistrictContainingIgnoreCase(@Param("district") String district);
    
    /**
     * Find high priority reports (HIGH and CRITICAL urgency)
     */
    @Query("SELECT hr FROM HealthReport hr WHERE hr.urgency IN ('HIGH', 'CRITICAL')")
    List<HealthReport> findHighPriorityReports();
    
    /**
     * Find high priority reports with pagination
     */
    @Query("SELECT hr FROM HealthReport hr WHERE hr.urgency IN ('HIGH', 'CRITICAL')")
    Page<HealthReport> findHighPriorityReports(Pageable pageable);
    
    /**
     * Find pending reports
     */
    @Query("SELECT hr FROM HealthReport hr WHERE hr.status = 'PENDING' ORDER BY hr.urgency DESC, hr.createdAt ASC")
    List<HealthReport> findPendingReportsOrderedByPriority();
    
    /**
     * Find pending reports with pagination
     */
    @Query("SELECT hr FROM HealthReport hr WHERE hr.status = 'PENDING' ORDER BY hr.urgency DESC, hr.createdAt ASC")
    Page<HealthReport> findPendingReportsOrderedByPriority(Pageable pageable);
    
    /**
     * Count reports by district
     */
    long countByDistrict(String district);
    
    /**
     * Count reports by status
     */
    long countByStatus(ReportStatus status);
    
    /**
     * Count reports by urgency
     */
    long countByUrgency(UrgencyLevel urgency);
    
    /**
     * Count reports by district and status
     */
    long countByDistrictAndStatus(String district, ReportStatus status);
    
    /**
     * Count reports by urgency and status
     */
    long countByUrgencyAndStatus(UrgencyLevel urgency, ReportStatus status);
    
    /**
     * Count reports created after a specific date
     */
    long countByCreatedAtAfter(LocalDateTime date);
    
    /**
     * Count reports created between two dates
     */
    long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Count reports by processed user
     */
    long countByProcessedBy(Long processedBy);
    
    /**
     * Find reports created today
     */
    @Query("SELECT hr FROM HealthReport hr WHERE hr.createdAt >= :startOfDay AND hr.createdAt < :endOfDay")
    List<HealthReport> findReportsCreatedToday(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
    
    /**
     * Find reports created this week
     */
    @Query("SELECT hr FROM HealthReport hr WHERE hr.createdAt >= :weekStart")
    List<HealthReport> findReportsCreatedThisWeek(@Param("weekStart") LocalDateTime weekStart);
    
    /**
     * Find reports created this month
     */
    @Query("SELECT hr FROM HealthReport hr WHERE YEAR(hr.createdAt) = YEAR(CURRENT_DATE) AND MONTH(hr.createdAt) = MONTH(CURRENT_DATE)")
    List<HealthReport> findReportsCreatedThisMonth();
    
    /**
     * Find reports by district and date range
     */
    @Query("SELECT hr FROM HealthReport hr WHERE hr.district = :district AND hr.createdAt BETWEEN :startDate AND :endDate")
    List<HealthReport> findByDistrictAndDateRange(@Param("district") String district, 
                                                  @Param("startDate") LocalDateTime startDate, 
                                                  @Param("endDate") LocalDateTime endDate);
}
