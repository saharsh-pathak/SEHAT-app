param(
    [switch]$Phone,
    [switch]$Emulator
)

$ErrorActionPreference = "Stop"

$env:JAVA_HOME = "$env:LOCALAPPDATA\Java\jdk-17.0.20.1+1"
$env:ANDROID_HOME = "$env:LOCALAPPDATA\Android\Sdk"
$adb = "$env:ANDROID_HOME\platform-tools\adb.exe"

Write-Host "==> Checking ADB Devices..." -ForegroundColor Cyan
$devicesOutput = & $adb devices -l
Write-Host $devicesOutput

# Check Windows PnP for OnePlus / Android devices in MTP mode
$pnpMtp = Get-PnpDevice -PresentOnly -ErrorAction SilentlyContinue | Where-Object { $_.InstanceId -match "22D9" -and $_.InstanceId -match "2764" }
if ($pnpMtp) {
    Write-Host "`n[!] WARNING: Phone detected in MTP (file transfer) mode, NOT ADB mode!" -ForegroundColor Yellow
    Write-Host "    To fix on OnePlus/Android:" -ForegroundColor Yellow
    Write-Host "    1. Settings -> Additional Settings -> Developer Options -> Enable 'USB debugging'" -ForegroundColor Yellow
    Write-Host "    2. Unplug & replug USB cable" -ForegroundColor Yellow
    Write-Host "    3. On phone screen, tap 'Allow USB debugging' and check 'Always allow'`n" -ForegroundColor Yellow
}

$deviceList = @()
$devicesOutput -split "`r?`n" | ForEach-Object {
    if ($_ -match "^([^\s]+)\s+device\b") {
        $deviceList += $matches[1]
    }
}

if ($deviceList.Count -eq 0) {
    Write-Host "[ERROR] No device connected in ADB mode! Start emulator or enable USB debugging on phone." -ForegroundColor Red
    exit 1
}

$targetDevice = ""
if ($Phone) {
    $targetDevice = $deviceList | Where-Object { $_ -notmatch "emulator" } | Select-Object -First 1
    if (-not $targetDevice) {
        Write-Host "[ERROR] No physical phone found in ADB mode!" -ForegroundColor Red
        exit 1
    }
} elseif ($Emulator) {
    $targetDevice = $deviceList | Where-Object { $_ -match "emulator" } | Select-Object -First 1
    if (-not $targetDevice) {
        Write-Host "[ERROR] No emulator found running!" -ForegroundColor Red
        exit 1
    }
} else {
    # Default: prefer physical phone if available, else emulator
    $phoneDev = $deviceList | Where-Object { $_ -notmatch "emulator" } | Select-Object -First 1
    if ($phoneDev) {
        $targetDevice = $phoneDev
    } else {
        $targetDevice = $deviceList[0]
    }
}

Write-Host "==> Targeting Device: $targetDevice" -ForegroundColor Green

Write-Host "==> Building Debug APK (JDK 17)..." -ForegroundColor Cyan
& .\gradlew.bat assembleDebug
if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERROR] Build failed." -ForegroundColor Red
    exit 1
}

$apkPath = "app\build\outputs\apk\debug\app-debug.apk"
if (-not (Test-Path $apkPath)) {
    $apkPath = "$env:LOCALAPPDATA\AndroidBuilds\SEHAT\app\outputs\apk\debug\app-debug.apk"
}
if (-not (Test-Path $apkPath)) {
    Write-Host "[ERROR] APK not found at $apkPath" -ForegroundColor Red
    exit 1
}

Write-Host "==> Installing fresh APK to $targetDevice..." -ForegroundColor Cyan
& $adb -s $targetDevice install -r $apkPath

Write-Host "==> Killing any old running instance..." -ForegroundColor Cyan
& $adb -s $targetDevice shell am force-stop com.example.sehat

Write-Host "==> Launching SEHAT..." -ForegroundColor Green
& $adb -s $targetDevice shell am start -n com.example.sehat/.MainActivity

Write-Host "`n==> SUCCESS! Fresh build deployed and launched on $targetDevice." -ForegroundColor Green
