'use client';

import { useState } from 'react';
import { useAuthStore } from '@/store/authStore';
import { eventService } from '@/services/eventService';
import { Event, EventCreateDto } from '@/types';
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/Card';
import { Button } from '@/components/ui/Button';
import { Input } from '@/components/ui/Input';
import { Modal } from '@/components/ui/Modal';
import { Calendar, Plus, Trash2, Edit } from 'lucide-react';
import { formatDate, formatTime } from '@/lib/utils';

export default function CalendarPage() {
  const { user } = useAuthStore();
  const [events, setEvents] = useState<Event[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedEvent, setSelectedEvent] = useState<Event | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const [formData, setFormData] = useState<Partial<EventCreateDto>>({
    title: '',
    description: '',
    startTime: '',
    endTime: '',
    location: '',
    eventType: 'OTHER',
    userId: user?.id || '',
  });

  const loadEvents = async () => {
    if (!user?.id) return;
    try {
      const data = await eventService.getByUserId(user.id);
      setEvents(data);
    } catch (error) {
      console.error('Error cargando eventos:', error);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!user?.id) return;

    setIsLoading(true);
    try {
      if (selectedEvent) {
        const updated = await eventService.update(selectedEvent.id, formData);
        setEvents(events.map(ev => ev.id === updated.id ? updated : ev));
      } else {
        const created = await eventService.create(formData as EventCreateDto);
        setEvents([...events, created]);
      }
      setIsModalOpen(false);
      resetForm();
    } catch (error) {
      console.error('Error guardando evento:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleDelete = async (id: string) => {
    if (!confirm('¿Eliminar este evento?')) return;
    try {
      await eventService.delete(id);
      setEvents(events.filter(ev => ev.id !== id));
    } catch (error) {
      console.error('Error eliminando evento:', error);
    }
  };

  const resetForm = () => {
    setFormData({
      title: '',
      description: '',
      startTime: '',
      endTime: '',
      location: '',
      eventType: 'OTHER',
      userId: user?.id || '',
    });
    setSelectedEvent(null);
  };

  const openEditModal = (event: Event) => {
    setSelectedEvent(event);
    setFormData({
      title: event.title,
      description: event.description || '',
      startTime: event.startTime,
      endTime: event.endTime,
      location: event.location || '',
      eventType: event.eventType,
      userId: event.userId,
    });
    setIsModalOpen(true);
  };

  return (
    <div className="min-h-screen bg-bg-app dark:bg-bg-dark-app p-8">
      <div className="max-w-7xl mx-auto">
        <div className="flex justify-between items-center mb-8">
          <div className="flex items-center gap-3">
            <Calendar className="w-8 h-8 text-brand-primary" />
            <h1 className="text-3xl font-bold text-text-primary dark:text-text-dark-primary">Calendario</h1>
          </div>
          <Button onClick={() => { resetForm(); setIsModalOpen(true); }}>
            <Plus className="w-4 h-4 mr-2" />
            Nuevo Evento
          </Button>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {events.map(event => (
            <Card key={event.id}>
              <CardHeader>
                <CardTitle>{event.title}</CardTitle>
                <p className="text-sm text-text-secondary dark:text-text-dark-secondary mt-1">
                  {formatDate(event.startTime)} - {formatTime(event.startTime)}
                </p>
              </CardHeader>
              <CardContent>
                {event.description && (
                  <p className="text-text-secondary dark:text-text-dark-secondary mb-3">{event.description}</p>
                )}
                {event.location && (
                  <p className="text-sm text-text-secondary dark:text-text-dark-secondary mb-3">
                    Ubicación: {event.location}
                  </p>
                )}
                <div className="flex gap-2">
                  <Button size="sm" variant="outline" onClick={() => openEditModal(event)}>
                    <Edit className="w-4 h-4" />
                  </Button>
                  <Button size="sm" variant="danger" onClick={() => handleDelete(event.id)}>
                    <Trash2 className="w-4 h-4" />
                  </Button>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>

        <Modal
          isOpen={isModalOpen}
          onClose={() => { setIsModalOpen(false); resetForm(); }}
          title={selectedEvent ? 'Editar Evento' : 'Nuevo Evento'}
        >
          <form onSubmit={handleSubmit} className="space-y-4">
            <Input
              label="Título"
              value={formData.title}
              onChange={(e) => setFormData({ ...formData, title: e.target.value })}
              required
            />
            <div>
              <label className="block text-sm font-medium text-text-primary dark:text-text-dark-primary mb-1">
                Descripción
              </label>
              <textarea
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-brand-primary bg-bg-surface dark:bg-bg-dark-surface text-text-primary dark:text-text-dark-primary"
                value={formData.description}
                onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                rows={3}
              />
            </div>
            <Input
              label="Inicio"
              type="datetime-local"
              value={formData.startTime}
              onChange={(e) => setFormData({ ...formData, startTime: e.target.value })}
              required
            />
            <Input
              label="Fin"
              type="datetime-local"
              value={formData.endTime}
              onChange={(e) => setFormData({ ...formData, endTime: e.target.value })}
              required
            />
            <Input
              label="Ubicación"
              value={formData.location}
              onChange={(e) => setFormData({ ...formData, location: e.target.value })}
            />
            <div className="flex gap-3 pt-4">
              <Button type="submit" isLoading={isLoading} className="flex-1">
                {selectedEvent ? 'Actualizar' : 'Crear'}
              </Button>
              <Button type="button" variant="outline" onClick={() => { setIsModalOpen(false); resetForm(); }}>
                Cancelar
              </Button>
            </div>
          </form>
        </Modal>
      </div>
    </div>
  );
}
