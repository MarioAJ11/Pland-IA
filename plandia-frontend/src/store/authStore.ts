import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import type { User } from '@/types';

interface AuthState {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
  login: (user: User, token: string) => void;
  logout: () => void;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      user: null,
      token: null,
      isAuthenticated: false,

      login: (user, token) => {
        // Guardar en localStorage para persistencia
        localStorage.setItem('auth_token', token);
        
        // Guardar en cookies para middleware (7 días)
        document.cookie = `accessToken=${token}; path=/; max-age=${7 * 24 * 60 * 60}`;
        
        set({ user, token, isAuthenticated: true });
      },

      logout: () => {
        localStorage.removeItem('auth_token');
        
        // Eliminar cookie
        document.cookie = 'accessToken=; path=/; max-age=0';
        
        set({ user: null, token: null, isAuthenticated: false });
      },
    }),
    {
      name: 'auth-storage',
    }
  )
);
