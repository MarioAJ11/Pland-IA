import { apiClient } from '@/lib/api';
import type { Project, ProjectCreateDto } from '@/types';

export const projectService = {
  // Obtener todos los proyectos de un workspace
  async getByWorkspace(workspaceId: string): Promise<Project[]> {
    const response = await apiClient.get<Project[]>('/projects', {
      params: { workspaceId },
    });
    return response.data;
  },

  // Obtener un proyecto por ID
  async getById(id: string): Promise<Project> {
    const response = await apiClient.get<Project>(`/projects/${id}`);
    return response.data;
  },

  // Crear un nuevo proyecto
  async create(data: ProjectCreateDto): Promise<Project> {
    const response = await apiClient.post<Project>('/projects', data);
    return response.data;
  },

  // Actualizar un proyecto
  async update(id: string, data: Partial<ProjectCreateDto>): Promise<Project> {
    const response = await apiClient.put<Project>(`/projects/${id}`, data);
    return response.data;
  },

  // Eliminar un proyecto
  async delete(id: string): Promise<void> {
    await apiClient.delete(`/projects/${id}`);
  },
};
