# Instrucciones para GitHub Copilot

## 🎯 Stack Tecnológico

### Frontend
- **Framework:** [Especificar: React / Vue / Angular]
- **Lenguaje:** TypeScript
- **UI Components:** [Material-UI / Tailwind / Ant Design]
- **State Management:** [Redux Toolkit / Zustand / Context]
- **HTTP Client:** Axios
- **Build Tool:** Vite

### Backend
- **Runtime:** Node.js
- **Framework:** Express.js
- **Lenguaje:** TypeScript
- **ORM:** [Prisma / TypeORM]
- **Validación:** Zod
- **Auth:** JWT

### Base de Datos
- **Motor:** [PostgreSQL / MongoDB / MySQL]
- **Migraciones:** [Prisma Migrate / TypeORM Migrations]

### Inteligencia Artificial
- **Provider:** [OpenAI / Claude / Otro]
- **Biblioteca:** [LangChain / SDK directo]

---

## 📐 Convenciones de Código

### Nomenclatura

#### TypeScript/JavaScript
```typescript
// ✅ Variables y funciones: camelCase
const userName = "Mario";
const getUserData = () => {};

// ✅ Clases y componentes: PascalCase
class UserService {}
const UserCard = () => {};

// ✅ Constantes: UPPER_SNAKE_CASE
const API_BASE_URL = "https://api.example.com";
const MAX_RETRIES = 3;

// ✅ Interfaces y Types: PascalCase
interface User {}
type UserRole = "admin" | "user";

// ✅ Enums: PascalCase
enum UserStatus {
  Active = "ACTIVE",
  Inactive = "INACTIVE"
}

// ✅ Archivos de componentes: PascalCase.tsx
// UserProfile.tsx, Dashboard.tsx

// ✅ Archivos de servicios/utils: kebab-case.ts
// user-service.ts, date-utils.ts
```

### Estructura de Código

#### Imports
```typescript
// 1. Imports de librerías externas
import React, { useState, useEffect } from 'react';
import axios from 'axios';

// 2. Imports internos (absolute paths)
import { UserService } from '@/services/user-service';
import { Button } from '@/components/ui/Button';

// 3. Imports relativos
import { formatDate } from '../utils/date-utils';

// 4. Imports de tipos
import type { User } from '@/types/User.types';

// 5. Imports de estilos
import './styles.css';
```

#### Componentes React
```typescript
/**
 * Descripción del componente
 * @param props - Descripción de las props
 */
interface UserCardProps {
  user: User;
  onEdit?: (id: string) => void;
}

export const UserCard: React.FC<UserCardProps> = ({ user, onEdit }) => {
  // 1. Hooks de estado
  const [isLoading, setIsLoading] = useState(false);
  
  // 2. Hooks de contexto
  const { theme } = useTheme();
  
  // 3. Custom hooks
  const { data, error } = useUserData(user.id);
  
  // 4. Efectos
  useEffect(() => {
    // Lógica del efecto
  }, [user.id]);
  
  // 5. Funciones de manejo
  const handleEdit = () => {
    if (onEdit) {
      onEdit(user.id);
    }
  };
  
  // 6. Early returns
  if (isLoading) return <Spinner />;
  if (error) return <ErrorMessage error={error} />;
  
  // 7. Render
  return (
    <div>
      {/* JSX */}
    </div>
  );
};
```

#### Servicios Backend
```typescript
/**
 * Servicio para gestión de usuarios
 */
export class UserService {
  /**
   * Obtiene un usuario por ID
   * @param id - ID del usuario
   * @returns Usuario encontrado
   * @throws NotFoundError si el usuario no existe
   */
  async getUserById(id: string): Promise<User> {
    try {
      const user = await prisma.user.findUnique({
        where: { id }
      });
      
      if (!user) {
        throw new NotFoundError('Usuario no encontrado');
      }
      
      return user;
    } catch (error) {
      logger.error('Error al obtener usuario', { id, error });
      throw error;
    }
  }
}
```

### Manejo de Errores

#### Frontend
```typescript
try {
  const response = await userService.getUser(id);
  setUser(response.data);
} catch (error) {
  if (axios.isAxiosError(error)) {
    // Manejar errores de API
    toast.error(error.response?.data?.message || 'Error al cargar usuario');
  } else {
    // Otros errores
    toast.error('Error inesperado');
  }
  console.error('Error:', error);
}
```

#### Backend
```typescript
// Middleware de manejo de errores
app.use((err: Error, req: Request, res: Response, next: NextFunction) => {
  logger.error('Error en petición', {
    error: err.message,
    stack: err.stack,
    url: req.url,
    method: req.method
  });
  
  if (err instanceof ValidationError) {
    return res.status(400).json({
      success: false,
      message: err.message,
      errors: err.errors
    });
  }
  
  if (err instanceof NotFoundError) {
    return res.status(404).json({
      success: false,
      message: err.message
    });
  }
  
  // Error genérico
  res.status(500).json({
    success: false,
    message: 'Error interno del servidor'
  });
});
```

### Async/Await
```typescript
// ✅ Preferir async/await
async function fetchUser(id: string) {
  const user = await userService.getUser(id);
  return user;
}

// ❌ Evitar .then()/.catch() cuando sea posible
function fetchUser(id: string) {
  return userService.getUser(id)
    .then(user => user)
    .catch(error => console.error(error));
}
```

### TypeScript

#### Tipado Estricto
```typescript
// ✅ Tipar todo explícitamente
function calculateTotal(items: CartItem[]): number {
  return items.reduce((sum, item) => sum + item.price, 0);
}

// ✅ Usar interfaces para objetos
interface User {
  id: string;
  email: string;
  name: string;
  role: UserRole;
  createdAt: Date;
}

// ✅ Union types para valores específicos
type UserRole = 'admin' | 'user' | 'guest';

// ✅ Utility types
type PartialUser = Partial<User>;
type UserWithoutDates = Omit<User, 'createdAt'>;
type UserCredentials = Pick<User, 'email'>;
```

---

## 🎨 Preferencias de Estilo

### Comentarios
- **Idioma:** Español
- **Formato:** JSDoc para funciones y componentes públicos
- Comentarios inline para lógica compleja
- No comentar lo obvio

### Formato
```typescript
// ✅ Destructuring cuando sea legible
const { id, name, email } = user;

// ✅ Template literals
const message = `Hola ${name}, tu email es ${email}`;

// ✅ Optional chaining
const userName = user?.profile?.name ?? 'Anónimo';

// ✅ Arrow functions para callbacks
const userNames = users.map(user => user.name);

// ✅ Objetos en múltiples líneas si es largo
const user = {
  id: '123',
  name: 'Mario',
  email: 'mario@example.com',
  role: 'admin'
};
```

---

## 🔒 Seguridad

### Validación de Inputs
```typescript
// ✅ Siempre validar datos de entrada
import { z } from 'zod';

const userSchema = z.object({
  email: z.string().email('Email inválido'),
  password: z.string().min(8, 'Mínimo 8 caracteres'),
  name: z.string().min(2).max(100)
});

// Validar antes de procesar
const validatedData = userSchema.parse(req.body);
```

### Autenticación
```typescript
// ✅ Middleware de autenticación
const authMiddleware = async (req: Request, res: Response, next: NextFunction) => {
  try {
    const token = req.cookies.token || req.headers.authorization?.split(' ')[1];
    
    if (!token) {
      throw new UnauthorizedError('Token no proporcionado');
    }
    
    const decoded = jwt.verify(token, process.env.JWT_SECRET!);
    req.user = decoded;
    next();
  } catch (error) {
    next(new UnauthorizedError('Token inválido'));
  }
};
```

---

## 📝 Estructura de Respuestas API

### Formato Estándar
```typescript
// ✅ Respuesta exitosa
{
  success: true,
  data: {
    // Datos solicitados
  },
  message: "Operación exitosa" // Opcional
}

// ✅ Respuesta con error
{
  success: false,
  message: "Mensaje de error user-friendly",
  errors: [ // Opcional, para errores de validación
    {
      field: "email",
      message: "Email inválido"
    }
  ]
}

// ✅ Respuesta con paginación
{
  success: true,
  data: {
    items: [...],
    pagination: {
      page: 1,
      limit: 10,
      total: 100,
      totalPages: 10
    }
  }
}
```

---

## 🧪 Testing

### Convenciones
```typescript
// ✅ Estructura de tests
describe('UserService', () => {
  describe('getUserById', () => {
    it('debería retornar un usuario cuando existe', async () => {
      // Arrange
      const userId = '123';
      const expectedUser = { id: userId, name: 'Test' };
      
      // Act
      const result = await userService.getUserById(userId);
      
      // Assert
      expect(result).toEqual(expectedUser);
    });
    
    it('debería lanzar NotFoundError cuando no existe', async () => {
      // Arrange
      const userId = 'nonexistent';
      
      // Act & Assert
      await expect(userService.getUserById(userId))
        .rejects.toThrow(NotFoundError);
    });
  });
});
```

---

## 🚀 Patrones de Diseño

### Repository Pattern
```typescript
// ✅ Separar lógica de acceso a datos
class UserRepository {
  async findById(id: string): Promise<User | null> {
    return prisma.user.findUnique({ where: { id } });
  }
}

class UserService {
  constructor(private userRepo: UserRepository) {}
  
  async getUser(id: string): Promise<User> {
    const user = await this.userRepo.findById(id);
    if (!user) throw new NotFoundError();
    return user;
  }
}
```

### Dependency Injection
```typescript
// ✅ Inyectar dependencias
class UserController {
  constructor(
    private userService: UserService,
    private logger: Logger
  ) {}
  
  async getUser(req: Request, res: Response) {
    // Lógica del controlador
  }
}
```

---

## 📌 Prioridades

1. **Seguridad** - Validar y sanitizar siempre
2. **Tipado** - TypeScript estricto en todo
3. **Legibilidad** - Código claro y mantenible
4. **Performance** - Optimizar consultas y renders
5. **Testing** - Tests para lógica crítica
6. **Documentación** - Comentar lo necesario

---

## ⚡ Performance

### Frontend
```typescript
// ✅ Usar React.memo para componentes que no cambian
export const UserCard = React.memo<UserCardProps>(({ user }) => {
  // ...
});

// ✅ useMemo para cálculos costosos
const sortedUsers = useMemo(() => {
  return users.sort((a, b) => a.name.localeCompare(b.name));
}, [users]);

// ✅ useCallback para funciones pasadas como props
const handleClick = useCallback(() => {
  doSomething(id);
}, [id]);
```

### Backend
```typescript
// ✅ Paginación siempre
async function getUsers(page = 1, limit = 10) {
  return prisma.user.findMany({
    skip: (page - 1) * limit,
    take: limit
  });
}

// ✅ Seleccionar solo campos necesarios
async function getUsers() {
  return prisma.user.findMany({
    select: {
      id: true,
      name: true,
      email: true
      // No incluir campos innecesarios
    }
  });
}
```

---

## 📂 Cuando Crear Archivos Nuevos

### Componentes
- Crear componente nuevo cuando tenga lógica reutilizable
- Archivos < 300 líneas idealmente
- Un componente principal por archivo

### Servicios
- Agrupar lógica relacionada
- Un servicio por entidad/dominio
- Métodos claros y bien documentados

### Utils
- Funciones puras y reutilizables
- Agrupar por categoría (date-utils, string-utils)

---

**Última actualización:** [Fecha]
**Versión:** 1.0
