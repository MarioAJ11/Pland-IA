'use client';

import { useState } from 'react';
import { Calendar, CheckSquare, Plus, Clock, ChevronLeft, ChevronRight } from 'lucide-react';

interface Task {
  id: number;
  title: string;
  completed: boolean;
  time?: string;
  priority: 'high' | 'medium' | 'low';
}

export default function DashboardPage() {
  const [currentDate] = useState(new Date());
  const [tasks, setTasks] = useState<Task[]>([
    { id: 1, title: 'Reunión con el equipo', completed: false, time: '09:00', priority: 'high' },
    { id: 2, title: 'Revisar documentación', completed: false, time: '11:30', priority: 'medium' },
    { id: 3, title: 'Hacer ejercicio', completed: false, time: '18:00', priority: 'low' },
    { id: 4, title: 'Preparar presentación', completed: false, priority: 'high' },
    { id: 5, title: 'Comprar víveres', completed: false, priority: 'medium' },
  ]);

  const toggleTask = (id: number) => {
    setTasks(tasks.map(task => 
      task.id === id ? { ...task, completed: !task.completed } : task
    ));
  };

  const formatDate = (date: Date) => {
    return new Intl.DateTimeFormat('es-ES', {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    }).format(date);
  };

  const getDaysInMonth = () => {
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();
    const firstDay = new Date(year, month, 1).getDay();
    const daysInMonth = new Date(year, month + 1, 0).getDate();
    
    const days = [];
    for (let i = 0; i < firstDay; i++) {
      days.push(null);
    }
    for (let i = 1; i <= daysInMonth; i++) {
      days.push(i);
    }
    return days;
  };

  const priorityColors = {
    high: 'border-l-4 border-red-500 bg-red-50',
    medium: 'border-l-4 border-yellow-500 bg-yellow-50',
    low: 'border-l-4 border-green-500 bg-green-50',
  };

  return (
    <div className="container mx-auto px-4 py-6">
      {/* Header de fecha */}
      <div className="mb-6">
        <h2 className="text-3xl font-bold text-text-primary dark:text-text-dark-primary font-mono capitalize">
          {formatDate(currentDate)}
        </h2>
        <p className="text-text-secondary dark:text-text-dark-secondary mt-1">
          Tienes {tasks.filter(t => !t.completed).length} tareas pendientes
        </p>
      </div>

      {/* Grid principal: Calendario + Tareas */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        
        {/* Calendario mensual */}
        <div className="lg:col-span-2">
          <div className="bg-bg-surface dark:bg-bg-dark-surface rounded-2xl shadow-lg p-6">
            <div className="flex items-center justify-between mb-6">
              <h3 className="text-xl font-bold text-text-primary dark:text-text-dark-primary flex items-center gap-2 capitalize">
                <Calendar className="w-5 h-5 text-brand-primary" />
                {new Intl.DateTimeFormat('es-ES', { month: 'long', year: 'numeric' }).format(currentDate)}
              </h3>
              <div className="flex gap-2">
                <button className="p-2 hover:bg-brand-primary/10 rounded-lg transition-colors">
                  <ChevronLeft className="w-5 h-5 text-text-secondary dark:text-text-dark-secondary" />
                </button>
                <button className="p-2 hover:bg-brand-primary/10 rounded-lg transition-colors">
                  <ChevronRight className="w-5 h-5 text-text-secondary dark:text-text-dark-secondary" />
                </button>
              </div>
            </div>

            {/* Días de la semana */}
            <div className="grid grid-cols-7 gap-2 mb-2">
              {['Dom', 'Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb'].map(day => (
                <div key={day} className="text-center text-sm font-bold text-text-secondary dark:text-text-dark-secondary py-2">
                  {day}
                </div>
              ))}
            </div>

            {/* Días del mes */}
            <div className="grid grid-cols-7 gap-2">
              {getDaysInMonth().map((day, index) => (
                <div
                  key={index}
                  className={`aspect-square flex items-center justify-center rounded-lg text-sm transition-all ${
                    day === null
                      ? 'invisible'
                      : day === currentDate.getDate()
                      ? 'bg-brand-primary text-text-inverse font-bold shadow-lg'
                      : 'hover:bg-brand-primary/10 text-text-primary dark:text-text-dark-primary cursor-pointer'
                  }`}
                >
                  {day}
                </div>
              ))}
            </div>

            {/* Eventos de hoy */}
            <div className="mt-6 pt-6 border-t border-gray-200 dark:border-gray-700">
              <h4 className="text-sm font-bold text-text-secondary dark:text-text-dark-secondary mb-3 flex items-center gap-2">
                <Clock className="w-4 h-4" />
                Eventos de hoy
              </h4>
              <div className="space-y-2">
                {tasks.filter(t => t.time && !t.completed).map(task => (
                  <div
                    key={task.id}
                    className="flex items-center gap-3 p-3 bg-bg-app dark:bg-bg-dark-app rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
                  >
                    <span className="text-xs font-mono text-brand-primary font-bold">
                      {task.time}
                    </span>
                    <span className="text-sm text-text-primary dark:text-text-dark-primary">{task.title}</span>
                  </div>
                ))}
                {tasks.filter(t => t.time && !t.completed).length === 0 && (
                  <p className="text-sm text-text-secondary dark:text-text-dark-secondary">No hay eventos programados</p>
                )}
              </div>
            </div>
          </div>
        </div>

        {/* Tareas pendientes */}
        <div className="lg:col-span-1">
          <div className="bg-bg-surface dark:bg-bg-dark-surface rounded-2xl shadow-lg p-6 sticky top-20">
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-xl font-bold text-text-primary dark:text-text-dark-primary flex items-center gap-2">
                <CheckSquare className="w-5 h-5 text-brand-primary" />
                Tareas
              </h3>
              <button className="p-2 bg-brand-primary text-text-inverse rounded-lg hover:bg-brand-primary/90 transition-all shadow-lg">
                <Plus className="w-5 h-5" />
              </button>
            </div>

            <div className="space-y-3 max-h-[600px] overflow-y-auto">
              {tasks.map(task => (
                <div
                  key={task.id}
                  className={`p-4 rounded-lg transition-all cursor-pointer ${
                    task.completed
                      ? 'bg-bg-app dark:bg-bg-dark-app opacity-60'
                      : priorityColors[task.priority]
                  }`}
                  onClick={() => toggleTask(task.id)}
                >
                  <div className="flex items-start gap-3">
                    <div className="mt-1">
                      <input
                        type="checkbox"
                        checked={task.completed}
                        onChange={() => toggleTask(task.id)}
                        className="w-5 h-5 rounded border-2 border-brand-primary text-brand-primary focus:ring-brand-primary cursor-pointer"
                      />
                    </div>
                    <div className="flex-1">
                      <p className={`text-sm font-medium ${
                        task.completed ? 'line-through text-text-secondary dark:text-text-dark-secondary' : 'text-text-primary dark:text-text-dark-primary'
                      }`}>
                        {task.title}
                      </p>
                      {task.time && (
                        <p className="text-xs text-text-secondary dark:text-text-dark-secondary mt-1 font-mono">
                          {task.time}
                        </p>
                      )}
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
