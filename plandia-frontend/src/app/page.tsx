'use client';

import Link from 'next/link';
import { LayoutDashboard, Calendar, DollarSign, UtensilsCrossed } from 'lucide-react';
import { useState, useEffect } from 'react';
import BatPixel from '@/components/BatPixel';

export default function Home() {
  const [mounted, setMounted] = useState(false);

  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    <div className="min-h-screen bg-bat-mist">
      {/* Header */}
      <header className="border-b border-bat-cloud bg-white/90 backdrop-blur-sm shadow-bat-soft">
        <div className="container mx-auto px-4 py-4 flex justify-between items-center">
          <div className="flex items-center gap-3">
            {/* Murciélago pixel art logo */}
            <div className="w-12 h-12 flex items-center justify-center bg-bat-purple/10 rounded-lg shadow-bat-button">
              <BatPixel size={2} />
            </div>
            <div>
              <h1 className="text-2xl font-bold bg-gradient-to-r from-bat-purple to-bat-lavender bg-clip-text text-transparent">
                BatPlan
              </h1>
            </div>
          </div>
          <div className="flex gap-4">
            <Link 
              href="/login" 
              className="px-4 py-2 text-bat-purple hover:text-bat-violet font-medium transition-colors"
            >
              Iniciar sesión
            </Link>
            <Link 
              href="/register" 
              className="px-4 py-2 bg-bat-purple text-white rounded-lg hover:bg-bat-violet transition-all shadow-bat-button hover:shadow-bat-glow hover:-translate-y-0.5"
            >
              Registrarse
            </Link>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <section className="container mx-auto px-4 py-20 text-center">
        <div className="animate-bat-float">
          <h2 className="text-4xl md:text-5xl lg:text-6xl font-bold mb-6 bg-gradient-to-r from-bat-purple via-bat-lavender to-moonlight bg-clip-text text-transparent leading-tight">
            Organiza tu vida con inteligencia
          </h2>
        </div>
        <p className="text-lg md:text-xl text-bat-charcoal mb-8 max-w-2xl mx-auto">
          BatPlan combina gestión de proyectos, calendario, gastos y comidas en una sola plataforma. 
          Potenciado por inteligencia artificial.
        </p>
        <Link 
          href="/dashboard" 
          className="inline-block px-8 py-4 bg-bat-purple text-white text-lg rounded-lg hover:bg-bat-violet transition-all shadow-bat-button hover:shadow-bat-glow hover:-translate-y-1"
        >
          Comenzar gratis
        </Link>
      </section>

      {/* Features */}
      <section className="container mx-auto px-4 py-20">
        <h3 className="text-3xl font-bold text-center mb-12 text-bat-purple">
          Características principales
        </h3>
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
          <FeatureCard 
            icon={<LayoutDashboard className="w-8 h-8" />}
            title="Proyectos & Tareas"
            description="Organiza tu trabajo con tableros Kanban, prioridades y fechas límite."
            color="bat-purple"
          />
          <FeatureCard 
            icon={<Calendar className="w-8 h-8" />}
            title="Calendario Inteligente"
            description="Gestiona eventos, citas y horarios con sugerencias de IA."
            color="bat-lavender"
          />
          <FeatureCard 
            icon={<DollarSign className="w-8 h-8" />}
            title="Control de Gastos"
            description="Presupuestos, categorías y análisis automático de finanzas."
            color="twilight-purple"
          />
          <FeatureCard 
            icon={<UtensilsCrossed className="w-8 h-8" />}
            title="Planificador de Comidas"
            description="Recetas, listas de compras y control nutricional optimizado."
            color="night-blue"
          />
          <FeatureCard 
            icon={<BatPixel size={1.5} />}
            title="Asistente IA"
            description="Sugerencias inteligentes y optimización automática de tiempo."
            color="bat-violet"
          />
          <div className="bg-bat-light/50 backdrop-blur-sm rounded-xl p-6 border-2 border-bat-silver hover:border-bat-purple/40 transition-colors">
            <h3 className="text-xl font-semibold mb-2 text-bat-purple">100% Gratis</h3>
            <p className="text-bat-charcoal">
              Sin tarjeta de crédito. Comienza ahora y evoluciona cuando estés listo.
            </p>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="border-t border-bat-cloud bg-white/70 backdrop-blur-sm mt-20">
        <div className="container mx-auto px-4 py-8 text-center text-bat-charcoal">
          <p className="mb-2">&copy; 2025 BatPlan. Organiza tu vida con inteligencia.</p>
          <p className="text-sm text-bat-slate">Potenciado por inteligencia artificial</p>
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
    indigo: 'from-emerald-500 to-emerald-600',
    violet: 'from-teal-500 to-teal-600',
    purple: 'from-green-500 to-green-600',
    pink: 'from-emerald-400 to-emerald-500',
    blue: 'from-cyan-500 to-cyan-600',
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
