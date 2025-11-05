# 🍽️ Pland-IA - Planificador Inteligente con Despensa

> Sistema multiplataforma de productividad personal inspirado en Notion, con una funcionalidad única de "Despensa Inteligente" potenciada por IA para planificar comidas y gestionar listas de la compra.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.0+-blue.svg)](https://www.typescriptlang.org/)
[![Node.js](https://img.shields.io/badge/Node.js-18+-green.svg)](https://nodejs.org/)
[![React](https://img.shields.io/badge/React-18+-blue.svg)](https://react.dev/)
[![Flutter](https://img.shields.io/badge/Flutter-3.0+-blue.svg)](https://flutter.dev/)

---

## 📋 Tabla de Contenidos

- [Sobre el Proyecto](#-sobre-el-proyecto)
- [Características](#-características)
- [Stack Tecnológico](#️-stack-tecnológico)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación](#-instalación)
- [Configuración](#️-configuración)
- [Uso](#-uso)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Scripts Disponibles](#-scripts-disponibles)
- [Documentación](#-documentación)
- [Contribuir](#-contribuir)
- [Licencia](#-licencia)

---

## 🎯 Sobre el Proyecto

**Pland-IA** es un **planificador personal tipo Notion, pero SIMPLE y USABLE**. 

### ¿Por qué Pland-IA?

Notion es increíble, pero **demasiado complejo** para el usuario promedio. Pland-IA te da el 80% de la funcionalidad con el 20% de la complejidad:

- 📋 **Organiza toda tu vida:** Workspaces, proyectos, páginas, tareas, notas - TODO en un solo lugar
- ✨ **Simple e intuitivo:** Sin curva de aprendizaje. Crea, organiza y encuentra rápido
- 🎯 **Productividad real:** Enfócate en lo importante sin perderte en configuraciones

### 🍽️ El Plus: Despensa Inteligente con IA

Además de ser tu organizador personal, Pland-IA incluye algo único:

- 🤖 **Genera dietas personalizadas** con OpenAI según tus preferencias y calorías
- 🛒 **Gestiona tu despensa virtual** y crea listas de compra automáticas
- 🍳 **Recibe sugerencias de recetas** según lo que tienes en casa
- 📊 **Optimiza tu alimentación** - El planificador que también cuida tu salud

### En resumen:

**Pland-IA = Notion Simple (80%) + Despensa Inteligente con IA (20%)**

### Objetivos del Proyecto

1. **Desarrollar un producto Full-Stack completo** y multiplataforma para portfolio profesional
2. **Integrar IA (OpenAI)** de forma útil y práctica en la vida diaria
3. **Crear una experiencia unificada** entre Web, Escritorio (Tauri) y Móvil (Flutter)
4. **Dominar tecnologías modernas** en un entorno de Monorepo con TypeScript

---

## ✨ Características

### 📋 Productividad
- ✅ **Workspaces y Proyectos** - Organiza tu contenido como en Notion
- ✅ **Páginas y Tareas** - Crea documentos y gestiona tus tareas
- ✅ **Editor Rico** - Escribe y formatea tus notas con facilidad
- ✅ **Autenticación Segura** - Sistema completo de registro/login con JWT

### 🍽️ Despensa Inteligente
- 🥗 **Generador de Dietas con IA** - Crea planes semanales personalizados según calorías, preferencias y alergias
- 📦 **Gestión de Despensa** - Controla tu inventario de ingredientes en tiempo real
- 🛒 **Lista de Compra Automática** - Genera listas basadas en tus recetas y lo que te falta
- 👨‍🍳 **Sugerencias de Recetas** - IA recomienda qué cocinar con lo que tienes

### 🤖 Inteligencia Artificial
- 🎯 **Dietas Personalizadas** - Generación de planes de comidas con OpenAI GPT-4
- 🔄 **Optimización de Compras** - Calcula exactamente qué comprar para la semana
- � **Sugerencias Contextuales** - Recetas basadas en ingredientes disponibles
- � **Análisis Nutricional** - Seguimiento de calorías y nutrientes (futuro)

### Técnicas
- 🔒 **Seguridad** - Encriptación, validación y protección contra ataques
- 📱 **Responsive** - Diseño adaptado a todos los dispositivos
- ⚡ **Performance** - Optimizado para carga rápida
- ♿ **Accesible** - Cumple con estándares de accesibilidad

---

## 🛠️ Stack Tecnológico

### Arquitectura
- **Monorepo:** `pnpm workspaces` - Gestión centralizada de dependencias
- **Estructura:** `apps/` (ejecutables) + `packages/` (código compartido)

### Frontend Web (`apps/web-desktop`)
- **Framework:** [React](https://react.dev/) 18+ con TypeScript
- **Build Tool:** [Vite](https://vitejs.dev/)
- **UI Library:** [Material-UI (MUI)](https://mui.com/)
- **State Management:** [Zustand](https://zustand-demo.pmnd.rs/)
- **HTTP Client:** [Axios](https://axios-http.com/)
- **Routing:** [React Router](https://reactrouter.com/) v6

### Frontend Escritorio (`apps/web-desktop`)
- **Framework:** [Tauri](https://tauri.app/) - Empaqueta la app React para Windows/Linux
- **Lenguaje Backend:** Rust (gestionado por Tauri)

### Frontend Móvil (`apps/mobile`)
- **Framework:** [Flutter](https://flutter.dev/)
- **Lenguaje:** Dart
- **State Management:** Riverpod o Provider
- **HTTP Client:** `dio` o `http`
- **Plataformas:** Android (iOS potencialmente)

### Backend (Arquitectura de Microservicios)

#### 1. Auth Service (`apps/auth-service`)
- **Lenguaje:** C# 12
- **Framework:** [ASP.NET Core 8](https://learn.microsoft.com/aspnet/core/) Web API
- **ORM:** [Entity Framework Core](https://learn.microsoft.com/ef/core/)
- **Base de Datos:** PostgreSQL (schema: `auth_schema`)
- **Responsabilidad:** Autenticación JWT, Gestión de usuarios
- **Puerto:** 5001

#### 2. Core Service (`apps/core-service`)
- **Lenguaje:** Java 17+
- **Framework:** [Spring Boot 3.x](https://spring.io/projects/spring-boot)
- **ORM:** Spring Data JPA (Hibernate)
- **Base de Datos:** PostgreSQL (schema: `core_schema`)
- **Responsabilidad:** Workspaces, Projects, Pages, Tasks
- **Puerto:** 8080

#### 3. Pantry/IA Service (`apps/pantry-service`)
- **Lenguaje:** Python 3.11+
- **Framework:** [FastAPI](https://fastapi.tiangolo.com/)
- **ORM:** [SQLAlchemy 2.0](https://www.sqlalchemy.org/)
- **Base de Datos:** PostgreSQL (schema: `pantry_schema`)
- **IA:** [OpenAI API](https://platform.openai.com/docs) (GPT-4)
- **Responsabilidad:** Despensa, Dietas con IA, Recetas, Listas de compra
- **Puerto:** 8000
- **Auth:** JWT (Access + Refresh Tokens)

### Base de Datos
- **Motor:** [PostgreSQL](https://www.postgresql.org/) 15+
- **Schemas:** Separados por servicio (`auth_schema`, `core_schema`, `pantry_schema`)
- **Migraciones:** 
  - Entity Framework Migrations (.NET)
  - Flyway/Liquibase (Spring Boot)
  - Alembic (Python FastAPI)
- **Local:** Docker para desarrollo

### Código Compartido (`packages/shared`)
- **Tipos TypeScript:** Interfaces compartidas para Web frontend
- **Utilidades:** Funciones helper reutilizables

### Inteligencia Artificial
- **Provider:** [OpenAI](https://openai.com/) GPT-4 / GPT-3.5
- **SDK:** `openai` oficial (llamado desde Backend)
- **Uso:** Generación de dietas, sugerencias de recetas, listas de compra

### DevOps y Herramientas
- **Package Manager:** [pnpm](https://pnpm.io/) (monorepo)
- **Linting:** ESLint + Prettier
- **Testing:** Jest / Vitest (futuro)
- **CI/CD:** GitHub Actions (futuro)
- **Containerization:** Docker para PostgreSQL

---

## 📦 Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

### Esenciales
- **Node.js** >= 18.0.0 (para Web frontend y tooling)
- **pnpm** >= 8.0.0 (gestor de paquetes del monorepo)
- **Git**
- **Docker Desktop** (para PostgreSQL)

### Backend Services
- **.NET SDK 8** (para Auth Service)
- **Java JDK 17+** (para Core Service - Spring Boot)
- **Python 3.11+** (para Pantry/IA Service)
- **Maven** o **Gradle** (para gestión de dependencias Java)

### API Keys
- **Cuenta API OpenAI** (para funcionalidades de IA)

### Opcionales (según plataforma de desarrollo)
- **Rust + Tauri CLI** (para desarrollo desktop)
- **Flutter SDK** (para desarrollo móvil)
- **Android Studio** (para emulador móvil)

### Verificar instalación
```bash
node --version          # v18.0.0+
pnpm --version          # 8.0.0+
dotnet --version        # 8.x.x
java --version          # 17+
python --version        # 3.11+
git --version
docker --version
```

---

## 🚀 Instalación

### 1. Clonar el repositorio
```bash
git clone https://github.com/MarioAJ11/Pland-IA.git
cd Pland-IA
```

### 2. Instalar dependencias del monorepo
```bash
pnpm install
```

Esto instalará todas las dependencias de `apps/backend`, `apps/web-desktop`, `apps/mobile` y `packages/shared` automáticamente.

### 3. Configurar PostgreSQL con Docker

Inicia PostgreSQL en un contenedor:
```bash
docker run --name pland-ia-postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=mysecretpassword \
  -e POSTGRES_DB=plandia_db \
  -p 5432:5432 \
  -d postgres:15
```

### 4. Configurar variables de entorno

#### Backend
Crea el archivo `apps/backend/.env`:
```bash
cd apps/backend
cp .env.example .env
```

Edita `.env` con tus valores:
```env
# Base de datos
DATABASE_URL="postgresql://postgres:mysecretpassword@localhost:5432/plandia_db"

# JWT
JWT_SECRET=tu_super_secreto_jwt_cambialo
JWT_REFRESH_SECRET=tu_super_secreto_refresh_diferente
JWT_EXPIRES_IN=15m
JWT_REFRESH_EXPIRES_IN=7d

# OpenAI
OPENAI_API_KEY=sk-tu-api-key-aqui

# Server
PORT=3000
NODE_ENV=development
```

#### Frontend (cuando esté creado)
Crea el archivo `apps/web-desktop/.env`:
```env
VITE_API_URL=http://localhost:3000
VITE_APP_NAME=Pland-IA
```

### 5. Inicializar base de datos con Prisma

```bash
cd apps/backend

# Generar Prisma Client
npx prisma generate

# Ejecutar migraciones
npx prisma migrate dev --name init

# (Opcional) Abrir Prisma Studio para ver la BD
npx prisma studio
```

# Generar cliente Prisma
npx prisma generate

# Ejecutar migraciones
npx prisma migrate dev

# (Opcional) Seed data para desarrollo
npx prisma db seed
```

---

## 🎮 Uso

### Desarrollo

#### Iniciar Backend
```bash
cd apps/backend
pnpm dev
```
El servidor estará disponible en `http://localhost:3000`

#### Iniciar Frontend Web
```bash
cd apps/web-desktop
pnpm dev
```
La aplicación estará disponible en `http://localhost:5173`

#### Iniciar App Desktop (Tauri)
```bash
cd apps/web-desktop
pnpm tauri dev
```

#### Iniciar App Móvil (Flutter)
```bash
cd apps/mobile
flutter run
```

---

## 📁 Estructura del Proyecto (Monorepo Microservicios)

```
Pland-IA/                           # Raíz del monorepo
├── .github/                        # Configuración de GitHub
│   ├── copilot-instructions.md    # Instrucciones para GitHub Copilot
│   ├── PLANNING_COMPLETO.md       # 📅 Planning detallado de 12 semanas
│   ├── PROJECT_CONTEXT.md         # Contexto completo del proyecto
│   └── ROADMAP.md                 # Roadmap de desarrollo
│
├── apps/                           # Aplicaciones ejecutables
│   ├── auth-service/              # 🔐 Servicio de Autenticación (.NET 8)
│   │   ├── Controllers/
│   │   ├── Services/
│   │   ├── Models/
│   │   │   ├── Entities/         # Entidades de BD
│   │   │   └── DTOs/             # Data Transfer Objects
│   │   ├── Data/
│   │   │   └── AppDbContext.cs   # Entity Framework DbContext
│   │   ├── Middleware/
│   │   ├── appsettings.json
│   │   └── Program.cs            # Entry point
│   │
│   ├── core-service/              # 📋 Servicio Core (Spring Boot)
│   │   └── src/main/java/com/plandaia/core/
│   │       ├── controller/
│   │       ├── service/
│   │       ├── repository/
│   │       ├── model/
│   │       │   ├── entity/       # JPA Entities
│   │       │   └── dto/          # DTOs
│   │       ├── config/           # Configuración Spring
│   │       ├── exception/        # Exception handlers
│   │       ├── security/         # Security config
│   │       └── CoreServiceApplication.java
│   │
│   ├── pantry-service/            # 🍽️ Servicio Despensa/IA (Python)
│   │   ├── app/
│   │   │   ├── main.py           # FastAPI app
│   │   │   ├── config.py
│   │   │   ├── database.py       # SQLAlchemy setup
│   │   │   ├── models/           # SQLAlchemy models
│   │   │   ├── schemas/          # Pydantic schemas
│   │   │   ├── routers/          # API routes
│   │   │   ├── services/
│   │   │   │   └── ai_service.py # OpenAI integration
│   │   │   └── middleware/
│   │   ├── tests/
│   │   ├── requirements.txt
│   │   └── .env
│   │
│   ├── web-desktop/               # 🌐 Frontend Web (React + Tauri)
│   │   ├── src/
│   │   │   ├── components/
│   │   │   ├── pages/
│   │   │   ├── services/         # API calls a los 3 backends
│   │   │   ├── stores/           # Zustand state
│   │   │   ├── hooks/
│   │   │   ├── types/
│   │   │   └── App.tsx
│   │   ├── src-tauri/            # Tauri (Rust)
│   │   ├── package.json
│   │   └── vite.config.ts
│   │
│   └── mobile/                    # 📱 App Móvil (Flutter)
│       ├── lib/
│       │   ├── screens/
│       │   ├── widgets/
│       │   ├── services/
│       │   ├── providers/        # Riverpod
│       │   └── main.dart
│       └── pubspec.yaml
│
├── packages/                       # Código compartido
│   └── shared/                    # Tipos TypeScript compartidos (opcional)
│       ├── types/
│       └── utils/
│
├── docker/                         # Docker setup
│   ├── docker-compose.yml         # Orquestación de todos los servicios
│   └── postgres-init/             # Scripts de inicialización de BD
│       └── init.sql               # Crear schemas separados
│
├── pnpm-workspace.yaml            # Configuración del monorepo
├── package.json                   # Dependencias raíz
└── README.md                      # Este archivo
```
│   │   ├── middleware/          # Middleware custom
│   │   ├── utils/               # Utilidades
│   │   ├── config/              # Configuraciones
│   │   ├── types/               # TypeScript types
│   │   ├── validators/          # Schemas de validación
│   │   └── index.ts             # Entry point
│   ├── prisma/
│   │   ├── schema.prisma        # Esquema de BD
│   │   ├── migrations/          # Migraciones
│   │   └── seed.ts              # Seed data
│   ├── tests/                   # Tests
│   ├── .env.example
│   ├── tsconfig.json
│   └── package.json
│
├── docs/                        # Documentación
│   ├── PROJECT_CONTEXT.md       # Contexto del proyecto
│   ├── ARCHITECTURE.md          # Arquitectura
│   ├── API.md                   # Documentación API
│   └── CONVENTIONS.md           # Convenciones
│
├── .github/
│   ├── copilot-instructions.md  # Instrucciones para Copilot
│   └── workflows/               # GitHub Actions
│       ├── ci.yml
│       └── deploy.yml
│
├── .gitignore
├── README.md
├── ROADMAP.md
└── LICENSE
```

---

## 🔧 Scripts Disponibles

### Frontend

```bash
npm run dev          # Modo desarrollo
npm run build        # Build para producción
npm run preview      # Preview del build
npm run lint         # Ejecutar ESLint
npm run lint:fix     # Fix errores de linting
## 🔧 Scripts Disponibles

### Root (Monorepo)

```bash
pnpm install             # Instalar todas las dependencias
pnpm --filter backend dev         # Iniciar solo backend
pnpm --filter web-desktop dev     # Iniciar solo frontend web
pnpm run dev:all         # Iniciar todos los proyectos (si está configurado)
```

### Backend

```bash
cd apps/backend

pnpm dev                 # Modo desarrollo con hot reload
pnpm build               # Compilar TypeScript
pnpm start               # Producción (requiere build previo)
pnpm lint                # Ejecutar ESLint
pnpm test                # Ejecutar tests (futuro)

# Prisma
npx prisma generate      # Generar cliente Prisma
npx prisma migrate dev   # Ejecutar migraciones
npx prisma studio        # Abrir Prisma Studio GUI
npx prisma db seed       # Ejecutar seed (futuro)
```

### Frontend Web

```bash
cd apps/web-desktop

pnpm dev                 # Modo desarrollo (Vite)
pnpm build               # Build para producción
pnpm preview             # Preview del build
pnpm lint                # Ejecutar ESLint
pnpm test                # Ejecutar tests (futuro)

# Tauri (Desktop)
pnpm tauri dev           # Modo desarrollo desktop
pnpm tauri build         # Build para Windows/Linux
```

### Mobile (Flutter)

```bash
cd apps/mobile

flutter run              # Ejecutar en emulador/dispositivo
flutter build apk        # Build APK para Android
flutter test             # Ejecutar tests
```

---

## 📚 Documentación

### Documentación del Proyecto
- **[.github/PROJECT_CONTEXT.md](./.github/PROJECT_CONTEXT.md)** - Contexto completo del proyecto
- **[.github/ROADMAP.md](./.github/ROADMAP.md)** - Plan de desarrollo por fases
- **[.github/copilot-instructions.md](./.github/copilot-instructions.md)** - Instrucciones para GitHub Copilot

### Guías de Configuración
- **[.github/GUIA_CONFIGURACION_COPILOT.md](./.github/GUIA_CONFIGURACION_COPILOT.md)** - Cómo configurar Copilot
- **[.github/COMO_EMPEZAR.md](./.github/COMO_EMPEZAR.md)** - Guía de inicio rápido
- **[.github/PLANTILLAS_CONFIGURACION.md](./.github/PLANTILLAS_CONFIGURACION.md)** - Templates de `.env`

### Referencias
- [Prisma Docs](https://www.prisma.io/docs)
- [React Docs](https://react.dev/)
- [Express Docs](https://expressjs.com/)
- [Flutter Docs](https://flutter.dev/docs)
- [Tauri Docs](https://tauri.app/)

---

## 🤝 Contribuir

Este es un proyecto personal de portfolio, pero las sugerencias y feedback son bienvenidos.

### Convenciones de Commits

Seguimos [Conventional Commits](https://www.conventionalcommits.org/):

- `feat:` Nueva funcionalidad
- `fix:` Corrección de bug
- `docs:` Documentación
- `style:` Formato, sin cambios de código
- `refactor:` Refactorización
- `test:` Tests
- `chore:` Mantenimiento

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver [LICENSE](./LICENSE) para detalles.

---

## 👥 Equipo

- **[Tu Nombre]** - Desarrollador Principal - [@tu_usuario](https://github.com/tu_usuario)

---

## 🙏 Agradecimientos

- [OpenAI](https://openai.com/) por la API de IA
- [Vercel](https://vercel.com/) por el hosting
- Comunidad de código abierto

---

## 📞 Contacto

- **Email:** tu_email@ejemplo.com
- **GitHub:** [@tu_usuario](https://github.com/tu_usuario)

---

## 👥 Autor

- **Mario Alguacil Juárez** - Desarrollador Full-Stack
- **GitHub:** [@MarioAJ11](https://github.com/MarioAJ11)
- **Proyecto:** Portfolio personal de desarrollo Full-Stack con IA

---

## 🙏 Agradecimientos

- [OpenAI](https://openai.com/) por la API GPT-4
- [Prisma](https://www.prisma.io/) por el excelente ORM
- [Tauri](https://tauri.app/) por hacer posible apps desktop con React
- [Flutter](https://flutter.dev/) por el framework móvil multiplataforma
- Comunidad de código abierto

---

## 🔗 Enlaces Útiles

- [Documentación del Proyecto](./.github/)
- [Roadmap del Desarrollo](./.github/ROADMAP.md)
- [Contexto del Proyecto](./.github/PROJECT_CONTEXT.md)
- [Reportar un Bug](https://github.com/MarioAJ11/Pland-IA/issues)
- [Solicitar Feature](https://github.com/MarioAJ11/Pland-IA/issues)

---

## 📝 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](./LICENSE) para más detalles.

---

<p align="center">
  <strong>Pland-IA</strong> - Planificador Inteligente con Despensa 🍽️<br>
  Hecho con ❤️ por <a href="https://github.com/MarioAJ11">Mario Alguacil Juárez</a><br>
  <em>Proyecto de Portfolio Full-Stack 2025</em>
</p>
