'use client';

import { useAuthStore } from '@/store/authStore';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import { FolderKanban, Calendar, Wallet, UtensilsCrossed, Cpu, BarChart3 } from 'lucide-react';

export default function DashboardPage() {
  const { user, logout } = useAuthStore();
  const router = useRouter();

  const handleLogout = () => {
    logout();
    router.push('/login');
  };

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4 flex justify-between items-center">
          <h1 className="text-2xl font-bold text-gray-900">Pland-IA Dashboard</h1>
          <div className="flex items-center gap-4">
            <span className="text-gray-700">Hola, {user?.name || user?.email}</span>
            <button
              onClick={handleLogout}
              className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 transition"
            >
              Cerrar Sesión
            </button>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {/* Card: Proyectos */}
          <Link href="/projects">
            <div className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition cursor-pointer">
              <div className="flex items-center justify-between mb-4">
                <h2 className="text-lg font-semibold text-gray-900">Proyectos</h2>
                <FolderKanban className="w-8 h-8 text-indigo-600" />
              </div>
              <p className="text-gray-600 mb-4">Gestiona tus proyectos y tareas</p>
              <div className="w-full py-2 bg-indigo-600 text-white rounded-lg text-center hover:bg-indigo-700 transition">
                Ver Proyectos
              </div>
            </div>
          </Link>

          {/* Card: Calendario */}
          <Link href="/calendar">
            <div className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition cursor-pointer">
              <div className="flex items-center justify-between mb-4">
                <h2 className="text-lg font-semibold text-gray-900">Calendario</h2>
                <Calendar className="w-8 h-8 text-indigo-600" />
              </div>
              <p className="text-gray-600 mb-4">Organiza tus eventos y citas</p>
              <div className="w-full py-2 bg-indigo-600 text-white rounded-lg text-center hover:bg-indigo-700 transition">
                Ver Calendario
              </div>
            </div>
          </Link>

          {/* Card: Gastos */}
          <Link href="/expenses">
            <div className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition cursor-pointer">
              <div className="flex items-center justify-between mb-4">
                <h2 className="text-lg font-semibold text-gray-900">Gastos</h2>
                <Wallet className="w-8 h-8 text-indigo-600" />
              </div>
              <p className="text-gray-600 mb-4">Controla tus finanzas</p>
              <div className="w-full py-2 bg-indigo-600 text-white rounded-lg text-center hover:bg-indigo-700 transition">
                Ver Gastos
              </div>
            </div>
          </Link>

          {/* Card: Comidas */}
          <Link href="/meals">
            <div className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition cursor-pointer">
              <div className="flex items-center justify-between mb-4">
                <h2 className="text-lg font-semibold text-gray-900">Comidas</h2>
                <UtensilsCrossed className="w-8 h-8 text-indigo-600" />
              </div>
              <p className="text-gray-600 mb-4">Planifica tus comidas</p>
              <div className="w-full py-2 bg-indigo-600 text-white rounded-lg text-center hover:bg-indigo-700 transition">
                Ver Comidas
              </div>
            </div>
          </Link>

          {/* Card: IA */}
          <div className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition">
            <div className="flex items-center justify-between mb-4">
              <h2 className="text-lg font-semibold text-gray-900">Asistente IA</h2>
              <Cpu className="w-8 h-8 text-indigo-600" />
            </div>
            <p className="text-gray-600 mb-4">Obtén sugerencias inteligentes</p>
            <button className="w-full py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition">
              Abrir Chat
            </button>
          </div>

          {/* Card: Stats */}
          <div className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition">
            <div className="flex items-center justify-between mb-4">
              <h2 className="text-lg font-semibold text-gray-900">Estadísticas</h2>
              <BarChart3 className="w-8 h-8 text-indigo-600" />
            </div>
            <p className="text-gray-600 mb-4">Visualiza tu progreso</p>
            <button className="w-full py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition">
              Ver Estadísticas
            </button>
          </div>
        </div>

        {/* Welcome Message */}
        <div className="mt-8 bg-indigo-50 border border-indigo-200 rounded-lg p-6">
          <h3 className="text-lg font-semibold text-indigo-900 mb-2">
            ¡Bienvenido a Pland-IA!
          </h3>
          <p className="text-indigo-700">
            Tu plataforma completa para gestionar proyectos, tareas, calendario, gastos y comidas.
            Estamos en fase alpha - más funciones próximamente.
          </p>
        </div>
      </main>
    </div>
  );
}
