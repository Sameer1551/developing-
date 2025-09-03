# GADM Data Download for North-East India

This project downloads and processes GADM (Global Administrative Areas) data for India to create GeoJSON files for North-East India states.

## What is GADM?

GADM is a spatial database of the location of the world's administrative areas (or administrative boundaries) for use in GIS and similar software. The data is available at different administrative levels:
- **Level 1**: States/Provinces
- **Level 2**: Districts/Counties  
- **Level 3**: Sub-districts/Sub-counties

## North-East India States

The following 8 states are included in the North-East India region:
1. Arunachal Pradesh
2. Assam
3. Manipur
4. Meghalaya
5. Mizoram
6. Nagaland
7. Tripura
8. Sikkim

## Files Generated

### Full India Data
- `/public/data/geojson/gadm_IND_level2.geojson` - All districts in India
- `/public/data/geojson/gadm_IND_level3.geojson` - All sub-districts in India

### North-East India Data (Filtered)
- `/public/data/geojson/gadm_NE_level2.geojson` - Districts in North-East India only
- `/public/data/geojson/gadm_NE_level3.geojson` - Sub-districts in North-East India only

## Download Methods

### Method 1: Node.js Script (Recommended for most users)

**Prerequisites:**
- Node.js 16+ installed
- npm or yarn package manager

**Steps:**
1. Install dependencies:
   ```bash
   npm install
   ```

2. Run the download script:
   ```bash
   npm run download-gadm
   ```
   
   Or directly:
   ```bash
   node scripts/download-gadm.js
   ```

**What it does:**
- Downloads GADM data from official sources
- Converts to GeoJSON format (EPSG:4326)
- Filters data to North-East India states
- Saves both full and filtered datasets
- Generates a summary report

### Method 2: Mapshaper CLI (Advanced users)

**Prerequisites:**
- Node.js 16+ installed
- Mapshaper CLI installed globally: `npm install -g mapshaper`
- curl available in system PATH

**Steps:**
1. Install dependencies:
   ```bash
   npm install
   ```

2. Run the mapshaper script:
   ```bash
   node scripts/download-gadm-mapshaper.js
   ```

**What it does:**
- Downloads data using curl (more reliable for large files)
- Uses mapshaper for efficient GeoJSON processing
- Better performance for large datasets
- Generates exact mapshaper commands used

## GADM Source URLs

The data is downloaded from the official GADM repository:
- **Level 2**: https://geodata.ucdavis.edu/gadm/gadm4.1/json/gadm41_IND_2.json
- **Level 3**: https://geodata.ucdavis.edu/gadm/gadm4.1/json/gadm41_IND_3.json

## Data Structure

Each GeoJSON feature contains the following properties:
- `NAME_0`: Country name (India)
- `NAME_1`: State/Province name
- `NAME_2`: District/County name (level 2)
- `NAME_3`: Sub-district/Sub-county name (level 3)
- `GID_0`, `GID_1`, `GID_2`, `GID_3`: Unique identifiers
- `VARNAME_*`: Alternative names
- `ENGTYPE_*`: English type descriptions
- `CC_1`, `CC_2`, `CC_3`: Country codes

## Coordinate System

All output files use **EPSG:4326** (WGS84) coordinate system, which is the standard for web mapping applications.

## File Sizes

Typical file sizes (may vary):
- `gadm_IND_level2.geojson`: ~15-25 MB
- `gadm_IND_level3.geojson`: ~50-80 MB
- `gadm_NE_level2.geojson`: ~2-4 MB
- `gadm_NE_level3.geojson`: ~8-12 MB

## Usage Examples

### In Leaflet.js
```javascript
fetch('/data/geojson/gadm_NE_level2.geojson')
  .then(response => response.json())
  .then(data => {
    L.geoJSON(data).addTo(map);
  });
```

### In React with react-leaflet
```jsx
import { GeoJSON } from 'react-leaflet';

function MapComponent() {
  const [neData, setNeData] = useState(null);
  
  useEffect(() => {
    fetch('/data/geojson/gadm_NE_level2.geojson')
      .then(response => response.json())
      .then(setNeData);
  }, []);
  
  return neData ? <GeoJSON data={neData} /> : null;
}
```

## Troubleshooting

### Common Issues

1. **Download fails**: Check internet connection and try again
2. **Memory errors**: Use the mapshaper method for large files
3. **Permission errors**: Ensure write permissions to `/public/data/geojson/`
4. **Mapshaper not found**: Install globally with `npm install -g mapshaper`

### Performance Tips

- For development: Use the filtered NE files
- For production: Consider hosting files on a CDN
- For large applications: Implement lazy loading of administrative levels

## Data Updates

GADM data is updated periodically. To get the latest data:
1. Check the GADM website for new releases
2. Update the URLs in the scripts if needed
3. Re-run the download scripts

## License

GADM data is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. See the GADM website for full license terms.

## Support

For issues with the download scripts, check:
1. Node.js version compatibility
2. Network connectivity
3. File system permissions
4. Available disk space

For GADM data questions, refer to the official documentation at https://gadm.org/
