'use client';

import { useEffect, useState } from 'react';

export default function LoadingScreen() {
  const [isLoading, setIsLoading] = useState(true);
  const [hasShownBefore, setHasShownBefore] = useState(false);

  useEffect(() => {
    // Verificar si ya se mostró el loading antes en esta sesión
    const alreadyShown = sessionStorage.getItem('loadingShown');
    
    if (alreadyShown) {
      // Si ya se mostró, no mostrar de nuevo
      setIsLoading(false);
      setHasShownBefore(true);
      return;
    }

    // Primera vez: mostrar loading 2 segundos
    const timer = setTimeout(() => {
      setIsLoading(false);
      sessionStorage.setItem('loadingShown', 'true');
    }, 2000);

    return () => clearTimeout(timer);
  }, []);

  // No mostrar nada si ya se mostró antes
  if (hasShownBefore || !isLoading) return null;

  return (
    <div className="fixed inset-0 z-50 flex flex-col items-center justify-center bg-bg-app dark:bg-bg-dark-app">
      {/* Aquí irá tu animación de murciélago */}
      <div className="mb-8">
        <div className="w-32 h-32 bg-brand-primary/10 rounded-full flex items-center justify-center">
          {/* Placeholder para tu animación */}
          <div className="text-6xl">🦇</div>
        </div>
      </div>
      
      {/* Nombre de la app */}
      <h1 className="text-4xl font-bold font-mono text-brand-primary mb-8">
        BatPlan
      </h1>
      
      {/* Barra de carga */}
      <div className="w-64 h-2 bg-gray-200 dark:bg-gray-700 rounded-full overflow-hidden">
        <div className="h-full bg-brand-primary animate-loading-bar rounded-full"></div>
      </div>
      
      <p className="mt-4 text-text-secondary dark:text-gray-400 text-sm">Cargando tu agenda...</p>
    </div>
  );
}
