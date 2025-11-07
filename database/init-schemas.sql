-- 🔧 Script de inicialización de PostgreSQL
-- Este script se ejecuta automáticamente al crear el contenedor por primera vez

-- Crear schema para Auth Service
CREATE SCHEMA IF NOT EXISTS auth_schema;

-- Crear schema para Core Service
CREATE SCHEMA IF NOT EXISTS core_schema;

-- Dar permisos al usuario postgres en ambos schemas
GRANT ALL PRIVILEGES ON SCHEMA auth_schema TO postgres;
GRANT ALL PRIVILEGES ON SCHEMA core_schema TO postgres;

-- Configurar search_path por defecto (opcional)
ALTER DATABASE plandiadb SET search_path TO auth_schema, core_schema, public;

-- Mostrar schemas creados
\echo '✅ Schemas creados correctamente:'
\dn+
