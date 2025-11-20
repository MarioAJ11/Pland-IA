import { apiClient } from '../lib/api';
import type { Expense, ExpenseCreateDto } from '@/types';

const BASE_URL = '/expenses';

export const expenseService = {
  /**
   * Obtener todos los gastos de un usuario
   */
  async getByUserId(userId: string): Promise<Expense[]> {
    const response = await apiClient.get(`${BASE_URL}?userId=${userId}`);
    return response.data;
  },

  /**
   * Obtener gastos por rango de fechas
   */
  async getByDateRange(userId: string, startDate: string, endDate: string): Promise<Expense[]> {
    const response = await apiClient.get(
      `${BASE_URL}?userId=${userId}&startDate=${startDate}&endDate=${endDate}`
    );
    return response.data;
  },

  /**
   * Obtener gastos por categoría
   */
  async getByCategory(userId: string, categoryId: string): Promise<Expense[]> {
    const response = await apiClient.get(
      `${BASE_URL}?userId=${userId}&categoryId=${categoryId}`
    );
    return response.data;
  },

  /**
   * Obtener total de gastos por rango de fechas
   */
  async getTotalByDateRange(userId: string, startDate: string, endDate: string): Promise<{ total: number }> {
    const response = await apiClient.get(
      `${BASE_URL}/total?userId=${userId}&startDate=${startDate}&endDate=${endDate}`
    );
    return response.data;
  },

  /**
   * Obtener total de gastos por categoría y rango de fechas
   */
  async getTotalByCategory(
    userId: string,
    categoryId: string,
    startDate: string,
    endDate: string
  ): Promise<{ total: number }> {
    const response = await apiClient.get(
      `${BASE_URL}/total?userId=${userId}&categoryId=${categoryId}&startDate=${startDate}&endDate=${endDate}`
    );
    return response.data;
  },

  /**
   * Obtener un gasto por ID
   */
  async getById(id: string): Promise<Expense> {
    const response = await apiClient.get(`${BASE_URL}/${id}`);
    return response.data;
  },

  /**
   * Crear un nuevo gasto
   */
  async create(expense: ExpenseCreateDto): Promise<Expense> {
    const response = await apiClient.post(BASE_URL, expense);
    return response.data;
  },

  /**
   * Actualizar un gasto existente
   */
  async update(id: string, expense: Partial<ExpenseCreateDto>): Promise<Expense> {
    const response = await apiClient.put(`${BASE_URL}/${id}`, expense);
    return response.data;
  },

  /**
   * Eliminar un gasto
   */
  async delete(id: string): Promise<void> {
    await apiClient.delete(`${BASE_URL}/${id}`);
  },
};
