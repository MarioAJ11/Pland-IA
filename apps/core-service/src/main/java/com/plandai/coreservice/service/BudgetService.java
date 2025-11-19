package com.plandai.coreservice.service;

import com.plandai.coreservice.exception.ResourceNotFoundException;
import com.plandai.coreservice.model.Budget;
import com.plandai.coreservice.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BudgetService {

    private final BudgetRepository budgetRepository;

    @Transactional
    public Budget createBudget(Budget budget) {
        log.info("Creating budget for user: {} category: {} month: {}", 
                budget.getUserId(), budget.getCategory().getId(), budget.getMonthYear());
        return budgetRepository.save(budget);
    }

    @Transactional(readOnly = true)
    public Budget getBudgetById(UUID id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget", id));
    }

    @Transactional(readOnly = true)
    public List<Budget> getBudgetsByUserIdAndMonth(UUID userId, String monthYear) {
        return budgetRepository.findByUserIdAndMonthYearOrderByCategoryNameAsc(userId, monthYear);
    }

    @Transactional(readOnly = true)
    public List<Budget> getAllBudgetsByUserId(UUID userId) {
        return budgetRepository.findByUserIdOrderByMonthYearDesc(userId);
    }

    @Transactional
    public Budget updateBudget(UUID id, Budget budgetDetails) {
        Budget budget = getBudgetById(id);
        
        if (budgetDetails.getMonthlyLimit() != null) {
            budget.setMonthlyLimit(budgetDetails.getMonthlyLimit());
        }
        if (budgetDetails.getMonthYear() != null) {
            budget.setMonthYear(budgetDetails.getMonthYear());
        }
        if (budgetDetails.getCategory() != null) {
            budget.setCategory(budgetDetails.getCategory());
        }

        log.info("Updating budget: {}", id);
        return budgetRepository.save(budget);
    }

    @Transactional
    public void deleteBudget(UUID id) {
        if (!budgetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Budget", id);
        }
        log.info("Deleting budget: {}", id);
        budgetRepository.deleteById(id);
    }
}
