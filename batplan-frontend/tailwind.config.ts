import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./src/pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/components/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        sans: ['var(--font-inter)', 'sans-serif'],
        mono: ['var(--font-vt323)', 'monospace'],
      },
      colors: {
        // Brand Colors (del logo)
        'brand-primary': '#54556b',  // Shadow Slate
        'brand-dark': '#202020',     // Void Black
        'brand-light': '#FFFFFF',    // Pure White
        
        // Text Colors
        'text-primary': '#202020',      // Void Black
        'text-secondary': '#7D7E90',    // Soft Slate
        'text-inverse': '#FFFFFF',      // Pure White
        
        // Light Mode
        'bg-app': '#EEF0F5',           // Bat Mist
        'bg-surface': '#FFFFFF',       // Pure White
        
        // Dark Mode
        'bg-dark-app': '#202020',      // Void Black
        'bg-dark-surface': '#2D2D3A',  // Slate Oscuro
        
        // Functional Colors
        'success': '#2A9D8F',
        'warning': '#E9C46A',
        'danger': '#D62828',
      },
      backgroundImage: {
        'gradient-radial': 'radial-gradient(var(--tw-gradient-stops))',
        'gradient-conic': 'conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))',
      },
      boxShadow: {
        'card': '0 2px 8px rgba(0, 0, 0, 0.08)',
        'card-hover': '0 4px 12px rgba(0, 0, 0, 0.12)',
        'button': '0 2px 4px rgba(79, 93, 117, 0.2)',
      },
      animation: {
        'float': 'float 6s ease-in-out infinite',
        'loading-bar': 'loadingBar 2s ease-in-out infinite',
      },
      keyframes: {
        float: {
          '0%, 100%': { 
            transform: 'translateY(0px)' 
          },
          '50%': { 
            transform: 'translateY(-10px)' 
          },
        },
        loadingBar: {
          '0%': { 
            width: '0%' 
          },
          '100%': { 
            width: '100%' 
          },
        },
      },
    },
  },
  plugins: [],
};

export default config;
