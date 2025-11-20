import { apiClient } from '../lib/api';
import type { MealPlan, MealPlanCreateDto } from '@/types';

const BASE_URL = '/meal-plans';

export const mealPlanService = {
  /**
   * Obtener planes de comida por rango de fechas
   */
  async getByDateRange(userId: string, startDate: string, endDate: string): Promise<MealPlan[]> {
    const response = await apiClient.get(
      `${BASE_URL}?userId=${userId}&startDate=${startDate}&endDate=${endDate}`
    );
    return response.data;
  },

  /**
   * Obtener planes de un día específico
   */
  async getByDate(userId: string, date: string): Promise<MealPlan[]> {
    const response = await apiClient.get(`${BASE_URL}?userId=${userId}&date=${date}`);
    return response.data;
  },

  /**
   * Obtener planes de la semana actual
   */
  async getCurrentWeek(userId: string): Promise<MealPlan[]> {
    const response = await apiClient.get(`${BASE_URL}?userId=${userId}`);
    return response.data;
  },

  /**
   * Obtener un plan de comida por ID
   */
  async getById(id: string): Promise<MealPlan> {
    const response = await apiClient.get(`${BASE_URL}/${id}`);
    return response.data;
  },

  /**
   * Crear un nuevo plan de comida
   */
  async create(mealPlan: MealPlanCreateDto): Promise<MealPlan> {
    const response = await apiClient.post(BASE_URL, mealPlan);
    return response.data;
  },

  /**
   * Actualizar un plan de comida existente
   */
  async update(id: string, mealPlan: Partial<MealPlanCreateDto>): Promise<MealPlan> {
    const response = await apiClient.put(`${BASE_URL}/${id}`, mealPlan);
    return response.data;
  },

  /**
   * Marcar un plan como completado
   */
  async markCompleted(id: string): Promise<MealPlan> {
    const response = await apiClient.put(`${BASE_URL}/${id}`, { isCompleted: true });
    return response.data;
  },

  /**
   * Eliminar un plan de comida
   */
  async delete(id: string): Promise<void> {
    await apiClient.delete(`${BASE_URL}/${id}`);
  },
};
