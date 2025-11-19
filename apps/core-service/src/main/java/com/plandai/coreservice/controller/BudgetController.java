package com.plandai.coreservice.controller;

import com.plandai.coreservice.model.Budget;
import com.plandai.coreservice.model.ExpenseCategory;
import com.plandai.coreservice.service.BudgetService;
import com.plandai.coreservice.service.ExpenseCategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
@Slf4j
public class BudgetController {

    private final BudgetService budgetService;
    private final ExpenseCategoryService expenseCategoryService;

    @PostMapping
    public ResponseEntity<Budget> createBudget(@Valid @RequestBody BudgetCreateDto createDto) {
        ExpenseCategory category = expenseCategoryService.getCategoryById(createDto.getCategoryId());
        
        Budget budget = new Budget();
        budget.setCategory(category);
        budget.setMonthlyLimit(createDto.getMonthlyLimit());
        budget.setMonthYear(createDto.getMonthYear());
        budget.setUserId(createDto.getUserId());

        Budget created = budgetService.createBudget(budget);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Budget>> getBudgets(
            @RequestParam UUID userId,
            @RequestParam(required = false) String monthYear
    ) {
        List<Budget> budgets;

        if (monthYear != null) {
            budgets = budgetService.getBudgetsByUserIdAndMonth(userId, monthYear);
        } else {
            budgets = budgetService.getAllBudgetsByUserId(userId);
        }

        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable UUID id) {
        Budget budget = budgetService.getBudgetById(id);
        return ResponseEntity.ok(budget);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(
            @PathVariable UUID id,
            @Valid @RequestBody BudgetUpdateDto updateDto
    ) {
        Budget budgetDetails = new Budget();
        
        if (updateDto.getMonthlyLimit() != null) {
            budgetDetails.setMonthlyLimit(updateDto.getMonthlyLimit());
        }
        if (updateDto.getMonthYear() != null) {
            budgetDetails.setMonthYear(updateDto.getMonthYear());
        }
        if (updateDto.getCategoryId() != null) {
            ExpenseCategory category = expenseCategoryService.getCategoryById(updateDto.getCategoryId());
            budgetDetails.setCategory(category);
        }

        Budget updated = budgetService.updateBudget(id, budgetDetails);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable UUID id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }

    // DTOs internos
    @Data
    static class BudgetCreateDto {
        @NotNull
        private UUID categoryId;
        @NotNull
        private BigDecimal monthlyLimit;
        @NotNull
        private String monthYear; // YYYY-MM
        @NotNull
        private UUID userId;
    }

    @Data
    static class BudgetUpdateDto {
        private UUID categoryId;
        private BigDecimal monthlyLimit;
        private String monthYear;
    }
}
