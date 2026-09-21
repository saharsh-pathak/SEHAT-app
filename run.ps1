$env:JAVA_HOME = "$env:LOCALAPPDATA\Java\jdk-17.0.20.1+1"
$env:ANDROID_HOME = "$env:LOCALAPPDATA\Android\Sdk"
$ADB = "$env:ANDROID_HOME\platform-tools\adb.exe"
$EMU = "$env:ANDROID_HOME\emulator\emulator.exe"

Write-Host "=== Checking device ===" -ForegroundColor Cyan
$devices = & $ADB devices
if ($devices -notmatch 'device\s*$') {
    Write-Host "Starting Pixel_7 emulator..." -ForegroundColor Yellow
    Start-Process $EMU -ArgumentList "-avd Pixel_7"
    Write-Host "Waiting for emulator boot..." -ForegroundColor Yellow
    & $ADB wait-for-device
    while ((& $ADB shell getprop sys.boot_completed).Trim() -ne '1') { Start-Sleep -Seconds 2 }
    Write-Host "Emulator ready!" -ForegroundColor Green
}

Write-Host "=== Building APK ===" -ForegroundColor Cyan
Remove-Item -Recurse -Force "app\build" -ErrorAction SilentlyContinue
& .\gradlew.bat assembleDebug
if ($LASTEXITCODE -ne 0) { Write-Host "BUILD FAILED" -ForegroundColor Red; exit 1 }

Write-Host "=== Uninstalling old package ===" -ForegroundColor Cyan
& $ADB uninstall com.example.sehat

Write-Host "=== Installing fresh APK to device ===" -ForegroundColor Cyan
& $ADB install -r "app\build\outputs\apk\debug\app-debug.apk"
if ($LASTEXITCODE -ne 0) { Write-Host "INSTALL FAILED" -ForegroundColor Red; exit 1 }

Write-Host "=== Launching app ===" -ForegroundColor Cyan
& $ADB shell am force-stop com.example.sehat
& $ADB shell am start -n com.example.sehat/.MainActivity

Write-Host "=== Done ===" -ForegroundColor Green
