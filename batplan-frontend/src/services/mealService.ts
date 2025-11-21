import { apiClient } from '../lib/api';
import type { Meal, MealCreateDto } from '@/types';

const BASE_URL = '/meals';

export const mealService = {
  /**
   * Obtener todas las recetas disponibles (públicas + del usuario)
   */
  async getAvailable(userId: string): Promise<Meal[]> {
    const response = await apiClient.get(`${BASE_URL}?userId=${userId}`);
    return response.data;
  },

  /**
   * Obtener solo recetas públicas
   */
  async getPublic(): Promise<Meal[]> {
    const response = await apiClient.get(`${BASE_URL}?publicOnly=true`);
    return response.data;
  },

  /**
   * Obtener recetas del usuario
   */
  async getUserMeals(userId: string): Promise<Meal[]> {
    const response = await apiClient.get(`${BASE_URL}?userId=${userId}`);
    return response.data;
  },

  /**
   * Obtener recetas por tipo de comida
   */
  async getByType(userId: string, mealType: Meal['mealType']): Promise<Meal[]> {
    const response = await apiClient.get(`${BASE_URL}?userId=${userId}&mealType=${mealType}`);
    return response.data;
  },

  /**
   * Obtener recetas favoritas del usuario
   */
  async getFavorites(userId: string): Promise<Meal[]> {
    const response = await apiClient.get(`${BASE_URL}?userId=${userId}&favoritesOnly=true`);
    return response.data;
  },

  /**
   * Obtener una receta por ID
   */
  async getById(id: string): Promise<Meal> {
    const response = await apiClient.get(`${BASE_URL}/${id}`);
    return response.data;
  },

  /**
   * Crear una nueva receta
   */
  async create(meal: MealCreateDto): Promise<Meal> {
    const response = await apiClient.post(BASE_URL, meal);
    return response.data;
  },

  /**
   * Actualizar una receta existente
   */
  async update(id: string, meal: Partial<MealCreateDto>): Promise<Meal> {
    const response = await apiClient.put(`${BASE_URL}/${id}`, meal);
    return response.data;
  },

  /**
   * Eliminar una receta
   */
  async delete(id: string): Promise<void> {
    await apiClient.delete(`${BASE_URL}/${id}`);
  },

  /**
   * Marcar/desmarcar una receta como favorita
   */
  async toggleFavorite(id: string, isFavorite: boolean): Promise<Meal> {
    const response = await apiClient.put(`${BASE_URL}/${id}`, { isFavorite });
    return response.data;
  },
};
