# Northeast India Interactive Map

## Overview
This feature provides a comprehensive, interactive map of Northeast India showing all districts and sub-districts with detailed information.

## Features

### 🗺️ **Complete Coverage**
- **8 States**: Assam, Manipur, Meghalaya, Nagaland, Tripura, Arunachal Pradesh, Mizoram, Sikkim
- **District Level**: All district boundaries with names and details (including all 18 Arunachal Pradesh districts)
- **Sub-District Level**: Optional detailed sub-district boundaries (tehsils, blocks, etc.)

### 🖱️ **Interactive Features**
- **Clickable Areas**: Click on any district or sub-district to view detailed information
- **Hover Effects**: Areas highlight when you hover over them
- **Layer Toggle**: Switch between district-only and district+sub-district views
- **Zoom & Pan**: Full map navigation capabilities

### 📱 **User Interface**
- **Left Panel**: Shows selected area details and controls
- **Right Panel**: Interactive map with OpenStreetMap tiles
- **Responsive Design**: Works on desktop, tablet, and mobile devices

## How to Use

### 1. **Access the Map**
- Navigate to `/northeast-map` in your application
- Or click "Northeast Map" in the main navigation

### 2. **Explore Districts**
- **Default View**: Shows all district boundaries in blue
- **Click**: Any district to see its details in the left panel
- **Hover**: Over districts to highlight them

### 3. **View Sub-Districts**
- **Toggle**: Check "Show Sub-districts" checkbox
- **Detail Level**: Sub-districts appear in red with thinner borders
- **Click**: Any sub-district to see detailed information

### 4. **Information Displayed**
For **Districts**:
- District Name
- State Name
- Administrative Type
- GADM ID
- HASC Code

For **Sub-Districts**:
- Sub-District Name
- Parent District Name
- State Name
- Administrative Type
- GADM ID

## Technical Implementation

### **Data Sources**
- **Level 2**: `gadm_NE_level2.geojson` - District boundaries
- **Level 3**: `gadm_NE_level3.geojson` - Sub-district boundaries
- **Base Map**: OpenStreetMap tiles

### **Technologies Used**
- **React**: Component framework
- **Leaflet**: Interactive mapping library
- **React-Leaflet**: React bindings for Leaflet
- **TypeScript**: Type safety and better development experience
- **Tailwind CSS**: Styling and responsive design

### **File Structure**
```
src/
├── components/
│   └── NortheastMap.tsx          # Main map component
├── pages/
│   └── NortheastMapPage.tsx      # Map page wrapper
└── App.tsx                       # Routing configuration
```

## Data Structure

### **District Properties (Level 2)**
```json
{
  "GID_2": "IND.4.1_1",
  "GID_1": "IND.4_1",
  "GID_0": "IND",
  "NAME_1": "Assam",
  "NAME_2": "Baksa",
  "TYPE_2": "District",
  "HASC_2": "IN.AS.BK"
}
```

### **Sub-District Properties (Level 3)**
```json
{
  "GID_3": "IND.4.1.1_1",
  "GID_2": "IND.4.1_1",
  "GID_1": "IND.4_1",
  "GID_0": "IND",
  "NAME_1": "Assam",
  "NAME_2": "Baksa",
  "NAME_3": "Berpeta",
  "TYPE_3": "Sub-district"
}
```

## Customization Options

### **Styling**
- **District Colors**: Blue theme (`#3388ff`)
- **Sub-District Colors**: Red theme (`#e74c3c`)
- **Hover Effects**: Orange highlight (`#ff6b6b`)
- **Panel Design**: Clean white cards with shadows

### **Map Bounds**
- **Northeast India**: Automatically centered and bounded
- **Coordinates**: [20.5, 72.5] to [29.5, 97.5]
- **Zoom Levels**: Automatic fitting to region

### **Performance**
- **Lazy Loading**: Data loaded on component mount
- **Conditional Rendering**: Sub-districts only when needed
- **Efficient Updates**: Minimal re-renders on interactions

## Integration with Existing App

### **Navigation**
- Added to main navigation menu
- Available to all user types (authenticated and non-authenticated)
- Consistent with existing design patterns

### **Routing**
- **Path**: `/northeast-map`
- **Component**: `NortheastMapPage`
- **Layout**: Consistent with other pages

### **Accessibility**
- **Keyboard Navigation**: Full keyboard support
- **Screen Readers**: Proper ARIA labels and descriptions
- **Color Contrast**: Meets accessibility standards

## Future Enhancements

### **Potential Features**
- **Data Overlay**: Water quality data, health statistics
- **Search Functionality**: Find districts/sub-districts by name
- **Export Options**: Download map data or screenshots
- **Custom Styling**: User-configurable color schemes
- **Offline Support**: Cached map data for offline use

### **Performance Improvements**
- **Data Compression**: Optimize GeoJSON file sizes
- **Lazy Loading**: Load data on demand based on zoom level
- **Caching**: Implement browser storage for faster loading

## Troubleshooting

### **Common Issues**
1. **Map Not Loading**: Check if GeoJSON files are accessible
2. **Slow Performance**: Ensure files are properly optimized
3. **Styling Issues**: Verify Tailwind CSS is loaded
4. **Mobile Issues**: Test responsive design on various devices

### **Browser Support**
- **Modern Browsers**: Chrome, Firefox, Safari, Edge (latest versions)
- **Mobile**: iOS Safari, Chrome Mobile, Samsung Internet
- **Fallbacks**: Graceful degradation for older browsers

## Contributing

To enhance this map feature:
1. **Data Updates**: Replace GeoJSON files with newer versions
2. **Styling**: Modify Tailwind classes for different themes
3. **Functionality**: Add new interactive features
4. **Performance**: Optimize data loading and rendering

---

**Note**: This map uses GADM (Global Administrative Areas) data for accurate administrative boundaries. For production use, ensure you have the latest data and proper licensing.
