import { apiClient } from '../lib/api';
import type { Budget, BudgetCreateDto, BudgetUpdateDto } from '@/types';

const BASE_URL = '/budgets';

export const budgetService = {
  /**
   * Obtener todos los presupuestos de un usuario
   */
  async getByUserId(userId: string): Promise<Budget[]> {
    const response = await apiClient.get(`${BASE_URL}?userId=${userId}`);
    return response.data;
  },

  /**
   * Obtener presupuestos de un mes específico
   */
  async getByMonth(userId: string, monthYear: string): Promise<Budget[]> {
    const response = await apiClient.get(`${BASE_URL}?userId=${userId}&monthYear=${monthYear}`);
    return response.data;
  },

  /**
   * Obtener un presupuesto por ID
   */
  async getById(id: string): Promise<Budget> {
    const response = await apiClient.get(`${BASE_URL}/${id}`);
    return response.data;
  },

  /**
   * Crear un nuevo presupuesto
   */
  async create(budget: BudgetCreateDto): Promise<Budget> {
    const response = await apiClient.post(BASE_URL, budget);
    return response.data;
  },

  /**
   * Actualizar un presupuesto existente
   */
  async update(id: string, budget: BudgetUpdateDto): Promise<Budget> {
    const response = await apiClient.put(`${BASE_URL}/${id}`, budget);
    return response.data;
  },

  /**
   * Eliminar un presupuesto
   */
  async delete(id: string): Promise<void> {
    await apiClient.delete(`${BASE_URL}/${id}`);
  },
};
