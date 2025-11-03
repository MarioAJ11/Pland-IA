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

**Pland-IA** es un sistema de productividad personal full-stack y multiplataforma que combina las capacidades de un gestor de tareas tipo Notion con una funcionalidad única: una **Despensa Inteligente** potenciada por IA.

### ¿Qué hace diferente a Pland-IA?

Este proyecto te permite:
- 📝 **Gestionar tareas, notas y proyectos** como en Notion
- 🍽️ **Planificar tus comidas semanales** con dietas generadas por IA
- 🛒 **Controlar tu despensa virtual** y generar listas de la compra automáticas
- 🤖 **Recibir sugerencias de recetas** basadas en lo que tienes disponible
- 📊 **Optimizar tu alimentación** según tus preferencias, calorías y alergias

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

### Backend (`apps/backend`)
- **Runtime:** [Node.js](https://nodejs.org/) 18+
- **Framework:** [Express.js](https://expressjs.com/)
- **Lenguaje:** TypeScript
- **ORM:** [Prisma](https://www.prisma.io/)
- **Validation:** [Zod](https://zod.dev/) (desde `packages/shared`)
- **Auth:** JWT (Access + Refresh Tokens)

### Base de Datos
- **Motor:** [PostgreSQL](https://www.postgresql.org/) 15+
- **Migraciones:** Prisma Migrate
- **Local:** Docker para desarrollo

### Código Compartido (`packages/shared`)
- **Tipos TypeScript:** Interfaces de User, Task, Diet, Recipe, etc.
- **Validación:** Esquemas Zod compartidos entre frontend y backend
- **Utilidades:** Funciones reutilizables

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
- **Node.js** >= 18.0.0
- **pnpm** >= 8.0.0 (gestor de paquetes del monorepo)
- **Git**
- **Docker Desktop** (para PostgreSQL)
- **Cuenta API OpenAI** (para funcionalidades de IA)

### Opcionales (según plataforma)
- **Rust + Tauri CLI** (para desarrollo desktop)
- **Flutter SDK** (para desarrollo móvil)
- **Android Studio** (para emulador móvil)

### Verificar instalación
```bash
node --version          # v18.0.0+
pnpm --version          # 8.0.0+
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

## 📁 Estructura del Proyecto (Monorepo)

```
Pland-IA/
├── apps/
│   ├── backend/                 # API Node.js + Express
│   │   ├── src/
│   │   │   ├── controllers/     # Controladores de rutas
│   │   │   ├── services/        # Lógica de negocio
│   │   │   ├── routes/          # Definición de rutas
│   │   │   ├── middleware/      # Middleware custom
│   │   │   ├── utils/           # Utilidades
│   │   │   ├── config/          # Configuraciones
│   │   │   ├── types/           # TypeScript types
│   │   │   ├── validators/      # Schemas de validación
│   │   │   └── index.ts         # Entry point
│   │   ├── prisma/
│   │   │   ├── schema.prisma    # Esquema de BD
│   │   │   ├── migrations/      # Migraciones
│   │   │   └── seed.ts          # Seed data
│   │   ├── tests/               # Tests
│   │   ├── .env                 # Variables de entorno
│   │   └── package.json
│   │
│   ├── web-desktop/             # Frontend React + Tauri
│   │   ├── src/
│   │   │   ├── components/      # Componentes React
│   │   │   │   ├── ui/          # Componentes UI base
│   │   │   │   ├── layout/      # Layouts
│   │   │   │   └── features/    # Features específicas
│   │   │   ├── pages/           # Páginas/Rutas
│   │   │   ├── services/        # Servicios API
│   │   │   ├── hooks/           # Custom hooks
│   │   │   ├── store/           # Zustand store
│   │   │   ├── utils/           # Utilidades
│   │   │   ├── types/           # TypeScript types
│   │   │   ├── App.tsx
│   │   │   └── main.tsx
│   │   ├── src-tauri/           # Código Rust de Tauri
│   │   ├── public/              # Assets estáticos
│   │   └── package.json
│   │
│   └── mobile/                  # App Flutter
│       ├── lib/
│       │   ├── models/          # Modelos de datos
│       │   ├── services/        # Servicios API
│       │   ├── providers/       # State management
│       │   ├── screens/         # Pantallas
│       │   ├── widgets/         # Widgets reutilizables
│       │   └── main.dart
│       ├── android/             # Config Android
│       ├── ios/                 # Config iOS
│       └── pubspec.yaml
│
├── packages/
│   └── shared/                  # Código compartido
│       ├── src/
│       │   ├── types/           # Interfaces TypeScript
│       │   ├── validators/      # Esquemas Zod
│       │   └── utils/           # Utilidades compartidas
│       └── package.json
│
├── .github/
│   ├── copilot-instructions.md  # Instrucciones para Copilot
│   ├── PROJECT_CONTEXT.md       # Contexto del proyecto
│   └── ROADMAP.md               # Plan de desarrollo
│
├── pnpm-workspace.yaml          # Configuración del monorepo
├── package.json                 # Root package.json
└── README.md                    # Este archivo
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
