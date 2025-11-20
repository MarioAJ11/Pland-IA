'use client';

import { useState, useEffect } from 'react';
import { useAuthStore } from '@/store/authStore';
import { expenseService } from '@/services/expenseService';
import { expenseCategoryService } from '@/services/expenseCategoryService';
import { Expense, ExpenseCreateDto, ExpenseCategory } from '@/types';
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/Card';
import { Button } from '@/components/ui/Button';
import { Input } from '@/components/ui/Input';
import { Modal } from '@/components/ui/Modal';
import { Wallet, Plus, Trash2, Edit, TrendingUp } from 'lucide-react';
import { formatCurrency, formatDate } from '@/lib/utils';

export default function ExpensesPage() {
  const { user } = useAuthStore();
  const [expenses, setExpenses] = useState<Expense[]>([]);
  const [categories, setCategories] = useState<ExpenseCategory[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedExpense, setSelectedExpense] = useState<Expense | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [totalExpenses, setTotalExpenses] = useState(0);

  const [formData, setFormData] = useState<Partial<ExpenseCreateDto>>({
    description: '',
    amount: 0,
    expenseDate: new Date().toISOString().split('T')[0],
    categoryId: '',
    paymentMethod: 'CASH',
    userId: user?.id || '',
  });

  useEffect(() => {
    loadExpenses();
    loadCategories();
  }, [user?.id]);

  const loadExpenses = async () => {
    if (!user?.id) return;
    try {
      const data = await expenseService.getByUserId(user.id);
      setExpenses(data);
      
      const startDate = new Date();
      startDate.setMonth(startDate.getMonth() - 1);
      const total = await expenseService.getTotalByDateRange(
        user.id,
        startDate.toISOString().split('T')[0],
        new Date().toISOString().split('T')[0]
      );
      setTotalExpenses(total.total);
    } catch (error) {
      console.error('Error cargando gastos:', error);
    }
  };

  const loadCategories = async () => {
    if (!user?.id) return;
    try {
      const data = await expenseCategoryService.getByUserId(user.id);
      setCategories(data);
    } catch (error) {
      console.error('Error cargando categorías:', error);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!user?.id) return;

    setIsLoading(true);
    try {
      if (selectedExpense) {
        const updated = await expenseService.update(selectedExpense.id, formData);
        setExpenses(expenses.map(exp => exp.id === updated.id ? updated : exp));
      } else {
        const created = await expenseService.create(formData as ExpenseCreateDto);
        setExpenses([...expenses, created]);
      }
      setIsModalOpen(false);
      resetForm();
      loadExpenses();
    } catch (error) {
      console.error('Error guardando gasto:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleDelete = async (id: string) => {
    if (!confirm('¿Eliminar este gasto?')) return;
    try {
      await expenseService.delete(id);
      setExpenses(expenses.filter(exp => exp.id !== id));
      loadExpenses();
    } catch (error) {
      console.error('Error eliminando gasto:', error);
    }
  };

  const resetForm = () => {
    setFormData({
      description: '',
      amount: 0,
      expenseDate: new Date().toISOString().split('T')[0],
      categoryId: '',
      paymentMethod: 'CASH',
      userId: user?.id || '',
    });
    setSelectedExpense(null);
  };

  const openEditModal = (expense: Expense) => {
    setSelectedExpense(expense);
    setFormData({
      description: expense.description,
      amount: expense.amount,
      expenseDate: expense.expenseDate,
      categoryId: expense.categoryId,
      paymentMethod: expense.paymentMethod,
      userId: expense.userId,
    });
    setIsModalOpen(true);
  };

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <div className="max-w-7xl mx-auto">
        <div className="flex justify-between items-center mb-8">
          <div className="flex items-center gap-3">
            <Wallet className="w-8 h-8 text-indigo-600" />
            <h1 className="text-3xl font-bold text-gray-900">Gastos</h1>
          </div>
          <Button onClick={() => { resetForm(); setIsModalOpen(true); }}>
            <Plus className="w-4 h-4 mr-2" />
            Nuevo Gasto
          </Button>
        </div>

        <Card className="mb-6">
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <TrendingUp className="w-5 h-5 text-green-600" />
              Total Último Mes
            </CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-3xl font-bold text-gray-900">{formatCurrency(totalExpenses)}</p>
          </CardContent>
        </Card>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {expenses.map(expense => (
            <Card key={expense.id}>
              <CardHeader>
                <CardTitle>{expense.description}</CardTitle>
                <p className="text-sm text-gray-500 mt-1">
                  {formatDate(expense.expenseDate)}
                </p>
              </CardHeader>
              <CardContent>
                <p className="text-2xl font-bold text-indigo-600 mb-3">
                  {formatCurrency(expense.amount)}
                </p>
                <p className="text-sm text-gray-600 mb-3">
                  Método: {expense.paymentMethod}
                </p>
                <div className="flex gap-2">
                  <Button size="sm" variant="outline" onClick={() => openEditModal(expense)}>
                    <Edit className="w-4 h-4" />
                  </Button>
                  <Button size="sm" variant="danger" onClick={() => handleDelete(expense.id)}>
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
          title={selectedExpense ? 'Editar Gasto' : 'Nuevo Gasto'}
        >
          <form onSubmit={handleSubmit} className="space-y-4">
            <Input
              label="Descripción"
              value={formData.description}
              onChange={(e) => setFormData({ ...formData, description: e.target.value })}
              required
            />
            <Input
              label="Cantidad"
              type="number"
              step="0.01"
              value={formData.amount}
              onChange={(e) => setFormData({ ...formData, amount: parseFloat(e.target.value) })}
              required
            />
            <Input
              label="Fecha"
              type="date"
              value={formData.expenseDate}
              onChange={(e) => setFormData({ ...formData, expenseDate: e.target.value })}
              required
            />
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Categoría
              </label>
              <select
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500"
                value={formData.categoryId}
                onChange={(e) => setFormData({ ...formData, categoryId: e.target.value })}
                required
              >
                <option value="">Seleccionar categoría</option>
                {categories.map(cat => (
                  <option key={cat.id} value={cat.id}>{cat.name}</option>
                ))}
              </select>
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Método de Pago
              </label>
              <select
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500"
                value={formData.paymentMethod}
                onChange={(e) => setFormData({ ...formData, paymentMethod: e.target.value as any })}
                required
              >
                <option value="CASH">Efectivo</option>
                <option value="DEBIT_CARD">Tarjeta de Débito</option>
                <option value="CREDIT_CARD">Tarjeta de Crédito</option>
                <option value="TRANSFER">Transferencia</option>
                <option value="OTHER">Otro</option>
              </select>
            </div>
            <div className="flex gap-3 pt-4">
              <Button type="submit" isLoading={isLoading} className="flex-1">
                {selectedExpense ? 'Actualizar' : 'Crear'}
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
