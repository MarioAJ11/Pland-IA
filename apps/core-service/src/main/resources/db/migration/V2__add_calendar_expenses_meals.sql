-- Migración V2: Agregar módulos de Calendario, Gastos y Comidas
-- Fecha: 2025-11-19
-- Descripción: Expansión de Pland-IA con gestión completa de vida

-- ============================================
-- TABLA: events (Calendario y Citas)
-- ============================================
CREATE TABLE IF NOT EXISTS core_schema.events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(200) NOT NULL,
    description TEXT,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    event_type VARCHAR(50) NOT NULL CHECK (event_type IN ('MEETING', 'CLASS', 'APPOINTMENT', 'REMINDER', 'DEADLINE', 'OTHER')),
    location VARCHAR(200),
    is_all_day BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    project_id UUID REFERENCES core_schema.projects(id) ON DELETE SET NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Índices para eventos
CREATE INDEX idx_events_user_id ON core_schema.events(user_id);
CREATE INDEX idx_events_start_time ON core_schema.events(start_time);
CREATE INDEX idx_events_project_id ON core_schema.events(project_id);

-- Trigger para actualizar updated_at
CREATE OR REPLACE FUNCTION core_schema.update_events_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS update_events_updated_at ON core_schema.events;
CREATE TRIGGER update_events_updated_at
    BEFORE UPDATE ON core_schema.events
    FOR EACH ROW
    EXECUTE FUNCTION core_schema.update_events_updated_at();


-- ============================================
-- TABLA: expense_categories (Categorías de gastos)
-- ============================================
CREATE TABLE IF NOT EXISTS core_schema.expense_categories (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    color VARCHAR(20) DEFAULT '#6366f1', -- Color para UI
    icon VARCHAR(50),
    user_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, name)
);

CREATE INDEX idx_expense_categories_user_id ON core_schema.expense_categories(user_id);


-- ============================================
-- TABLA: expenses (Gastos)
-- ============================================
CREATE TABLE IF NOT EXISTS core_schema.expenses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),
    description VARCHAR(500),
    expense_date DATE NOT NULL,
    payment_method VARCHAR(50) CHECK (payment_method IN ('CASH', 'DEBIT_CARD', 'CREDIT_CARD', 'TRANSFER', 'OTHER')),
    category_id UUID REFERENCES core_schema.expense_categories(id) ON DELETE SET NULL,
    user_id UUID NOT NULL,
    is_recurring BOOLEAN DEFAULT FALSE,
    recurrence_period VARCHAR(20) CHECK (recurrence_period IN ('DAILY', 'WEEKLY', 'MONTHLY', 'YEARLY')),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Índices para gastos
CREATE INDEX idx_expenses_user_id ON core_schema.expenses(user_id);
CREATE INDEX idx_expenses_date ON core_schema.expenses(expense_date);
CREATE INDEX idx_expenses_category_id ON core_schema.expenses(category_id);

-- Trigger para actualizar updated_at
DROP TRIGGER IF EXISTS update_expenses_updated_at ON core_schema.expenses;
CREATE TRIGGER update_expenses_updated_at
    BEFORE UPDATE ON core_schema.expenses
    FOR EACH ROW
    EXECUTE FUNCTION core_schema.update_events_updated_at(); -- Reutilizar función


-- ============================================
-- TABLA: budgets (Presupuestos mensuales)
-- ============================================
CREATE TABLE IF NOT EXISTS core_schema.budgets (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    category_id UUID REFERENCES core_schema.expense_categories(id) ON DELETE CASCADE,
    monthly_limit DECIMAL(10,2) NOT NULL CHECK (monthly_limit > 0),
    month_year VARCHAR(7) NOT NULL, -- Formato: 'YYYY-MM'
    user_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, category_id, month_year)
);

CREATE INDEX idx_budgets_user_id ON core_schema.budgets(user_id);
CREATE INDEX idx_budgets_month_year ON core_schema.budgets(month_year);


-- ============================================
-- TABLA: meals (Recetas)
-- ============================================
CREATE TABLE IF NOT EXISTS core_schema.meals (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(200) NOT NULL,
    description TEXT,
    meal_type VARCHAR(20) CHECK (meal_type IN ('BREAKFAST', 'LUNCH', 'DINNER', 'SNACK')),
    calories INT,
    proteins DECIMAL(5,2), -- gramos
    carbs DECIMAL(5,2),    -- gramos
    fats DECIMAL(5,2),     -- gramos
    ingredients TEXT[], -- Array de ingredientes
    instructions TEXT,
    prep_time_minutes INT,
    serving_size INT DEFAULT 1,
    user_id UUID, -- NULL = receta pública, UUID = receta personal
    is_favorite BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Índices para recetas
CREATE INDEX idx_meals_user_id ON core_schema.meals(user_id);
CREATE INDEX idx_meals_type ON core_schema.meals(meal_type);

-- Trigger para actualizar updated_at
DROP TRIGGER IF EXISTS update_meals_updated_at ON core_schema.meals;
CREATE TRIGGER update_meals_updated_at
    BEFORE UPDATE ON core_schema.meals
    FOR EACH ROW
    EXECUTE FUNCTION core_schema.update_events_updated_at();


-- ============================================
-- TABLA: meal_plans (Planificación de comidas)
-- ============================================
CREATE TABLE IF NOT EXISTS core_schema.meal_plans (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    meal_date DATE NOT NULL,
    meal_type VARCHAR(20) NOT NULL CHECK (meal_type IN ('BREAKFAST', 'LUNCH', 'DINNER', 'SNACK')),
    meal_id UUID REFERENCES core_schema.meals(id) ON DELETE CASCADE,
    user_id UUID NOT NULL,
    notes TEXT,
    is_completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, meal_date, meal_type)
);

-- Índices para planificación
CREATE INDEX idx_meal_plans_user_id ON core_schema.meal_plans(user_id);
CREATE INDEX idx_meal_plans_date ON core_schema.meal_plans(meal_date);
CREATE INDEX idx_meal_plans_meal_id ON core_schema.meal_plans(meal_id);


-- ============================================
-- DATOS INICIALES: Categorías de gastos por defecto
-- ============================================
-- Nota: Estas se crearán automáticamente para cada usuario al registrarse
-- Por ahora quedan como plantilla en el código del servicio


-- ============================================
-- COMENTARIOS DE TABLAS
-- ============================================
COMMENT ON TABLE core_schema.events IS 'Eventos del calendario: reuniones, citas, clases, recordatorios';
COMMENT ON TABLE core_schema.expense_categories IS 'Categorías personalizables para clasificar gastos';
COMMENT ON TABLE core_schema.expenses IS 'Registro de todos los gastos del usuario';
COMMENT ON TABLE core_schema.budgets IS 'Presupuestos mensuales por categoría';
COMMENT ON TABLE core_schema.meals IS 'Base de datos de recetas (públicas y personales)';
COMMENT ON TABLE core_schema.meal_plans IS 'Planificación de comidas por fecha';
