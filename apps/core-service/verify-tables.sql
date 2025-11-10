-- Verificar si el schema existe
SELECT schema_name 
FROM information_schema.schemata 
WHERE schema_name = 'core_schema';

-- Verificar las tablas en core_schema
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'core_schema';

-- Ver estructura de workspaces si existe
SELECT column_name, data_type, is_nullable
FROM information_schema.columns
WHERE table_schema = 'core_schema' 
  AND table_name = 'workspaces'
ORDER BY ordinal_position;
