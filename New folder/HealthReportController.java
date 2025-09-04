--- a/backend-java/src/main/java/com/healthnet/controller/HealthReportController.java
+++ b/backend-java/src/main/java/com/healthnet/controller/HealthReportController.java
@@ -1,13 +1,15 @@
 package com.healthnet.controller;
 
 import com.healthnet.dto.HealthReportDto;
 import com.healthnet.service.HealthReportService;
 import io.swagger.v3.oas.annotations.Operation;
 import io.swagger.v3.oas.annotations.responses.ApiResponse;
 import io.swagger.v3.oas.annotations.responses.ApiResponses;
 import io.swagger.v3.oas.annotations.tags.Tag;
 import jakarta.validation.Valid;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
+
+import java.util.List;
 
 /**
  * REST Controller for Health Report operations.
@@ -35,4 +37,15 @@
         HealthReportDto createdReport = healthReportService.submitReport(healthReportDto);
         return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
     }
+
+    @GetMapping
+    @Operation(summary = "Get all health reports", description = "Retrieve a list of all submitted health reports. Requires authentication.")
+    @ApiResponses(value = {
+            @ApiResponse(responseCode = "200", description = "Successfully retrieved all reports"),
+            @ApiResponse(responseCode = "401", description = "Unauthorized")
+    })
+    public ResponseEntity<List<HealthReportDto>> getAllHealthReports() {
+        List<HealthReportDto> reports = healthReportService.findAllReports();
+        return ResponseEntity.ok(reports);
+    }
 }