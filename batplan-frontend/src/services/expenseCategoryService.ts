import { apiClient } from '../lib/api';
import type { ExpenseCategory, ExpenseCategoryCreateDto } from '@/types';

const BASE_URL = '/expense-categories';

export const expenseCategoryService = {
  /**
   * Obtener todas las categorías de un usuario
   */
  async getByUserId(userId: string): Promise<ExpenseCategory[]> {
    const response = await apiClient.get(`${BASE_URL}?userId=${userId}`);
    return response.data;
  },

  /**
   * Obtener una categoría por ID
   */
  async getById(id: string): Promise<ExpenseCategory> {
    const response = await apiClient.get(`${BASE_URL}/${id}`);
    return response.data;
  },

  /**
   * Crear una nueva categoría
   */
  async create(category: ExpenseCategoryCreateDto): Promise<ExpenseCategory> {
    const response = await apiClient.post(BASE_URL, category);
    return response.data;
  },

  /**
   * Actualizar una categoría existente
   */
  async update(id: string, category: Partial<ExpenseCategoryCreateDto>): Promise<ExpenseCategory> {
    const response = await apiClient.put(`${BASE_URL}/${id}`, category);
    return response.data;
  },

  /**
   * Eliminar una categoría
   */
  async delete(id: string): Promise<void> {
    await apiClient.delete(`${BASE_URL}/${id}`);
  },
};
