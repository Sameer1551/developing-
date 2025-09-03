package com.healthnet.controller;

import com.healthnet.dto.DistributionRecordDto;
import com.healthnet.service.DistributionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Medicine Distribution operations
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Medicine Distribution", description = "APIs for managing medicine distribution records")
public class DistributionController {
    
    private final DistributionService distributionService;
    
    public DistributionController(DistributionService distributionService) {
        this.distributionService = distributionService;
    }
    
    /**
     * Save a new distribution record
     */
    @PostMapping("/save-distribution")
    @Operation(summary = "Save distribution record", description = "Save a new medicine distribution record to JSON file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Distribution record saved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<DistributionRecordDto> saveDistribution(@Valid @RequestBody DistributionRecordDto recordDto) {
        DistributionRecordDto savedRecord = distributionService.saveDistributionRecord(recordDto);
        return ResponseEntity.ok(savedRecord);
    }
    
    /**
     * Get all distribution records
     */
    @GetMapping("/distribution-records")
    @Operation(summary = "Get all distribution records", description = "Retrieve all medicine distribution records")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Distribution records retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<List<DistributionRecordDto>> getAllDistributionRecords() {
        List<DistributionRecordDto> records = distributionService.getAllDistributionRecords();
        return ResponseEntity.ok(records);
    }
    
    /**
     * Get distribution records by patient name
     */
    @GetMapping("/distribution-records/patient/{patientName}")
    @Operation(summary = "Get distribution records by patient", description = "Retrieve distribution records for a specific patient")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Distribution records retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<List<DistributionRecordDto>> getDistributionRecordsByPatient(@PathVariable String patientName) {
        List<DistributionRecordDto> records = distributionService.getDistributionRecordsByPatient(patientName);
        return ResponseEntity.ok(records);
    }
    
    /**
     * Get distribution records by medicine type
     */
    @GetMapping("/distribution-records/medicine/{medicineType}")
    @Operation(summary = "Get distribution records by medicine type", description = "Retrieve distribution records for a specific medicine type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Distribution records retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<List<DistributionRecordDto>> getDistributionRecordsByMedicineType(@PathVariable String medicineType) {
        List<DistributionRecordDto> records = distributionService.getDistributionRecordsByMedicineType(medicineType);
        return ResponseEntity.ok(records);
    }
}
