import axios from 'axios';
import fs from 'fs-extra';
import path from 'path';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// GADM source URLs for India
const GADM_URLS = {
  level1: 'https://geodata.ucdavis.edu/gadm/gadm4.1/json/gadm41_IND_1.json',
  level2: 'https://geodata.ucdavis.edu/gadm/gadm4.1/json/gadm41_IND_2.json',
  level3: 'https://geodata.ucdavis.edu/gadm/gadm4.1/json/gadm41_IND_3.json'
};

// North-East India states (admin level 1 names)
const NE_STATES = [
  'ArunachalPradesh',
  'Assam', 
  'Manipur',
  'Meghalaya',
  'Mizoram',
  'Nagaland',
  'Tripura',
  'Sikkim'
];

// Output paths
const OUTPUT_PATHS = {
  full: {
    level1: path.join(__dirname, '../public/data/geojson/gadm_IND_level1.geojson'),
    level2: path.join(__dirname, '../public/data/geojson/gadm_IND_level2.geojson'),
    level3: path.join(__dirname, '../public/data/geojson/gadm_IND_level3.geojson')
  },
  ne: {
    level1: path.join(__dirname, '../public/data/geojson/gadm_NE_level1.geojson'),
    level2: path.join(__dirname, '../public/data/geojson/gadm_NE_level2.geojson'),
    level3: path.join(__dirname, '../public/data/geojson/gadm_NE_level3.geojson')
  }
};

async function downloadFile(url, outputPath) {
  try {
    console.log(`Downloading ${url}...`);
    const response = await axios.get(url, {
      responseType: 'json',
      timeout: 300000 // 5 minutes timeout
    });
    
    console.log(`Downloaded ${url}`);
    return response.data;
  } catch (error) {
    console.error(`Error downloading ${url}:`, error.message);
    throw error;
  }
}

function filterToNorthEast(geojsonData, level) {
  console.log(`Filtering ${level} data to North-East India...`);
  
  const filteredFeatures = geojsonData.features.filter(feature => {
    // For level 1, check if the state name is in NE states
    if (level === 'level1') {
      const stateName = feature.properties.NAME_1;
      return NE_STATES.includes(stateName);
    }
    
    // For level 2, check if the parent state (level 1) is in NE states
    if (level === 'level2') {
      const stateName = feature.properties.NAME_1;
      return NE_STATES.includes(stateName);
    }
    
    // For level 3, check if the grandparent state (level 1) is in NE states
    if (level === 'level3') {
      const stateName = feature.properties.NAME_1;
      return NE_STATES.includes(stateName);
    }
    
    return false;
  });
  
  console.log(`Filtered ${level}: ${filteredFeatures.length} features out of ${geojsonData.features.length}`);
  
  return {
    type: 'FeatureCollection',
    features: filteredFeatures
  };
}

async function saveFile(data, filePath) {
  try {
    await fs.ensureDir(path.dirname(filePath));
    await fs.writeJson(filePath, data, { spaces: 2 });
    
    const stats = await fs.stat(filePath);
    const fileSizeInMB = (stats.size / (1024 * 1024)).toFixed(2);
    
    console.log(`Saved: ${filePath} (${fileSizeInMB} MB)`);
    return fileSizeInMB;
  } catch (error) {
    console.error(`Error saving ${filePath}:`, error.message);
    throw error;
  }
}

async function main() {
  try {
    console.log('Starting GADM data download and processing...\n');
    
    const results = {
      sourceUrls: GADM_URLS,
      localPaths: OUTPUT_PATHS,
      fileSizes: {},
      commands: []
    };
    
    // Download and process level 1 data
    console.log('=== Processing Level 1 Data ===');
    const level1Data = await downloadFile(GADM_URLS.level1, OUTPUT_PATHS.full.level1);
    
    // Save full level 1 data
    const level1FullSize = await saveFile(level1Data, OUTPUT_PATHS.full.level1);
    results.fileSizes.gadm_IND_level1 = `${level1FullSize} MB`;
    
    // Filter and save NE level 1 data
    const level1NEData = filterToNorthEast(level1Data, 'level1');
    const level1NESize = await saveFile(level1NEData, OUTPUT_PATHS.ne.level1);
    results.fileSizes.gadm_NE_level1 = `${level1NESize} MB`;
    
    // Download and process level 2 data
    console.log('\n=== Processing Level 2 Data ===');
    const level2Data = await downloadFile(GADM_URLS.level2, OUTPUT_PATHS.full.level2);
    
    // Save full level 2 data
    const level2FullSize = await saveFile(level2Data, OUTPUT_PATHS.full.level2);
    results.fileSizes.gadm_IND_level2 = `${level2FullSize} MB`;
    
    // Filter and save NE level 2 data
    const level2NEData = filterToNorthEast(level2Data, 'level2');
    const level2NESize = await saveFile(level2NEData, OUTPUT_PATHS.ne.level2);
    results.fileSizes.gadm_NE_level2 = `${level2NESize} MB`;
    
    // Download and process level 3 data
    console.log('\n=== Processing Level 3 Data ===');
    const level3Data = await downloadFile(GADM_URLS.level3, OUTPUT_PATHS.full.level3);
    
    // Save full level 3 data
    const level3FullSize = await saveFile(level3Data, OUTPUT_PATHS.full.level3);
    results.fileSizes.gadm_IND_level3 = `${level3FullSize} MB`;
    
    // Filter and save NE level 3 data
    const level3NEData = filterToNorthEast(level3Data, 'level3');
    const level3NESize = await saveFile(level3NEData, OUTPUT_PATHS.ne.level3);
    results.fileSizes.gadm_NE_level3 = `${level3NESize} MB`;
    
    // Generate commands used
    results.commands = [
      'npm run download-gadm',
      'node scripts/download-gadm.js'
    ];
    
    console.log('\n=== Summary ===');
    console.log('✅ All files processed successfully!');
    console.log('\n📁 Files created:');
    Object.entries(results.localPaths.full).forEach(([level, path]) => {
      console.log(`   ${path} (${results.fileSizes[`gadm_IND_${level}`]})`);
    });
    Object.entries(results.localPaths.ne).forEach(([level, path]) => {
      console.log(`   ${path} (${results.fileSizes[`gadm_NE_${level}`]})`);
    });
    
    console.log('\n🌐 Source URLs:');
    Object.entries(results.sourceUrls).forEach(([level, url]) => {
      console.log(`   ${level}: ${url}`);
    });
    
    console.log('\n⚡ Commands used:');
    results.commands.forEach(cmd => console.log(`   ${cmd}`));
    
    // Save results summary
    const summaryPath = path.join(__dirname, '../public/data/geojson/download-summary.json');
    await saveFile(results, summaryPath);
    console.log(`\n📋 Summary saved to: ${summaryPath}`);
    
  } catch (error) {
    console.error('❌ Error in main process:', error.message);
    process.exit(1);
  }
}

// Run the script
main();
