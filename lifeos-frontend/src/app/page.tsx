'use client';

import Link from 'next/link';
import { LayoutDashboard, Calendar, DollarSign, UtensilsCrossed, Brain } from 'lucide-react';

export default function Home() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-50 via-white to-violet-50">
      {/* Header */}
      <header className="border-b bg-white/50 backdrop-blur-sm">
        <div className="container mx-auto px-4 py-4 flex justify-between items-center">
          <div className="flex items-center gap-2">
            <Brain className="w-8 h-8 text-indigo-600" />
            <h1 className="text-2xl font-bold bg-gradient-to-r from-indigo-600 to-violet-600 bg-clip-text text-transparent">
              Pland-IA
            </h1>
          </div>
          <div className="flex gap-4">
            <Link 
              href="/login" 
              className="px-4 py-2 text-gray-700 hover:text-indigo-600 transition-colors"
            >
              Iniciar sesión
            </Link>
            <Link 
              href="/register" 
              className="px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition-colors"
            >
              Registrarse
            </Link>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <section className="container mx-auto px-4 py-20 text-center">
        <h2 className="text-4xl md:text-5xl lg:text-6xl font-bold mb-6 bg-gradient-to-r from-indigo-600 via-violet-600 to-purple-600 bg-clip-text text-transparent leading-tight">
          Tu vida, organizada con IA
        </h2>
        <p className="text-lg md:text-xl text-gray-600 mb-8 max-w-2xl mx-auto">
          Gestiona proyectos, tareas, calendario, gastos y comidas en un solo lugar. 
          Potenciado por inteligencia artificial.
        </p>
        <Link 
          href="/dashboard" 
          className="inline-block px-8 py-4 bg-indigo-600 text-white text-lg rounded-lg hover:bg-indigo-700 transition-colors shadow-lg hover:shadow-xl"
        >
          Empezar gratis
        </Link>
      </section>

      {/* Features */}
      <section className="container mx-auto px-4 py-20">
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
          <FeatureCard 
            icon={<LayoutDashboard className="w-8 h-8" />}
            title="Proyectos & Tareas"
            description="Organiza tu trabajo con tableros Kanban, prioridades y fechas de vencimiento."
            color="indigo"
          />
          <FeatureCard 
            icon={<Calendar className="w-8 h-8" />}
            title="Calendario Inteligente"
            description="Gestiona citas, eventos y horarios. La IA optimiza tu tiempo."
            color="violet"
          />
          <FeatureCard 
            icon={<DollarSign className="w-8 h-8" />}
            title="Control de Gastos"
            description="Presupuestos, categorías y análisis automático de tus finanzas."
            color="purple"
          />
          <FeatureCard 
            icon={<UtensilsCrossed className="w-8 h-8" />}
            title="Planificador de Comidas"
            description="Recetas, lista de compras y control nutricional con IA."
            color="pink"
          />
          <FeatureCard 
            icon={<Brain className="w-8 h-8" />}
            title="Asistente IA"
            description="Sugerencias inteligentes para tareas, comidas y optimización de tiempo."
            color="blue"
          />
          <div className="bg-gradient-to-br from-indigo-100 to-violet-100 rounded-xl p-6 border border-indigo-200">
            <h3 className="text-xl font-semibold mb-2">100% Gratis para empezar</h3>
            <p className="text-gray-600">
              Sin tarjeta de crédito. Empieza ahora y actualiza cuando necesites más funciones.
            </p>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="border-t bg-white/50 backdrop-blur-sm mt-20">
        <div className="container mx-auto px-4 py-8 text-center text-gray-600">
          <p>&copy; 2025 Pland-IA. Planifica y gestiona tu vida con inteligencia artificial.</p>
        </div>
      </footer>
    </div>
  );
}

interface FeatureCardProps {
  icon: React.ReactNode;
  title: string;
  description: string;
  color: string;
}

function FeatureCard({ icon, title, description, color }: FeatureCardProps) {
  const colorClasses = {
    indigo: 'from-indigo-500 to-indigo-600',
    violet: 'from-violet-500 to-violet-600',
    purple: 'from-purple-500 to-purple-600',
    pink: 'from-pink-500 to-pink-600',
    blue: 'from-blue-500 to-blue-600',
  };

  return (
    <div className="bg-white rounded-xl p-6 shadow-lg hover:shadow-xl transition-shadow border border-gray-100">
      <div className={`inline-flex p-3 rounded-lg bg-gradient-to-br ${colorClasses[color as keyof typeof colorClasses]} text-white mb-4`}>
        {icon}
      </div>
      <h3 className="text-xl font-semibold mb-2">{title}</h3>
      <p className="text-gray-600">{description}</p>
    </div>
  );
}
