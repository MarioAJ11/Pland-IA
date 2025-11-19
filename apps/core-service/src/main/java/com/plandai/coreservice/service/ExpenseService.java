package com.plandai.coreservice.service;

import com.plandai.coreservice.exception.ResourceNotFoundException;
import com.plandai.coreservice.model.Expense;
import com.plandai.coreservice.model.ExpenseCategory;
import com.plandai.coreservice.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryService expenseCategoryService;

    @Transactional
    public Expense createExpense(Expense expense) {
        log.info("Creating expense: {} for user: {}", expense.getAmount(), expense.getUserId());
        return expenseRepository.save(expense);
    }

    @Transactional(readOnly = true)
    public Expense getExpenseById(UUID id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", id));
    }

    @Transactional(readOnly = true)
    public List<Expense> getExpensesByUserId(UUID userId) {
        return expenseRepository.findByUserIdOrderByExpenseDateDesc(userId);
    }

    @Transactional(readOnly = true)
    public List<Expense> getExpensesByUserIdAndDateRange(UUID userId, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUserIdAndExpenseDateBetweenOrderByExpenseDateDesc(userId, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<Expense> getExpensesByCategory(UUID userId, UUID categoryId) {
        return expenseRepository.findByUserIdAndCategoryIdOrderByExpenseDateDesc(userId, categoryId);
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalExpensesByDateRange(UUID userId, LocalDate startDate, LocalDate endDate) {
        BigDecimal total = expenseRepository.sumAmountByUserIdAndDateRange(userId, startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalExpensesByCategory(UUID userId, UUID categoryId, LocalDate startDate, LocalDate endDate) {
        BigDecimal total = expenseRepository.sumAmountByCategoryAndDateRange(userId, categoryId, startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Transactional
    public Expense updateExpense(UUID id, Expense expenseDetails) {
        Expense expense = getExpenseById(id);
        
        if (expenseDetails.getAmount() != null) {
            expense.setAmount(expenseDetails.getAmount());
        }
        if (expenseDetails.getDescription() != null) {
            expense.setDescription(expenseDetails.getDescription());
        }
        if (expenseDetails.getExpenseDate() != null) {
            expense.setExpenseDate(expenseDetails.getExpenseDate());
        }
        if (expenseDetails.getPaymentMethod() != null) {
            expense.setPaymentMethod(expenseDetails.getPaymentMethod());
        }
        if (expenseDetails.getCategory() != null) {
            expense.setCategory(expenseDetails.getCategory());
        }
        if (expenseDetails.getIsRecurring() != null) {
            expense.setIsRecurring(expenseDetails.getIsRecurring());
        }
        if (expenseDetails.getRecurrencePeriod() != null) {
            expense.setRecurrencePeriod(expenseDetails.getRecurrencePeriod());
        }

        log.info("Updating expense: {}", id);
        return expenseRepository.save(expense);
    }

    @Transactional
    public void deleteExpense(UUID id) {
        if (!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Expense", id);
        }
        log.info("Deleting expense: {}", id);
        expenseRepository.deleteById(id);
    }
}
