# 📋 Contexto del Proyecto: Planificador Inteligente

## 🎯 Información General

### Nombre del Proyecto
**Planificador Inteligente con IA**

### Descripción
[Completa con la descripción de tu proyecto del PDF]

Sistema full-stack que integra inteligencia artificial para...

### Objetivos Principales
1. [Objetivo 1 del PDF]
2. [Objetivo 2 del PDF]
3. [Objetivo 3 del PDF]

---

## 🛠️ Stack Tecnológico

### Frontend
- **Framework:** [React / Vue / Angular / etc.]
- **Lenguaje:** [JavaScript / TypeScript]
- **UI Library:** [Material-UI / Tailwind / Ant Design / etc.]
- **State Management:** [Redux / Zustand / Context API / etc.]
- **Build Tool:** [Vite / Webpack / etc.]

### Backend
- **Runtime/Framework:** [Node.js + Express / Python + FastAPI / etc.]
- **Lenguaje:** [TypeScript / Python / etc.]
- **API Type:** [REST / GraphQL]
- **Validación:** [Zod / Joi / etc.]

### Base de Datos
- **Motor:** [PostgreSQL / MongoDB / MySQL / etc.]
- **ORM/ODM:** [Prisma / TypeORM / Mongoose / etc.]
- **Migraciones:** [Sí/No - Herramienta]

### Inteligencia Artificial
- **Modelo/API:** [OpenAI GPT / Claude / Modelo propio]
- **Biblioteca:** [LangChain / llamar directamente API]
- **Funcionalidades IA:**
  - [Funcionalidad 1]
  - [Funcionalidad 2]
  - [Funcionalidad 3]

### Infraestructura
- **Hosting Frontend:** [Vercel / Netlify / AWS S3 / etc.]
- **Hosting Backend:** [Railway / Render / AWS EC2 / etc.]
- **Base de Datos:** [Supabase / PlanetScale / AWS RDS / etc.]
- **CI/CD:** [GitHub Actions / GitLab CI / etc.]

---

## 🏗️ Arquitectura del Sistema

### Diagrama de Alto Nivel
```
[Cliente Web] <--> [API REST] <--> [Base de Datos]
                      ↓
                 [Servicio IA]
```

### Componentes Principales

#### 1. Frontend
- **Páginas/Vistas:**
  - [Página 1: descripción]
  - [Página 2: descripción]
  - [Página 3: descripción]

- **Componentes Principales:**
  - [Componente 1: función]
  - [Componente 2: función]

#### 2. Backend
- **Endpoints API:**
  - `POST /api/auth/login` - Autenticación
  - `GET /api/users` - [descripción]
  - [Más endpoints...]

- **Servicios:**
  - AuthService - Gestión de autenticación
  - [Servicio 2]
  - [Servicio 3]

#### 3. Base de Datos
- **Tablas/Colecciones:**
  - users - Información de usuarios
  - [Tabla 2]
  - [Tabla 3]

---

## 🎨 Convenciones de Código

### Nomenclatura

#### Archivos
- Componentes React: `PascalCase.tsx` (ej: `UserProfile.tsx`)
- Páginas: `PascalCase.tsx` (ej: `Dashboard.tsx`)
- Servicios: `kebab-case.ts` (ej: `user-service.ts`)
- Utils: `kebab-case.ts` (ej: `date-utils.ts`)
- Tipos: `PascalCase.types.ts` (ej: `User.types.ts`)

#### Variables y Funciones
- Variables: `camelCase` (ej: `userName`, `isActive`)
- Funciones: `camelCase` (ej: `getUserData`, `calculateTotal`)
- Constantes: `UPPER_SNAKE_CASE` (ej: `API_BASE_URL`, `MAX_RETRY_ATTEMPTS`)
- Componentes: `PascalCase` (ej: `UserCard`, `NavigationBar`)
- Interfaces/Types: `PascalCase` con prefijo `I` opcional (ej: `User`, `IUserProps`)

### Estructura de Carpetas
```
proyecto-raiz/
├── frontend/
│   ├── src/
│   │   ├── components/        # Componentes reutilizables
│   │   ├── pages/            # Páginas/vistas
│   │   ├── services/         # Servicios API
│   │   ├── hooks/            # Custom hooks
│   │   ├── context/          # React Context
│   │   ├── utils/            # Utilidades
│   │   ├── types/            # Tipos TypeScript
│   │   ├── constants/        # Constantes
│   │   ├── assets/           # Imágenes, iconos
│   │   └── styles/           # Estilos globales
│   ├── public/
│   └── package.json
├── backend/
│   ├── src/
│   │   ├── controllers/      # Controladores
│   │   ├── services/         # Lógica de negocio
│   │   ├── models/           # Modelos de datos
│   │   ├── routes/           # Rutas API
│   │   ├── middleware/       # Middleware
│   │   ├── utils/            # Utilidades
│   │   ├── config/           # Configuración
│   │   └── types/            # Tipos TypeScript
│   ├── prisma/              # Esquema y migraciones
│   └── package.json
├── docs/                     # Documentación
└── README.md
```

### Preferencias de Código

#### General
- ✅ Usar TypeScript para type safety
- ✅ Preferir `const` sobre `let`, evitar `var`
- ✅ Usar arrow functions para callbacks y funciones pequeñas
- ✅ Destructuring de objetos y arrays cuando sea legible
- ✅ Async/await en lugar de .then()/.catch()
- ✅ Template literals en lugar de concatenación de strings
- ✅ Optional chaining (`?.`) y nullish coalescing (`??`)

#### Comentarios
- **Idioma:** Español
- **Formato:** JSDoc para funciones públicas
- **Ejemplo:**
```typescript
/**
 * Obtiene los datos del usuario por ID
 * @param userId - ID del usuario a buscar
 * @returns Promise con los datos del usuario
 * @throws Error si el usuario no existe
 */
async function getUserById(userId: string): Promise<User> {
  // Implementación...
}
```

#### Manejo de Errores
- Usar try-catch para operaciones async
- Lanzar errores personalizados con mensajes claros
- Logging de errores en servidor
- Mostrar mensajes user-friendly en frontend

---

## 🔒 Seguridad

### Autenticación y Autorización
- **Método:** [JWT / Session / OAuth]
- **Almacenamiento:** [httpOnly cookies / localStorage]
- **Expiración:** [Tiempo de sesión]
- **Refresh Tokens:** [Sí/No]

### Validación de Datos
- Validar en frontend y backend
- Sanitizar inputs
- Usar esquemas de validación (Zod, Joi, etc.)

### API Security
- CORS configurado correctamente
- Rate limiting
- Helmet.js para headers de seguridad
- Variables de entorno para secretos

---

## 🧪 Testing

### Estrategia
- **Unit Tests:** [Jest / Vitest]
- **Integration Tests:** [Supertest / Testing Library]
- **E2E Tests:** [Cypress / Playwright]
- **Coverage Mínimo:** [80%]

### Convenciones de Tests
- Archivos: `*.test.ts` o `*.spec.ts`
- Ubicación: Junto al archivo que prueban o en carpeta `/tests`
- Estructura: Arrange-Act-Assert (AAA)

---

## 📦 Dependencias Principales

### Frontend
```json
{
  "react": "^18.x",
  "[otra-dependencia]": "^version"
}
```

### Backend
```json
{
  "express": "^4.x",
  "[otra-dependencia]": "^version"
}
```

---

## 🚀 Comandos Importantes

### Desarrollo
```bash
# Frontend
cd frontend
npm install
npm run dev

# Backend
cd backend
npm install
npm run dev
```

### Build y Deploy
```bash
# Frontend
npm run build

# Backend
npm run build
```

### Testing
```bash
npm run test
npm run test:coverage
```

---

## 📈 Roadmap y Prioridades

### Fase 1: Setup Inicial (Semana 1)
- [ ] Configuración de repositorio
- [ ] Setup frontend (Vite + React + TS)
- [ ] Setup backend (Node + Express + TS)
- [ ] Configuración de base de datos
- [ ] Sistema de autenticación básico

### Fase 2: Funcionalidades Core (Semanas 2-4)
- [ ] [Funcionalidad principal 1]
- [ ] [Funcionalidad principal 2]
- [ ] [Funcionalidad principal 3]
- [ ] Integración con IA

### Fase 3: Integración IA (Semanas 5-6)
- [ ] Setup de API de IA
- [ ] [Feature IA 1]
- [ ] [Feature IA 2]
- [ ] Optimización de prompts

### Fase 4: Testing y Optimización (Semana 7)
- [ ] Tests unitarios
- [ ] Tests de integración
- [ ] Optimización de performance
- [ ] Corrección de bugs

### Fase 5: Deploy y Documentación (Semana 8)
- [ ] Deploy a producción
- [ ] Documentación completa
- [ ] Guías de usuario
- [ ] Monitoring y logs

---

## 🐛 Debugging y Troubleshooting

### Logs
- **Frontend:** console.log en desarrollo, herramienta de logging en producción
- **Backend:** Winston o similar para logs estructurados
- **Niveles:** error, warn, info, debug

### Error Handling
- Errors centralizados en backend
- Toast notifications en frontend
- Páginas de error personalizadas (404, 500)

---

## 📚 Recursos y Referencias

### Documentación
- [Enlace a documentación oficial de tecnologías]
- [Tutoriales relevantes]
- [Arquitecturas de referencia]

### APIs Externas
- [API de IA: documentación y límites]
- [Otras APIs usadas]

---

## 👥 Equipo y Roles

- **Desarrollador:** [Tu nombre]
- **GitHub Copilot:** Asistente de desarrollo

---

## 📝 Notas Adicionales

### Decisiones Arquitectónicas
1. [Decisión 1: Justificación]
2. [Decisión 2: Justificación]

### Consideraciones Especiales
- [Consideración 1]
- [Consideración 2]

### Próximos Pasos
1. Completar este documento con información específica del proyecto
2. Crear `.github/copilot-instructions.md`
3. Comenzar con la configuración inicial

---

**Última Actualización:** [Fecha]
**Versión:** 1.0
