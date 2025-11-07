-- Flyway Migration V1: Initial Schema for Core Service
-- Description: Creates core_schema and workspaces table
-- Author: Mario Alguacil
-- Date: 2025-11-07

-- Create schema if not exists
CREATE SCHEMA IF NOT EXISTS core_schema;

-- Set search path to core_schema
SET search_path TO core_schema;

-- Table: workspaces
-- Represents user workspaces (project containers)
CREATE TABLE IF NOT EXISTS workspaces (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL CHECK (char_length(name) >= 3),
    description VARCHAR(500),
    user_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Indexes for performance
    CONSTRAINT workspaces_name_not_empty CHECK (trim(name) <> '')
);

-- Index for querying workspaces by user
CREATE INDEX idx_workspaces_user_id ON workspaces(user_id);

-- Index for searching by name
CREATE INDEX idx_workspaces_name ON workspaces(name);

-- Trigger to auto-update updated_at column
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_workspaces_updated_at
    BEFORE UPDATE ON workspaces
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Comments for documentation
COMMENT ON TABLE workspaces IS 'User workspaces - containers for projects and pages';
COMMENT ON COLUMN workspaces.id IS 'Unique workspace identifier (UUID)';
COMMENT ON COLUMN workspaces.name IS 'Workspace display name (3-100 chars)';
COMMENT ON COLUMN workspaces.description IS 'Optional workspace description (max 500 chars)';
COMMENT ON COLUMN workspaces.user_id IS 'Owner user ID (references auth_schema.users)';
COMMENT ON COLUMN workspaces.created_at IS 'Workspace creation timestamp';
COMMENT ON COLUMN workspaces.updated_at IS 'Last update timestamp (auto-updated by trigger)';
