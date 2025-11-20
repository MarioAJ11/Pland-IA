import { apiClient } from '../lib/api';
import type { Event, EventCreateDto } from '@/types';

const BASE_URL = '/events';

export const eventService = {
  /**
   * Obtener todos los eventos de un usuario
   */
  async getByUserId(userId: string): Promise<Event[]> {
    const response = await apiClient.get(`${BASE_URL}?userId=${userId}`);
    return response.data;
  },

  /**
   * Obtener eventos por rango de fechas
   */
  async getByDateRange(userId: string, startTime: string, endTime: string): Promise<Event[]> {
    const response = await apiClient.get(
      `${BASE_URL}?userId=${userId}&startTime=${startTime}&endTime=${endTime}`
    );
    return response.data;
  },

  /**
   * Obtener eventos de un proyecto específico
   */
  async getByProjectId(projectId: string): Promise<Event[]> {
    const response = await apiClient.get(`${BASE_URL}?projectId=${projectId}`);
    return response.data;
  },

  /**
   * Obtener un evento por ID
   */
  async getById(id: string): Promise<Event> {
    const response = await apiClient.get(`${BASE_URL}/${id}`);
    return response.data;
  },

  /**
   * Crear un nuevo evento
   */
  async create(event: EventCreateDto): Promise<Event> {
    const response = await apiClient.post(BASE_URL, event);
    return response.data;
  },

  /**
   * Actualizar un evento existente
   */
  async update(id: string, event: Partial<EventCreateDto>): Promise<Event> {
    const response = await apiClient.put(`${BASE_URL}/${id}`, event);
    return response.data;
  },

  /**
   * Eliminar un evento
   */
  async delete(id: string): Promise<void> {
    await apiClient.delete(`${BASE_URL}/${id}`);
  },
};
