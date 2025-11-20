import { apiClient } from '../lib/api';

export interface RegisterRequest {
  email: string;
  password: string;
  name: string;
  rememberMe?: boolean;
}

export interface LoginRequest {
  email: string;
  password: string;
  rememberMe?: boolean;
}

export interface AuthResponse {
  userId: string;
  email: string;
  name: string;
  avatar?: string;
  accessToken: string;
  refreshToken: string;
  expiresIn: number;
}

const AUTH_API_URL = process.env.NEXT_PUBLIC_AUTH_URL || 'http://localhost:5012/api/auth';

export const authService = {
  async register(data: RegisterRequest): Promise<AuthResponse> {
    const response = await apiClient.post(`${AUTH_API_URL}/register`, data);
    return response.data;
  },

  async login(data: LoginRequest): Promise<AuthResponse> {
    const response = await apiClient.post(`${AUTH_API_URL}/login`, data);
    return response.data;
  },

  async refreshToken(refreshToken: string): Promise<AuthResponse> {
    const response = await apiClient.post(`${AUTH_API_URL}/refresh`, { refreshToken });
    return response.data;
  },

  async logout(userId: string): Promise<void> {
    await apiClient.post(`${AUTH_API_URL}/logout/${userId}`);
  },
};
