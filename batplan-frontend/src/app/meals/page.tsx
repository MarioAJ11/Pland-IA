'use client';

import { useState, useEffect } from 'react';
import { useAuthStore } from '@/store/authStore';
import { mealService } from '@/services/mealService';
import { mealPlanService } from '@/services/mealPlanService';
import { Meal, MealPlan, MealCreateDto, MealPlanCreateDto } from '@/types';
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/Card';
import { Button } from '@/components/ui/Button';
import { Input } from '@/components/ui/Input';
import { Modal } from '@/components/ui/Modal';
import { UtensilsCrossed, Plus, Trash2, Edit, CheckCircle } from 'lucide-react';

export default function MealsPage() {
  const { user } = useAuthStore();
  const [meals, setMeals] = useState<Meal[]>([]);
  const [mealPlans, setMealPlans] = useState<MealPlan[]>([]);
  const [activeTab, setActiveTab] = useState<'recipes' | 'plans'>('recipes');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedMeal, setSelectedMeal] = useState<Meal | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const [mealFormData, setMealFormData] = useState<Partial<MealCreateDto>>({
    name: '',
    description: '',
    mealType: 'LUNCH',
    prepTimeMinutes: 0,
    servingSize: 1,
    userId: user?.id || '',
  });

  useEffect(() => {
    if (activeTab === 'recipes') loadMeals();
    else loadMealPlans();
  }, [activeTab, user?.id]);

  const loadMeals = async () => {
    if (!user?.id) return;
    try {
      const data = await mealService.getAvailable(user.id);
      setMeals(data);
    } catch (error) {
      console.error('Error cargando recetas:', error);
    }
  };

  const loadMealPlans = async () => {
    if (!user?.id) return;
    try {
      const data = await mealPlanService.getCurrentWeek(user.id);
      setMealPlans(data);
    } catch (error) {
      console.error('Error cargando planes:', error);
    }
  };

  const handleSubmitMeal = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!user?.id) return;

    setIsLoading(true);
    try {
      if (selectedMeal) {
        const updated = await mealService.update(selectedMeal.id, mealFormData);
        setMeals(meals.map(m => m.id === updated.id ? updated : m));
      } else {
        const created = await mealService.create(mealFormData as MealCreateDto);
        setMeals([...meals, created]);
      }
      setIsModalOpen(false);
      resetMealForm();
    } catch (error) {
      console.error('Error guardando receta:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleDeleteMeal = async (id: string) => {
    if (!confirm('¿Eliminar esta receta?')) return;
    try {
      await mealService.delete(id);
      setMeals(meals.filter(m => m.id !== id));
    } catch (error) {
      console.error('Error eliminando receta:', error);
    }
  };

  const handleToggleFavorite = async (meal: Meal) => {
    try {
      const updated = await mealService.toggleFavorite(meal.id, !meal.isFavorite);
      setMeals(meals.map(m => m.id === updated.id ? updated : m));
    } catch (error) {
      console.error('Error actualizando favorito:', error);
    }
  };

  const resetMealForm = () => {
    setMealFormData({
      name: '',
      description: '',
      mealType: 'LUNCH',
      prepTimeMinutes: 0,
      servingSize: 1,
      userId: user?.id || '',
    });
    setSelectedMeal(null);
  };

  const openEditModal = (meal: Meal) => {
    setSelectedMeal(meal);
    setMealFormData({
      name: meal.name,
      description: meal.description || '',
      mealType: meal.mealType,
      prepTimeMinutes: meal.prepTimeMinutes,
      servingSize: meal.servingSize,
      userId: meal.userId,
    });
    setIsModalOpen(true);
  };

  return (
    <div className="min-h-screen bg-bg-app dark:bg-bg-dark-app p-8">
      <div className="max-w-7xl mx-auto">
        <div className="flex justify-between items-center mb-8">
          <div className="flex items-center gap-3">
            <UtensilsCrossed className="w-8 h-8 text-brand-primary" />
            <h1 className="text-3xl font-bold text-text-primary dark:text-text-dark-primary">Comidas</h1>
          </div>
          <Button onClick={() => { resetMealForm(); setIsModalOpen(true); }}>
            <Plus className="w-4 h-4 mr-2" />
            Nueva Receta
          </Button>
        </div>

        <div className="mb-6 flex gap-4">
          <Button
            variant={activeTab === 'recipes' ? 'primary' : 'outline'}
            onClick={() => setActiveTab('recipes')}
          >
            Recetas
          </Button>
          <Button
            variant={activeTab === 'plans' ? 'primary' : 'outline'}
            onClick={() => setActiveTab('plans')}
          >
            Planificación
          </Button>
        </div>

        {activeTab === 'recipes' ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {meals.map(meal => (
              <Card key={meal.id}>
                <CardHeader>
                  <div className="flex justify-between items-start">
                    <CardTitle>{meal.name}</CardTitle>
                    <button
                      onClick={() => handleToggleFavorite(meal)}
                      className={`text-2xl ${meal.isFavorite ? 'text-yellow-500' : 'text-gray-300 dark:text-gray-600'}`}
                    >
                      ★
                    </button>
                  </div>
                  <p className="text-sm text-text-secondary dark:text-text-dark-secondary mt-1">
                    {meal.mealType} - {meal.prepTimeMinutes || 0} min
                  </p>
                </CardHeader>
                <CardContent>
                  {meal.description && (
                    <p className="text-text-secondary dark:text-text-dark-secondary mb-3">{meal.description}</p>
                  )}
                  <p className="text-sm text-text-secondary dark:text-text-dark-secondary mb-3">
                    Porciones: {meal.servingSize || 1}
                  </p>
                  <div className="flex gap-2">
                    <Button size="sm" variant="outline" onClick={() => openEditModal(meal)}>
                      <Edit className="w-4 h-4" />
                    </Button>
                    <Button size="sm" variant="danger" onClick={() => handleDeleteMeal(meal.id)}>
                      <Trash2 className="w-4 h-4" />
                    </Button>
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {mealPlans.map(plan => (
              <Card key={plan.id}>
                <CardHeader>
                  <CardTitle>{plan.meal?.name || 'Comida'}</CardTitle>
                  <p className="text-sm text-text-secondary dark:text-text-dark-secondary mt-1">
                    {new Date(plan.mealDate).toLocaleDateString('es-ES')}
                  </p>
                </CardHeader>
                <CardContent>
                  <p className="text-text-secondary dark:text-text-dark-secondary mb-3">{plan.mealType}</p>
                  {plan.isCompleted && (
                    <div className="flex items-center gap-2 text-green-600">
                      <CheckCircle className="w-4 h-4" />
                      <span className="text-sm">Completado</span>
                    </div>
                  )}
                </CardContent>
              </Card>
            ))}
          </div>
        )}

        <Modal
          isOpen={isModalOpen}
          onClose={() => { setIsModalOpen(false); resetMealForm(); }}
          title={selectedMeal ? 'Editar Receta' : 'Nueva Receta'}
        >
          <form onSubmit={handleSubmitMeal} className="space-y-4">
            <Input
              label="Nombre"
              value={mealFormData.name}
              onChange={(e) => setMealFormData({ ...mealFormData, name: e.target.value })}
              required
            />
            <div>
              <label className="block text-sm font-medium text-text-primary dark:text-text-dark-primary mb-1">
                Descripción
              </label>
              <textarea
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-brand-primary bg-bg-surface dark:bg-bg-dark-surface text-text-primary dark:text-text-dark-primary"
                value={mealFormData.description}
                onChange={(e) => setMealFormData({ ...mealFormData, description: e.target.value })}
                rows={3}
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-text-primary dark:text-text-dark-primary mb-1">
                Tipo de Comida
              </label>
              <select
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-brand-primary bg-bg-surface dark:bg-bg-dark-surface text-text-primary dark:text-text-dark-primary"
                value={mealFormData.mealType}
                onChange={(e) => setMealFormData({ ...mealFormData, mealType: e.target.value as any })}
                required
              >
                <option value="BREAKFAST">Desayuno</option>
                <option value="LUNCH">Comida</option>
                <option value="DINNER">Cena</option>
                <option value="SNACK">Snack</option>
              </select>
            </div>
            <Input
              label="Tiempo de preparación (min)"
              type="number"
              value={mealFormData.prepTimeMinutes}
              onChange={(e) => setMealFormData({ ...mealFormData, prepTimeMinutes: parseInt(e.target.value) })}
              required
            />
            <Input
              label="Porciones"
              type="number"
              value={mealFormData.servingSize}
              onChange={(e) => setMealFormData({ ...mealFormData, servingSize: parseInt(e.target.value) })}
              required
            />
            <div className="flex gap-3 pt-4">
              <Button type="submit" isLoading={isLoading} className="flex-1">
                {selectedMeal ? 'Actualizar' : 'Crear'}
              </Button>
              <Button type="button" variant="outline" onClick={() => { setIsModalOpen(false); resetMealForm(); }}>
                Cancelar
              </Button>
            </div>
          </form>
        </Modal>
      </div>
    </div>
  );
}
