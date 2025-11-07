# TODO - Siguientes Fases del Proyecto

## Backend - Completado ✅

- [x] TODO #1: Variables de entorno
- [x] TODO #2: Profiles de configuración
- [x] TODO #3: Integración JWT Auth ↔ Core
- [x] TODO #4: Logging unificado
- [x] TODO #5: CORS configuración flexible
- [x] TODO #6: Tests unitarios completos (34/34 passing)
- [x] TODO #7: README profesional
- [x] TODO #8: Flyway migrations

---

## Fase 2 - Infraestructura DevOps

### TODO #9: Pipeline CI/CD con GitHub Actions
**Prioridad:** Alta  
**Tiempo estimado:** 4-6 horas

**Tareas:**
- Configurar workflow de build automático para Auth Service
- Configurar workflow de build automático para Core Service
- Ejecutar tests automáticamente en cada push/PR
- Generar reportes de cobertura de tests
- Configurar análisis de código estático (SonarCloud opcional)

**Archivos a crear:**
- `.github/workflows/auth-service-ci.yml`
- `.github/workflows/core-service-ci.yml`

**Criterios de aceptación:**
- Build exitoso en cada push
- Tests ejecutados automáticamente
- Badge de build status en README

---

### TODO #10: Dockerización Completa
**Prioridad:** Alta  
**Tiempo estimado:** 3-4 horas

**Tareas:**
- Crear Dockerfile multi-stage para Auth Service (.NET)
- Crear Dockerfile multi-stage para Core Service (Spring Boot)
- Actualizar docker-compose.yml con servicios optimizados
- Configurar health checks en contenedores
- Optimizar tamaño de imágenes Docker

**Archivos a crear:**
- `apps/auth-service/Dockerfile`
- `apps/core-service/Dockerfile`
- Actualizar `docker-compose.yml`

**Criterios de aceptación:**
- Imágenes Docker construidas correctamente
- Sistema completo levanta con `docker-compose up`
- Health checks funcionando

---

## Fase 3 - Testing Avanzado

### TODO #11: Tests de Integración
**Prioridad:** Media  
**Tiempo estimado:** 6-8 horas

**Tareas:**
- Implementar tests de integración para Auth Service con base de datos real
- Implementar tests de integración para Core Service con TestContainers
- Tests end-to-end de flujo Auth → Core (login + workspace CRUD)
- Configurar base de datos de pruebas en memoria/contenedores

**Tecnologías:**
- xUnit + WebApplicationFactory para Auth Service
- JUnit + TestContainers para Core Service
- REST Assured para tests E2E

**Criterios de aceptación:**
- Al menos 10 tests de integración por servicio
- Tests E2E cubren flujos críticos
- Ejecución automática en CI/CD

---

### TODO #12: Monitoreo y Observabilidad
**Prioridad:** Baja  
**Tiempo estimado:** 4-5 horas

**Tareas:**
- Configurar métricas con Prometheus endpoints
- Implementar health checks avanzados
- Agregar tracing distribuido con OpenTelemetry (opcional)
- Configurar alertas básicas

**Archivos a modificar:**
- `application.properties` (agregar actuator endpoints)
- `appsettings.json` (agregar health checks)

---

## Fase 4 - Frontend Web

### TODO #13: Estructura Base del Frontend
**Prioridad:** Alta  
**Tiempo estimado:** 8-10 horas

**Tareas:**
- Inicializar proyecto React con Vite + TypeScript
- Configurar ESLint, Prettier, estructura de carpetas
- Implementar routing con React Router
- Configurar Zustand para state management
- Implementar cliente Axios con interceptores

**Estructura:**
```
apps/web-client/
├── src/
│   ├── api/          # Servicios API
│   ├── components/   # Componentes reutilizables
│   ├── pages/        # Páginas/vistas
│   ├── store/        # State management
│   ├── types/        # TypeScript types
│   └── utils/        # Utilidades
```

**Criterios de aceptación:**
- Proyecto inicializado y ejecutándose
- Autenticación JWT funcionando
- Layout base implementado

---

### TODO #14: Pantallas de Autenticación
**Prioridad:** Alta  
**Tiempo estimado:** 6-8 horas

**Tareas:**
- Página de Login con validación
- Página de Registro
- Manejo de tokens JWT en localStorage
- Protección de rutas privadas
- Manejo de errores de autenticación

**Tecnologías:**
- React Hook Form para formularios
- Zod para validación
- Material-UI o TailwindCSS para estilos

---

### TODO #15: Dashboard y Gestión de Workspaces
**Prioridad:** Alta  
**Tiempo estimado:** 10-12 horas

**Tareas:**
- Dashboard principal con lista de workspaces
- Formulario de creación de workspace
- Edición y eliminación de workspaces
- Búsqueda y filtros
- Paginación

**Criterios de aceptación:**
- CRUD completo de workspaces
- UX fluida y responsive
- Manejo de estados de carga

---

## Fase 5 - Pantry Service (IA)

### TODO #16: Base del Pantry Service
**Prioridad:** Media  
**Tiempo estimado:** 8-10 horas

**Tareas:**
- Inicializar proyecto FastAPI
- Configurar estructura de directorios
- Implementar conexión a PostgreSQL con SQLAlchemy
- Configurar variables de entorno
- Implementar logging

**Estructura:**
```
apps/pantry-service/
├── app/
│   ├── api/         # Endpoints
│   ├── models/      # Modelos SQLAlchemy
│   ├── services/    # Lógica de negocio
│   ├── schemas/     # Pydantic schemas
│   └── core/        # Config, deps
```

---

### TODO #17: Integración con OpenAI
**Prioridad:** Media  
**Tiempo estimado:** 8-10 horas

**Tareas:**
- Integrar OpenAI API para sugerencias de recetas
- Implementar endpoint de generación de recetas basado en ingredientes
- Implementar caché de respuestas para optimizar costos
- Manejo de errores de API externa

---

## Fase 6 - Despliegue

### TODO #18: Despliegue en Cloud
**Prioridad:** Baja  
**Tiempo estimado:** 6-8 horas

**Tareas:**
- Configurar despliegue en Railway/Render/AWS
- Configurar bases de datos en producción
- Configurar variables de entorno en producción
- SSL/TLS para conexiones seguras

---

## Próximos Pasos Recomendados

**Para mañana (sesión 1):**
1. TODO #9: Pipeline CI/CD - Automatizar builds y tests
2. TODO #10: Dockerización - Contenedores optimizados

**Para la semana:**
1. TODO #11: Tests de integración
2. TODO #13: Estructura base del frontend
3. TODO #14: Pantallas de autenticación

**Criterio de priorización:**
- **Alta:** Imprescindible para demo funcional
- **Media:** Mejora significativa del proyecto
- **Baja:** Nice to have, showcase avanzado

---

**Estado actual:** Backend completado y seguro para repositorio público  
**Tests:** 34/34 passing  
**Cobertura:** ~75%  
**Última actualización:** 7 de noviembre de 2025
