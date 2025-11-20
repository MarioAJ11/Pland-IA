// Tipos que coinciden con tu backend

export interface Workspace {
  id: string;
  name: string;
  description?: string;
  userId: string;
  createdAt: string;
  updatedAt: string;
}

export interface Project {
  id: string;
  name: string;
  description?: string;
  workspaceId: string;
  createdAt: string;
  updatedAt: string;
}

export interface Task {
  id: string;
  title: string;
  description?: string;
  status: 'TO_DO' | 'IN_PROGRESS' | 'DONE';
  priority: 'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT';
  dueDate?: string;
  assignedTo?: string;
  projectId: string;
  createdAt: string;
  updatedAt: string;
}

// DTOs para crear/actualizar (coinciden con tu backend)
export interface WorkspaceCreateDto {
  name: string;
  description?: string;
  userId: string;
}

export interface WorkspaceUpdateDto {
  name?: string;
  description?: string;
}

export interface ProjectCreateDto {
  name: string;
  description?: string;
  workspaceId: string;
}

export interface TaskCreateDto {
  title: string;
  description?: string;
  status: Task['status'];
  priority: Task['priority'];
  dueDate?: string;
  assignedTo?: string;
  projectId: string;
}

// Usuario (simplificado por ahora)
export interface User {
  id: string;
  email: string;
  name?: string;
  avatar?: string;
}

// ============================================
// CALENDARIO - Events
// ============================================
export interface Event {
  id: string;
  title: string;
  description?: string;
  startTime: string;
  endTime: string;
  eventType: 'MEETING' | 'CLASS' | 'APPOINTMENT' | 'REMINDER' | 'DEADLINE' | 'OTHER';
  location?: string;
  isAllDay: boolean;
  userId: string;
  projectId?: string;
  createdAt: string;
  updatedAt: string;
}

export interface EventCreateDto {
  title: string;
  description?: string;
  startTime: string;
  endTime: string;
  eventType: Event['eventType'];
  location?: string;
  isAllDay?: boolean;
  userId: string;
  projectId?: string;
}

// ============================================
// GASTOS - Expense Categories
// ============================================
export interface ExpenseCategory {
  id: string;
  name: string;
  description?: string;
  color: string;
  icon?: string;
  userId: string;
  createdAt: string;
}

export interface ExpenseCategoryCreateDto {
  name: string;
  description?: string;
  color?: string;
  icon?: string;
  userId: string;
}

// ============================================
// GASTOS - Expenses
// ============================================
export interface Expense {
  id: string;
  amount: number;
  description?: string;
  expenseDate: string;
  paymentMethod?: 'CASH' | 'DEBIT_CARD' | 'CREDIT_CARD' | 'TRANSFER' | 'OTHER';
  categoryId?: string;
  category?: ExpenseCategory;
  userId: string;
  isRecurring: boolean;
  recurrencePeriod?: 'DAILY' | 'WEEKLY' | 'MONTHLY' | 'YEARLY';
  createdAt: string;
  updatedAt: string;
}

export interface ExpenseCreateDto {
  amount: number;
  description?: string;
  expenseDate: string;
  paymentMethod?: Expense['paymentMethod'];
  categoryId?: string;
  userId: string;
  isRecurring?: boolean;
  recurrencePeriod?: Expense['recurrencePeriod'];
}

// ============================================
// PRESUPUESTOS - Budgets
// ============================================
export interface Budget {
  id: string;
  categoryId: string;
  category?: ExpenseCategory;
  monthlyLimit: number;
  monthYear: string; // YYYY-MM
  userId: string;
  createdAt: string;
  updatedAt: string;
}

export interface BudgetCreateDto {
  categoryId: string;
  monthlyLimit: number;
  monthYear: string;
  userId: string;
}

export interface BudgetUpdateDto {
  categoryId?: string;
  monthlyLimit?: number;
  monthYear?: string;
}

// ============================================
// COMIDAS - Meals
// ============================================
export interface Meal {
  id: string;
  name: string;
  description?: string;
  mealType?: 'BREAKFAST' | 'LUNCH' | 'DINNER' | 'SNACK';
  calories?: number;
  proteins?: number;
  carbs?: number;
  fats?: number;
  ingredients?: string[];
  instructions?: string;
  prepTimeMinutes?: number;
  servingSize?: number;
  userId?: string; // null = receta pública
  isFavorite: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface MealCreateDto {
  name: string;
  description?: string;
  mealType?: Meal['mealType'];
  calories?: number;
  proteins?: number;
  carbs?: number;
  fats?: number;
  ingredients?: string[];
  instructions?: string;
  prepTimeMinutes?: number;
  servingSize?: number;
  userId?: string;
  isFavorite?: boolean;
}

// ============================================
// PLANIFICACIÓN DE COMIDAS - Meal Plans
// ============================================
export interface MealPlan {
  id: string;
  mealDate: string;
  mealType: 'BREAKFAST' | 'LUNCH' | 'DINNER' | 'SNACK';
  mealId: string;
  meal?: Meal;
  userId: string;
  notes?: string;
  isCompleted: boolean;
  createdAt: string;
}

export interface MealPlanCreateDto {
  mealDate: string;
  mealType: MealPlan['mealType'];
  mealId: string;
  userId: string;
  notes?: string;
  isCompleted?: boolean;
}
