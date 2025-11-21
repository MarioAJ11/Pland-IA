'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { authService } from '@/services/authService';
import { useAuthStore } from '@/store/authStore';
import Link from 'next/link';

export default function RegisterPage() {
  const router = useRouter();
  const login = useAuthStore((state) => state.login);
  
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
    rememberMe: false,
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    // Validaciones
    if (formData.password !== formData.confirmPassword) {
      setError('Las contraseñas no coinciden');
      return;
    }

    if (formData.password.length < 6) {
      setError('La contraseña debe tener al menos 6 caracteres');
      return;
    }

    setLoading(true);

    try {
      const response = await authService.register({
        name: formData.name,
        email: formData.email,
        password: formData.password,
        rememberMe: formData.rememberMe,
      });
      
      // Guardar en Zustand y localStorage
      login(
        {
          id: response.userId,
          email: response.email,
          name: response.name,
          avatar: response.avatar,
        },
        response.accessToken
      );

      // Redirigir al dashboard
      router.push('/dashboard');
    } catch (err: any) {
      console.error('Error completo:', err);
      console.error('Respuesta del servidor:', err.response);
      setError(err.response?.data?.message || err.message || 'Error al registrar usuario');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-bg-app dark:bg-bg-dark-app py-12">
      <div className="max-w-md w-full bg-bg-surface dark:bg-bg-dark-surface rounded-2xl shadow-xl p-8">
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold font-mono text-brand-primary dark:text-brand-primary">BatPlan</h1>
          <p className="text-text-secondary dark:text-text-dark-secondary mt-2">Crea tu cuenta gratis</p>
        </div>

        {error && (
          <div className="mb-4 p-3 bg-red-50 border border-red-200 text-red-700 rounded-lg text-sm">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-5">
          <div>
            <label htmlFor="name" className="block text-sm font-medium text-text-primary dark:text-text-dark-primary mb-2">
              Nombre completo
            </label>
            <input
              id="name"
              type="text"
              required
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-brand-primary focus:border-transparent outline-none transition text-text-primary dark:text-text-dark-primary placeholder:text-text-secondary dark:placeholder:text-text-dark-secondary bg-bg-surface dark:bg-bg-dark-surface"
              placeholder="Juan Pérez"
            />
          </div>

          <div>
            <label htmlFor="email" className="block text-sm font-medium text-text-primary dark:text-text-dark-primary mb-2">
              Email
            </label>
            <input
              id="email"
              type="email"
              required
              value={formData.email}
              onChange={(e) => setFormData({ ...formData, email: e.target.value })}
              className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-brand-primary focus:border-transparent outline-none transition text-text-primary dark:text-text-dark-primary placeholder:text-text-secondary dark:placeholder:text-text-dark-secondary bg-bg-surface dark:bg-bg-dark-surface"
              placeholder="tu@email.com"
            />
          </div>

          <div>
            <label htmlFor="password" className="block text-sm font-medium text-text-primary dark:text-text-dark-primary mb-2">
              Contraseña
            </label>
            <input
              id="password"
              type="password"
              required
              value={formData.password}
              onChange={(e) => setFormData({ ...formData, password: e.target.value })}
              className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-brand-primary focus:border-transparent outline-none transition text-text-primary dark:text-text-dark-primary placeholder:text-text-secondary dark:placeholder:text-text-dark-secondary bg-bg-surface dark:bg-bg-dark-surface"
              placeholder="Mínimo 6 caracteres"
            />
          </div>

          <div>
            <label htmlFor="confirmPassword" className="block text-sm font-medium text-text-primary dark:text-text-dark-primary mb-2">
              Confirmar contraseña
            </label>
            <input
              id="confirmPassword"
              type="password"
              required
              value={formData.confirmPassword}
              onChange={(e) => setFormData({ ...formData, confirmPassword: e.target.value })}
              className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-brand-primary focus:border-transparent outline-none transition text-text-primary dark:text-text-dark-primary placeholder:text-text-secondary dark:placeholder:text-text-dark-secondary bg-bg-surface dark:bg-bg-dark-surface"
              placeholder="Repite tu contraseña"
            />
          </div>

          <div className="flex items-center">
            <input
              type="checkbox"
              id="rememberMe"
              checked={formData.rememberMe}
              onChange={(e) => setFormData({ ...formData, rememberMe: e.target.checked })}
              className="w-4 h-4 text-brand-primary border-gray-300 dark:border-gray-600 rounded focus:ring-brand-primary"
            />
            <label htmlFor="rememberMe" className="ml-2 text-sm text-text-primary dark:text-text-dark-primary">
              Mantener sesión iniciada
            </label>
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full bg-brand-primary text-text-inverse py-3 rounded-lg font-medium hover:bg-brand-primary/90 disabled:opacity-50 disabled:cursor-not-allowed transition"
          >
            {loading ? 'Creando cuenta...' : 'Crear Cuenta'}
          </button>
        </form>

        <p className="mt-6 text-center text-sm text-text-secondary dark:text-text-dark-secondary">
          ¿Ya tienes cuenta?{' '}
          <Link href="/login" className="text-brand-primary hover:text-brand-primary/80 font-medium">
            Inicia sesión
          </Link>
        </p>

        <p className="mt-4 text-center text-xs text-text-secondary dark:text-text-dark-secondary">
          Al registrarte, aceptas nuestros{' '}
          <Link href="/terms" className="text-brand-primary hover:underline">
            Términos de Servicio
          </Link>
        </p>
      </div>
    </div>
  );
}
