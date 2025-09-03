
package com.healthnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main application class for NE HealthNet Backend
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
public class NeHealthNetApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeHealthNetApplication.class, args);
    }
}
