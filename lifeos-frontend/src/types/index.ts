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
}
