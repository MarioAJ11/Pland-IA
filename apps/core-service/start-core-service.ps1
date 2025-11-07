# Script para iniciar Core Service con variables de entorno
Write-Host "🚀 Iniciando Core Service con variables de entorno..." -ForegroundColor Cyan
Write-Host ""

# Cargar variables del archivo .env
$envFile = Join-Path $PSScriptRoot ".env"
if (Test-Path $envFile) {
    Write-Host "📄 Cargando variables desde .env..." -ForegroundColor Yellow
    Get-Content $envFile | ForEach-Object {
        if ($_ -match '^\s*([^#][^=]+)=(.+)$') {
            $name = $matches[1].Trim()
            $value = $matches[2].Trim()
            [Environment]::SetEnvironmentVariable($name, $value, "Process")
            Write-Host "   $name = $value" -ForegroundColor Gray
        }
    }
    Write-Host ""
}

# Iniciar Core Service
Write-Host "🔥 Iniciando Spring Boot..." -ForegroundColor Green
Write-Host ""
.\mvnw.cmd spring-boot:run
