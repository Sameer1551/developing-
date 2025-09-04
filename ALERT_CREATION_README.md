# Alert Creation and SMS Notification System

## Overview

The Alert Creation feature allows administrators to create and send emergency alerts to health workers and administrators across Northeast India. The system includes automatic SMS notifications to ensure immediate response to critical situations.

## Features

### 🚨 **Alert Creation**
- **Multiple Alert Types**: Health Emergency, Water Quality, Disease Outbreak, Infrastructure, Weather
- **Priority Levels**: Critical, High, Medium, Low with visual indicators
- **District Targeting**: Send to all districts or specific districts
- **Rich Content**: Title, description, location, assignment, and notes
- **Real-time Preview**: See how many health workers will receive the alert

### 📱 **SMS Notifications**
- **Automatic Sending**: SMS sent immediately when alert is created
- **Targeted Recipients**: Only health workers and administrators in relevant districts
- **Formatted Messages**: Professional SMS format with emojis and clear information
- **Delivery Tracking**: Real-time status updates during sending process
- **Failure Handling**: Tracks failed SMS and provides retry options

### 🎯 **Target Audience**
The system automatically identifies and notifies:
- **Administrators**: District Health Officers, Government Officials
- **Health Workers**: ASHA Workers, ANM, Nurses, Health Staff
- **Medical Personnel**: Doctors, Medical Officers, Health Officers

## How to Use

### 1. **Access Alert Creation**
1. Navigate to Admin Dashboard
2. Click on "Alert Management" tab
3. Click "Create Alert" button

### 2. **Fill Alert Details**
1. **Alert Type**: Select from 5 predefined types
2. **Priority**: Choose urgency level (Critical/High/Medium/Low)
3. **Title**: Enter a clear, concise alert title
4. **Description**: Provide detailed information about the situation
5. **District**: Choose "All Districts" or specific district
6. **Location**: Optional specific location details
7. **Assignment**: Optional team assignment
8. **Notes**: Additional instructions or context

### 3. **Review and Send**
1. Review the SMS preview showing recipient count
2. Verify all information is correct
3. Click "Create Alert" to send
4. Monitor SMS delivery status in real-time

## SMS Message Format

The system sends professionally formatted SMS messages:

```
🚨 ALERT: [Alert Title]

🏥 Type: [Alert Type]
📍 District: [District Name]
⚡ Priority: [Priority Level]

[Detailed Description]

Please take immediate action.

- NE HealthNet System
```

## Technical Implementation

### **Frontend Components**
- `CreateAlertModal.tsx`: Main alert creation interface
- `AlertManagementTab.tsx`: Alert listing and management
- `smsService.ts`: SMS sending utility

### **SMS Service Architecture**
```typescript
// Supported SMS Providers
- Mock (Development/Testing)
- Twilio (International)
- AWS SNS (Cloud-based)
- MSG91 (India)
- TextLocal (India)
- Fast2SMS (India)
```

### **Data Flow**
1. **User Input** → Form validation
2. **Alert Creation** → Database storage
3. **User Filtering** → District-based recipient selection
4. **SMS Sending** → Batch processing with rate limiting
5. **Status Tracking** → Real-time delivery updates

## Configuration

### **SMS Provider Setup**

#### **For Development (Mock)**
```typescript
// Default configuration - no setup required
const smsService = new SMSService({ provider: 'mock' });
```

#### **For Production (MSG91)**
```typescript
const smsService = new SMSService({
  provider: 'msg91',
  apiKey: 'YOUR_MSG91_API_KEY',
  templateId: 'YOUR_TEMPLATE_ID'
});
```

#### **For Production (TextLocal)**
```typescript
const smsService = new SMSService({
  provider: 'textlocal',
  apiKey: 'YOUR_TEXTLOCAL_API_KEY',
  from: 'TXTLCL'
});
```

### **Environment Variables**
```env
# SMS Configuration
SMS_PROVIDER=msg91
SMS_API_KEY=your_api_key_here
SMS_TEMPLATE_ID=your_template_id
SMS_SENDER_ID=NEHEALTH
```

## District Coverage

The system covers all districts in Northeast India:

### **States and Districts**
- **Arunachal Pradesh**: 25 districts
- **Assam**: 32 districts  
- **Manipur**: 16 districts
- **Meghalaya**: 12 districts
- **Mizoram**: 11 districts
- **Nagaland**: 12 districts
- **Tripura**: 8 districts
- **Sikkim**: 4 districts

### **Total Coverage**: 120 districts

## Security and Privacy

### **Data Protection**
- Phone numbers are encrypted in transit
- SMS content is logged for audit purposes
- User consent required for SMS notifications
- GDPR-compliant data handling

### **Rate Limiting**
- Maximum 100 SMS per minute
- Automatic retry with exponential backoff
- Failure tracking and reporting

## Monitoring and Analytics

### **SMS Delivery Metrics**
- **Success Rate**: Percentage of successful deliveries
- **Response Time**: Average time to delivery
- **Cost Tracking**: SMS costs per alert
- **Failure Analysis**: Common failure reasons

### **Alert Analytics**
- **Alert Volume**: Number of alerts per district
- **Response Time**: Time from alert to first response
- **Alert Types**: Distribution by type and priority
- **Geographic Distribution**: Alerts by region

## Troubleshooting

### **Common Issues**

#### **SMS Not Sending**
1. Check SMS provider configuration
2. Verify API key and credentials
3. Check network connectivity
4. Review rate limiting settings

#### **Wrong Recipients**
1. Verify district selection
2. Check user role filtering
3. Update user data in DATAUAD.json
4. Clear browser cache

#### **SMS Format Issues**
1. Check message length limits
2. Verify emoji compatibility
3. Test with different providers
4. Review message template

### **Debug Mode**
Enable debug logging:
```typescript
// Add to console for detailed SMS logs
localStorage.setItem('debug_sms', 'true');
```

## Future Enhancements

### **Planned Features**
- **Email Notifications**: Parallel email alerts
- **Push Notifications**: Mobile app integration
- **Voice Calls**: Automated voice alerts
- **WhatsApp Integration**: WhatsApp Business API
- **Multi-language Support**: Local language SMS
- **Alert Templates**: Predefined alert templates
- **Escalation Rules**: Automatic escalation for critical alerts

### **API Integration**
- **Health Department APIs**: Real-time data integration
- **Weather APIs**: Automatic weather alerts
- **Hospital Systems**: Direct integration with hospital databases
- **Government Portals**: Integration with official health portals

## Support

For technical support or questions about the alert system:

- **Email**: tech-support@nehealthnet.gov.in
- **Phone**: +91-XXX-XXXX-XXXX
- **Documentation**: [Internal Wiki Link]
- **Emergency**: Contact system administrator immediately

---

**Last Updated**: January 2025  
**Version**: 1.0.0  
**Maintainer**: NE HealthNet Development Team
