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
- **Microservicios**: 2 operativos (Auth Service + Core Service)

---

## ⚙️ FASE 3: Core Service (Spring Boot)
**Fecha**: 7 de noviembre de 2025  
**Estado**: ✅ Completado

### Objetivos
- Implementar microservicio principal de gestión de proyectos
- Sistema completo de Workspaces → Projects → Tasks
- API REST con documentación Swagger
- Validaciones y manejo de errores global

### Logros Técnicos

#### 3.1. Configuración del Proyecto
- ✅ Spring Boot 3.5.7 con Java 21
- ✅ Spring Data JPA + Hibernate 6.6.33
- ✅ PostgreSQL conexión a `core_schema`
- ✅ Lombok para reducir boilerplate
- ✅ SpringDoc OpenAPI 2.7.0 (Swagger)
- ✅ Maven Wrapper configurado

#### 3.2. Modelo de Datos (Entidades JPA)

**Workspace** (Espacio de Trabajo):
- Entidad raíz que agrupa proyectos
- Campos: `id` (UUID), `name`, `description`, `userId`, `createdAt`, `updatedAt`
- Relación: 1 Workspace → N Projects
- Analogía: Carpeta principal

**Project** (Proyecto):
- Pertenece a un Workspace, contiene Tasks
- Campos: `id` (UUID), `name`, `description`, `workspaceId`, `createdAt`, `updatedAt`
- Relación: N Projects → 1 Workspace, 1 Project → N Tasks
- Analogía: Subcarpeta

**Task** (Tarea):
- Pertenece a un Project
- Campos: `id` (UUID), `title`, `description`, `status`, `priority`, `dueDate`, `assignedTo`, `projectId`, `createdAt`, `updatedAt`
- Enums: `TaskStatus` (TO_DO, IN_PROGRESS, DONE), `TaskPriority` (LOW, MEDIUM, HIGH, URGENT)
- Relación: N Tasks → 1 Project
- Analogía: Archivo dentro de subcarpeta

**Anotaciones JPA**:
- `@ManyToOne` / `@OneToMany` para relaciones bidireccionales
- `@JsonManagedReference` / `@JsonBackReference` para evitar loops infinitos en JSON
- `@CreationTimestamp` / `@UpdateTimestamp` para timestamps automáticos
- Validaciones: `@NotBlank`, `@Size`, `@Valid`

#### 3.3. Capa de Persistencia (Repositories)

**WorkspaceRepository**:
- `findByUserId(UUID userId)` - Todos los workspaces de un usuario
- `existsByNameAndUserId(String name, UUID userId)` - Validar duplicados

**ProjectRepository**:
- `findByWorkspaceId(UUID workspaceId)` - Proyectos de un workspace
- `existsByNameAndWorkspaceId(String name, UUID workspaceId)` - Validar duplicados
- `countByWorkspaceId(UUID workspaceId)` - Contar proyectos

**TaskRepository**:
- `findByProjectId(UUID projectId)` - Tareas de un proyecto
- `findByStatus(TaskStatus status)` - Filtrar por estado
- `findByAssignedTo(UUID userId)` - Tareas asignadas a usuario
- `findUrgentIncompleteTasks()` - Query JPQL personalizada
- `findTasksDueSoon(LocalDate date, int days)` - Tareas próximas a vencer

#### 3.4. Lógica de Negocio (Services)

**WorkspaceService**:
- CRUD completo con `@Transactional`
- Validación de nombres duplicados por usuario
- Logging estructurado con `@Slf4j`

**ProjectService**:
- CRUD con validación de workspace existente
- Validación de nombres duplicados por workspace
- Manejo de relaciones bidireccionales

**TaskService**:
- CRUD completo
- Métodos especializados: `updateTaskStatus()`, `assignTask()`
- Queries de filtrado: urgentes, por vencer, por estado
- Validación de project existente

#### 3.5. API REST (Controllers)

**WorkspaceController** (`/api/workspaces`):
- `GET /` - Listar todos
- `GET /{id}` - Obtener por ID
- `GET /user/{userId}` - Por usuario
- `POST /` - Crear
- `PUT /{id}` - Actualizar
- `DELETE /{id}` - Eliminar

**ProjectController** (`/api/projects`):
- `GET /` - Listar todos (filtrable por workspace)
- `GET /{id}` - Obtener por ID
- `POST /` - Crear (requiere `workspaceId` query param)
- `PUT /{id}` - Actualizar
- `DELETE /{id}` - Eliminar

**TaskController** (`/api/tasks`):
- `GET /` - Listar todas (filtrable por status)
- `GET /{id}` - Obtener por ID
- `GET /project/{projectId}` - Por proyecto
- `GET /assigned/{userId}` - Asignadas a usuario
- `GET /urgent` - Urgentes incompletas
- `GET /due-soon` - Próximas a vencer (query param `days`)
- `POST /` - Crear (requiere `projectId` query param)
- `PUT /{id}` - Actualizar completa
- `PATCH /{id}/status` - Cambiar solo estado
- `PATCH /{id}/assign` - Asignar a usuario
- `DELETE /{id}` - Eliminar

**Total de endpoints**: 24 endpoints REST documentados

#### 3.6. Manejo de Errores Global

**GlobalExceptionHandler** (`@RestControllerAdvice`):
- `IllegalArgumentException` → 400 Bad Request
- `NoSuchElementException` → 404 Not Found
- `MethodArgumentNotValidException` → 400 (validaciones Bean)
- `Exception` genérica → 500 Internal Server Error
- Respuestas JSON estandarizadas con timestamp, status, error, message

#### 3.7. Documentación API

**SpringDoc OpenAPI 2.7.0**:
- Swagger UI accesible en `/swagger-ui.html`
- Documentación automática desde anotaciones
- Configuración personalizada en `OpenAPIConfig`:
  - Título: "Pland-IA Core Service API"
  - Versión: 1.0
  - Descripción detallada
  - Información de contacto
  - Licencia Apache 2.0

#### 3.8. Problemas Resueltos

**Problema 1: Compilación con Lombok**
- Error: Métodos getter/setter no reconocidos
- Causa: Clase duplicada en paquete `com.plandia` (typo)
- Solución: Eliminado paquete duplicado, recompilación limpia
- Resultado: Lombok funcionando correctamente

**Problema 2: Infinite JSON Serialization Loop**
- Error: Respuestas JSON infinitas en relaciones bidireccionales
- Causa: Jackson serializa Workspace → Projects → Workspace → Projects...
- Solución: 
  - `@JsonManagedReference` en colecciones (permite serialización forward)
  - `@JsonBackReference` en referencias (previene serialización back)
- Resultado: JSON limpio sin loops

**Problema 3: SpringDoc Incompatibilidad**
- Error: `NoSuchMethodError` con SpringDoc 2.6.0 en Spring Boot 3.5.7
- Causa: Incompatibilidad de versiones
- Solución: Actualizar a SpringDoc 2.7.0
- Resultado: Swagger UI funcionando sin errores

**Problema 4: HTTP Status Codes Incorrectos**
- Error: Excepciones de negocio retornaban 500 en lugar de 400
- Causa: Spring no maneja `IllegalArgumentException` por defecto
- Solución: `GlobalExceptionHandler` con `@RestControllerAdvice`
- Resultado: Códigos HTTP apropiados (400 para validaciones, 404 para not found)

### Arquitectura Implementada

```
┌─────────────────┐
│   Swagger UI    │ (localhost:8080/swagger-ui.html)
└────────┬────────┘
         │
┌────────▼─────────┐
│  Controllers     │ (REST endpoints - 24 endpoints)
│  - Workspace     │
│  - Project       │
│  - Task          │
└────────┬─────────┘
         │
┌────────▼─────────┐
│   Services       │ (Business logic + @Transactional)
│  - WorkspaceService
│  - ProjectService
│  - TaskService
└────────┬─────────┘
         │
┌────────▼─────────┐
│  Repositories    │ (Spring Data JPA - 12+ custom queries)
│  - WorkspaceRepo
│  - ProjectRepo
│  - TaskRepo
└────────┬─────────┘
         │
┌────────▼─────────┐
│   PostgreSQL     │ (plandiadb.core_schema)
│  - workspaces    │
│  - projects      │
│  - tasks         │
└──────────────────┘
```

### Estadísticas del Código

- **Entidades**: 3 (Workspace, Project, Task)
- **Repositories**: 3 interfaces con 12+ métodos custom
- **Services**: 3 clases (~450 líneas de lógica de negocio)
- **Controllers**: 3 clases (~430 líneas de endpoints REST)
- **Endpoints REST**: 24 endpoints documentados
- **Líneas de código**: ~1,500 líneas (sin contar generado por Lombok)
- **Archivos Java**: 15 archivos
- **Commits**: 1 (este)

### Pruebas Realizadas

- ✅ Compilación exitosa con Maven
- ✅ Aplicación inicia sin errores en puerto 8080
- ✅ Swagger UI accesible y funcional
- ✅ PostgreSQL conectado correctamente
- ✅ Hibernate crea tablas automáticamente
- ✅ HikariCP pool de conexiones funcionando
- ✅ Spring Data repositories encontrados (3)
- ✅ Jackson serialización sin loops infinitos
- ✅ Global exception handler respondiendo correctamente

### Próximos Pasos

- [ ] Frontend básico con React/Tauri
- [ ] Integración Auth Service ↔ Core Service
- [ ] Testing con Swagger UI (crear workspaces, projects, tasks)
- [ ] Tests unitarios con JUnit
- [ ] Tests de integración
- [ ] Despliegue en cloud (AWS/Azure/Railway)

### Tecnologías

- Spring Boot 3.5.7
- Java 21 (OpenJDK)
- Spring Data JPA
- Hibernate 6.6.33
- PostgreSQL 15
- Lombok 1.18.x
- SpringDoc OpenAPI 2.7.0
- Maven 3.x
- HikariCP (connection pooling)
- Jakarta Bean Validation
- Jackson (JSON serialization)

### Aprendizajes

1. **Lombok acelera desarrollo**: Reduce código boilerplate en 50-70%
2. **Jackson bidirectional relationships**: Siempre usar `@JsonManagedReference`/`@JsonBackReference`
3. **Global exception handling**: Centralizar manejo de errores mejora consistencia API
4. **SpringDoc versioning**: Importante verificar compatibilidad con Spring Boot
5. **Spring Data JPA**: Custom queries con `@Query` son potentes para casos específicos
6. **Arquitectura en capas**: Controller → Service → Repository mantiene código organizado

---

## 📄 FASE 4: Documentación Estratégica
**Fecha**: 7 de noviembre de 2025  
**Estado**: ✅ Completado

### Objetivos
- Documentar estrategia de negocio completa
- Plan de monetización y validación
- Roadmap de crecimiento

### Logros

- ✅ Documento `info/ESTRATEGIA_NEGOCIO.md` creado (100+ páginas)
- ✅ Contenido incluido:
  - Modelos de monetización (Freemium, B2B, Marketplace)
  - Estrategia de validación (landing page, beta testers)
  - Plan Go-to-Market por fases
  - **Estrategia SEO completa** (keywords, contenido, link building)
  - Roadmap de desarrollo (Q1-Q4 2026)
  - Métricas KPI (MAU, MRR, CAC, LTV, etc.)
  - Arquitectura escalable (Kubernetes, multi-región)
  - Proyecciones financieras (Año 1-5)
  - Plan de acción inmediato (próximos 7 días)

### SEO (Search Engine Optimization)

**Definición**: Optimizar el sitio web para aparecer en primeros resultados de Google

**Keywords Target**:
- "gestor de proyectos" (10,000 búsquedas/mes)
- "alternativa a trello" (5,000/mes)
- "app para organizar tareas" (8,000/mes)
- "herramienta gestión proyectos gratis" (4,000/mes)

**Estrategia de Contenido**:
- 2-3 artículos de blog/semana (1,500+ palabras)
- Temas: Comparativas, tutoriales, guías de productividad
- Meta: 10,000 visitas orgánicas/mes en 6 meses

**Estructura Sitio**:
```
pland-ia.com/
├── / (Home)
├── /features
├── /pricing
├── /blog/ (artículos SEO)
├── /comparisons/ (vs Trello, Asana, Notion)
└── /templates
```

### Decisiones de Negocio Documentadas

1. **Modelo Freemium**: Free + Pro ($9) + Team ($49) + Enterprise ($199+)
2. **Target inicial**: Freelancers tech, startups 5-20 personas
3. **Validación primero**: Landing page + $100 en ads antes de continuar
4. **Bootstrap inicial**: 6-12 meses sin inversión, luego decidir
5. **Marketing orgánico**: SEO + Product Hunt + Reddit + Twitter

### Archivos Privados

- `info/ESTRATEGIA_NEGOCIO.md` - No se sube al repo (en `.gitignore`)
- `HISTORIAL_DESARROLLO.md` - No se sube al repo (en `.gitignore`)

---

## 🛠️ Tecnologías Utilizadas

### Backend
- **Auth Service**: .NET 8 / ASP.NET Core 8
- **Core Service**: Spring Boot 3.5.7 / Java 21

### Base de Datos
- PostgreSQL 15 (multi-schema)
- Entity Framework Core 9.0
- Spring Data JPA + Hibernate 6.6.33
- HikariCP (connection pooling)

### DevOps & Tools
- Docker & Docker Compose
- Git & GitHub
- Maven Wrapper
- Swagger/OpenAPI (SpringDoc 2.7.0)
- Serilog (.NET)
- Lombok (Java)

### Seguridad
- JWT (JSON Web Tokens)
- BCrypt (password hashing)
- Refresh Tokens con Sliding Expiration
- Jakarta Bean Validation

---

## 📝 Notas Importantes

### Decisiones de Arquitectura
1. **Microservicios**: Cada servicio en su propia tecnología (diversidad para portfolio)
2. **Multi-schema**: Un solo PostgreSQL con schemas separados por servicio
3. **JWT Stateless**: No guardamos sesiones en servidor
4. **Sliding Expiration**: Mientras el usuario esté activo, no expira
5. **API REST**: Comunicación entre servicios y con frontend
6. **Relaciones JPA**: Bidireccionales con Jackson annotations para JSON limpio

### Buenas Prácticas Implementadas
- ✅ Contraseñas hasheadas con BCrypt (nunca texto plano)
- ✅ User Secrets para desarrollo (nunca en código)
- ✅ Manejo centralizado de errores (GlobalExceptionHandler)
- ✅ Logging estructurado con Serilog (.NET) y Slf4j (Java)
- ✅ Validaciones en DTOs y entidades
- ✅ CORS configurado para desarrollo
- ✅ Documentación con Swagger en ambos servicios
- ✅ Commits descriptivos en español
- ✅ Separación de responsabilidades (Controller → Service → Repository)
- ✅ Transacciones con `@Transactional`
- ✅ Lazy loading de relaciones JPA

### Archivos Privados (Solo Local)
- `.github/info/` - Documentación privada del proyecto
- `info/ESTRATEGIA_NEGOCIO.md` - Estrategia de negocio y monetización
- `HISTORIAL_DESARROLLO.md` - Este archivo (historial completo)

---

**Última actualización**: 7 de noviembre de 2025 - 02:15h
**Sesión**: Core Service completado + Documentación estratégica
