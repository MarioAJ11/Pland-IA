# 📋 Historial de Desarrollo - Pland-IA

> Registro detallado de todas las fases del desarrollo del proyecto

---

## 🏗️ FASE 1: Configuración Inicial del Proyecto
**Fecha**: Octubre-Noviembre 2025  
**Estado**: ✅ Completado

### Objetivos
- Configurar arquitectura de microservicios
- Establecer base de datos PostgreSQL multi-schema
- Preparar entorno de desarrollo

### Logros
- ✅ Estructura de carpetas creada (`apps/`, `docker/`, `.github/`)
- ✅ PostgreSQL configurado con Docker Compose
- ✅ Schemas creados: `auth_schema`, `core_schema`, `pantry_schema`
- ✅ Docker Compose con PostgreSQL 15 y Redis
- ✅ Documentación técnica inicial

### Tecnologías
- Docker & Docker Compose
- PostgreSQL 15
- Redis

---

## 🔐 FASE 2: Auth Service (.NET 8)
**Fecha**: Noviembre 2025  
**Estado**: ✅ Completado

### Objetivos
- Implementar microservicio de autenticación con JWT
- Sistema de refresh tokens con sliding expiration
- Seguridad con BCrypt y validaciones

### Logros Técnicos

#### 2.1. Configuración Base
- ✅ Proyecto ASP.NET Core 8 Web API creado
- ✅ Entity Framework Core 9.0 configurado
- ✅ Conexión a PostgreSQL con `auth_schema`
- ✅ Swagger/OpenAPI para documentación

#### 2.2. Modelos y Migraciones
- ✅ Modelo `User` con campos: Id, Email, PasswordHash, Name, Avatar, IsActive
- ✅ Tokens de refresco: `RefreshToken`, `RefreshTokenExpiry`
- ✅ Migraciones aplicadas exitosamente
- ✅ Timestamps: `CreatedAt`, `UpdatedAt`

#### 2.3. Autenticación JWT
- ✅ Generación de Access Tokens (15 minutos)
- ✅ Generación de Refresh Tokens (7-30 días)
- ✅ RememberMe: 7 días (normal) vs 30 días (remember me)
- ✅ Sliding Expiration: renovación automática mientras el usuario esté activo
- ✅ JWT almacenado en User Secrets (desarrollo)

#### 2.4. Endpoints REST
- ✅ `POST /api/auth/register` - Registro de usuarios
- ✅ `POST /api/auth/login` - Inicio de sesión con RememberMe
- ✅ `POST /api/auth/refresh-token` - Renovación de tokens
- ✅ `POST /api/auth/logout` - Cierre de sesión

#### 2.5. Seguridad
- ✅ BCrypt para hash de contraseñas (nunca en texto plano)
- ✅ Validación de email único
- ✅ Validación de usuarios activos
- ✅ Rotación de refresh tokens (seguridad)

#### 2.6. Middleware y Arquitectura
- ✅ **GlobalErrorHandlerMiddleware**: Manejo centralizado de excepciones
  - Captura automática de todas las excepciones
  - Respuestas JSON estandarizadas
  - Mapeo de tipos de excepción a códigos HTTP
- ✅ **CORS configurado** para desarrollo:
  - React (puerto 3000)
  - Vite (puerto 5173)
  - Tauri (puerto 1420)
- ✅ Simplificación de controladores (eliminados try-catch repetitivos)

#### 2.7. Logging Profesional (Serilog)
- ✅ **Serilog.AspNetCore** instalado y configurado
- ✅ **Console Sink**: logs con colores y formato legible
- ✅ **File Sink**: archivos diarios en `logs/auth-service-YYYY-MM-DD.log`
- ✅ Retención de 30 días de logs
- ✅ Filtrado inteligente (solo warnings de Microsoft/EF)
- ✅ Logs con emojis para operaciones clave:
  - 📝 Registro de usuarios
  - 🔐 Intentos de login
  - ✅ Operaciones exitosas
  - ⚠️ Advertencias de seguridad
  - 🔄 Renovación de tokens
  - 🚪 Cierre de sesión

### Estructura del Código
```
apps/auth-service/AuthService/
├── Controllers/
│   └── AuthController.cs          # Endpoints REST
├── Services/
│   ├── IAuthService.cs            # Interfaz del servicio
│   └── AuthService.cs             # Lógica de negocio
├── Models/
│   ├── Entities/
│   │   └── User.cs                # Entidad de usuario
│   └── DTOs/
│       ├── LoginRequest.cs        # DTO de login
│       ├── LoginResponse.cs       # DTO de respuesta
│       ├── RegisterRequest.cs     # DTO de registro
│       └── RefreshTokenRequest.cs # DTO de refresh
├── Data/
│   └── AppDbContext.cs            # Contexto EF Core
├── Middleware/
│   └── GlobalErrorHandlerMiddleware.cs  # Manejo de errores
├── Migrations/                    # Migraciones EF
├── logs/                          # Archivos de log (gitignored)
└── Program.cs                     # Configuración + Serilog
```

### Paquetes NuGet Instalados
```xml
<PackageReference Include="Microsoft.EntityFrameworkCore" Version="9.0.0" />
<PackageReference Include="Npgsql.EntityFrameworkCore.PostgreSQL" Version="9.0.0" />
<PackageReference Include="BCrypt.Net-Next" Version="4.0.3" />
<PackageReference Include="Microsoft.AspNetCore.Authentication.JwtBearer" Version="8.0.11" />
<PackageReference Include="Swashbuckle.AspNetCore" Version="6.6.2" />
<PackageReference Include="Serilog.AspNetCore" Version="9.0.0" />
<PackageReference Include="Serilog.Sinks.Console" Version="6.1.1" />
<PackageReference Include="Serilog.Sinks.File" Version="7.0.0" />
```

### Commits Realizados
1. ✅ Migración inicial de base de datos
2. ✅ Agregada solución de seguridad (JWT + BCrypt)
3. ✅ Agregados DTOs con soporte RememberMe
4. ✅ Implementado AuthService con JWT y sliding expiration
5. ✅ Agregados endpoints REST en AuthController
6. ✅ Configurada inyección de dependencias y JWT settings
7. ✅ Mejoras en manejo de errores y CORS configurado
8. ✅ Implementado Serilog para logging profesional
9. ✅ Recuperados archivos .gitignore y .github

### Pruebas Realizadas
- ✅ Compilación exitosa (0 errores, 0 warnings)
- ✅ PostgreSQL conectado correctamente
- ✅ Registro de usuario funcionando
- ✅ Login con generación de JWT
- ✅ Swagger UI accesible en http://localhost:5012/swagger
- ✅ Logs apareciendo en consola y archivos

### Configuración de Despliegue
- Puerto: `5012` (HTTP en desarrollo)
- Base de datos: PostgreSQL en Docker (puerto 5432)
- Schema: `auth_schema`
- Logs: `logs/auth-service-YYYY-MM-DD.log`

---

## 🎯 FASE 3: Core Service (Spring Boot) - PENDIENTE
**Estado**: ⏳ Por iniciar

### Objetivos Planificados
- Crear microservicio de gestión de tareas con Spring Boot 3.x
- Implementar entidades: Workspace, Project, Task
- CRUD completo con Spring Data JPA
- Integración con Auth Service

### Tareas Pendientes
- [ ] Crear proyecto Spring Boot con Maven/Gradle
- [ ] Configurar PostgreSQL con `core_schema`
- [ ] Implementar modelos de dominio
- [ ] Crear repositorios y servicios
- [ ] Implementar endpoints REST
- [ ] Validaciones con Bean Validation
- [ ] Documentación con Swagger

---

## 📊 Resumen del Progreso

### ✅ Completado
- Configuración inicial
- Auth Service completo y funcional
- Logging profesional
- Manejo de errores centralizado
- Documentación técnica

### ⏳ En Progreso
- Ninguno actualmente

### 🔜 Próximo
- Core Service con Spring Boot

### 📈 Estadísticas
- **Commits**: 12+
- **Líneas de código**: ~2,000+
- **Archivos creados**: 20+
- **Tecnologías**: .NET 8, PostgreSQL, Docker, Serilog, JWT, BCrypt, Swagger

---

## 🛠️ Tecnologías Utilizadas

### Backend
- **Auth Service**: .NET 8 / ASP.NET Core 8
- **Core Service**: Spring Boot 3.x (planificado)

### Base de Datos
- PostgreSQL 15 (multi-schema)
- Entity Framework Core 9.0
- Spring Data JPA (planificado)

### DevOps & Tools
- Docker & Docker Compose
- Git & GitHub
- Swagger/OpenAPI
- Serilog

### Seguridad
- JWT (JSON Web Tokens)
- BCrypt (password hashing)
- Refresh Tokens con Sliding Expiration

---

## 📝 Notas Importantes

### Decisiones de Arquitectura
1. **Microservicios**: Cada servicio en su propia tecnología (diversidad para portfolio)
2. **Multi-schema**: Un solo PostgreSQL con schemas separados por servicio
3. **JWT Stateless**: No guardamos sesiones en servidor
4. **Sliding Expiration**: Mientras el usuario esté activo, no expira

### Buenas Prácticas Implementadas
- ✅ Contraseñas hasheadas con BCrypt (nunca texto plano)
- ✅ User Secrets para desarrollo (nunca en código)
- ✅ Manejo centralizado de errores
- ✅ Logging estructurado con Serilog
- ✅ Validaciones en DTOs
- ✅ CORS configurado para desarrollo
- ✅ Documentación con Swagger
- ✅ Commits descriptivos en español

### Archivos Privados (Solo Local)
- `.github/info/` - Documentación privada del proyecto
- `.gitignore` - Configuración de Git (no se sube al repo público)

---

**Última actualización**: 5 de noviembre de 2025
