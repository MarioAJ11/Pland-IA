'use client';

import React, { useEffect, useState } from 'react';

interface BatLoadingProps {
  onLoadingComplete?: () => void;
  duration?: number; // Duración total en ms (default: 5500ms)
}

export default function BatLoading({ onLoadingComplete, duration = 5500 }: BatLoadingProps) {
  const [isFlapping, setIsFlapping] = useState(false);
  const [currentFrame, setCurrentFrame] = useState(1);
  const [progress, setProgress] = useState(0);

  useEffect(() => {
    // Animación de caída termina a los 2s
    const fallTimer = setTimeout(() => {
      setIsFlapping(true);
    }, 2000);

    // Iniciar progreso a los 2.5s
    const progressTimer = setTimeout(() => {
      let prog = 0;
      const progressInterval = setInterval(() => {
        prog += 1;
        setProgress(prog);
        if (prog >= 100) {
          clearInterval(progressInterval);
        }
      }, 30); // 100% en 3s = 30ms por 1%
    }, 2500);

    // Completar carga
    const completeTimer = setTimeout(() => {
      if (onLoadingComplete) {
        onLoadingComplete();
      }
    }, duration);

    return () => {
      clearTimeout(fallTimer);
      clearTimeout(progressTimer);
      clearTimeout(completeTimer);
    };
  }, [duration, onLoadingComplete]);

  // Aleteo: secuencia 1 → 2 → 3 → 2 → 1
  useEffect(() => {
    if (!isFlapping) return;

    const flapSequence = [1, 2, 3, 2, 1];
    let index = 0;

    const flapInterval = setInterval(() => {
      setCurrentFrame(flapSequence[index]);
      index = (index + 1) % flapSequence.length;
    }, 150);

    return () => clearInterval(flapInterval);
  }, [isFlapping]);

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-gradient-to-br from-bat-shadow to-eerie-black">
      <div className="text-center">
        {/* Murciélago Pixel Art */}
        <div className="relative">
          <div
            className={`bat-pixel mx-auto ${isFlapping ? 'flapping' : 'falling'} frame-${currentFrame}`}
            style={{
              width: '1px',
              height: '1px',
              transform: 'scale(4)',
              filter: 'drop-shadow(0 0 20px rgba(156, 77, 204, 0.6))',
            }}
          />
        </div>

        {/* Texto de carga */}
        <div
          className="mt-36 text-2xl font-semibold tracking-widest uppercase text-bat-lavender animate-fade-in"
          style={{ animationDelay: '2s', opacity: 0 }}
        >
          Cargando
          <span className="loading-dots">...</span>
        </div>

        {/* Barra de progreso */}
        <div
          className="w-[300px] h-1 mx-auto mt-8 overflow-hidden rounded-full bg-bat-purple/20 animate-fade-in"
          style={{ animationDelay: '2.5s', opacity: 0 }}
        >
          <div
            className="h-full transition-all duration-100 ease-linear bg-gradient-to-r from-bat-purple to-bat-lavender"
            style={{
              width: `${progress}%`,
              boxShadow: '0 0 10px rgba(156, 77, 204, 0.8)',
            }}
          />
        </div>

        {/* Porcentaje (opcional) */}
        <div
          className="mt-4 text-sm tracking-wider text-bat-lavender/70 animate-fade-in"
          style={{ animationDelay: '2.5s', opacity: 0 }}
        >
          {progress}%
        </div>
      </div>

      <style jsx>{`
        /* Frames del murciélago */
        .bat-pixel.frame-1 {
          box-shadow:
            21px 0px #4A148C, 22px 0px #4A148C, 23px 0px #4A148C,
            20px 1px #4A148C, 21px 1px #232323, 22px 1px #232323, 23px 1px #232323, 24px 1px #4A148C,
            19px 2px #4A148C, 20px 2px #232323, 21px 2px #232323, 22px 2px #232323, 23px 2px #232323, 24px 2px #232323, 25px 2px #4A148C,
            19px 3px #4A148C, 20px 3px #232323, 21px 3px #FF4444, 22px 3px #232323, 23px 3px #FF4444, 24px 3px #232323, 25px 3px #4A148C,
            18px 4px #4A148C, 19px 4px #232323, 20px 4px #232323, 21px 4px #232323, 22px 4px #232323, 23px 4px #232323, 24px 4px #232323, 25px 4px #232323, 26px 4px #4A148C,
            18px 5px #4A148C, 19px 5px #232323, 20px 5px #232323, 21px 5px #232323, 22px 5px #232323, 23px 5px #232323, 24px 5px #232323, 25px 5px #232323, 26px 5px #4A148C,
            17px 6px #4A148C, 18px 6px #232323, 19px 6px #232323, 20px 6px #232323, 21px 6px #232323, 22px 6px #232323, 23px 6px #232323, 24px 6px #232323, 25px 6px #232323, 26px 6px #232323, 27px 6px #4A148C,
            17px 7px #4A148C, 18px 7px #232323, 19px 7px #232323, 20px 7px #232323, 21px 7px #232323, 22px 7px #232323, 23px 7px #232323, 24px 7px #232323, 25px 7px #232323, 26px 7px #232323, 27px 7px #4A148C,
            18px 8px #4A148C, 19px 8px #232323, 20px 8px #232323, 21px 8px #232323, 22px 8px #232323, 23px 8px #232323, 24px 8px #232323, 25px 8px #232323, 26px 8px #4A148C,
            19px 9px #4A148C, 20px 9px #232323, 21px 9px #232323, 22px 9px #232323, 23px 9px #232323, 24px 9px #232323, 25px 9px #4A148C,
            20px 10px #4A148C, 21px 10px #4A148C, 22px 10px #4A148C, 23px 10px #4A148C, 24px 10px #4A148C,
            21px 11px #4A148C, 22px 11px #4A148C, 23px 11px #4A148C;
        }

        .bat-pixel.frame-2 {
          box-shadow:
            16px 0px #4A148C, 17px 0px #4A148C, 21px 0px #4A148C, 22px 0px #4A148C, 23px 0px #4A148C, 27px 0px #4A148C, 28px 0px #4A148C,
            15px 1px #4A148C, 16px 1px #232323, 17px 1px #4A148C, 20px 1px #4A148C, 21px 1px #232323, 22px 1px #232323, 23px 1px #232323, 24px 1px #4A148C, 27px 1px #4A148C, 28px 1px #232323, 29px 1px #4A148C,
            14px 2px #4A148C, 15px 2px #232323, 16px 2px #232323, 17px 2px #4A148C, 19px 2px #4A148C, 20px 2px #232323, 21px 2px #232323, 22px 2px #232323, 23px 2px #232323, 24px 2px #232323, 25px 2px #4A148C, 27px 2px #4A148C, 28px 2px #232323, 29px 2px #232323, 30px 2px #4A148C,
            13px 3px #4A148C, 14px 3px #232323, 15px 3px #232323, 16px 3px #232323, 17px 3px #4A148C, 19px 3px #4A148C, 20px 3px #232323, 21px 3px #FF4444, 22px 3px #232323, 23px 3px #FF4444, 24px 3px #232323, 25px 3px #4A148C, 27px 3px #4A148C, 28px 3px #232323, 29px 3px #232323, 30px 3px #232323, 31px 3px #4A148C,
            12px 4px #4A148C, 13px 4px #232323, 14px 4px #232323, 15px 4px #232323, 16px 4px #232323, 17px 4px #4A148C, 18px 4px #4A148C, 19px 4px #232323, 20px 4px #232323, 21px 4px #232323, 22px 4px #232323, 23px 4px #232323, 24px 4px #232323, 25px 4px #232323, 26px 4px #4A148C, 27px 4px #4A148C, 28px 4px #232323, 29px 4px #232323, 30px 4px #232323, 31px 4px #232323, 32px 4px #4A148C,
            11px 5px #4A148C, 12px 5px #232323, 13px 5px #232323, 14px 5px #232323, 15px 5px #232323, 16px 5px #232323, 17px 5px #4A148C, 18px 5px #4A148C, 19px 5px #232323, 20px 5px #232323, 21px 5px #232323, 22px 5px #232323, 23px 5px #232323, 24px 5px #232323, 25px 5px #232323, 26px 5px #4A148C, 27px 5px #4A148C, 28px 5px #232323, 29px 5px #232323, 30px 5px #232323, 31px 5px #232323, 32px 5px #232323, 33px 5px #4A148C,
            10px 6px #4A148C, 11px 6px #232323, 12px 6px #232323, 13px 6px #232323, 14px 6px #232323, 15px 6px #232323, 16px 6px #232323, 17px 6px #4A148C, 18px 6px #4A148C, 19px 6px #232323, 20px 6px #232323, 21px 6px #232323, 22px 6px #232323, 23px 6px #232323, 24px 6px #232323, 25px 6px #232323, 26px 6px #4A148C, 27px 6px #4A148C, 28px 6px #232323, 29px 6px #232323, 30px 6px #232323, 31px 6px #232323, 32px 6px #232323, 33px 6px #232323, 34px 6px #4A148C,
            10px 7px #4A148C, 11px 7px #232323, 12px 7px #232323, 13px 7px #232323, 14px 7px #232323, 15px 7px #232323, 16px 7px #232323, 17px 7px #4A148C, 18px 7px #4A148C, 19px 7px #232323, 20px 7px #232323, 21px 7px #232323, 22px 7px #232323, 23px 7px #232323, 24px 7px #232323, 25px 7px #232323, 26px 7px #4A148C, 27px 7px #4A148C, 28px 7px #232323, 29px 7px #232323, 30px 7px #232323, 31px 7px #232323, 32px 7px #232323, 33px 7px #232323, 34px 7px #4A148C,
            11px 8px #4A148C, 12px 8px #232323, 13px 8px #232323, 14px 8px #232323, 15px 8px #232323, 16px 8px #4A148C, 18px 8px #4A148C, 19px 8px #232323, 20px 8px #232323, 21px 8px #232323, 22px 8px #232323, 23px 8px #232323, 24px 8px #232323, 25px 8px #232323, 26px 8px #4A148C, 28px 8px #4A148C, 29px 8px #232323, 30px 8px #232323, 31px 8px #232323, 32px 8px #232323, 33px 8px #4A148C,
            12px 9px #4A148C, 13px 9px #4A148C, 14px 9px #4A148C, 15px 9px #4A148C, 19px 9px #4A148C, 20px 9px #232323, 21px 9px #232323, 22px 9px #232323, 23px 9px #232323, 24px 9px #232323, 25px 9px #4A148C, 29px 9px #4A148C, 30px 9px #4A148C, 31px 9px #4A148C, 32px 9px #4A148C,
            20px 10px #4A148C, 21px 10px #4A148C, 22px 10px #4A148C, 23px 10px #4A148C, 24px 10px #4A148C,
            21px 11px #4A148C, 22px 11px #4A148C, 23px 11px #4A148C;
        }

        .bat-pixel.frame-3 {
          box-shadow:
            10px 0px #4A148C, 11px 0px #4A148C, 12px 0px #4A148C, 16px 0px #4A148C, 17px 0px #4A148C, 21px 0px #4A148C, 22px 0px #4A148C, 23px 0px #4A148C, 27px 0px #4A148C, 28px 0px #4A148C, 32px 0px #4A148C, 33px 0px #4A148C, 34px 0px #4A148C,
            9px 1px #4A148C, 10px 1px #232323, 11px 1px #232323, 12px 1px #232323, 13px 1px #4A148C, 15px 1px #4A148C, 16px 1px #232323, 17px 1px #4A148C, 20px 1px #4A148C, 21px 1px #232323, 22px 1px #232323, 23px 1px #232323, 24px 1px #4A148C, 27px 1px #4A148C, 28px 1px #232323, 29px 1px #4A148C, 31px 1px #4A148C, 32px 1px #232323, 33px 1px #232323, 34px 1px #232323, 35px 1px #4A148C,
            8px 2px #4A148C, 9px 2px #232323, 10px 2px #232323, 11px 2px #232323, 12px 2px #232323, 13px 2px #232323, 14px 2px #4A148C, 15px 2px #4A148C, 16px 2px #232323, 17px 2px #4A148C, 19px 2px #4A148C, 20px 2px #232323, 21px 2px #232323, 22px 2px #232323, 23px 2px #232323, 24px 2px #232323, 25px 2px #4A148C, 27px 2px #4A148C, 28px 2px #232323, 29px 2px #4A148C, 30px 2px #4A148C, 31px 2px #232323, 32px 2px #232323, 33px 2px #232323, 34px 2px #232323, 35px 2px #232323, 36px 2px #4A148C,
            7px 3px #4A148C, 8px 3px #232323, 9px 3px #232323, 10px 3px #232323, 11px 3px #232323, 12px 3px #232323, 13px 3px #232323, 14px 3px #4A148C, 15px 3px #4A148C, 16px 3px #232323, 17px 3px #4A148C, 19px 3px #4A148C, 20px 3px #232323, 21px 3px #FF4444, 22px 3px #232323, 23px 3px #FF4444, 24px 3px #232323, 25px 3px #4A148C, 27px 3px #4A148C, 28px 3px #232323, 29px 3px #4A148C, 30px 3px #4A148C, 31px 3px #232323, 32px 3px #232323, 33px 3px #232323, 34px 3px #232323, 35px 3px #232323, 36px 3px #232323, 37px 3px #4A148C,
            6px 4px #4A148C, 7px 4px #232323, 8px 4px #232323, 9px 4px #232323, 10px 4px #232323, 11px 4px #232323, 12px 4px #232323, 13px 4px #232323, 14px 4px #4A148C, 18px 4px #4A148C, 19px 4px #232323, 20px 4px #232323, 21px 4px #232323, 22px 4px #232323, 23px 4px #232323, 24px 4px #232323, 25px 4px #232323, 26px 4px #4A148C, 30px 4px #4A148C, 31px 4px #232323, 32px 4px #232323, 33px 4px #232323, 34px 4px #232323, 35px 4px #232323, 36px 4px #232323, 37px 4px #232323, 38px 4px #4A148C,
            5px 5px #4A148C, 6px 5px #232323, 7px 5px #232323, 8px 5px #232323, 9px 5px #232323, 10px 5px #232323, 11px 5px #232323, 12px 5px #232323, 13px 5px #232323, 14px 5px #4A148C, 18px 5px #4A148C, 19px 5px #232323, 20px 5px #232323, 21px 5px #232323, 22px 5px #232323, 23px 5px #232323, 24px 5px #232323, 25px 5px #232323, 26px 5px #4A148C, 30px 5px #4A148C, 31px 5px #232323, 32px 5px #232323, 33px 5px #232323, 34px 5px #232323, 35px 5px #232323, 36px 5px #232323, 37px 5px #232323, 38px 5px #232323, 39px 5px #4A148C,
            5px 6px #4A148C, 6px 6px #232323, 7px 6px #232323, 8px 6px #232323, 9px 6px #232323, 10px 6px #232323, 11px 6px #232323, 12px 6px #232323, 13px 6px #4A148C, 18px 6px #4A148C, 19px 6px #232323, 20px 6px #232323, 21px 6px #232323, 22px 6px #232323, 23px 6px #232323, 24px 6px #232323, 25px 6px #232323, 26px 6px #4A148C, 31px 6px #4A148C, 32px 6px #232323, 33px 6px #232323, 34px 6px #232323, 35px 6px #232323, 36px 6px #232323, 37px 6px #232323, 38px 6px #232323, 39px 6px #4A148C,
            6px 7px #4A148C, 7px 7px #232323, 8px 7px #232323, 9px 7px #232323, 10px 7px #232323, 11px 7px #232323, 12px 7px #4A148C, 18px 7px #4A148C, 19px 7px #232323, 20px 7px #232323, 21px 7px #232323, 22px 7px #232323, 23px 7px #232323, 24px 7px #232323, 25px 7px #232323, 26px 7px #4A148C, 32px 7px #4A148C, 33px 7px #232323, 34px 7px #232323, 35px 7px #232323, 36px 7px #232323, 37px 7px #232323, 38px 7px #4A148C,
            7px 8px #4A148C, 8px 8px #4A148C, 9px 8px #4A148C, 10px 8px #4A148C, 11px 8px #4A148C, 18px 8px #4A148C, 19px 8px #232323, 20px 8px #232323, 21px 8px #232323, 22px 8px #232323, 23px 8px #232323, 24px 8px #232323, 25px 8px #232323, 26px 8px #4A148C, 33px 8px #4A148C, 34px 8px #4A148C, 35px 8px #4A148C, 36px 8px #4A148C, 37px 8px #4A148C,
            19px 9px #4A148C, 20px 9px #232323, 21px 9px #232323, 22px 9px #232323, 23px 9px #232323, 24px 9px #232323, 25px 9px #4A148C,
            20px 10px #4A148C, 21px 10px #4A148C, 22px 10px #4A148C, 23px 10px #4A148C, 24px 10px #4A148C,
            21px 11px #4A148C, 22px 11px #4A148C, 23px 11px #4A148C;
        }

        /* Animación de caída */
        .bat-pixel.falling {
          animation: batFalling 2s cubic-bezier(0.68, -0.55, 0.265, 1.55) forwards;
        }

        @keyframes batFalling {
          0% {
            transform: scale(4) translateY(-200px) rotate(180deg);
            opacity: 0;
          }
          30% {
            transform: scale(4) translateY(-50px) rotate(180deg);
            opacity: 1;
          }
          60% {
            transform: scale(4) translateY(0) rotate(180deg);
          }
          100% {
            transform: scale(4) translateY(0) rotate(0deg);
          }
        }

        /* Animación de aparición */
        .animate-fade-in {
          animation: fadeIn 0.5s ease-in forwards;
        }

        @keyframes fadeIn {
          to {
            opacity: 1;
          }
        }

        /* Animación de puntos */
        .loading-dots::after {
          content: '...';
          animation: dots 1.5s infinite;
        }

        @keyframes dots {
          0%, 20% {
            content: '.';
          }
          40% {
            content: '..';
          }
          60%, 100% {
            content: '...';
          }
        }
      `}</style>
    </div>
  );
}
