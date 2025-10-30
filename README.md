# 🤖 Planificador Inteligente con IA

> Sistema full-stack que integra inteligencia artificial para optimizar la planificación y gestión de tareas.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.0+-blue.svg)](https://www.typescriptlang.org/)
[![Node.js](https://img.shields.io/badge/Node.js-18+-green.svg)](https://nodejs.org/)

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

[Descripción detallada del proyecto, su propósito y objetivos principales]

Este proyecto es un planificador inteligente que utiliza inteligencia artificial para:
- 🎯 [Objetivo principal 1]
- 📊 [Objetivo principal 2]
- 🤖 [Objetivo principal 3]

---

## ✨ Características

### Funcionalidades Core
- ✅ **Autenticación Segura** - Sistema completo de registro/login con JWT
- ✅ **[Feature 1]** - Descripción breve
- ✅ **[Feature 2]** - Descripción breve
- ✅ **Dashboard Intuitivo** - Visualización completa de datos

### Inteligencia Artificial
- 🤖 **Sugerencias Inteligentes** - IA que analiza y sugiere optimizaciones
- 📈 **Análisis Predictivo** - Predicciones basadas en patrones
- 💬 **Asistente Virtual** - Chat con IA para ayuda contextual

### Técnicas
- 🔒 **Seguridad** - Encriptación, validación y protección contra ataques
- 📱 **Responsive** - Diseño adaptado a todos los dispositivos
- ⚡ **Performance** - Optimizado para carga rápida
- ♿ **Accesible** - Cumple con estándares de accesibilidad

---

## 🛠️ Stack Tecnológico

### Frontend
- **Framework:** [React](https://react.dev/) 18+ con TypeScript
- **Build Tool:** [Vite](https://vitejs.dev/)
- **UI Library:** [Material-UI](https://mui.com/) / [Tailwind CSS](https://tailwindcss.com/)
- **State Management:** [Redux Toolkit](https://redux-toolkit.js.org/) / [Zustand](https://zustand-demo.pmnd.rs/)
- **HTTP Client:** [Axios](https://axios-http.com/)
- **Routing:** [React Router](https://reactrouter.com/)

### Backend
- **Runtime:** [Node.js](https://nodejs.org/) 18+
- **Framework:** [Express.js](https://expressjs.com/)
- **Language:** TypeScript
- **ORM:** [Prisma](https://www.prisma.io/) / [TypeORM](https://typeorm.io/)
- **Validation:** [Zod](https://zod.dev/)
- **Auth:** JWT (JSON Web Tokens)

### Base de Datos
- **Motor:** [PostgreSQL](https://www.postgresql.org/) / [MongoDB](https://www.mongodb.com/)
- **Cloud:** [Supabase](https://supabase.com/) / [PlanetScale](https://planetscale.com/)

### Inteligencia Artificial
- **Provider:** [OpenAI](https://openai.com/) / [Anthropic Claude](https://www.anthropic.com/)
- **Library:** [LangChain](https://www.langchain.com/) (opcional)

### DevOps y Herramientas
- **Hosting Frontend:** [Vercel](https://vercel.com/) / [Netlify](https://www.netlify.com/)
- **Hosting Backend:** [Railway](https://railway.app/) / [Render](https://render.com/)
- **CI/CD:** [GitHub Actions](https://github.com/features/actions)
- **Testing:** [Jest](https://jestjs.io/) / [Vitest](https://vitest.dev/)
- **E2E:** [Cypress](https://www.cypress.io/) / [Playwright](https://playwright.dev/)

---

## 📦 Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

- **Node.js** >= 18.0.0
- **npm** >= 9.0.0 o **pnpm** >= 8.0.0
- **Git**
- **PostgreSQL** >= 15.0 (si es local) o cuenta en servicio cloud
- **Cuenta API OpenAI** o similar (para funcionalidades IA)

### Verificar instalación
```bash
node --version
npm --version
git --version
```

---

## 🚀 Instalación

### 1. Clonar el repositorio
```bash
git clone https://github.com/TU_USUARIO/proyecto-planificador-ia.git
cd proyecto-planificador-ia
```

### 2. Instalar dependencias

#### Frontend
```bash
cd frontend
npm install
```

#### Backend
```bash
cd backend
npm install
```

### 3. Configurar variables de entorno

#### Frontend
```bash
cd frontend
cp .env.example .env
```

Edita `.env` con tus valores:
```env
VITE_API_URL=http://localhost:3000
VITE_APP_NAME=Planificador IA
```

#### Backend
```bash
cd backend
cp .env.example .env
```

Edita `.env` con tus valores:
```env
# Server
PORT=3000
NODE_ENV=development

# Database
DATABASE_URL=postgresql://usuario:password@localhost:5432/planificador_db

# JWT
JWT_SECRET=tu_super_secreto_jwt_key_aqui
JWT_EXPIRES_IN=7d
JWT_REFRESH_SECRET=tu_refresh_token_secret
JWT_REFRESH_EXPIRES_IN=30d

# OpenAI (o tu provider de IA)
OPENAI_API_KEY=sk-...

# CORS
ALLOWED_ORIGINS=http://localhost:5173,http://localhost:3000
```

### 4. Configurar base de datos

```bash
cd backend

# Generar cliente Prisma
npx prisma generate

# Ejecutar migraciones
npx prisma migrate dev

# (Opcional) Seed data para desarrollo
npx prisma db seed
```

---

## ⚙️ Configuración

### Base de Datos Local (PostgreSQL)

#### Opción 1: Instalación directa
1. Instala PostgreSQL desde [postgresql.org](https://www.postgresql.org/download/)
2. Crea una base de datos:
```sql
CREATE DATABASE planificador_db;
```

#### Opción 2: Docker
```bash
docker run --name postgres-planificador \
  -e POSTGRES_DB=planificador_db \
  -e POSTGRES_USER=usuario \
  -e POSTGRES_PASSWORD=password \
  -p 5432:5432 \
  -d postgres:15
```

### API Keys

#### OpenAI
1. Crea cuenta en [platform.openai.com](https://platform.openai.com)
2. Genera API key en el dashboard
3. Agrega la key a tu `.env`

---

## 🎮 Uso

### Desarrollo

#### Iniciar Backend
```bash
cd backend
npm run dev
```
El servidor estará disponible en `http://localhost:3000`

#### Iniciar Frontend
```bash
cd frontend
npm run dev
```
La aplicación estará disponible en `http://localhost:5173`

### Producción

#### Build Frontend
```bash
cd frontend
npm run build
npm run preview  # Preview del build
```

#### Build Backend
```bash
cd backend
npm run build
npm start
```

---

## 📁 Estructura del Proyecto

```
proyecto-raiz/
├── frontend/                    # Aplicación React
│   ├── public/                  # Assets estáticos
│   ├── src/
│   │   ├── components/          # Componentes React
│   │   │   ├── ui/              # Componentes UI base
│   │   │   ├── layout/          # Layout components
│   │   │   └── features/        # Componentes de features
│   │   ├── pages/               # Páginas/Vistas
│   │   ├── services/            # Servicios API
│   │   ├── hooks/               # Custom React hooks
│   │   ├── context/             # React Context providers
│   │   ├── store/               # Redux store (si aplica)
│   │   ├── utils/               # Utilidades
│   │   ├── types/               # TypeScript types
│   │   ├── constants/           # Constantes
│   │   ├── styles/              # Estilos globales
│   │   ├── App.tsx              # Componente principal
│   │   └── main.tsx             # Entry point
│   ├── .env.example             # Variables de entorno ejemplo
│   ├── vite.config.ts           # Configuración Vite
│   ├── tsconfig.json            # Configuración TypeScript
│   └── package.json
│
├── backend/                     # API Node.js
│   ├── src/
│   │   ├── controllers/         # Controladores de rutas
│   │   ├── services/            # Lógica de negocio
│   │   ├── models/              # Modelos de datos
│   │   ├── routes/              # Definición de rutas
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
npm run type-check   # Verificar tipos TypeScript
npm run test         # Ejecutar tests
npm run test:watch   # Tests en modo watch
npm run test:coverage # Coverage de tests
```

### Backend

```bash
npm run dev          # Modo desarrollo con hot reload
npm run build        # Compilar TypeScript
npm start            # Producción (requiere build previo)
npm run lint         # Ejecutar ESLint
npm run lint:fix     # Fix errores de linting
npm run test         # Ejecutar tests
npm run test:watch   # Tests en modo watch
npm run test:coverage # Coverage de tests

# Prisma
npm run prisma:generate  # Generar cliente Prisma
npm run prisma:migrate   # Ejecutar migraciones
npm run prisma:studio    # Abrir Prisma Studio
npm run prisma:seed      # Ejecutar seed
```

---

## 📚 Documentación

- **[PROJECT_CONTEXT.md](./PROJECT_CONTEXT.md)** - Contexto completo del proyecto
- **[ARCHITECTURE.md](./docs/ARCHITECTURE.md)** - Arquitectura del sistema
- **[ROADMAP.md](./ROADMAP.md)** - Plan de desarrollo
- **[.github/copilot-instructions.md](./.github/copilot-instructions.md)** - Guía para GitHub Copilot
- **[API.md](./docs/API.md)** - Documentación de la API
- **[GUIA_CONFIGURACION_COPILOT.md](./GUIA_CONFIGURACION_COPILOT.md)** - Cómo configurar Copilot

---

## 🧪 Testing

```bash
# Frontend
cd frontend
npm run test              # Tests unitarios
npm run test:coverage     # Con coverage
npm run test:e2e          # Tests E2E (Cypress/Playwright)

# Backend
cd backend
npm run test              # Tests unitarios
npm run test:integration  # Tests de integración
npm run test:coverage     # Con coverage
```

### Coverage mínimo requerido
- Statements: 80%
- Branches: 75%
- Functions: 80%
- Lines: 80%

---

## 🚀 Deploy

### Frontend (Vercel)

1. Conecta tu repositorio en [vercel.com](https://vercel.com)
2. Configura variables de entorno
3. Deploy automático en cada push a `main`

### Backend (Railway/Render)

1. Conecta tu repositorio
2. Configura variables de entorno
3. Configura base de datos
4. Deploy automático

Ver [DEPLOY.md](./docs/DEPLOY.md) para instrucciones detalladas.

---

## 🤝 Contribuir

¡Las contribuciones son bienvenidas! Por favor lee [CONTRIBUTING.md](./CONTRIBUTING.md) para detalles.

### Proceso

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'feat: add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

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
- **LinkedIn:** [Tu Nombre](https://linkedin.com/in/tu-perfil)

---

## 🔗 Enlaces Útiles

- [Documentación del Proyecto](./docs/)
- [Reportar un Bug](https://github.com/tu_usuario/proyecto/issues)
- [Solicitar Feature](https://github.com/tu_usuario/proyecto/issues)
- [Roadmap](./ROADMAP.md)

---

<p align="center">
  Hecho con Mario Alguacil Juárez :)
</p>
