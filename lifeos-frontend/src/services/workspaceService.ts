import { apiClient } from '@/lib/api';
import type { Workspace, WorkspaceCreateDto, WorkspaceUpdateDto } from '@/types';

export const workspaceService = {
  // Obtener todos los workspaces del usuario
  async getAll(userId?: string): Promise<Workspace[]> {
    const params = userId ? { userId } : {};
    const response = await apiClient.get<Workspace[]>('/workspaces', { params });
    return response.data;
  },

  // Obtener un workspace por ID
  async getById(id: string): Promise<Workspace> {
    const response = await apiClient.get<Workspace>(`/workspaces/${id}`);
    return response.data;
  },

  // Crear un nuevo workspace
  async create(data: WorkspaceCreateDto): Promise<Workspace> {
    const response = await apiClient.post<Workspace>('/workspaces', data);
    return response.data;
  },

  // Actualizar un workspace
  async update(id: string, data: WorkspaceUpdateDto): Promise<Workspace> {
    const response = await apiClient.put<Workspace>(`/workspaces/${id}`, data);
    return response.data;
  },

  // Eliminar un workspace
  async delete(id: string): Promise<void> {
    await apiClient.delete(`/workspaces/${id}`);
  },
};
