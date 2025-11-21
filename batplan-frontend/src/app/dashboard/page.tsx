'use client';

import { useState, useMemo } from 'react';
import { Calendar, CheckSquare, Plus, Clock, ChevronLeft, ChevronRight } from 'lucide-react';

interface Task {
  id: number;
  title: string;
  completed: boolean;
  time?: string;
  priority: 'high' | 'medium' | 'low';
  date?: string; // Fecha del evento (formato YYYY-MM-DD)
}

export default function DashboardPage() {
  const [currentDate, setCurrentDate] = useState(new Date());
  const [tasks, setTasks] = useState<Task[]>([
    { id: 1, title: 'Reunión con el equipo', completed: false, time: '09:00', priority: 'high', date: new Date().toISOString().split('T')[0] },
    { id: 2, title: 'Revisar documentación', completed: false, time: '11:30', priority: 'medium', date: new Date().toISOString().split('T')[0] },
    { id: 3, title: 'Hacer ejercicio', completed: false, time: '18:00', priority: 'low', date: new Date().toISOString().split('T')[0] },
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

  const formatShortDate = (date: Date) => {
    return new Intl.DateTimeFormat('es-ES', {
      weekday: 'short',
      day: 'numeric',
      month: 'short'
    }).format(date);
  };

  // Generar 7 días a partir de hoy
  const getNext7Days = useMemo(() => {
    const days = [];
    const today = new Date(currentDate);
    
    for (let i = 0; i < 7; i++) {
      const day = new Date(today);
      day.setDate(today.getDate() + i);
      days.push(day);
    }
    return days;
  }, [currentDate]);

  // Navegar semana adelante/atrás
  const navigateWeek = (direction: 'prev' | 'next') => {
    const newDate = new Date(currentDate);
    newDate.setDate(currentDate.getDate() + (direction === 'next' ? 7 : -7));
    setCurrentDate(newDate);
  };

  const resetToToday = () => {
    setCurrentDate(new Date());
  };

  const isToday = (date: Date) => {
    const today = new Date();
    return date.toDateString() === today.toDateString();
  };

  const getTasksForDate = (date: Date) => {
    const dateStr = date.toISOString().split('T')[0];
    return tasks.filter(task => task.date === dateStr && task.time);
  };

  const priorityColors = {
    high: 'border-l-4 border-red-500 bg-red-50 dark:bg-red-900/20',
    medium: 'border-l-4 border-yellow-500 bg-yellow-50 dark:bg-yellow-900/20',
    low: 'border-l-4 border-green-500 bg-green-50 dark:bg-green-900/20',
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

      {/* Grid principal: Calendario semanal (70%) + Tareas (30%) */}
      <div className="grid grid-cols-1 lg:grid-cols-10 gap-6">
        
        {/* Calendario semanal - 7 días */}
        <div className="lg:col-span-7">
          <div className="bg-bg-surface dark:bg-bg-dark-surface rounded-2xl shadow-lg p-6">
            <div className="flex items-center justify-between mb-6">
              <h3 className="text-xl font-bold text-text-primary dark:text-text-dark-primary flex items-center gap-2">
                <Calendar className="w-5 h-5 text-brand-primary" />
                Próximos 7 días
              </h3>
              <div className="flex gap-2">
                <button 
                  onClick={() => navigateWeek('prev')}
                  className="p-2 hover:bg-brand-primary/10 rounded-lg transition-colors"
                  title="Semana anterior"
                >
                  <ChevronLeft className="w-5 h-5 text-text-secondary dark:text-text-dark-secondary" />
                </button>
                <button
                  onClick={resetToToday}
                  className="px-4 py-2 text-sm font-medium bg-brand-primary/10 text-brand-primary rounded-lg hover:bg-brand-primary/20 transition-colors"
                >
                  Hoy
                </button>
                <button 
                  onClick={() => navigateWeek('next')}
                  className="p-2 hover:bg-brand-primary/10 rounded-lg transition-colors"
                  title="Semana siguiente"
                >
                  <ChevronRight className="w-5 h-5 text-text-secondary dark:text-text-dark-secondary" />
                </button>
              </div>
            </div>

            {/* Vista de 7 días en cards */}
            <div className="space-y-4">
              {getNext7Days.map((day, index) => {
                const dayTasks = getTasksForDate(day);
                const todayHighlight = isToday(day);
                
                return (
                  <div
                    key={index}
                    className={`p-4 rounded-xl border-2 transition-all ${
                      todayHighlight
                        ? 'bg-brand-primary/5 border-brand-primary shadow-md'
                        : 'bg-bg-app dark:bg-bg-dark-app border-transparent hover:border-brand-primary/30'
                    }`}
                  >
                    <div className="flex items-start gap-4">
                      {/* Columna de fecha */}
                      <div className="shrink-0 text-center">
                        <div className={`text-3xl font-bold font-mono ${
                          todayHighlight ? 'text-brand-primary' : 'text-text-primary dark:text-text-dark-primary'
                        }`}>
                          {day.getDate()}
                        </div>
                        <div className={`text-xs uppercase font-medium ${
                          todayHighlight ? 'text-brand-primary' : 'text-text-secondary dark:text-text-dark-secondary'
                        }`}>
                          {new Intl.DateTimeFormat('es-ES', { weekday: 'short' }).format(day)}
                        </div>
                        {todayHighlight && (
                          <div className="mt-1 text-[10px] font-bold text-brand-primary uppercase">
                            Hoy
                          </div>
                        )}
                      </div>

                      {/* Columna de eventos */}
                      <div className="flex-1">
                        {dayTasks.length > 0 ? (
                          <div className="space-y-2">
                            {dayTasks.map(task => (
                              <div
                                key={task.id}
                                className={`flex items-center gap-3 p-3 rounded-lg ${priorityColors[task.priority]} cursor-pointer hover:opacity-80 transition-opacity`}
                                onClick={() => toggleTask(task.id)}
                              >
                                <input
                                  type="checkbox"
                                  checked={task.completed}
                                  onChange={() => toggleTask(task.id)}
                                  className="w-4 h-4 rounded border-2 border-brand-primary text-brand-primary focus:ring-brand-primary cursor-pointer"
                                  onClick={(e) => e.stopPropagation()}
                                />
                                <div className="flex-1 flex items-center gap-2">
                                  <Clock className="w-3 h-3 text-text-secondary dark:text-text-dark-secondary" />
                                  <span className="text-xs font-mono font-bold text-brand-primary">
                                    {task.time}
                                  </span>
                                  <span className={`text-sm ${
                                    task.completed ? 'line-through text-text-secondary dark:text-text-dark-secondary' : 'text-text-primary dark:text-text-dark-primary'
                                  }`}>
                                    {task.title}
                                  </span>
                                </div>
                              </div>
                            ))}
                          </div>
                        ) : (
                          <div className="flex items-center h-12 text-sm text-text-secondary dark:text-text-dark-secondary italic">
                            Sin eventos programados
                          </div>
                        )}
                      </div>
                    </div>
                  </div>
                );
              })}
            </div>
          </div>
        </div>

        {/* Panel de Tareas pendientes (30%) */}
        <div className="lg:col-span-3">
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
                        onClick={(e) => e.stopPropagation()}
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
