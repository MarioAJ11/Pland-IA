-- Script de inicialización para PostgreSQL
-- Crea los schemas separados para cada microservicio

-- ============================================
-- SCHEMAS
-- ============================================

-- Schema para Auth Service (.NET)
CREATE SCHEMA IF NOT EXISTS auth_schema;

-- Schema para Core Service (Spring Boot)
CREATE SCHEMA IF NOT EXISTS core_schema;

-- Schema para Pantry/IA Service (Python)
CREATE SCHEMA IF NOT EXISTS pantry_schema;

-- ============================================
-- EXTENSIONES (si son necesarias)
-- ============================================

-- UUID extension (si usas UUIDs en PostgreSQL)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ============================================
-- PERMISOS (opcional, para mayor seguridad)
-- ============================================

-- Por ahora, el usuario 'postgres' tiene acceso a todos los schemas
-- En producción, deberías crear usuarios específicos por servicio

GRANT ALL PRIVILEGES ON SCHEMA auth_schema TO postgres;
GRANT ALL PRIVILEGES ON SCHEMA core_schema TO postgres;
GRANT ALL PRIVILEGES ON SCHEMA pantry_schema TO postgres;

-- ============================================
-- COMENTARIOS
-- ============================================

COMMENT ON SCHEMA auth_schema IS 'Schema para el Auth Service (.NET) - Gestión de usuarios y autenticación';
COMMENT ON SCHEMA core_schema IS 'Schema para el Core Service (Spring Boot) - Workspaces, Projects, Tasks';
COMMENT ON SCHEMA pantry_schema IS 'Schema para el Pantry/IA Service (Python) - Despensa, Dietas, Recetas';

-- ============================================
-- INFORMACIÓN
-- ============================================

DO $$
BEGIN
    RAISE NOTICE 'Schemas creados exitosamente:';
    RAISE NOTICE '  - auth_schema (Auth Service - .NET)';
    RAISE NOTICE '  - core_schema (Core Service - Spring Boot)';
    RAISE NOTICE '  - pantry_schema (Pantry/IA Service - Python FastAPI)';
END $$;
