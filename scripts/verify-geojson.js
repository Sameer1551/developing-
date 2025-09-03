import fs from 'fs-extra';
import path from 'path';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const GEODATA_DIR = path.join(__dirname, '../public/data/geojson');

async function verifyGeoJSON() {
  try {
    console.log('🔍 Verifying GeoJSON files...\n');
    
    const files = [
      'gadm_IND_level2.geojson',
      'gadm_IND_level3.geojson', 
      'gadm_NE_level2.geojson',
      'gadm_NE_level3.geojson'
    ];
    
    for (const filename of files) {
      const filePath = path.join(GEODATA_DIR, filename);
      
      if (await fs.pathExists(filePath)) {
        console.log(`✅ ${filename}:`);
        
        // Read and parse the file
        const content = await fs.readJson(filePath);
        
        // Basic GeoJSON validation
        if (content.type === 'FeatureCollection' && Array.isArray(content.features)) {
          console.log(`   Type: ${content.type}`);
          console.log(`   Features: ${content.features.length}`);
          
          // Check coordinate system (should be EPSG:4326)
          if (content.features.length > 0) {
            const firstFeature = content.features[0];
            if (firstFeature.geometry && firstFeature.geometry.coordinates) {
              const coords = firstFeature.geometry.coordinates[0][0]; // First coordinate
              if (coords[0] >= -180 && coords[0] <= 180 && coords[1] >= -90 && coords[1] <= 90) {
                console.log(`   Coordinate System: EPSG:4326 (WGS84) ✅`);
              } else {
                console.log(`   ⚠️  Coordinate System: Unknown (coordinates: ${coords[0]}, ${coords[1]})`);
              }
            }
          }
          
          // Check properties structure
          if (content.features.length > 0) {
            const firstFeature = content.features[0];
            const properties = firstFeature.properties;
            console.log(`   Properties: ${Object.keys(properties).join(', ')}`);
            
            // Check for North-East states in filtered files
            if (filename.includes('NE_')) {
              const neStates = new Set();
              content.features.forEach(feature => {
                if (feature.properties.NAME_1) {
                  neStates.add(feature.properties.NAME_1);
                }
              });
              console.log(`   NE States found: ${Array.from(neStates).join(', ')}`);
            }
          }
          
          // File size
          const stats = await fs.stat(filePath);
          const sizeMB = (stats.size / (1024 * 1024)).toFixed(2);
          console.log(`   File Size: ${sizeMB} MB`);
          
        } else {
          console.log(`   ❌ Invalid GeoJSON structure`);
        }
        
        console.log('');
      } else {
        console.log(`❌ ${filename}: File not found`);
      }
    }
    
    console.log('🎯 Verification complete!');
    
  } catch (error) {
    console.error('❌ Error during verification:', error.message);
  }
}

// Run verification
verifyGeoJSON();
