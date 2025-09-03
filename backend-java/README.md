# NE HealthNet Backend - Java Spring Boot

## Overview
This is the Java Spring Boot backend for the NE HealthNet (Northeast India Health Monitoring System). It provides a robust, enterprise-grade REST API for managing health reports, users, and authentication.

## Features

### 🔐 **Security & Authentication**
- JWT-based authentication
- Role-based access control (Admin, Health Officer, Staff)
- Spring Security integration
- CORS configuration

### 👥 **User Management**
- User CRUD operations
- Role-based permissions
- User statistics and analytics
- District and state-based filtering

### 📊 **Health Reports**
- Public health report submission
- Report status management
- Urgency level classification
- Symptom tracking
- Photo upload support
- Admin notes and processing

### 🗄️ **Database**
- JPA/Hibernate integration
- H2 database for development
- PostgreSQL support for production
- Automatic schema generation
- Sample data seeding

### 📚 **API Documentation**
- Swagger/OpenAPI 3.0 documentation
- Interactive API explorer
- Comprehensive endpoint documentation

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security 6**
- **Spring Data JPA**
- **H2 Database** (Development)
- **PostgreSQL** (Production)
- **JWT** (JSON Web Tokens)
- **Swagger/OpenAPI 3.0**
- **Maven** (Build Tool)

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd backend-java
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:4000`
   - Swagger UI: `http://localhost:4000/swagger-ui.html`
   - H2 Console: `http://localhost:4000/h2-console`

### Configuration

The application uses different profiles for different environments:

- **Development**: `application.yml` (default)
- **Production**: `application-prod.yml`

Key configuration properties:
```yaml
# JWT Configuration
jwt:
  secret: your-secret-key
  expiration: 86400000 # 24 hours

# Database Configuration
spring:
  datasource:
    url: jdbc:h2:mem:healthnetdb
    username: sa
    password: password
```

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/validate` - Validate JWT token
- `POST /api/auth/logout` - User logout
- `POST /api/auth/refresh` - Refresh JWT token

### Users (Admin Only)
- `GET /api/users` - Get all users (paginated)
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `GET /api/users/role/{role}` - Get users by role
- `GET /api/users/district/{district}` - Get users by district
- `GET /api/users/statistics` - Get user statistics

### Health Reports
- `GET /api/health-reports` - Get all reports (paginated)
- `GET /api/health-reports/{id}` - Get report by ID
- `POST /api/health-reports` - Create new report (Public)
- `PUT /api/health-reports/{id}` - Update report
- `DELETE /api/health-reports/{id}` - Delete report
- `GET /api/health-reports/district/{district}` - Get reports by district
- `GET /api/health-reports/status/{status}` - Get reports by status
- `GET /api/health-reports/urgency/{urgency}` - Get reports by urgency
- `GET /api/health-reports/pending` - Get pending reports
- `GET /api/health-reports/high-priority` - Get high priority reports
- `PATCH /api/health-reports/{id}/status` - Update report status
- `PATCH /api/health-reports/{id}/notes` - Add admin notes
- `GET /api/health-reports/today` - Get today's reports
- `GET /api/health-reports/statistics` - Get report statistics

## User Roles & Permissions

### Admin
- Full access to all endpoints
- User management
- Report management
- Analytics and statistics

### Health Officer
- View and manage health reports
- Update report status
- Add admin notes
- View statistics

### Staff
- View health reports
- Basic report management
- Limited access to statistics

## Database Schema

### Users Table
- `id` - Primary key
- `name` - User's full name
- `email` - Email address (unique)
- `phone` - Phone number (unique)
- `role` - User role (ADMIN, HEALTH_OFFICER, STAFF)
- `status` - Account status (ACTIVE, INACTIVE, SUSPENDED)
- `district` - User's district
- `state` - User's state
- `permissions` - List of permissions
- `join_date` - Account creation date
- `last_active` - Last activity timestamp

### Health Reports Table
- `id` - Primary key
- `reporter_name` - Name of person reporting
- `reporter_phone` - Phone number of reporter
- `village` - Village name
- `district` - District name
- `symptoms` - List of symptoms
- `description` - Additional details
- `urgency` - Urgency level (LOW, MEDIUM, HIGH, CRITICAL)
- `status` - Report status (PENDING, IN_PROGRESS, PROCESSED, RESOLVED)
- `photo_urls` - List of photo URLs
- `consent_given` - Consent flag
- `created_at` - Report creation timestamp
- `updated_at` - Last update timestamp
- `processed_by` - ID of user who processed
- `processed_at` - Processing timestamp
- `admin_notes` - Administrative notes

## Development

### Running Tests
```bash
mvn test
```

### Code Quality
```bash
mvn checkstyle:check
mvn spotbugs:check
```

### Building for Production
```bash
mvn clean package -Pprod
```

## Deployment

### Docker Deployment
```bash
# Build Docker image
docker build -t ne-healthnet-backend .

# Run container
docker run -p 4000:4000 ne-healthnet-backend
```

### Environment Variables
```bash
# Database
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password

# JWT
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000

# Server
PORT=4000
```

## Monitoring & Health Checks

The application includes Spring Boot Actuator for monitoring:

- Health Check: `GET /actuator/health`
- Application Info: `GET /actuator/info`
- Metrics: `GET /actuator/metrics`

## Security Considerations

1. **JWT Secret**: Use a strong, unique secret key in production
2. **Database Credentials**: Store securely using environment variables
3. **CORS**: Configure appropriate CORS policies for your frontend
4. **HTTPS**: Always use HTTPS in production
5. **Input Validation**: All inputs are validated using Bean Validation
6. **SQL Injection**: Protected by JPA/Hibernate
7. **XSS**: Input sanitization and output encoding

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions:
- Email: support@healthnet.gov.in
- Documentation: [API Documentation](http://localhost:4000/swagger-ui.html)
- Issues: GitHub Issues

## Changelog

### Version 1.0.0
- Initial release
- JWT authentication
- User management
- Health report management
- API documentation
- Database integration
