@echo off
echo ========================================
echo GADM Data Download for North-East India
echo ========================================
echo.

echo Installing dependencies...
call npm install

echo.
echo Starting download process...
echo.

call npm run download-gadm

echo.
echo ========================================
echo Download process completed!
echo ========================================
echo.
echo Files are saved in: public\data\geojson\
echo.
pause
