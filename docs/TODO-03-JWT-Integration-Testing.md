# 🔐 Pruebas de JWT Integration

## ✅ TODO #3 - JWT Integration Implementado

### 📋 Cambios realizados:

#### 1. Dependencias agregadas (pom.xml):
- `spring-boot-starter-security` - Framework de seguridad
- `io.jsonwebtoken:jjwt-api` (0.12.6) - Librería JWT
- `io.jsonwebtoken:jjwt-impl` - Implementación JWT
- `io.jsonwebtoken:jjwt-jackson` - Serialización JSON

#### 2. Clases de seguridad creadas:
- **`JwtUtil.java`** - Valida tokens JWT y extrae claims (userId, email, name)
- **`JwtAuthenticationFilter.java`** - Intercepta requests y valida Authorization header
- **`SecurityConfig.java`** - Configura Spring Security (endpoints públicos vs protegidos)
- **`AuthenticatedUserHelper.java`** - Helper para obtener userId del contexto

#### 3. Controllers actualizados:
- **`WorkspaceController.java`** - Extrae userId del JWT automáticamente (no del RequestParam)

#### 4. Configuración:
- **`application.properties`** - Variables JWT (secret, issuer, audience)
- **`.env`** - JWT_SECRET debe coincidir con Auth Service

---

## 🧪 Cómo probar la integración JWT:

### Paso 1: Iniciar Auth Service (puerto 5012)
```powershell
cd C:\Users\mario\Desktop\Pland-IA\apps\auth-service\AuthService
dotnet run
```

### Paso 2: Iniciar Core Service (puerto 8080)
```powershell
cd C:\Users\mario\Desktop\Pland-IA\apps\core-service
.\mvnw.cmd spring-boot:run
```

### Paso 3: Obtener JWT del Auth Service
```powershell
# Registrar usuario (si no existe)
$registerBody = @{
    email = "test@jwt.com"
    password = "Test1234!"
    name = "Test JWT User"
    rememberMe = $false
} | ConvertTo-Json

$registerResponse = Invoke-RestMethod `
    -Uri "http://localhost:5012/api/auth/register" `
    -Method POST `
    -Body $registerBody `
    -ContentType "application/json"

# Login para obtener JWT
$loginBody = @{
    email = "test@jwt.com"
    password = "Test1234!"
    rememberMe = $false
} | ConvertTo-Json

$loginResponse = Invoke-RestMethod `
    -Uri "http://localhost:5012/api/auth/login" `
    -Method POST `
    -Body $loginBody `
    -ContentType "application/json"

$jwt = $loginResponse.accessToken
Write-Host "✅ JWT obtenido: $jwt"
```

### Paso 4: Probar Core Service SIN token (debe fallar con 403)
```powershell
# Esto debe devolver 403 Forbidden
Invoke-RestMethod -Uri "http://localhost:8080/api/workspaces" -Method GET
```

### Paso 5: Probar Core Service CON token (debe funcionar)
```powershell
# Esto debe funcionar ✅
$headers = @{
    "Authorization" = "Bearer $jwt"
}

$workspaces = Invoke-RestMethod `
    -Uri "http://localhost:8080/api/workspaces" `
    -Method GET `
    -Headers $headers

Write-Host "✅ Workspaces obtenidos: $($workspaces.Count)"
$workspaces | ConvertTo-Json
```

### Paso 6: Crear workspace con JWT
```powershell
$newWorkspace = @{
    name = "Workspace desde JWT"
    description = "Creado con autenticación JWT"
    color = "#FF5733"
} | ConvertTo-Json

$created = Invoke-RestMethod `
    -Uri "http://localhost:8080/api/workspaces" `
    -Method POST `
    -Body $newWorkspace `
    -ContentType "application/json" `
    -Headers $headers

Write-Host "✅ Workspace creado con ID: $($created.id)"
$created | ConvertTo-Json
```

---

## 🔍 Verificaciones:

### ✅ Endpoints públicos (sin token):
- `/swagger-ui/**` - Swagger UI accesible
- `/v3/api-docs/**` - OpenAPI docs
- `/actuator/health` - Health check

### 🔒 Endpoints protegidos (requieren Bearer token):
- `/api/workspaces` - Todos los endpoints de workspaces
- `/api/projects` - Todos los endpoints de proyectos
- `/api/tasks` - Todos los endpoints de tareas

---

## 🔧 Troubleshooting:

### Error: "Invalid JWT signature"
- ✅ Verificar que JWT_SECRET en Core Service coincida con Auth Service
- Revisar archivos `.env` en ambos servicios

### Error: "403 Forbidden"
- ✅ Verificar que el header Authorization tenga formato: `Bearer <token>`
- El token debe ser válido y no expirado

### Error: "userId null"
- ✅ Verificar que el claim `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier` existe en el JWT
- Revisar JwtUtil.extractUserId()

---

## 🎉 Resultado esperado:

```
✅ Core Service valida JWT del Auth Service
✅ Endpoints protegidos requieren autenticación
✅ userId se extrae automáticamente del token
✅ Swagger UI accesible sin autenticación (solo en dev)
✅ Workspaces filtrados por usuario autenticado
```

---

## 📝 Próximos pasos:

- TODO #4: Logging unificado
- TODO #5: CORS configuración flexible
- TODO #6: Tests unitarios básicos
- TODO #7: Migraciones con Flyway
