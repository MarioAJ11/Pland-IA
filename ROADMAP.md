# 🗺️ Pland-IA - Roadmap de Desarrollo

> **Última actualización:** 19 de noviembre de 2025  
> **Estado del proyecto:** Backend completo (100%), Frontend en desarrollo inicial (30%)

---

## 📋 Índice
- [Estado Actual](#-estado-actual)
- [Fase 1: Backend](#fase-1-backend-completo--completada)
- [Fase 2: Frontend](#fase-2-frontend-base--en-progreso-30)
- [Fase 3: Integración](#fase-3-integración--pendiente)
- [Fase 4: IA](#fase-4-inteligencia-artificial--pendiente)
- [Fase 5: Producción](#fase-5-producción--pendiente)

---

## 🎯 Estado Actual

### ✅ Completado (63/128 tareas - 49%)

#### Backend REST API - 100%
- ✅ 9 entidades JPA (Workspaces, Projects, Tasks, Events, ExpenseCategories, Expenses, Budgets, Meals, MealPlans)
- ✅ 9 repositorios con queries personalizadas
- ✅ 6 servicios con lógica de negocio completa
- ✅ 6 controllers REST con validación
- ✅ Manejo de errores global
- ✅ Transacciones configuradas

#### Base de datos PostgreSQL - 100%
- ✅ Docker configurado con PostgreSQL 15.14
- ✅ Flyway migrations (V1 + V2)
- ✅ 9 tablas en `core_schema`
- ✅ Triggers, índices y constraints

#### Frontend Next.js - 30%
- ✅ Proyecto con TypeScript + TailwindCSS
- ✅ React Query + Zustand configurados
- ✅ Landing page diseñada
- ✅ 3 servicios API base

### 🔄 En Progreso
- Servicios API del frontend para nuevos módulos
- Componentes UI

### ⏳ Pendiente
- Dashboard principal
- Autenticación JWT
- Integración IA con Ollama
- Testing
- Deployment

---

## Fase 1: Backend Completo ✅ (Completada)

### Infraestructura ✅
- [x] PostgreSQL local con Docker
- [x] Flyway para migraciones
- [x] Spring Boot 3.5.7 con Java 21
- [x] Perfiles dev/prod

### Migración V1 ✅
- [x] Tabla `workspaces`
- [x] Tabla `projects`
- [x] Tabla `tasks`
- [x] Triggers `updated_at`
- [x] Índices optimizados

### Migración V2 ✅
- [x] Tabla `events` - Calendario
- [x] Tabla `expense_categories` - Categorías
- [x] Tabla `expenses` - Gastos
- [x] Tabla `budgets` - Presupuestos
- [x] Tabla `meals` - Recetas
- [x] Tabla `meal_plans` - Planificación

### Entidades y Repositorios ✅
- [x] 9 entidades JPA con relaciones
- [x] Queries de agregación (SUM expenses)
- [x] Filtros por fecha
- [x] Validación de duplicados
- [x] Queries públicas/privadas (meals)

### Servicios ✅
- [x] EventService - Eventos del calendario
- [x] ExpenseCategoryService - Categorías con validación
- [x] ExpenseService - Tracking con totales
- [x] BudgetService - Presupuestos mensuales
- [x] MealService - Recetas públicas/privadas
- [x] MealPlanService - Planificación por fecha

### Controllers REST ✅
- [x] EventController - CRUD + filtros
- [x] ExpenseCategoryController - CRUD
- [x] ExpenseController - CRUD + `/total`
- [x] BudgetController - CRUD + filtros mes
- [x] MealController - CRUD + filtros tipo
- [x] MealPlanController - CRUD + día/semana

---

## Fase 2: Frontend Base 🔄 (30% completado)

### Estructura ✅
- [x] Next.js 15 + TypeScript
- [x] TailwindCSS v4
- [x] React Query + Zustand
- [x] Axios con interceptores
- [x] Landing page

### Servicios API
- [x] `workspaceService.ts`
- [x] `projectService.ts`
- [x] `taskService.ts`
- [ ] `eventService.ts`
- [ ] `expenseService.ts`
- [ ] `expenseCategoryService.ts`
- [ ] `budgetService.ts`
- [ ] `mealService.ts`
- [ ] `mealPlanService.ts`

### Tipos TypeScript
- [x] Workspace, Project, Task
- [ ] Event, Expense, ExpenseCategory
- [ ] Budget, Meal, MealPlan
- [ ] DTOs completos

### Páginas
- [x] `/` - Landing
- [ ] `/login`
- [ ] `/register`
- [ ] `/dashboard`
- [ ] `/projects`
- [ ] `/calendar`
- [ ] `/expenses`
- [ ] `/meals`

### Componentes UI
- [ ] Sidebar navigation
- [ ] Kanban board (drag & drop)
- [ ] Calendar view (FullCalendar)
- [ ] Expense charts (Chart.js)
- [ ] Budget progress bars
- [ ] Meal planner calendar
- [ ] Recipe cards

---

## Fase 3: Integración ⏳ (Pendiente)

### Autenticación
- [ ] Auth Service con PostgreSQL
- [ ] Endpoints registro/login
- [ ] JWT generation
- [ ] Refresh token
- [ ] Next.js middleware
- [ ] Protected routes

### CRUD Completo
- [ ] Workspaces funcional
- [ ] Projects con workspace
- [ ] Tasks Kanban drag & drop
- [ ] Events calendario
- [ ] Expenses con gráficas
- [ ] Budgets vs actual
- [ ] Meal planning

### Testing
- [ ] Tests servicios (JUnit)
- [ ] Tests integración (MockMvc)
- [ ] Tests componentes (Jest)
- [ ] E2E tests (Playwright)

---

## Fase 4: Inteligencia Artificial ⏳ (Pendiente)

### Setup
- [ ] Instalar Ollama
- [ ] Descargar `llama3.2:3b`
- [ ] Configurar endpoint

### Backend
- [ ] AIController `/api/ai/chat`
- [ ] AIService con Ollama
- [ ] Prompts por módulo:
  - Sugerencias de tareas
  - Optimización calendario
  - Recomendaciones comidas
  - Análisis gastos

### Frontend
- [ ] Chat component
- [ ] Botón flotante asistente
- [ ] Sugerencias contextuales
- [ ] Historial conversaciones

---

## Fase 5: Producción ⏳ (Pendiente)

### Optimizaciones
- [ ] Spring Security
- [ ] CORS producción
- [ ] Rate limiting
- [ ] Logging estructurado
- [ ] Metrics (Actuator)

### Docker
- [ ] Dockerfile backend multi-stage
- [ ] Dockerfile frontend
- [ ] docker-compose producción
- [ ] Volúmenes persistentes

### CI/CD
- [ ] GitHub Actions workflow
- [ ] Tests automáticos
- [ ] Build/deploy automático
- [ ] Environment variables

### Deployment
- [ ] Backend en Railway/Render
- [ ] Frontend en Vercel
- [ ] PostgreSQL en Railway
- [ ] Ollama servidor dedicado

### Documentación
- [ ] API docs (Swagger)
- [ ] README completo
- [ ] Guía contribución
- [ ] Guía instalación

---

## 📊 Progreso Global

| Fase | Completado | Total | % |
|------|------------|-------|---|
| **Fase 1: Backend** | 52 | 52 | **100%** ✅ |
| **Fase 2: Frontend** | 11 | 37 | **30%** 🔄 |
| **Fase 3: Integración** | 0 | 15 | **0%** ⏳ |
| **Fase 4: IA** | 0 | 9 | **0%** ⏳ |
| **Fase 5: Producción** | 0 | 15 | **0%** ⏳ |
| **TOTAL** | **63** | **128** | **49%** |

---

## 🎯 Próximos Pasos

### Inmediatos (Esta semana)
1. Completar servicios API frontend
2. Crear tipos TypeScript faltantes
3. Implementar dashboard básico
4. Configurar autenticación JWT

### Corto plazo (Próximas 2 semanas)
5. Build Kanban board
6. Integrar FullCalendar
7. Crear expense tracker
8. Implementar meal planner UI

### Medio plazo (Mes siguiente)
9. Testing completo
10. Integración Ollama
11. UI polish
12. Deployment alpha

---

## 💡 Stack Tecnológico

### Backend
- **Framework:** Spring Boot 3.5.7
- **Lenguaje:** Java 21
- **Base de datos:** PostgreSQL 15.14
- **Migraciones:** Flyway 11.7.2
- **ORM:** Hibernate/JPA

### Frontend
- **Framework:** Next.js 15
- **Lenguaje:** TypeScript
- **Estilos:** TailwindCSS v4
- **Estado:** Zustand + React Query
- **HTTP:** Axios

### IA (Planeado)
- **Engine:** Ollama
- **Modelo:** llama3.2:3b

### DevOps
- **Contenedores:** Docker
- **CI/CD:** GitHub Actions (planeado)
- **Hosting:** Railway/Vercel (planeado)

---

## 🏗️ Arquitectura

```
Pland-IA/
├── apps/
│   ├── core-service/        # Backend Spring Boot
│   │   ├── src/
│   │   │   ├── controller/  # 6 REST Controllers ✅
│   │   │   ├── service/     # 6 Services ✅
│   │   │   ├── model/       # 9 Entities ✅
│   │   │   ├── repository/  # 9 Repositories ✅
│   │   │   └── dto/         # 8 DTOs ✅
│   │   └── resources/
│   │       └── db/migration # Flyway V1+V2 ✅
│   │
│   └── lifeos-frontend/     # Frontend Next.js
│       ├── src/
│       │   ├── app/         # Pages ✅
│       │   ├── components/  # UI (pendiente)
│       │   ├── services/    # API (parcial)
│       │   ├── store/       # Zustand ✅
│       │   └── types/       # TypeScript (parcial)
│       └── public/
│
├── database/
│   ├── docker-compose.yml   # PostgreSQL ✅
│   └── init-schemas.sql     # Init script ✅
│
└── ROADMAP.md               # Este archivo
```

---

## 📝 Notas de Desarrollo

### Decisiones Técnicas
- **Local-first:** PostgreSQL en Docker para desarrollo rápido
- **Monorepo:** Separación clara backend/frontend
- **API REST:** Patrón RESTful estricto
- **DTOs:** Separación API/dominio
- **Transactions:** A nivel de servicio

### Lecciones Aprendidas
- Flyway migrations deben ser completas desde V1
- Null-safety warnings aceptables en Spring Data
- Service layer valida reglas de negocio
- Controllers delgados, servicios gruesos

### Consideraciones Futuras
- GraphQL para queries complejas
- WebSockets para notificaciones real-time
- PWA para uso offline
- React Native (mismo backend)
