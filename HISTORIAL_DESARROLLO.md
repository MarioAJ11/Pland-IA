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

## 🎯 FASE 3: Core Service (Spring Boot) - EN PROGRESO
**Fecha**: 5 de noviembre de 2025  
**Estado**: ⏳ Configuración inicial completada

### Objetivos
- Crear microservicio de gestión de tareas con Spring Boot 3.x
- Implementar entidades: Workspace, Project, Task
- CRUD completo con Spring Data JPA
- Integración con Auth Service (futuro)

### Logros Técnicos

#### 3.1. Configuración del Entorno
- ✅ Java 21 (OpenJDK Microsoft Build) instalado con winget
- ✅ JAVA_HOME configurado correctamente
- ✅ Maven Wrapper incluido en el proyecto

#### 3.2. Creación del Proyecto
- ✅ Proyecto generado con Spring Initializr
- ✅ Spring Boot 3.5.7 configurado
- ✅ Maven como gestor de dependencias
- ✅ Estructura de paquetes: `com.plandia.coreservice`

#### 3.3. Dependencias Instaladas
- ✅ **Spring Web**: REST APIs
- ✅ **Spring Data JPA**: ORM con Hibernate
- ✅ **PostgreSQL Driver**: Conexión a base de datos
- ✅ **Lombok**: Reducción de boilerplate
- ✅ **Validation**: Bean Validation (Jakarta)
- ✅ **Spring Boot DevTools**: Hot reload en desarrollo

#### 3.4. Configuración de Base de Datos
- ✅ Conexión a PostgreSQL configurada en `application.properties`
- ✅ Schema: `core_schema` (separado del Auth Service)
- ✅ Hibernate DDL: `update` (crea tablas automáticamente)
- ✅ HikariCP como pool de conexiones
- ✅ Logs SQL habilitados para desarrollo

#### 3.5. Primera Ejecución Exitosa
- ✅ Compilación exitosa con Maven
- ✅ Conexión a PostgreSQL establecida
- ✅ Tomcat iniciado en puerto 8080
- ✅ Tiempo de arranque: ~1.5 segundos

### Estructura del Proyecto
```
apps/core-service/
├── .mvn/                          # Maven Wrapper
├── src/
│   ├── main/
│   │   ├── java/com/plandia/coreservice/
│   │   │   └── CoreServiceApplication.java  # Clase principal
│   │   └── resources/
│   │       └── application.properties       # Configuración
│   └── test/                      # Tests (pendiente)
├── target/                        # Build output
├── pom.xml                        # Dependencias Maven
├── mvnw / mvnw.cmd               # Maven Wrapper scripts
└── .gitignore                     # Archivos ignorados
```

### Configuración Actual

**application.properties:**
```properties
spring.application.name=CoreService
server.port=8080

# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/plandiadb?currentSchema=core_schema
spring.datasource.username=postgres
spring.datasource.password=mysecretpassword

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.default_schema=core_schema

# Logging
logging.level.com.plandia.coreservice=INFO
logging.level.org.hibernate.SQL=DEBUG
```

### Logs de Arranque
```
✅ HikariPool-1 - Added connection org.postgresql.jdbc.PgConnection
✅ Database version: 15.14
✅ Tomcat started on port 8080 (http)
✅ Started CoreServiceApplication in 1.522 seconds
```

### Tareas Pendientes (Próxima Sesión)
- [ ] Crear entidad `Workspace` con JPA
- [ ] Crear entidad `Project` con relación a Workspace
- [ ] Crear entidad `Task` con relación a Project
- [ ] Implementar repositorios con Spring Data JPA
- [ ] Crear servicios de negocio
- [ ] Implementar controladores REST
- [ ] Agregar validaciones con Bean Validation
- [ ] Configurar SpringDoc OpenAPI (Swagger)
- [ ] Tests unitarios con JUnit y Mockito

### Problemas Resueltos
1. ❌ **JAVA_HOME no definido** → ✅ Variable configurada en PowerShell
2. ❌ **Puerto 8080 ocupado** → ✅ Proceso anterior terminado
3. ❌ **Credenciales incorrectas** → ✅ Actualizado a `postgres`/`mysecretpassword`

---

## 🎯 FASE 3 (ANTERIOR): Core Service - REEMPLAZADA
> Esta sección fue reemplazada por la FASE 3 actual (arriba)

---

## 📊 Resumen del Progreso

### ✅ Completado
- Configuración inicial del proyecto
- Auth Service completo y funcional (.NET 8)
- Logging profesional con Serilog
- Manejo de errores centralizado
- Documentación técnica privada
- Core Service configurado y corriendo (Spring Boot 3.5.7)
- Java 21 instalado
- Conexión a PostgreSQL establecida

### ⏳ En Progreso
- Core Service - Desarrollo de entidades y CRUD (próxima sesión)

### 🔜 Próximo
- Entidades JPA: Workspace, Project, Task
- Repositorios y servicios Spring
- Endpoints REST del Core Service

### 📈 Estadísticas
- **Commits**: 14+
- **Líneas de código**: ~2,500+
- **Archivos creados**: 25+
- **Tecnologías**: .NET 8, Spring Boot 3.5.7, PostgreSQL 15, Docker, Java 21
- **Microservicios**: 2 (Auth Service operativo, Core Service configurado)

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

**Última actualización**: 5 de noviembre de 2025 - 20:00h
**Sesión**: Auth Service + Core Service (configuración inicial)
