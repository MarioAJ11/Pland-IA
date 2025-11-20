import { apiClient } from '@/lib/api';
import type { Task, TaskCreateDto } from '@/types';

export const taskService = {
  // Obtener todas las tareas de un proyecto
  async getByProject(projectId: string): Promise<Task[]> {
    const response = await apiClient.get<Task[]>('/tasks', {
      params: { projectId },
    });
    return response.data;
  },

  // Obtener una tarea por ID
  async getById(id: string): Promise<Task> {
    const response = await apiClient.get<Task>(`/tasks/${id}`);
    return response.data;
  },

  // Crear una nueva tarea
  async create(data: TaskCreateDto): Promise<Task> {
    const response = await apiClient.post<Task>('/tasks', data);
    return response.data;
  },

  // Actualizar una tarea
  async update(id: string, data: Partial<TaskCreateDto>): Promise<Task> {
    const response = await apiClient.put<Task>(`/tasks/${id}`, data);
    return response.data;
  },

  // Eliminar una tarea
  async delete(id: string): Promise<void> {
    await apiClient.delete(`/tasks/${id}`);
  },

  // Actualizar solo el status de una tarea (para drag & drop)
  async updateStatus(id: string, status: Task['status']): Promise<Task> {
    const response = await apiClient.patch<Task>(`/tasks/${id}/status`, { status });
    return response.data;
  },
};
