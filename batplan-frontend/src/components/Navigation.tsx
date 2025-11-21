'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';
import { Calendar, CheckSquare, DollarSign, UtensilsCrossed, LayoutDashboard } from 'lucide-react';

const navItems = [
  { href: '/dashboard', icon: LayoutDashboard, label: 'Dashboard' },
  { href: '/calendar', icon: Calendar, label: 'Agenda' },
  { href: '/tasks', icon: CheckSquare, label: 'Tareas' },
  { href: '/expenses', icon: DollarSign, label: 'Gastos' },
  { href: '/pantry', icon: UtensilsCrossed, label: 'Despensa' },
];

export default function Navigation() {
  const pathname = usePathname();

  return (
    <nav className="fixed bottom-0 left-0 right-0 bg-bg-surface dark:bg-bg-dark-surface border-t border-gray-200 dark:border-gray-800 shadow-lg md:top-0 md:bottom-auto z-40">
      <div className="container mx-auto px-4">
        <div className="flex justify-around md:justify-center md:gap-8 py-3">
          {navItems.map((item) => {
            const Icon = item.icon;
            const isActive = pathname === item.href;
            
            return (
              <Link
                key={item.href}
                href={item.href}
                className={`flex flex-col md:flex-row items-center gap-1 md:gap-2 px-3 py-2 rounded-lg transition-all ${
                  isActive
                    ? 'text-brand-primary bg-brand-primary/10'
                    : 'text-text-secondary dark:text-gray-400 hover:text-brand-primary hover:bg-brand-primary/5'
                }`}
              >
                <Icon className="w-5 h-5" />
                <span className="text-xs md:text-sm font-medium">{item.label}</span>
              </Link>
            );
          })}
        </div>
      </div>
    </nav>
  );
}
