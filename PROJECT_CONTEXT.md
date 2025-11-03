# 📋 Contexto del Proyecto: Planificador IA con Despensa

## 🎯 Información General

### Nombre del Proyecto
**Planificador Inteligente "Pland-IA"**

### Descripción
Un sistema de productividad personal multiplataforma (Web, Escritorio, Móvil) inspirado en Notion, con una funcionalidad única de "Despensa Inteligente". El sistema permite a los usuarios gestionar tareas, notas y proyectos, y además planificar sus comidas basándose en dietas predefinidas o generadas por IA, controlando una despensa virtual para generar listas de la compra.

### Objetivos Principales
1.  **Desarrollar un producto Full-Stack completo** y multiplataforma para portafolio.
2.  **Integrar IA (OpenAI)** para funcionalidades útiles (generación de dietas, sugerencias de recetas, listas de la compra).
3.  **Crear una experiencia de usuario fluida** y unificada entre la web, el escritorio y el móvil.
4.  **Dominar el flujo de trabajo** con TypeScript, React, Node.js, Flutter y Prisma en un entorno Monorepo.

---

## 🛠️ Stack Tecnológico

### Arquitectura
- **Monorepo:** `pnpm workspaces`
- **Comunicación:** API REST (Backend) consumida por todos los frontends.

### Backend (`apps/backend`)
- **Runtime/Framework:** Node.js + Express
- **Lenguaje:** TypeScript
- **Base de Datos:** PostgreSQL
- **ORM/ODM:** Prisma
- **Validación:** Zod (desde `packages/shared`)
- **Auth:** JWT (Access + Refresh Tokens)

### Frontend Web (`apps/web-desktop`)
- **Framework:** React
- **Lenguaje:** TypeScript
- **UI Library:** Material-UI (MUI)
- **State Management:** Zustand
- **Build Tool:** Vite

### Frontend Escritorio (`apps/web-desktop`)
- **Framework:** Tauri (empaquetando la app de React)
- **Plataformas:** Windows, Linux

### Frontend Móvil (`apps/mobile`)
- **Framework:** Flutter
- **Lenguaje:** Dart
- **Plataformas:** Android (y potencialmente iOS)

### Paquetes Compartidos (`packages/shared`)
- **Contenido:** Tipos de TypeScript (interfaces de Usuario, Tarea, Comida, Dieta) y esquemas de validación de Zod.

### Inteligencia Artificial
- **Modelo/API:** OpenAI (GPT-4 / GPT-3.5)
- **Funcionalidades IA:**
  - **Generador de Dietas:** Crea planes de comidas semanales según calorías, preferencias y alérgenos.
  - **Analizador de Despensa:** Basado en la dieta y recetas, sugiere qué comprar.
  - **Asistente de Productividad:** Sugerencias para organizar tareas (futuro).

---

## 🏗️ Arquitectura del Sistema

### Diagrama de Alto Nivel
```

[Cliente Web (React)]
[Cliente Desktop (Tauri+React)]   \<--\> [API REST Backend (Node.js)] \<--\> [BD (PostgreSQL)]
[Cliente Móvil (Flutter)]                ↓  
[Servicio IA (OpenAI)]

```

### Componentes Principales (Entidades de BD)

-   **User:** Autenticación, perfil, preferencias (calorías, alérgenos).
-   **Workspace / Project:** Contenedores (como en Notion).
-   **Page / Task:** Unidades de contenido (documentos, tareas).
-   **Diet:** Plan de comidas (Lunes: Desayuno, Almuerzo...).
-   **Recipe:** Ingredientes e instrucciones.
-   **Ingredient:** Items reutilizables.
-   **Pantry:** Inventario del usuario (Ingrediente + cantidad).
-   **ShoppingList:** Lista de la compra generada.

---

## 🎨 Convenciones de Código

* Seguir las definidas en `.github/copilot-instructions.md`.
* Estructura Monorepo (ver `ROADMAP.md` Fase 1).
```

pland-ia/
├── apps/
│   ├── backend/         \# Node.js API
│   ├── web-desktop/     \# React + Vite + Tauri
│   └── mobile/          \# Flutter
├── packages/
│   ├── shared/          \# Tipos y Zod
│   ├── ui/              \# (Opcional) Componentes React compartidos
│   └── eslint-config/   \# Config ESLint
├── package.json         \# Raíz del Monorepo
└── pnpm-workspace.yaml

```