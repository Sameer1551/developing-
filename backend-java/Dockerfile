# Multi-stage build for NE HealthNet Backend
FROM maven:3.9.5-openjdk-17-slim AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jre-slim

# Set working directory
WORKDIR /app

# Create non-root user
RUN groupadd -r healthnet && useradd -r -g healthnet healthnet

# Copy the built JAR file
COPY --from=build /app/target/ne-healthnet-backend-1.0.0.jar app.jar

# Create logs directory
RUN mkdir -p /app/logs && chown -R healthnet:healthnet /app

# Switch to non-root user
USER healthnet

# Expose port
EXPOSE 4000

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:4000/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
