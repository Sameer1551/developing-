# NE HealthNet - Northeast India Health Monitoring System

## 🌟 Overview
NE HealthNet is a comprehensive health monitoring and management system designed specifically for Northeast India. It provides multilingual support for 25+ regional languages, open access health reporting for citizens, and role-based dashboards for health workers and administrators. The system features a hybrid Java-Python backend architecture for enterprise-grade security and AI-powered health analytics.

## 🚀 Key Features

### 🌐 Multilingual Support (25+ Languages)
- **English, Hindi, Assamese, Bengali, Nepali**
- **Northeast Regional Languages**: Bodo, Karbi, Mishing, Manipuri, Khasi, Garo, Jaintia, Mizo, Nagamese, Ao, Angami, Sema, Lotha, Nyishi, Apatani, Adi, Mishmi, Monpa, Tripuri, Kokborok
- **Dynamic Language Switching**: Real-time language change without page reload
- **Localized Content**: All UI elements, forms, and content translated

### 🔓 Open Access Features (No Login Required)
- **Health Issue Reporting**: Submit detailed health reports with symptoms, photos, and location
- **Community Alerts**: View real-time health alerts and disease outbreak notifications
- **Health Awareness**: Access educational content and prevention information
- **Water Quality Information**: Public access to water quality data and testing results
- **Interactive Northeast Map**: Explore health data across Northeast India regions

### 👥 Role-Based Access Control
- **Public Users**: Blue theme, basic health reporting and alert viewing
- **Health Workers**: Green theme, staff dashboard, water testing, medicine distribution
- **Administrators**: Purple theme, admin dashboard, analytics, user management, AI predictions

### 🗺️ Geographic Features
- **Interactive Maps**: Leaflet-based maps showing health data across Northeast India
- **District-wise Analytics**: Health statistics and reports organized by districts
- **GADM Integration**: Administrative boundary data for accurate geographic representation
- **Location-based Reporting**: Precise location tracking for health incidents

## 🏗️ Architecture

### Frontend (React + TypeScript)
- **Modern React 18** with TypeScript for type safety
- **Vite** for fast development and building
- **Tailwind CSS** for responsive, modern UI design
- **React Router** for client-side routing
- **i18next** for comprehensive internationalization
- **Leaflet** for interactive mapping
- **Lucide React** for consistent iconography

### Backend (Hybrid Java + Python)
- **Java Spring Boot**: Enterprise-grade security, authentication, and user management
- **Python FastAPI**: Health APIs, data processing, and AI/ML capabilities
- **JWT Authentication**: Secure token-based authentication
- **PostgreSQL**: Production database with H2 for development
- **Swagger/OpenAPI**: Comprehensive API documentation

### Data & Analytics
- **Health Reports**: Structured health incident reporting with photo uploads
- **Alert System**: Real-time health alerts and notifications
- **Analytics Dashboard**: Comprehensive health statistics and trends
- **AI Predictions**: Machine learning models for disease outbreak prediction

## 🛠️ Technology Stack

### Frontend
```json
{
  "react": "^18.3.1",
  "typescript": "^5.5.3",
  "vite": "^5.4.2",
  "tailwindcss": "^3.4.1",
  "react-router-dom": "^7.8.2",
  "i18next": "^25.4.2",
  "leaflet": "^1.9.4",
  "lucide-react": "^0.344.0"
}
```

### Backend
```xml
<!-- Java Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
</dependency>
```

```python
# Python FastAPI
fastapi==0.104.1
uvicorn==0.24.0
sqlalchemy==2.0.23
scikit-learn==1.3.2
pandas==2.1.4
```

## 🚀 Quick Start

### Prerequisites
- **Node.js** 18+ and npm
- **Java** 17+
- **Maven** 3.6+
- **Python** 3.9+
- **PostgreSQL** (optional, H2 included for development)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd ne-healthnet
   ```

2. **Install frontend dependencies**
   ```bash
   npm install
   ```

3. **Start the development servers**
   ```bash
   # Starts both frontend (Vite) and backend (Spring Boot) concurrently
   npm run dev
   ```

4. **Access the application**
   - Frontend: `http://localhost:5173`
   - Backend API: `http://localhost:4000`
   - API Documentation: `http://localhost:4000/swagger-ui.html`

### Alternative Setup (Individual Services)

**Frontend Only:**
```bash
npm run dev
# Frontend runs on http://localhost:5173
```

**Backend Only:**
```bash
cd backend-java
mvn spring-boot:run
# Backend runs on http://localhost:4000
```

## 📱 User Interfaces

### Public Interface
- **Home Page**: Health statistics, quick access to reporting
- **Report Issues**: Comprehensive health incident reporting form
- **Check Alerts**: Real-time health alerts and notifications
- **Awareness**: Health education and prevention information
- **Water Quality**: Public water quality data and testing results
- **Northeast Map**: Interactive map with health data visualization

### Staff Dashboard
- **Reports Management**: View and process health reports
- **Water Testing**: Submit water quality test results
- **Medicine Distribution**: Track medicine distribution
- **Statistics**: District-wise health statistics

### Admin Dashboard
- **User Management**: Create and manage user accounts
- **Alert Management**: Create and manage health alerts
- **Analytics**: Comprehensive health analytics and trends
- **AI Predictions**: Disease outbreak predictions and risk assessment
- **Resource Deployment**: Manage health resources and personnel

## 🌍 Multilingual Support

The system supports 25+ languages with complete localization:

### Supported Languages
- **Major Languages**: English, Hindi, Assamese, Bengali, Nepali
- **Northeast Regional**: Bodo, Karbi, Mishing, Manipuri, Khasi, Garo, Jaintia, Mizo, Nagamese, Ao, Angami, Sema, Lotha, Nyishi, Apatani, Adi, Mishmi, Monpa, Tripuri, Kokborok

### Language Features
- **Dynamic Switching**: Change language without page reload
- **Localized Content**: All UI elements, forms, and messages translated
- **Regional Context**: Health information adapted for local contexts
- **Browser Detection**: Automatic language detection based on browser settings

## 🔐 Security Features

### Authentication & Authorization
- **JWT Tokens**: Secure token-based authentication
- **Role-Based Access**: Admin, Health Officer, Staff roles with specific permissions
- **Session Management**: Secure session handling with token refresh
- **CORS Configuration**: Proper cross-origin resource sharing setup

### Data Security
- **Input Validation**: Comprehensive input sanitization and validation
- **SQL Injection Protection**: JPA/Hibernate protection against SQL injection
- **XSS Protection**: Input sanitization and output encoding
- **HTTPS Ready**: Production-ready HTTPS configuration

## 📊 Data Management

### Health Reports
- **Structured Reporting**: Standardized health incident reporting
- **Photo Uploads**: Support for multiple photo attachments
- **Location Tracking**: Precise geographic location capture
- **Urgency Levels**: Critical, High, Medium, Low priority classification
- **Status Tracking**: Pending, In Progress, Processed, Resolved statuses

### Alert System
- **Real-time Alerts**: Immediate notification of health incidents
- **Geographic Targeting**: District and location-specific alerts
- **Priority Management**: Critical, High, Medium, Low priority alerts
- **Response Tracking**: Alert response time and resolution tracking

## 🗺️ Geographic Features

### Interactive Maps
- **Leaflet Integration**: High-performance interactive maps
- **GADM Data**: Administrative boundary data for Northeast India
- **Health Data Overlay**: Visual representation of health incidents
- **District Boundaries**: Clear administrative divisions

### Location Services
- **Precise Location**: GPS and manual location input
- **District Mapping**: Automatic district assignment based on location
- **Regional Analytics**: Health data aggregated by geographic regions

## 🤖 AI & Analytics

### Machine Learning Features
- **Disease Prediction**: AI models for outbreak prediction
- **Health Trend Analysis**: Pattern recognition in health data
- **Risk Assessment**: Automated risk scoring for health incidents
- **Anomaly Detection**: Identification of unusual health patterns

### Analytics Dashboard
- **Real-time Statistics**: Live health data and metrics
- **Trend Analysis**: Historical data analysis and trends
- **Geographic Analytics**: Location-based health insights
- **Performance Metrics**: System performance and usage statistics

## 🚀 Deployment

### Development
```bash
# Start development environment
npm run dev
```

### Production Build
```bash
# Build frontend for production
npm run build

# Build backend for production
cd backend-java
mvn clean package -Pprod
```

### Docker Deployment
```bash
# Build and run with Docker Compose
docker-compose up -d
```

## 📁 Project Structure

```
ne-healthnet/
├── src/                          # Frontend React application
│   ├── components/               # Reusable React components
│   │   ├── AdminDashboard/       # Admin-specific components
│   │   ├── Dashboard/            # Staff dashboard components
│   │   ├── ReportForm/           # Health reporting form components
│   │   └── ...
│   ├── contexts/                 # React contexts (Auth, Language)
│   ├── locales/                  # Translation files (25+ languages)
│   ├── pages/                    # Main application pages
│   ├── types/                    # TypeScript type definitions
│   └── utils/                    # Utility functions
├── backend-java/                 # Java Spring Boot backend
│   ├── src/main/java/            # Java source code
│   ├── src/main/resources/       # Configuration files
│   ├── pom.xml                   # Maven configuration
│   └── Dockerfile               # Docker configuration
├── public/                       # Static assets
│   └── data/                     # Geographic and health data
├── scripts/                      # Utility scripts
└── docs/                         # Documentation files
```

## 🧪 Testing

### Demo Credentials
- **Email**: demo@healthworker.gov.in
- **Password**: demo123
- **Roles**: Test different roles (Staff/Admin) to see different interfaces

### Testing Features
1. **Public Access**: Navigate without login to test open access features
2. **Language Switching**: Test all 25+ supported languages
3. **Role-based Access**: Login with different roles to test permissions
4. **Geographic Features**: Test map functionality and location services
5. **Form Submission**: Test health reporting and alert creation

## 🔧 Configuration

### Environment Variables
```bash
# Database
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
DB_URL=jdbc:postgresql://localhost:5432/healthnet

# JWT
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION=86400000

# Server
PORT=4000
FRONTEND_URL=http://localhost:5173
```

### Language Configuration
Languages are configured in `src/i18n.ts` and translation files are located in `src/locales/`.

## 📈 Performance

### Frontend Optimization
- **Vite**: Fast development and optimized production builds
- **Code Splitting**: Lazy loading of components and pages
- **Image Optimization**: Optimized images and assets
- **Caching**: Browser caching for static assets

### Backend Optimization
- **Connection Pooling**: Database connection optimization
- **Caching**: Redis caching for frequently accessed data
- **API Optimization**: Efficient API endpoints and responses

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Guidelines
- Follow TypeScript best practices
- Write comprehensive tests
- Update documentation for new features
- Ensure multilingual support for new content

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

For support and questions:
- **Email**: support@healthnet.gov.in
- **Documentation**: [API Documentation](http://localhost:4000/swagger-ui.html)
- **Issues**: [GitHub Issues](https://github.com/your-repo/issues)

## 🗺️ Roadmap

### Phase 1 (Current)
- ✅ Multilingual support (25+ languages)
- ✅ Open access health reporting
- ✅ Role-based dashboards
- ✅ Interactive maps
- ✅ Java-Python hybrid backend

### Phase 2 (Upcoming)
- 🔄 Mobile app development
- 🔄 Advanced AI/ML models
- 🔄 Real-time notifications
- 🔄 Integration with health department systems
- 🔄 Advanced analytics and reporting

### Phase 3 (Future)
- 📋 IoT device integration
- 📋 Blockchain for data integrity
- 📋 Advanced predictive analytics
- 📋 Telemedicine integration
- 📋 Community health worker app

---

**NE HealthNet - Empowering Northeast India with accessible, multilingual health monitoring technology.**
