'use client';

import Link from 'next/link';
import Image from 'next/image';
import { LayoutDashboard, Calendar, DollarSign, UtensilsCrossed } from 'lucide-react';

export default function Home() {
  return (
    <div className="min-h-screen bg-bg-app dark:bg-bg-dark-app">
      {/* Header */}
      <header className="border-b border-gray-200 dark:border-gray-800 bg-bg-surface dark:bg-bg-dark-surface backdrop-blur-sm shadow-sm">
        <div className="container mx-auto px-4 py-4 flex justify-between items-center">
          <div className="flex items-center gap-3">
            <Image
              src="/batplan-icon.png"
              alt="BatPlan"
              width={48}
              height={48}
            />
            <h1 className="text-2xl font-bold font-mono text-brand-primary dark:text-brand-primary">
              BatPlan
            </h1>
          </div>
          <div className="flex gap-4">
            <Link 
              href="/login" 
              className="px-4 py-2 text-brand-primary dark:text-brand-primary hover:text-text-primary dark:hover:text-text-inverse font-medium transition-colors"
            >
              Iniciar sesión
            </Link>
            <Link 
              href="/register" 
              className="px-4 py-2 bg-brand-primary dark:bg-brand-primary text-white rounded-lg hover:opacity-90 transition-all shadow-button"
            >
              Registrarse
            </Link>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <section className="container mx-auto px-4 py-20 text-center">
        <h2 className="text-4xl md:text-5xl lg:text-6xl font-bold mb-6 text-text-primary dark:text-text-inverse leading-tight">
          Organiza tu vida con <span className="text-brand-primary dark:text-brand-primary">inteligencia</span>
        </h2>
        <p className="text-lg md:text-xl text-text-secondary dark:text-gray-400 mb-8 max-w-2xl mx-auto">
          BatPlan combina gestión de proyectos, calendario, gastos y comidas en una sola plataforma. 
          Potenciado por inteligencia artificial.
        </p>
        <Link 
          href="/dashboard" 
          className="inline-block px-8 py-4 bg-brand-primary dark:bg-brand-primary text-white text-lg rounded-lg hover:opacity-90 transition-all shadow-button"
        >
          Comenzar gratis
        </Link>
      </section>

      {/* Features */}
      <section className="container mx-auto px-4 py-20">
        <h3 className="text-3xl font-bold text-center mb-12 text-text-primary dark:text-text-inverse">
          Características principales
        </h3>
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div className="bg-bg-surface dark:bg-bg-dark-surface rounded-xl p-6 shadow-card hover:shadow-card-hover transition-all border border-gray-200 dark:border-gray-700">
            <div className="w-12 h-12 bg-brand-primary/10 dark:bg-brand-primary/20 rounded-lg flex items-center justify-center mb-4">
              <LayoutDashboard className="w-6 h-6 text-brand-primary" />
            </div>
            <h3 className="text-xl font-semibold mb-2 text-text-primary dark:text-text-inverse">Proyectos & Tareas</h3>
            <p className="text-text-secondary dark:text-gray-400">Organiza tu trabajo con tableros Kanban, prioridades y fechas límite.</p>
          </div>
          
          <div className="bg-bg-surface dark:bg-bg-dark-surface rounded-xl p-6 shadow-card hover:shadow-card-hover transition-all border border-gray-200 dark:border-gray-700">
            <div className="w-12 h-12 bg-brand-primary/10 dark:bg-brand-primary/20 rounded-lg flex items-center justify-center mb-4">
              <Calendar className="w-6 h-6 text-brand-primary" />
            </div>
            <h3 className="text-xl font-semibold mb-2 text-text-primary dark:text-text-inverse">Calendario Inteligente</h3>
            <p className="text-text-secondary dark:text-gray-400">Gestiona eventos, citas y horarios con sugerencias de IA.</p>
          </div>
          
          <div className="bg-bg-surface dark:bg-bg-dark-surface rounded-xl p-6 shadow-card hover:shadow-card-hover transition-all border border-gray-200 dark:border-gray-700">
            <div className="w-12 h-12 bg-brand-primary/10 dark:bg-brand-primary/20 rounded-lg flex items-center justify-center mb-4">
              <DollarSign className="w-6 h-6 text-brand-primary" />
            </div>
            <h3 className="text-xl font-semibold mb-2 text-text-primary dark:text-text-inverse">Control de Gastos</h3>
            <p className="text-text-secondary dark:text-gray-400">Presupuestos, categorías y análisis automático de finanzas.</p>
          </div>
          
          <div className="bg-bg-surface dark:bg-bg-dark-surface rounded-xl p-6 shadow-card hover:shadow-card-hover transition-all border border-gray-200 dark:border-gray-700">
            <div className="w-12 h-12 bg-brand-primary/10 dark:bg-brand-primary/20 rounded-lg flex items-center justify-center mb-4">
              <UtensilsCrossed className="w-6 h-6 text-brand-primary" />
            </div>
            <h3 className="text-xl font-semibold mb-2 text-text-primary dark:text-text-inverse">Planificador de Comidas</h3>
            <p className="text-text-secondary dark:text-gray-400">Recetas, listas de compras y control nutricional optimizado.</p>
          </div>
          
          <div className="bg-bg-surface dark:bg-bg-dark-surface rounded-xl p-6 shadow-card hover:shadow-card-hover transition-all border border-gray-200 dark:border-gray-700">
            <div className="w-12 h-12 bg-brand-primary/10 dark:bg-brand-primary/20 rounded-lg flex items-center justify-center mb-4">
              <Image src="/batplan-icon.png" alt="IA" width={24} height={24} />
            </div>
            <h3 className="text-xl font-semibold mb-2 text-text-primary dark:text-text-inverse">Asistente IA</h3>
            <p className="text-text-secondary dark:text-gray-400">Sugerencias inteligentes y optimización automática de tiempo.</p>
          </div>
          
          <div className="bg-bg-app dark:bg-bg-dark-app rounded-xl p-6 border-2 border-brand-primary hover:border-opacity-80 transition-all">
            <h3 className="text-xl font-semibold mb-2 text-brand-primary">100% Gratis</h3>
            <p className="text-text-primary dark:text-text-inverse">
              Sin tarjeta de crédito. Comienza ahora y evoluciona cuando estés listo.
            </p>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="border-t border-gray-200 dark:border-gray-800 bg-bg-surface dark:bg-bg-dark-surface backdrop-blur-sm mt-20">
        <div className="container mx-auto px-4 py-8 text-center text-text-primary dark:text-text-inverse">
          <p className="mb-2">&copy; 2025 BatPlan. Organiza tu vida con inteligencia.</p>
          <p className="text-sm text-text-secondary dark:text-gray-400">Potenciado por inteligencia artificial</p>
        </div>
      </footer>
    </div>
  );
}
