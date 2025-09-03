# PowerShell script to create language files for all languages from LanguageContext.tsx

# Define all language codes from LanguageContext.tsx
$languages = @(
    'karbi', 'mishing', 'mni', 'khasi', 'garo', 'jaintia', 'mizo', 
    'nagamese', 'ao', 'angami', 'sema', 'lotha', 'nyishi', 'apatani', 
    'adi', 'mishmi', 'monpa', 'tripuri', 'kokborok', 'bn', 'ne'
)

# Define the JSON file types
$jsonFiles = @(
    'alerts.json', 'awareness.json', 'common.json', 'dashboard.json', 
    'footer.json', 'hero.json', 'login.json', 'navigation.json', 
    'report.json', 'waterQuality.json'
)

# Function to create JSON files for a language
function Create-LanguageFiles {
    param($langCode)
    
    $langDir = ".\$langCode"
    
    # Create directory if it doesn't exist
    if (!(Test-Path $langDir)) {
        New-Item -ItemType Directory -Path $langDir -Force
        Write-Host "Created directory: $langDir"
    }
    
    # Copy English files as templates and modify them
    foreach ($jsonFile in $jsonFiles) {
        $sourceFile = ".\en\$jsonFile"
        $targetFile = "$langDir\$jsonFile"
        
        if (Test-Path $sourceFile) {
            Copy-Item $sourceFile $targetFile -Force
            Write-Host "Created: $targetFile"
        }
    }
}

# Create files for all languages
foreach ($lang in $languages) {
    Write-Host "Creating files for language: $lang"
    Create-LanguageFiles $lang
}

Write-Host "All language files created successfully!"
Write-Host "Now you need to manually update the translations in each file based on LanguageContext.tsx"
