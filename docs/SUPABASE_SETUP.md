# 🗄️ Supabase Configuration Guide

## ✅ Estado Actual

- ✅ Proyecto Supabase creado: `Pland-IA`
- ✅ Base de datos PostgreSQL configurada (región: Europa)
- ✅ Schema `core_schema` creado
- ✅ Tablas migradas: `workspaces`, `projects`, `tasks`
- ✅ Flyway migration ejecutada: `V1__initial_schema.sql`

## 📊 Detalles de Conexión

### Connection String (Producción)
```
jdbc:postgresql://db.zrrueidbplgrrjtvqxbv.supabase.co:5432/postgres
```

### Credenciales
- **Host**: `db.zrrueidbplgrrjtvqxbv.supabase.co`
- **Port**: `5432`
- **Database**: `postgres`
- **Username**: `postgres`
- **Password**: `8gYViVGg9cQag0Rt` ⚠️ (Ver nota de seguridad abajo)
- **Schema**: `core_schema`

### Project URL (Frontend)
```
https://zrrueidbplgrrjtvqxbv.supabase.co
```

---

## 🚀 Uso en Desarrollo

### 1. Ejecutar Core Service con Supabase

```bash
cd apps/core-service
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

O usando variables de entorno:
```bash
export SPRING_PROFILES_ACTIVE=prod
./mvnw spring-boot:run
```

### 2. Verificar Conexión

```bash
# Test endpoint
curl http://localhost:8080/api/workspaces
```

---

## 🔄 Migraciones Flyway

### Ejecutar nuevas migraciones en Supabase

Cuando crees un nuevo archivo de migración (ej: `V2__add_users_table.sql`):

```bash
cd apps/core-service
./mvnw flyway:migrate -Dflyway.configFiles=flyway-supabase.conf
```

### Ver estado de migraciones

```bash
./mvnw flyway:info -Dflyway.configFiles=flyway-supabase.conf
```

### Validar migraciones

```bash
./mvnw flyway:validate -Dflyway.configFiles=flyway-supabase.conf
```

---

## 🌐 Deployment en Producción

### GitHub Actions (CI/CD)

Configura estos **secrets** en tu repositorio:

1. Ve a: `Settings → Secrets and variables → Actions`
2. Agrega los siguientes secrets:

| Secret Name | Value |
|------------|-------|
| `SUPABASE_DB_URL` | `jdbc:postgresql://db.zrrueidbplgrrjtvqxbv.supabase.co:5432/postgres` |
| `SUPABASE_DB_USERNAME` | `postgres` |
| `SUPABASE_DB_PASSWORD` | `8gYViVGg9cQag0Rt` |
| `JWT_SECRET` | `tu-secret-jwt-minimo-32-caracteres` |

### Dockerfile (Producción)

```dockerfile
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/core-service-*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build y run:
```bash
docker build -t pland-ia-core .
docker run -e SPRING_PROFILES_ACTIVE=prod -p 8080:8080 pland-ia-core
```

---

## 🔍 Acceso a Supabase Dashboard

### SQL Editor
1. Ve a: https://supabase.com/dashboard/project/zrrueidbplgrrjtvqxbv
2. Click en **"SQL Editor"**
3. Ejecuta queries directamente:

```sql
-- Ver todas las workspaces
SELECT * FROM core_schema.workspaces;

-- Ver proyectos de un workspace
SELECT * FROM core_schema.projects WHERE workspace_id = 'uuid-aqui';

-- Ver tareas
SELECT * FROM core_schema.tasks;
```

### Table Editor
1. Click en **"Table Editor"**
2. Selecciona schema: `core_schema`
3. Edita datos visualmente (útil para testing)

### Database Settings
- **Connection pooling**: Ya configurado (max 10 conexiones)
- **Backups**: Automáticos cada 24h (plan Free)
- **Extensions**: Habilitadas (pgcrypto, uuid-ossp)

---

## ⚠️ Seguridad - IMPORTANTE

### 🔐 Proteger Credenciales

**NUNCA commitear credenciales al repositorio**

1. **Archivo `.env`** (local):
   ```bash
   # Crear .env desde .env.example
   cp .env.example .env
   
   # Editar .env con tus credenciales reales
   # Este archivo está en .gitignore
   ```

2. **Rotar contraseñas**:
   - Ve a: Project Settings → Database → Database password
   - Click en "Reset database password"
   - Actualiza `application-prod.yml` y secrets

3. **Variables de entorno**:
   ```bash
   # Usar en producción
   export SUPABASE_DB_PASSWORD="8gYViVGg9cQag0Rt"
   
   # O en application-prod.yml:
   spring:
     datasource:
       password: ${SUPABASE_DB_PASSWORD}
   ```

### 🛡️ Configuración de Seguridad Supabase

1. **Row Level Security (RLS)**:
   - Por defecto, todas las tablas están protegidas
   - Para desarrollo: Deshabilitar RLS en tablas de `core_schema`
   - Para producción: Configurar políticas RLS

2. **API Keys**:
   - **anon key**: Solo para frontend (lectura pública)
   - **service_role key**: Backend (acceso total) ⚠️ Nunca exponer

3. **Connection Pooling**:
   - Límite: 10 conexiones simultáneas (plan Free)
   - Usar pooling en aplicación (HikariCP ya configurado)

---

## 📈 Monitoreo

### Logs en Supabase
1. Ve a: **Logs** en el dashboard
2. Filtra por:
   - Database queries
   - Errores de conexión
   - Queries lentas

### Métricas
- **Database size**: 500MB límite (plan Free)
- **Bandwidth**: 5GB/mes
- **API requests**: Unlimited

Comando para ver tamaño actual:
```sql
SELECT 
    schemaname,
    tablename,
    pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename)) AS size
FROM pg_tables
WHERE schemaname = 'core_schema'
ORDER BY pg_total_relation_size(schemaname||'.'||tablename) DESC;
```

---

## 🔄 Rollback (Si algo sale mal)

### Flyway Rollback
```bash
# Ver historial
./mvnw flyway:info -Dflyway.configFiles=flyway-supabase.conf

# NO hay rollback automático en Flyway Community
# Debes crear migración manual V{n+1}__rollback_feature.sql
```

### Backup Manual
```bash
# Exportar datos
pg_dump -h db.zrrueidbplgrrjtvqxbv.supabase.co \
        -U postgres \
        -d postgres \
        -n core_schema \
        -f backup_$(date +%Y%m%d).sql

# Restaurar
psql -h db.zrrueidbplgrrjtvqxbv.supabase.co \
     -U postgres \
     -d postgres \
     -f backup_20251110.sql
```

---

## 📚 Recursos Adicionales

- **Supabase Docs**: https://supabase.com/docs
- **Flyway Docs**: https://flywaydb.org/documentation
- **PostgreSQL Docs**: https://www.postgresql.org/docs/

---

## ✅ Checklist de Configuración

- [x] Proyecto Supabase creado
- [x] Schema `core_schema` creado
- [x] Migraciones ejecutadas
- [x] `application-prod.yml` configurado
- [ ] GitHub Actions secrets configurados
- [ ] Variables de entorno en producción
- [ ] RLS policies configuradas (opcional)
- [ ] Monitoring setup
- [ ] Backup strategy definida

---

**Última actualización**: 10 de noviembre de 2025
**Versión de schema**: v1 (initial_schema)
