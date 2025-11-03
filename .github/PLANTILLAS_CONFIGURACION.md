# 📋 Plantillas de Archivos de Configuración

Este documento contiene las plantillas de archivos de configuración que necesitarás crear en tu proyecto.

---

## 1. Frontend: `.env.example`

**Ubicación:** `frontend/.env.example`

```env
# ==========================================
# FRONTEND ENVIRONMENT VARIABLES
# ==========================================

# API Backend URL
VITE_API_URL=http://localhost:3000

# App Configuration
VITE_APP_NAME=Planificador Inteligente IA
VITE_APP_VERSION=1.0.0

# Environment
VITE_NODE_ENV=development

# OpenAI (si llamas desde frontend - NO RECOMENDADO)
# VITE_OPENAI_API_KEY=sk-...

# Analytics (opcional)
# VITE_GOOGLE_ANALYTICS_ID=G-XXXXXXXXXX

# Sentry (opcional)
# VITE_SENTRY_DSN=https://...
```

**Notas:**
- Variables deben empezar con `VITE_` para estar disponibles en el cliente
- **NUNCA** expongas API keys sensibles en el frontend
- Crea una copia como `.env` y completa con valores reales
- `.env` debe estar en `.gitignore`

---

## 2. Backend: `.env.example`

**Ubicación:** `backend/.env.example`

```env
# ==========================================
# BACKEND ENVIRONMENT VARIABLES
# ==========================================

# ==========================================
# SERVER CONFIGURATION
# ==========================================
NODE_ENV=development
PORT=3000
HOST=localhost

# ==========================================
# DATABASE
# ==========================================

# PostgreSQL (ejemplo)
DATABASE_URL=postgresql://usuario:password@localhost:5432/planificador_db

# MongoDB (alternativa)
# DATABASE_URL=mongodb://localhost:27017/planificador_db

# Conexión pool
DB_POOL_MIN=2
DB_POOL_MAX=10

# ==========================================
# JWT AUTHENTICATION
# ==========================================

# Secret para tokens de acceso (genera uno seguro)
JWT_SECRET=tu_super_secreto_jwt_aqui_cambiar_en_produccion

# Tiempo de expiración del access token
JWT_EXPIRES_IN=15m

# Secret para refresh tokens (diferente al JWT_SECRET)
JWT_REFRESH_SECRET=tu_super_secreto_refresh_aqui_cambiar_en_produccion

# Tiempo de expiración del refresh token
JWT_REFRESH_EXPIRES_IN=7d

# ==========================================
# CORS
# ==========================================

# Orígenes permitidos (separados por coma)
ALLOWED_ORIGINS=http://localhost:5173,http://localhost:3000

# ==========================================
# INTELIGENCIA ARTIFICIAL
# ==========================================

# OpenAI
OPENAI_API_KEY=sk-...
OPENAI_MODEL=gpt-4-turbo-preview
OPENAI_MAX_TOKENS=1000
OPENAI_TEMPERATURE=0.7

# Claude (alternativa)
# ANTHROPIC_API_KEY=sk-ant-...
# ANTHROPIC_MODEL=claude-3-opus-20240229

# ==========================================
# RATE LIMITING
# ==========================================

# Límite de requests por IP
RATE_LIMIT_WINDOW_MS=900000
RATE_LIMIT_MAX_REQUESTS=100

# Límite específico para IA
AI_RATE_LIMIT_WINDOW_MS=3600000
AI_RATE_LIMIT_MAX_REQUESTS=50

# ==========================================
# EMAIL (opcional)
# ==========================================

# SMTP
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_SECURE=false
SMTP_USER=tu_email@gmail.com
SMTP_PASS=tu_app_password

# SendGrid (alternativa)
# SENDGRID_API_KEY=SG...
# SENDGRID_FROM_EMAIL=noreply@tudominio.com

# ==========================================
# STORAGE (opcional)
# ==========================================

# AWS S3
# AWS_REGION=us-east-1
# AWS_ACCESS_KEY_ID=AKIA...
# AWS_SECRET_ACCESS_KEY=...
# AWS_S3_BUCKET=mi-bucket

# Cloudinary (alternativa)
# CLOUDINARY_CLOUD_NAME=...
# CLOUDINARY_API_KEY=...
# CLOUDINARY_API_SECRET=...

# ==========================================
# LOGGING & MONITORING
# ==========================================

# Log level (error, warn, info, debug)
LOG_LEVEL=info

# Sentry (opcional)
# SENTRY_DSN=https://...

# ==========================================
# REDIS (opcional para caché y sesiones)
# ==========================================

# REDIS_URL=redis://localhost:6379
# REDIS_PASSWORD=
# REDIS_DB=0

# ==========================================
# OTROS
# ==========================================

# URL base de la aplicación (para emails, etc.)
APP_URL=http://localhost:5173

# Timezone
TZ=America/Mexico_City
```

**Importante:**
- Genera secrets seguros para JWT
- Cambia TODOS los valores por defecto en producción
- Usa gestores de secretos en producción (AWS Secrets Manager, etc.)

---

## 3. Docker Compose (Opcional)

**Ubicación:** `docker-compose.yml`

```yaml
version: '3.8'

services:
  # PostgreSQL Database
  postgres:
    image: postgres:15-alpine
    container_name: planificador-db
    environment:
      POSTGRES_USER: usuario
      POSTGRES_PASSWORD: password
      POSTGRES_DB: planificador_db
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - planificador-network

  # Redis (opcional)
  redis:
    image: redis:7-alpine
    container_name: planificador-redis
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data
    networks:
      - planificador-network

  # Backend (opcional - para desarrollo)
  backend:
    build:
      context: ./backend
      dockerfile: Dockerfile.dev
    container_name: planificador-backend
    ports:
      - "3000:3000"
    environment:
      - NODE_ENV=development
      - DATABASE_URL=postgresql://usuario:password@postgres:5432/planificador_db
    volumes:
      - ./backend:/app
      - /app/node_modules
    depends_on:
      - postgres
      - redis
    networks:
      - planificador-network
    command: npm run dev

volumes:
  postgres_data:
  redis_data:

networks:
  planificador-network:
    driver: bridge
```

---

## 4. Generador de Secrets

Puedes usar estos comandos para generar secrets seguros:

### Node.js (cualquier OS)
```bash
node -e "console.log(require('crypto').randomBytes(64).toString('hex'))"
```

### Linux/Mac
```bash
openssl rand -hex 64
```

### PowerShell (Windows)
```powershell
[Convert]::ToBase64String([System.Security.Cryptography.RNGCryptoServiceProvider]::GetBytes(64))
```

---

## 5. `.gitignore` Completo

**Ubicación:** Raíz del proyecto

```gitignore
# ==========================================
# DEPENDENCIES
# ==========================================
node_modules/
.pnp
.pnp.js

# ==========================================
# ENVIRONMENT VARIABLES
# ==========================================
.env
.env.local
.env.development.local
.env.test.local
.env.production.local
.env.*.local

# ==========================================
# TESTING
# ==========================================
coverage/
*.lcov
.nyc_output

# ==========================================
# BUILD
# ==========================================
dist/
build/
*.tsbuildinfo

# ==========================================
# LOGS
# ==========================================
logs/
*.log
npm-debug.log*
yarn-debug.log*
yarn-error.log*
lerna-debug.log*
pnpm-debug.log*

# ==========================================
# EDITOR / IDE
# ==========================================

# VS Code
.vscode/
!.vscode/extensions.json
!.vscode/settings.json

# IntelliJ IDEA
.idea/
*.iml
*.iws

# Eclipse
.project
.classpath
.settings/

# Sublime Text
*.sublime-project
*.sublime-workspace

# ==========================================
# OS
# ==========================================

# macOS
.DS_Store
.AppleDouble
.LSOverride
._*

# Windows
Thumbs.db
ehthumbs.db
Desktop.ini
$RECYCLE.BIN/

# Linux
*~

# ==========================================
# DATABASES
# ==========================================
*.db
*.sqlite
*.sqlite3

# Prisma
prisma/dev.db
prisma/dev.db-journal

# ==========================================
# TEMP FILES
# ==========================================
tmp/
temp/
*.tmp
*.swp
*.swo
*~

# ==========================================
# UPLOADS / USER FILES
# ==========================================
uploads/
public/uploads/

# ==========================================
# DOCKER
# ==========================================
.dockerignore

# ==========================================
# OTHERS
# ==========================================
.eslintcache
.cache/
.parcel-cache/

# Yarn
.yarn/cache
.yarn/unplugged
.yarn/build-state.yml
.yarn/install-state.gz
.pnp.*
```

---

## 6. `.env.production.example`

Para producción, crea un ejemplo separado:

**Ubicación:** `backend/.env.production.example`

```env
# ==========================================
# PRODUCTION ENVIRONMENT
# ==========================================

NODE_ENV=production
PORT=3000

# Database (usa URL de producción)
DATABASE_URL=postgresql://user:pass@host.com:5432/prod_db?ssl=true

# JWT (CAMBIA ESTOS VALORES)
JWT_SECRET=CAMBIAR_POR_SECRET_SEGURO_64_CARACTERES
JWT_EXPIRES_IN=15m
JWT_REFRESH_SECRET=CAMBIAR_POR_OTRO_SECRET_SEGURO_DIFERENTE
JWT_REFRESH_EXPIRES_IN=7d

# CORS (dominios de producción)
ALLOWED_ORIGINS=https://tuapp.com,https://www.tuapp.com

# OpenAI
OPENAI_API_KEY=sk-prod-key-aqui
OPENAI_MODEL=gpt-4-turbo-preview

# Rate Limiting (más estricto en producción)
RATE_LIMIT_WINDOW_MS=900000
RATE_LIMIT_MAX_REQUESTS=50
AI_RATE_LIMIT_WINDOW_MS=3600000
AI_RATE_LIMIT_MAX_REQUESTS=20

# Logging
LOG_LEVEL=warn

# Sentry
SENTRY_DSN=https://tu-sentry-dsn-aqui

# App URL
APP_URL=https://tuapp.com
```

---

## 7. Scripts de Setup

### `setup.sh` (Linux/Mac)

```bash
#!/bin/bash

echo "🚀 Configurando proyecto..."

# Frontend
echo "📦 Instalando dependencias del frontend..."
cd frontend
cp .env.example .env
npm install

# Backend
echo "📦 Instalando dependencias del backend..."
cd ../backend
cp .env.example .env
npm install

# Prisma
echo "🗄️ Configurando base de datos..."
npx prisma generate
npx prisma migrate dev --name init

echo "✅ Proyecto configurado!"
echo ""
echo "📝 Próximos pasos:"
echo "1. Edita frontend/.env con tu configuración"
echo "2. Edita backend/.env con tu configuración"
echo "3. Ejecuta 'npm run dev' en frontend y backend"
```

### `setup.ps1` (Windows PowerShell)

```powershell
Write-Host "🚀 Configurando proyecto..." -ForegroundColor Green

# Frontend
Write-Host "📦 Instalando dependencias del frontend..." -ForegroundColor Cyan
Set-Location frontend
Copy-Item .env.example .env
npm install

# Backend
Write-Host "📦 Instalando dependencias del backend..." -ForegroundColor Cyan
Set-Location ..\backend
Copy-Item .env.example .env
npm install

# Prisma
Write-Host "🗄️ Configurando base de datos..." -ForegroundColor Cyan
npx prisma generate
npx prisma migrate dev --name init

Write-Host "✅ Proyecto configurado!" -ForegroundColor Green
Write-Host ""
Write-Host "📝 Próximos pasos:" -ForegroundColor Yellow
Write-Host "1. Edita frontend\.env con tu configuración"
Write-Host "2. Edita backend\.env con tu configuración"
Write-Host "3. Ejecuta 'npm run dev' en frontend y backend"
```

---

## 8. Variables por Entorno

### Desarrollo
```env
NODE_ENV=development
DATABASE_URL=postgresql://localhost:5432/dev_db
LOG_LEVEL=debug
```

### Testing
```env
NODE_ENV=test
DATABASE_URL=postgresql://localhost:5432/test_db
LOG_LEVEL=error
```

### Staging
```env
NODE_ENV=staging
DATABASE_URL=postgresql://staging-host:5432/staging_db
LOG_LEVEL=info
```

### Producción
```env
NODE_ENV=production
DATABASE_URL=postgresql://prod-host:5432/prod_db
LOG_LEVEL=warn
```

---

## 📚 Recursos Útiles

### Gestión de Secrets en Producción

- **Railway:** Variables de entorno en el dashboard
- **Vercel:** Variables en Project Settings
- **AWS:** AWS Secrets Manager
- **Azure:** Key Vault
- **GCP:** Secret Manager

### Herramientas de Desarrollo

- **dotenv:** Para cargar variables de entorno
- **dotenv-expand:** Para expandir variables
- **envalid:** Para validar variables requeridas

---

## ⚠️ Seguridad

### ❌ NUNCA hacer:
- Commitear archivos `.env` al repositorio
- Exponer API keys en el frontend
- Usar valores por defecto en producción
- Compartir secrets en texto plano

### ✅ SIEMPRE hacer:
- Usar `.env.example` como plantilla
- Generar secrets únicos y seguros
- Rotar secrets regularmente
- Usar gestores de secretos en producción
- Validar variables de entorno al inicio

---

## 🎯 Checklist de Configuración

Antes de desarrollar:

- [ ] Crear `.env` en frontend desde `.env.example`
- [ ] Crear `.env` en backend desde `.env.example`
- [ ] Generar JWT_SECRET seguro
- [ ] Generar JWT_REFRESH_SECRET seguro
- [ ] Configurar DATABASE_URL
- [ ] Agregar OPENAI_API_KEY (o tu proveedor de IA)
- [ ] Configurar ALLOWED_ORIGINS
- [ ] Verificar que `.env` está en `.gitignore`
- [ ] Probar conexión a base de datos
- [ ] Probar que el backend inicia correctamente
- [ ] Probar que el frontend puede llamar al backend

---

Usa estas plantillas para configurar tu proyecto de forma segura y organizada! 🔒
