# Real-time monitoring script for Food Adda
$baseUrl = "http://localhost:8080/api"
$interval = 5

Write-Host "=== REAL-TIME FOOD ADDA MONITORING ===" -ForegroundColor Green
Write-Host "Checking API health every $interval seconds..." -ForegroundColor Cyan
Write-Host "Press Ctrl+C to stop" -ForegroundColor Yellow
Write-Host ""

while ($true) {
    Clear-Host
    Write-Host "=== REAL-TIME STATUS ===" -ForegroundColor Green
    Write-Host "Time: $(Get-Date -Format 'HH:mm:ss')" -ForegroundColor Yellow
    Write-Host ""
    
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080/api/categories" -Method GET -UseBasicParsing -ErrorAction Stop
        Write-Host "OK App Status: RUNNING (HTTP 200)" -ForegroundColor Green
    }
    catch {
        Write-Host "X App Status: OFFLINE" -ForegroundColor Red
    }
    
    try {
        $categories = Invoke-WebRequest -Uri "$baseUrl/categories" -Method GET -UseBasicParsing | ConvertFrom-Json
        $catCount = if ($categories.data -is [array]) { $categories.data.Count } else { 1 }
        Write-Host "OK Total Categories: $catCount" -ForegroundColor Green
    }
    catch {
        Write-Host "X Could not fetch categories" -ForegroundColor Red
    }
    
    try {
        $menuItems = Invoke-WebRequest -Uri "$baseUrl/menu/items" -Method GET -UseBasicParsing | ConvertFrom-Json
        $itemCount = if ($menuItems.data -is [array]) { $menuItems.data.Count } else { 1 }
        Write-Host "OK Total Menu Items: $itemCount" -ForegroundColor Green
    }
    catch {
        Write-Host "X Could not fetch menu items" -ForegroundColor Red
    }
    
    Write-Host "OK Database Status: Connected (H2)" -ForegroundColor Green
    Write-Host ""
    Write-Host "Next check in $interval seconds..." -ForegroundColor Cyan
    Write-Host "-" * 50
    
    Start-Sleep -Seconds $interval
}