package com.plandai.coreservice.controller;

import com.plandai.coreservice.dto.ExpenseCreateDto;
import com.plandai.coreservice.model.Expense;
import com.plandai.coreservice.model.ExpenseCategory;
import com.plandai.coreservice.service.ExpenseService;
import com.plandai.coreservice.service.ExpenseCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@Slf4j
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseCategoryService expenseCategoryService;

    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody ExpenseCreateDto createDto) {
        Expense expense = new Expense();
        expense.setAmount(createDto.getAmount());
        expense.setDescription(createDto.getDescription());
        expense.setExpenseDate(createDto.getExpenseDate());
        expense.setPaymentMethod(createDto.getPaymentMethod());
        expense.setUserId(createDto.getUserId());
        expense.setIsRecurring(createDto.getIsRecurring());
        expense.setRecurrencePeriod(createDto.getRecurrencePeriod());

        if (createDto.getCategoryId() != null) {
            ExpenseCategory category = expenseCategoryService.getCategoryById(createDto.getCategoryId());
            expense.setCategory(category);
        }

        Expense created = expenseService.createExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(
            @RequestParam UUID userId,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<Expense> expenses;

        if (categoryId != null) {
            expenses = expenseService.getExpensesByCategory(userId, categoryId);
        } else if (startDate != null && endDate != null) {
            expenses = expenseService.getExpensesByUserIdAndDateRange(userId, startDate, endDate);
        } else {
            expenses = expenseService.getExpensesByUserId(userId);
        }

        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/total")
    public ResponseEntity<Map<String, Object>> getTotalExpenses(
            @RequestParam UUID userId,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        BigDecimal total;

        if (categoryId != null) {
            total = expenseService.getTotalExpensesByCategory(userId, categoryId, startDate, endDate);
        } else {
            total = expenseService.getTotalExpensesByDateRange(userId, startDate, endDate);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("total", total);
        response.put("startDate", startDate);
        response.put("endDate", endDate);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable UUID id) {
        Expense expense = expenseService.getExpenseById(id);
        return ResponseEntity.ok(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(
            @PathVariable UUID id,
            @Valid @RequestBody ExpenseCreateDto updateDto
    ) {
        Expense expenseDetails = new Expense();
        expenseDetails.setAmount(updateDto.getAmount());
        expenseDetails.setDescription(updateDto.getDescription());
        expenseDetails.setExpenseDate(updateDto.getExpenseDate());
        expenseDetails.setPaymentMethod(updateDto.getPaymentMethod());
        expenseDetails.setIsRecurring(updateDto.getIsRecurring());
        expenseDetails.setRecurrencePeriod(updateDto.getRecurrencePeriod());

        if (updateDto.getCategoryId() != null) {
            ExpenseCategory category = expenseCategoryService.getCategoryById(updateDto.getCategoryId());
            expenseDetails.setCategory(category);
        }

        Expense updated = expenseService.updateExpense(id, expenseDetails);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
