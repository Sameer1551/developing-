import { execSync } from 'child_process';
import fs from 'fs-extra';
import path from 'path';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// GADM source URLs for India
const GADM_URLS = {
  level2: 'https://geodata.ucdavis.edu/gadm/gadm4.1/json/gadm41_IND_2.json',
  level3: 'https://geodata.ucdavis.edu/gadm/gadm4.1/json/gadm41_IND_3.json'
};

// North-East India states (admin level 1 names)
const NE_STATES = [
  'Arunachal Pradesh',
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
    level2: path.join(__dirname, '../public/data/geojson/gadm_IND_level2.geojson'),
    level3: path.join(__dirname, '../public/data/geojson/gadm_IND_level3.geojson')
  },
  ne: {
    level2: path.join(__dirname, '../public/data/geojson/gadm_NE_level2.geojson'),
    level3: path.join(__dirname, '../public/data/geojson/gadm_NE_level3.geojson')
  }
};

// Temporary files for processing
const TEMP_DIR = path.join(__dirname, '../temp');
const TEMP_FILES = {
  level2: path.join(TEMP_DIR, 'gadm41_IND_2.json'),
  level3: path.join(TEMP_DIR, 'gadm41_IND_3.json')
};

async function downloadWithCurl(url, outputPath) {
  try {
    console.log(`Downloading ${url}...`);
    
    // Use curl for downloading (more reliable for large files)
    const curlCommand = `curl -L -o "${outputPath}" "${url}"`;
    execSync(curlCommand, { stdio: 'inherit' });
    
    console.log(`Downloaded ${url}`);
  } catch (error) {
    console.error(`Error downloading ${url}:`, error.message);
    throw error;
  }
}

function createMapshaperFilter(level) {
  const stateNames = NE_STATES.map(name => `"${name}"`).join(',');
  
  if (level === 'level2') {
    return `-filter "NAME_1 in [${stateNames}]"`;
  } else if (level === 'level3') {
    return `-filter "NAME_1 in [${stateNames}]"`;
  }
  return '';
}

async function processWithMapshaper(inputFile, outputFile, filterExpression) {
  try {
    console.log(`Processing ${inputFile} with mapshaper...`);
    
    // Mapshaper command to filter and optimize
    const mapshaperCommand = `mapshaper "${inputFile}" ${filterExpression} -o format=geojson "${outputFile}"`;
    
    console.log(`Running: ${mapshaperCommand}`);
    execSync(mapshaperCommand, { stdio: 'inherit' });
    
    console.log(`Processed: ${outputFile}`);
  } catch (error) {
    console.error(`Error processing with mapshaper:`, error.message);
    throw error;
  }
}

async function getFileSize(filePath) {
  try {
    const stats = await fs.stat(filePath);
    return (stats.size / (1024 * 1024)).toFixed(2);
  } catch (error) {
    return '0.00';
  }
}

async function main() {
  try {
    console.log('Starting GADM data download and processing with mapshaper...\n');
    
    // Create temp directory
    await fs.ensureDir(TEMP_DIR);
    
    const results = {
      sourceUrls: GADM_URLS,
      localPaths: OUTPUT_PATHS,
      fileSizes: {},
      commands: [],
      mapshaperCommands: []
    };
    
    // Download and process level 2 data
    console.log('=== Processing Level 2 Data ===');
    await downloadWithCurl(GADM_URLS.level2, TEMP_FILES.level2);
    
    // Copy full file
    await fs.copy(TEMP_FILES.level2, OUTPUT_PATHS.full.level2);
    const level2FullSize = await getFileSize(OUTPUT_PATHS.full.level2);
    results.fileSizes.gadm_IND_level2 = `${level2FullSize} MB`;
    
    // Filter with mapshaper
    const level2Filter = createMapshaperFilter('level2');
    await processWithMapshaper(TEMP_FILES.level2, OUTPUT_PATHS.ne.level2, level2Filter);
    const level2NESize = await getFileSize(OUTPUT_PATHS.ne.level2);
    results.fileSizes.gadm_NE_level2 = `${level2NESize} MB`;
    
    // Download and process level 3 data
    console.log('\n=== Processing Level 3 Data ===');
    await downloadWithCurl(GADM_URLS.level3, TEMP_FILES.level3);
    
    // Copy full file
    await fs.copy(TEMP_FILES.level3, OUTPUT_PATHS.full.level3);
    const level3FullSize = await getFileSize(OUTPUT_PATHS.full.level3);
    results.fileSizes.gadm_IND_level3 = `${level3FullSize} MB`;
    
    // Filter with mapshaper
    const level3Filter = createMapshaperFilter('level3');
    await processWithMapshaper(TEMP_FILES.level3, OUTPUT_PATHS.ne.level3, level3Filter);
    const level3NESize = await getFileSize(OUTPUT_PATHS.ne.level3);
    results.fileSizes.gadm_NE_level3 = `${level3NESize} MB`;
    
    // Generate commands used
    results.commands = [
      'npm run download-gadm',
      'node scripts/download-gadm-mapshaper.js'
    ];
    
    // Generate mapshaper commands
    results.mapshaperCommands = [
      `mapshaper "${TEMP_FILES.level2}" ${level2Filter} -o format=geojson "${OUTPUT_PATHS.ne.level2}"`,
      `mapshaper "${TEMP_FILES.level3}" ${level3Filter} -o format=geojson "${OUTPUT_PATHS.ne.level3}"`
    ];
    
    console.log('\n=== Summary ===');
    console.log('✅ All files processed successfully with mapshaper!');
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
    
    console.log('\n🗺️ Mapshaper commands:');
    results.mapshaperCommands.forEach(cmd => console.log(`   ${cmd}`);
    
    // Clean up temp files
    await fs.remove(TEMP_DIR);
    console.log('\n🧹 Temporary files cleaned up');
    
    // Save results summary
    const summaryPath = path.join(__dirname, '../public/data/geojson/download-summary-mapshaper.json');
    await saveFile(results, summaryPath);
    console.log(`\n📋 Summary saved to: ${summaryPath}`);
    
  } catch (error) {
    console.error('❌ Error in main process:', error.message);
    process.exit(1);
  }
}

async function saveFile(data, filePath) {
  try {
    await fs.ensureDir(path.dirname(filePath));
    await fs.writeJson(filePath, data, { spaces: 2 });
    console.log(`Summary saved: ${filePath}`);
  } catch (error) {
    console.error(`Error saving summary:`, error.message);
  }
}

// Run the script
main();
