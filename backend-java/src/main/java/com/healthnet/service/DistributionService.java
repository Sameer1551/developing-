package com.healthnet.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthnet.dto.DistributionRecordDto;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service for managing medicine distribution records
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
@Service
public class DistributionService {
    
    private static final String DATA_DIR = "Data-UAD";
    private static final String DISTRIBUTION_FILE = "distributionrecod.json";
    private final ObjectMapper objectMapper;
    
    public DistributionService() {
        this.objectMapper = new ObjectMapper();
        // Ensure Data-UAD directory exists
        createDataDirectoryIfNotExists();
    }
    
    /**
     * Save a distribution record to the JSON file
     */
    public DistributionRecordDto saveDistributionRecord(DistributionRecordDto recordDto) {
        try {
            // Generate ID and timestamp if not provided
            if (recordDto.getId() == null || recordDto.getId().isEmpty()) {
                recordDto.setId(UUID.randomUUID().toString());
            }
            
            if (recordDto.getTimestamp() == null) {
                recordDto.setTimestamp(LocalDateTime.now());
            }
            
            if (recordDto.getDate() == null || recordDto.getDate().isEmpty()) {
                recordDto.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            
            // Read existing records
            List<DistributionRecordDto> existingRecords = getAllDistributionRecords();
            
            // Add new record
            existingRecords.add(recordDto);
            
            // Write back to file
            writeRecordsToFile(existingRecords);
            
            return recordDto;
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to save distribution record", e);
        }
    }
    
    /**
     * Get all distribution records
     */
    public List<DistributionRecordDto> getAllDistributionRecords() {
        try {
            Path filePath = Paths.get(DATA_DIR, DISTRIBUTION_FILE);
            
            if (!Files.exists(filePath)) {
                return new ArrayList<>();
            }
            
            String content = Files.readString(filePath);
            if (content.trim().isEmpty()) {
                return new ArrayList<>();
            }
            
            return objectMapper.readValue(content, new TypeReference<List<DistributionRecordDto>>() {});
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to read distribution records", e);
        }
    }
    
    /**
     * Get distribution records by patient name
     */
    public List<DistributionRecordDto> getDistributionRecordsByPatient(String patientName) {
        return getAllDistributionRecords().stream()
                .filter(record -> record.getPatientName().toLowerCase().contains(patientName.toLowerCase()))
                .toList();
    }
    
    /**
     * Get distribution records by medicine type
     */
    public List<DistributionRecordDto> getDistributionRecordsByMedicineType(String medicineType) {
        return getAllDistributionRecords().stream()
                .filter(record -> record.getMedicineType().toLowerCase().contains(medicineType.toLowerCase()))
                .toList();
    }
    
    /**
     * Write records to the JSON file
     */
    private void writeRecordsToFile(List<DistributionRecordDto> records) throws IOException {
        Path filePath = Paths.get(DATA_DIR, DISTRIBUTION_FILE);
        String jsonContent = objectMapper.writeValueAsString(records);
        Files.writeString(filePath, jsonContent);
    }
    
    /**
     * Create Data-UAD directory if it doesn't exist
     */
    private void createDataDirectoryIfNotExists() {
        try {
            Path dataDir = Paths.get(DATA_DIR);
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create Data-UAD directory", e);
        }
    }
}
