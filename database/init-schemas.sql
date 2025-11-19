-- 🔧 Script de inicialización de PostgreSQL
-- Este script se ejecuta automáticamente al crear el contenedor por primera vez

-- Crear schema para Auth Service
CREATE SCHEMA IF NOT EXISTS auth_schema;

-- Crear schema para Core Service
CREATE SCHEMA IF NOT EXISTS core_schema;

-- Dar permisos al usuario plandai_user en ambos schemas
GRANT ALL PRIVILEGES ON SCHEMA auth_schema TO plandai_user;
GRANT ALL PRIVILEGES ON SCHEMA core_schema TO plandai_user;

-- Dar permisos en todas las tablas actuales y futuras
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA auth_schema TO plandai_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA core_schema TO plandai_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA auth_schema TO plandai_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA core_schema TO plandai_user;

-- Configurar permisos por defecto para tablas nuevas
ALTER DEFAULT PRIVILEGES IN SCHEMA auth_schema GRANT ALL ON TABLES TO plandai_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA core_schema GRANT ALL ON TABLES TO plandai_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA auth_schema GRANT ALL ON SEQUENCES TO plandai_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA core_schema GRANT ALL ON SEQUENCES TO plandai_user;

-- Configurar search_path por defecto
ALTER DATABASE plandai_db SET search_path TO auth_schema, core_schema, public;

-- Mostrar schemas creados
\echo '✅ Schemas creados correctamente:'
\dn+
