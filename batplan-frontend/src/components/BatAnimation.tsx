'use client';

import { useEffect, useRef } from 'react';

export default function BatAnimation() {
  const canvasRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    // Animación de murciélago con CSS puro (smooth y profesional)
    if (canvasRef.current) {
      canvasRef.current.style.animation = 'batFly 3s ease-in-out infinite';
    }
  }, []);

  return (
    <div ref={canvasRef} className="relative w-32 h-32">
      {/* Cuerpo del murciélago */}
      <svg 
        viewBox="0 0 200 200" 
        className="w-full h-full"
        style={{
          filter: 'drop-shadow(0 4px 8px rgba(76, 78, 92, 0.3))'
        }}
      >
        {/* Ala izquierda */}
        <path
          d="M 60 100 Q 20 60, 40 30 Q 50 40, 60 50 L 70 80 Z"
          fill="#4C4E5C"
          className="animate-[wing-left_2s_ease-in-out_infinite]"
          style={{
            transformOrigin: '70px 80px',
          }}
        />
        
        {/* Cuerpo central */}
        <ellipse
          cx="100"
          cy="100"
          rx="15"
          ry="25"
          fill="#212121"
        />
        
        {/* Cabeza */}
        <circle
          cx="100"
          cy="85"
          r="12"
          fill="#212121"
        />
        
        {/* Orejas */}
        <path
          d="M 95 75 L 90 65 L 95 70 Z"
          fill="#4C4E5C"
        />
        <path
          d="M 105 75 L 110 65 L 105 70 Z"
          fill="#4C4E5C"
        />
        
        {/* Ala derecha */}
        <path
          d="M 140 100 Q 180 60, 160 30 Q 150 40, 140 50 L 130 80 Z"
          fill="#4C4E5C"
          className="animate-[wing-right_2s_ease-in-out_infinite]"
          style={{
            transformOrigin: '130px 80px',
          }}
        />
      </svg>

      <style jsx global>{`
        @keyframes wing-left {
          0%, 100% {
            transform: rotate(0deg);
          }
          50% {
            transform: rotate(-15deg);
          }
        }
        
        @keyframes wing-right {
          0%, 100% {
            transform: rotate(0deg);
          }
          50% {
            transform: rotate(15deg);
          }
        }
        
        @keyframes batFly {
          0%, 100% {
            transform: translateY(0px);
          }
          50% {
            transform: translateY(-10px);
          }
        }
      `}</style>
    </div>
  );
}
