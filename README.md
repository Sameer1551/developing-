# NE HealthNet - Open Access Health Portal

## Overview
This project implements a health monitoring system that provides open access to health reporting and alert checking for all users, while maintaining role-based access for health workers and administrators. Citizens can report health issues and check alerts without authentication.

## Features

### Open Access Features (No Login Required)
- **Report Health Issues**: Submit health reports with symptoms and location
- **Check Alerts**: View community health alerts and disease outbreaks
- **Awareness**: Access health education and prevention information
- **Home**: General health information and statistics

### Role-Based Features (Login Required)
- **Health Workers**: Staff dashboard, water quality testing, medicine distribution
- **Administrators**: Admin dashboard, analytics, user management, AI predictions

### Dynamic Header Styling
- **Public Users**: Blue theme (default)
- **Health Workers**: Green theme
- **Administrators**: Purple theme

### User Information Display
- Role badges with appropriate colors and icons (for authenticated users)
- Role-specific welcome messages
- User designation and location information
- Permission-based navigation items

## User Access Levels

### Public Users (Citizens)
- **Access**: Report health issues, check alerts, view awareness, home page
- **Authentication**: Not required
- **Navigation**: Home, Report Issues, Check Alerts, Awareness
- **Theme**: Blue

### Health Worker (Staff)
- **Permissions**: View reports, submit water tests, distribute medicine
- **Navigation**: All public features + Staff Dashboard
- **Theme**: Green
- **Authentication**: Required

### Administrator
- **Permissions**: View all reports, manage users, view analytics, manage alerts, view predictions
- **Navigation**: All features + Admin Dashboard with advanced tools
- **Theme**: Purple
- **Authentication**: Required

## Technical Implementation

### AuthContext
- Enhanced user type with permissions and designation
- Role-based permission checking
- Improved login flow with role assignment (staff/admin only)

### Header Component
- Dynamic navigation based on authentication status and user role
- Conditional styling and theming
- Responsive design for mobile and desktop
- Role-specific user information display

### Login System
- Role selection during login (staff/admin only)
- Automatic redirection based on role
- Demo credentials for testing

## Demo Credentials
- **Email**: demo@healthworker.gov.in
- **Password**: demo123
- **Roles**: Try different roles to see different dashboards and header changes

## File Structure
```
src/
├── components/
│   └── Header.tsx          # Main header component with role-based logic
├── contexts/
│   └── AuthContext.tsx     # Authentication context with role management
├── pages/
│   ├── LoginPage.tsx       # Login with role selection (staff/admin only)
│   ├── ReportIssuePage.tsx # Health issue reporting (open access)
│   ├── CheckAlertsPage.tsx # Health alerts (open access)
│   ├── StaffDashboard.tsx  # Health worker dashboard
│   └── AdminDashboard.tsx  # Administrator dashboard
└── App.tsx                 # Main app with routing
```

## How to Test
1. **Public Access**: Navigate to any page without login to access health reporting and alerts
2. **Staff/Admin Access**: Navigate to `/login` and select role
3. Use demo credentials to log in
4. Observe header changes:
   - Navigation items change based on authentication and role
   - Header color theme changes
   - User information displays role-specific details
   - Role badges appear with appropriate styling

## Future Enhancements
- Enhanced public health reporting
- Community health analytics
- Mobile app for easier reporting
- Integration with health department systems
