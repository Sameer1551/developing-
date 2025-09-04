--- /dev/null
+++ b/backend-java/src/main/java/com/healthnet/config/DataSeeder.java
@@ -0,0 +1,50 @@
+package com.healthnet.config;
+
+import com.healthnet.entity.HealthReport;
+import com.healthnet.entity.User;
+import com.healthnet.entity.UserRole;
+import com.healthnet.entity.ReportStatus;
+import com.healthnet.repository.HealthReportRepository;
+import com.healthnet.repository.UserRepository;
+import org.springframework.boot.CommandLineRunner;
+import org.springframework.context.annotation.Bean;
+import org.springframework.context.annotation.Configuration;
+import org.springframework.security.crypto.password.PasswordEncoder;
+
+import java.time.LocalDateTime;
+import java.util.Arrays;
+
+@Configuration
+public class DataSeeder {
+
+    @Bean
+    CommandLineRunner initDatabase(UserRepository userRepository, HealthReportRepository healthReportRepository, PasswordEncoder passwordEncoder) {
+        return args -> {
+            // Seed Users
+            User admin = new User();
+            admin.setUsername("admin");
+            admin.setPassword(passwordEncoder.encode("password"));
+            admin.setEmail("admin@healthnet.com");
+            admin.setRole(UserRole.ADMIN);
+            admin.setDistrict("Imphal West");
+            admin.setState("Manipur");
+            userRepository.save(admin);
+
+            User staff = new User();
+            staff.setUsername("staff");
+            staff.setPassword(passwordEncoder.encode("password"));
+            staff.setEmail("staff@healthnet.com");
+            staff.setRole(UserRole.STAFF);
+            staff.setDistrict("Imphal East");
+            staff.setState("Manipur");
+            userRepository.save(staff);
+
+            // Seed Health Reports
+            HealthReport report1 = new HealthReport();
+            report1.setPatientName("Anonymous");
+            report1.setSubmittedAt(LocalDateTime.now());
+            report1.setStatus(ReportStatus.PENDING);
+            healthReportRepository.save(report1);
+        };
+    }
+}