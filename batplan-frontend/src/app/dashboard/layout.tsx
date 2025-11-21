'use client';

import Navigation from '@/components/Navigation';
import Image from 'next/image';

export default function DashboardLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="min-h-screen bg-bg-app dark:bg-bg-dark-app">
        {/* Header con logo */}
        <header className="bg-bg-surface dark:bg-bg-dark-surface border-b border-gray-200 dark:border-gray-800 shadow-sm px-4 py-3 md:mb-16">
          <div className="container mx-auto flex items-center justify-between">
            <div className="flex items-center gap-3">
              <Image
                src="/batplan-icon.png"
                alt="BatPlan"
                width={40}
                height={40}
              />
              <h1 className="text-2xl font-bold font-mono text-brand-primary">
                BatPlan
              </h1>
            </div>
            
            <div className="flex items-center gap-4">
              <button className="p-2 hover:bg-bat-purple/10 rounded-lg transition-colors">
                <svg className="w-5 h-5 text-bat-slate" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
                </svg>
              </button>
              <div className="w-8 h-8 bg-bat-purple/20 rounded-full flex items-center justify-center">
                <span className="text-sm font-bold text-bat-purple">M</span>
              </div>
            </div>
          </div>
        </header>

        {/* Navegación */}
        <Navigation />

        {/* Contenido principal */}
        <main className="pb-20 md:pt-0 md:pb-8">
          {children}
        </main>
      </div>
  );
}
