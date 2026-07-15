# Ruta al backend
$BACKEND_DIR = "../algo-que-pedir-backend-2025-grupo1"
$REPO_URL = "https://github.com/algo3-unsam/algo-que-pedir-backend-2025-grupo1.git"
$PORT = 9000

# Si no existe el repo → clonarlo
if (!(Test-Path "$BACKEND_DIR/.git")) {
    Write-Host "📥 Clonando repositorio en $BACKEND_DIR ..."
    git clone $REPO_URL $BACKEND_DIR
} else {
    Write-Host "📡 Actualizando repo en $BACKEND_DIR ..."
    Set-Location $BACKEND_DIR
    git switch dev
    git pull
    Set-Location ".."
}

# Ejecutar el backend
Set-Location $BACKEND_DIR
git switch dev

Write-Host "🚀 Ejecutando backend en puerto $PORT ..."
./gradlew.bat bootRun -Pserver.port=$PORT --no-daemon
