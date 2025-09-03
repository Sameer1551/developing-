# Migration Guide: Node.js to Java Spring Boot Backend

## 🎯 **Migration Overview**

This guide will help you migrate from your existing Node.js backend to the new Java Spring Boot backend while maintaining all functionality and improving security, scalability, and maintainability.

## 📋 **Migration Checklist**

### ✅ **Completed Tasks**
- [x] Java Spring Boot project structure
- [x] JPA entities (User, HealthReport)
- [x] Repository layer with advanced queries
- [x] REST controllers with full CRUD operations
- [x] JWT authentication and authorization
- [x] Spring Security configuration
- [x] Input validation and error handling
- [x] Application configuration (dev/prod)
- [x] API documentation with Swagger
- [x] Docker containerization
- [x] Database migration scripts

## 🔄 **Migration Steps**

### **Step 1: Environment Setup**

1. **Install Prerequisites**
   ```bash
   # Install Java 17
   sudo apt update
   sudo apt install openjdk-17-jdk

   # Install Maven
   sudo apt install maven

   # Verify installations
   java -version
   mvn -version
   ```

2. **Clone and Setup**
   ```bash
   # The Java backend is already created in backend-java/
   cd backend-java
   mvn clean install
   ```

### **Step 2: Database Migration**

1. **Development (H2 Database)**
   - No setup required - H2 runs in-memory
   - Sample data automatically loaded from `data.sql`

2. **Production (PostgreSQL)**
   ```bash
   # Install PostgreSQL
   sudo apt install postgresql postgresql-contrib

   # Create database
   sudo -u postgres createdb healthnet_prod
   sudo -u postgres createuser healthnet_user
   sudo -u postgres psql -c "ALTER USER healthnet_user PASSWORD 'secure_password';"
   sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE healthnet_prod TO healthnet_user;"
   ```

### **Step 3: Configuration**

1. **Update Frontend API URLs**
   ```typescript
   // In your React frontend, update API base URL
   const API_BASE_URL = 'http://localhost:4000/api';
   ```

2. **Environment Variables**
   ```bash
   # Create .env file for production
   DB_USERNAME=healthnet_user
   DB_PASSWORD=secure_password
   JWT_SECRET=your-super-secure-jwt-secret-key-here
   JWT_EXPIRATION=86400000
   ```

### **Step 4: Data Migration**

1. **Export from Node.js Backend**
   ```bash
   # Your existing DATAUAD.json contains user data
   # This will be automatically imported via data.sql
   ```

2. **Verify Data**
   ```bash
   # Check H2 console at http://localhost:4000/h2-console
   # JDBC URL: jdbc:h2:mem:healthnetdb
   # Username: sa
   # Password: password
   ```

## 🚀 **Running the New Backend**

### **Development Mode**
```bash
cd backend-java
mvn spring-boot:run
```

### **Production Mode**
```bash
cd backend-java
mvn clean package -Pprod
java -jar target/ne-healthnet-backend-1.0.0.jar --spring.profiles.active=prod
```

### **Docker Deployment**
```bash
cd backend-java
docker-compose up -d
```

## 🔗 **API Endpoint Mapping**

| Node.js Endpoint | Java Spring Boot Endpoint | Notes |
|------------------|---------------------------|-------|
| `GET /api/users` | `GET /api/users` | ✅ Same endpoint |
| `POST /api/users` | `POST /api/users` | ✅ Same endpoint |
| `POST /api/auth/login` | `POST /api/auth/login` | ✅ Enhanced with JWT |
| `POST /api/health-reports` | `POST /api/health-reports` | ✅ Public endpoint |
| N/A | `GET /api/users/statistics` | 🆕 New feature |
| N/A | `GET /api/health-reports/pending` | 🆕 New feature |
| N/A | `GET /api/health-reports/high-priority` | 🆕 New feature |

## 🔐 **Authentication Changes**

### **Before (Node.js)**
```javascript
// Simple email/password validation
if (foundUser.mobile !== password) {
  return false;
}
```

### **After (Java Spring Boot)**
```java
// JWT-based authentication with role validation
@PostMapping("/login")
public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    LoginResponse response = authService.login(request);
    return ResponseEntity.ok(response);
}
```

### **Frontend Updates Required**
```typescript
// Update your AuthContext to handle JWT tokens
const login = async (email: string, password: string, role: string) => {
  const response = await fetch('http://localhost:4000/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password, role })
  });
  
  const data = await response.json();
  localStorage.setItem('token', data.token); // Store JWT token
  return data;
};
```

## 📊 **Enhanced Features**

### **1. Advanced Querying**
```java
// Find users by multiple criteria
List<User> users = userRepository.findByRoleAndDistrict(UserRole.HEALTH_OFFICER, "Baksa");

// Get pending reports ordered by priority
List<HealthReport> reports = healthReportRepository.findPendingReportsOrderedByPriority();
```

### **2. Pagination Support**
```java
// All list endpoints now support pagination
Page<User> users = userService.getAllUsers(PageRequest.of(0, 10));
```

### **3. Comprehensive Validation**
```java
@NotBlank(message = "Name is required")
@Email(message = "Email should be valid")
private String email;
```

### **4. Role-Based Security**
```java
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<List<UserDto>> getAllUsers() {
    // Only admins can access
}
```

## 🧪 **Testing the Migration**

### **1. Start Both Backends**
```bash
# Terminal 1: Start Node.js backend (for comparison)
cd backend
npm start

# Terminal 2: Start Java backend
cd backend-java
mvn spring-boot:run
```

### **2. Test API Endpoints**
```bash
# Test user creation
curl -X POST http://localhost:4000/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test@example.com","phone":"1234567890","role":"STAFF","district":"Test District","state":"Test State"}'

# Test authentication
curl -X POST http://localhost:4000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"1234567890","role":"staff"}'
```

### **3. Verify Data Consistency**
- Check that all users from `DATAUAD.json` are imported
- Verify health reports are accessible
- Test role-based permissions

## 🔧 **Troubleshooting**

### **Common Issues**

1. **Port Conflicts**
   ```bash
   # If port 4000 is busy, change in application.yml
   server:
     port: 4001
   ```

2. **Database Connection Issues**
   ```bash
   # Check H2 console access
   # URL: http://localhost:4000/h2-console
   # JDBC URL: jdbc:h2:mem:healthnetdb
   ```

3. **JWT Token Issues**
   ```bash
   # Check JWT secret in application.yml
   jwt:
     secret: mySecretKeyForNEHealthNetApplication2024
   ```

### **Logs and Debugging**
```bash
# Enable debug logging
logging:
  level:
    com.healthnet: DEBUG
    org.springframework.security: DEBUG
```

## 📈 **Performance Improvements**

### **Database Optimizations**
- JPA/Hibernate query optimization
- Connection pooling with HikariCP
- Indexed queries for better performance

### **Security Enhancements**
- JWT token-based authentication
- Role-based access control
- Input validation and sanitization
- CORS configuration

### **Scalability Features**
- Stateless authentication
- Horizontal scaling support
- Docker containerization
- Production-ready configuration

## 🎉 **Migration Benefits**

### **Enterprise Features**
- ✅ **Spring Security**: Industry-standard security
- ✅ **JPA/Hibernate**: Advanced ORM capabilities
- ✅ **Validation**: Comprehensive input validation
- ✅ **Documentation**: Auto-generated API docs
- ✅ **Testing**: Built-in testing framework
- ✅ **Monitoring**: Actuator health checks

### **Developer Experience**
- ✅ **Type Safety**: Strong typing with Java
- ✅ **IDE Support**: Excellent IntelliJ/Eclipse support
- ✅ **Debugging**: Advanced debugging capabilities
- ✅ **Hot Reload**: Spring Boot DevTools
- ✅ **Profiles**: Environment-specific configurations

### **Production Readiness**
- ✅ **Docker Support**: Containerized deployment
- ✅ **Health Checks**: Application monitoring
- ✅ **Logging**: Structured logging
- ✅ **Metrics**: Performance monitoring
- ✅ **Security**: Production-grade security

## 🚀 **Next Steps**

1. **Test the Migration**: Run both backends and verify functionality
2. **Update Frontend**: Modify API calls to use JWT authentication
3. **Deploy to Production**: Use Docker Compose for deployment
4. **Monitor Performance**: Use Actuator endpoints for monitoring
5. **Scale as Needed**: Add load balancers and multiple instances

## 📞 **Support**

If you encounter any issues during migration:
1. Check the logs: `tail -f logs/healthnet-backend.log`
2. Verify configuration: Check `application.yml`
3. Test endpoints: Use Swagger UI at `http://localhost:4000/swagger-ui.html`
4. Review documentation: Check the README.md in backend-java/

---

**🎯 Your Java Spring Boot backend is now ready for production use with enterprise-grade features!**
