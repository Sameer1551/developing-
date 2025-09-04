--- a/backend-java/src/main/java/com/healthnet/security/SecurityConfig.java
+++ b/backend-java/src/main/java/com/healthnet/security/SecurityConfig.java
@@ -1,13 +1,18 @@
 package com.healthnet.security;
 
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.security.authentication.AuthenticationManager;
 import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
 import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
 import org.springframework.security.config.http.SessionCreationPolicy;
 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import org.springframework.security.crypto.password.PasswordEncoder;
 import org.springframework.security.web.SecurityFilterChain;
 import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
+import org.springframework.web.cors.CorsConfiguration;
+import org.springframework.web.cors.CorsConfigurationSource;
+import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
+
+import java.util.Arrays;
 
 /**
  * Security configuration for the application.
@@ -37,6 +42,7 @@
                 // Permit all requests to Auth and Swagger endpoints
                 .authorizeHttpRequests(authorize -> authorize
                         .requestMatchers("/api/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/h2-console/**").permitAll()
+                        .requestMatchers("/api/health/**", "/api/users/**", "/api/analytics/**").permitAll()
                         .anyRequest().authenticated()
                 )
                 .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
@@ -46,4 +52,14 @@
     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
         return authenticationConfiguration.getAuthenticationManager();
     }
+
+    @Bean
+    public CorsConfigurationSource corsConfigurationSource() {
+        CorsConfiguration configuration = new CorsConfiguration();
+        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Allow frontend origin
+        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
+        configuration.setAllowedHeaders(Arrays.asList("*"));
+        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
+        source.registerCorsConfiguration("/**", configuration);
+        return source;
+    }
 }