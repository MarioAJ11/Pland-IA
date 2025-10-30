# 🤖 Guía de Configuración de GitHub Copilot

## 📋 Índice
1. [Introducción](#introducción)
2. [Archivos de Contexto](#archivos-de-contexto)
3. [Cómo Proporcionar Contexto Efectivo](#cómo-proporcionar-contexto-efectivo)
4. [Estructura Recomendada](#estructura-recomendada)
5. [Mejores Prácticas](#mejores-prácticas)
6. [Ejemplos Prácticos](#ejemplos-prácticos)

---

## 🎯 Introducción

GitHub Copilot funciona mejor cuando tiene **contexto claro y específico** sobre tu proyecto. Esta guía te enseñará a crear archivos de configuración que me permitirán:

- ✅ Entender la arquitectura de tu proyecto
- ✅ Seguir tus convenciones de código
- ✅ Tomar decisiones técnicas alineadas con tus objetivos
- ✅ Generar código consistente con tu estilo

---

## 📁 Archivos de Contexto

### 1. **PROJECT_CONTEXT.md** (Recomendado)
Archivo principal que describe el proyecto completo.

**Ubicación:** Raíz del proyecto
**Propósito:** Proporcionar una visión general del proyecto

```markdown
# Contexto del Proyecto

## Nombre del Proyecto
[Nombre descriptivo]

## Descripción
[Explicación breve del propósito]

## Tecnologías Principales
- Frontend: [React, Vue, Angular, etc.]
- Backend: [Node.js, Python, Java, etc.]
- Base de Datos: [PostgreSQL, MongoDB, etc.]
- Infraestructura: [AWS, Azure, Docker, etc.]

## Arquitectura
[Diagrama o descripción de la arquitectura]

## Objetivos del Proyecto
1. [Objetivo 1]
2. [Objetivo 2]
3. [Objetivo 3]

## Requisitos Clave
- [Requisito funcional 1]
- [Requisito no funcional 1]
```

---

### 2. **.github/copilot-instructions.md**
Instrucciones específicas para GitHub Copilot (reconocido automáticamente).

**Ubicación:** `.github/copilot-instructions.md`
**Propósito:** Configurar comportamiento y preferencias

```markdown
# Instrucciones para GitHub Copilot

## Stack Tecnológico
- **Frontend:** React con TypeScript
- **Backend:** Node.js con Express
- **Base de Datos:** PostgreSQL
- **Estilos:** Tailwind CSS

## Convenciones de Código

### Nomenclatura
- Componentes: PascalCase (ej: `UserProfile.tsx`)
- Funciones: camelCase (ej: `getUserData()`)
- Constantes: UPPER_SNAKE_CASE (ej: `API_BASE_URL`)
- Archivos: kebab-case (ej: `user-service.ts`)

### Estructura de Archivos
```
src/
├── components/     # Componentes reutilizables
├── pages/          # Páginas/vistas
├── services/       # Lógica de negocio
├── utils/          # Utilidades
├── types/          # Tipos TypeScript
└── constants/      # Constantes
```

### Preferencias de Código
- Usar async/await en lugar de .then()
- Preferir arrow functions para callbacks
- Usar destructuring cuando sea posible
- Comentarios en español
- JSDoc para funciones públicas

## Patrones de Diseño
- Repository Pattern para acceso a datos
- Dependency Injection
- Error handling centralizado

## Prioridades
1. **Seguridad:** Validación de entradas, autenticación
2. **Performance:** Optimización de consultas
3. **Mantenibilidad:** Código limpio y documentado
4. **Testing:** Tests unitarios y de integración
```

---

### 3. **ARCHITECTURE.md**
Documentación detallada de la arquitectura.

```markdown
# Arquitectura del Sistema

## Diagrama de Arquitectura
[Descripción o enlace a diagrama]

## Capas de la Aplicación

### Frontend
- **Framework:** React 18+
- **State Management:** Redux Toolkit
- **Routing:** React Router v6
- **HTTP Client:** Axios

### Backend
- **Framework:** Express.js
- **ORM:** Prisma
- **Autenticación:** JWT
- **Validación:** Zod

### Base de Datos
- **Motor:** PostgreSQL 15+
- **Migraciones:** Prisma Migrate
- **Backup:** Diario automático

## Flujo de Datos
1. Usuario interactúa con UI
2. Frontend envía request a API
3. Backend valida y procesa
4. Base de datos persiste información
5. Respuesta al cliente

## Seguridad
- HTTPS obligatorio
- CORS configurado
- Rate limiting
- Sanitización de inputs
```

---

### 4. **CONVENTIONS.md**
Convenciones específicas del equipo.

```markdown
# Convenciones del Proyecto

## Git Workflow

### Branches
- `main`: Producción
- `develop`: Desarrollo
- `feature/[nombre]`: Nuevas funcionalidades
- `bugfix/[nombre]`: Corrección de bugs
- `hotfix/[nombre]`: Correcciones urgentes

### Commits
Formato: `tipo(scope): mensaje`

Tipos:
- `feat`: Nueva funcionalidad
- `fix`: Corrección de bug
- `docs`: Documentación
- `style`: Formato
- `refactor`: Refactorización
- `test`: Tests
- `chore`: Tareas de mantenimiento

Ejemplo: `feat(auth): implementar login con OAuth`

## Code Review
- Al menos 1 aprobación requerida
- Tests deben pasar
- Sin conflictos de merge

## Testing
- Coverage mínimo: 80%
- Tests unitarios para lógica de negocio
- Tests de integración para APIs
```

---

### 5. **ROADMAP.md**
Plan de desarrollo y prioridades.

```markdown
# Roadmap del Proyecto

## Fase 1: MVP (Semanas 1-4)
- [ ] Configuración inicial del proyecto
- [ ] Sistema de autenticación
- [ ] CRUD básico de usuarios
- [ ] Dashboard principal

## Fase 2: Funcionalidades Core (Semanas 5-8)
- [ ] [Funcionalidad 1]
- [ ] [Funcionalidad 2]
- [ ] [Funcionalidad 3]

## Fase 3: Optimización (Semanas 9-12)
- [ ] Tests completos
- [ ] Optimización de performance
- [ ] Documentación
- [ ] Deploy a producción

## Backlog
- [ ] [Funcionalidad futura 1]
- [ ] [Funcionalidad futura 2]
```

---

## 💡 Cómo Proporcionar Contexto Efectivo

### ✅ DO (Hacer)

1. **Sé específico sobre tecnologías:**
   ```
   ❌ "Crea un backend"
   ✅ "Crea un servidor Express.js con TypeScript que use Prisma para PostgreSQL"
   ```

2. **Proporciona estructura clara:**
   ```
   ✅ "Crea un componente React llamado UserCard en src/components/ que reciba 
       props {name, email, avatar} y use Tailwind para estilos"
   ```

3. **Define requisitos funcionales:**
   ```
   ✅ "Implementa autenticación JWT con refresh tokens, almacena en httpOnly cookies,
       y maneja expiración automática"
   ```

4. **Incluye casos de error:**
   ```
   ✅ "Maneja errores: 400 para validación, 401 para no autorizado, 500 para errores del servidor"
   ```

### ❌ DON'T (No hacer)

1. **No seas vago:**
   ```
   ❌ "Haz algo de backend"
   ❌ "Arregla esto"
   ```

2. **No asumas conocimiento implícito:**
   ```
   ❌ "Usa la configuración normal"
   ✅ "Usa la configuración de Webpack del archivo webpack.config.js"
   ```

3. **No omitas restricciones importantes:**
   ```
   ❌ "Crea una API"
   ✅ "Crea una API REST que limite a 100 requests/minuto por usuario"
   ```

---

## 🏗️ Estructura Recomendada

```
proyecto/
├── .github/
│   └── copilot-instructions.md    # Instrucciones para Copilot
├── docs/
│   ├── PROJECT_CONTEXT.md         # Contexto general
│   ├── ARCHITECTURE.md            # Arquitectura
│   ├── CONVENTIONS.md             # Convenciones
│   ├── ROADMAP.md                 # Plan de desarrollo
│   └── API.md                     # Documentación de API
├── src/
│   └── ... (código fuente)
├── tests/
│   └── ... (tests)
├── README.md                       # Documentación principal
├── package.json                    # Dependencias
└── .env.example                    # Variables de entorno
```

---

## 🎯 Mejores Prácticas

### 1. **Actualiza el Contexto Regularmente**
- Actualiza archivos de contexto cuando cambies tecnologías
- Documenta decisiones arquitectónicas importantes
- Mantén el ROADMAP actualizado

### 2. **Sé Explícito en las Conversaciones**
```
💬 Ejemplo de buena conversación:

Usuario: "Necesito implementar autenticación"

Copilot: "¿Podrías especificar:
1. ¿Qué método? (JWT, OAuth, Session-based)
2. ¿Qué providers? (Google, GitHub, email/password)
3. ¿Dónde almacenar tokens? (localStorage, cookies, memoria)"

Usuario: "JWT con email/password, tokens en httpOnly cookies"

Copilot: "Perfecto, voy a crear:
1. Middleware de autenticación
2. Endpoints /login y /register
3. Service para gestión de tokens
¿Quieres que incluya refresh tokens?"
```

### 3. **Referencia Archivos Existentes**
```
✅ "Sigue el patrón del componente en src/components/UserCard.tsx"
✅ "Usa el mismo estilo de error handling que en src/services/user-service.ts"
```

### 4. **Proporciona Ejemplos**
```
✅ "La respuesta debe ser como:
{
  success: true,
  data: { ... },
  message: 'Operación exitosa'
}"
```

---

## 📝 Ejemplos Prácticos

### Ejemplo 1: Iniciar un Proyecto
```
📨 Mensaje al Copilot:

Voy a iniciar un proyecto full-stack de [descripción]. 

Stack:
- Frontend: React + TypeScript + Tailwind
- Backend: Node.js + Express + Prisma
- DB: PostgreSQL
- Auth: JWT

Por favor:
1. Genera la estructura de carpetas recomendada
2. Crea los archivos de configuración iniciales
3. Configura ESLint y Prettier
4. Crea un README.md base

Convenciones:
- Commits en español
- Código en inglés
- Comentarios en español
```

### Ejemplo 2: Implementar Feature
```
📨 Mensaje al Copilot:

Necesito implementar un sistema de notificaciones en tiempo real.

Requisitos:
- WebSockets con Socket.io
- Notificaciones persisten en PostgreSQL
- Usuario puede marcar como leídas
- Badge con contador en navbar
- Toast cuando llega nueva notificación

Archivos a crear:
- Backend: src/services/notification-service.ts
- Frontend: src/components/NotificationCenter.tsx
- Socket events: notification:new, notification:read

Sigue los patrones de src/services/user-service.ts
```

### Ejemplo 3: Refactorizar Código
```
📨 Mensaje al Copilot:

Necesito refactorizar src/components/UserDashboard.tsx porque está muy grande (500 líneas).

Objetivo:
- Separar en componentes más pequeños
- Extraer lógica a custom hooks
- Mantener la misma funcionalidad

Criterios:
- Componentes < 150 líneas
- Un componente = una responsabilidad
- Props con TypeScript interfaces
- Mantener tests existentes funcionando
```

---

## 🚀 Cómo Empezar

### Paso 1: Crea tu PROJECT_CONTEXT.md
Describe tu proyecto en detalle con la plantilla proporcionada arriba.

### Paso 2: Configura .github/copilot-instructions.md
Define tus preferencias y convenciones de código.

### Paso 3: Primera Conversación
```
"Hola, he creado los archivos PROJECT_CONTEXT.md y copilot-instructions.md 
con toda la información del proyecto. Por favor, léelos y dime si necesitas 
alguna aclaración antes de empezar a desarrollar."
```

### Paso 4: Itera y Mejora
- Ajusta las instrucciones según necesites
- Agrega más documentación específica
- Mantén todo actualizado

---

## 📚 Recursos Adicionales

### Comandos Útiles en Conversación

- **"Lee el archivo X y..."** → Para que analice un archivo específico
- **"Siguiendo el patrón de Y..."** → Para mantener consistencia
- **"Genera la estructura de..."** → Para scaffolding
- **"Explícame cómo funciona..."** → Para entender código existente
- **"Revisa los errores en..."** → Para debugging
- **"Optimiza..."** → Para mejoras de performance
- **"Agrega tests para..."** → Para testing

### Tips de Productividad

1. **Usa archivos de contexto** en lugar de repetir información
2. **Referencias específicas** a archivos y líneas de código
3. **Divide tareas grandes** en pasos más pequeños
4. **Pide explicaciones** cuando no entiendas algo
5. **Revisa y ajusta** el código generado

---

## ⚠️ Limitaciones a Tener en Cuenta

- No puedo acceder a internet o APIs externas
- No puedo ejecutar código por ti (pero puedo generarlo)
- Mi conocimiento tiene una fecha de corte
- Debo trabajar dentro de las limitaciones de VS Code
- No puedo modificar configuraciones del sistema operativo

---

## 🎓 Conclusión

La clave para trabajar eficientemente conmigo es:

1. ✅ **Contexto claro** desde el inicio
2. ✅ **Documentación actualizada** en tu proyecto
3. ✅ **Comunicación específica** en cada petición
4. ✅ **Iteración continua** y feedback

Con esta configuración, podremos trabajar juntos de manera más eficiente y productiva. ¡Estoy listo para ayudarte a construir tu proyecto!

---

**¿Listo para empezar?** 🚀

Crea tus archivos de contexto y comencemos a desarrollar tu proyecto.
