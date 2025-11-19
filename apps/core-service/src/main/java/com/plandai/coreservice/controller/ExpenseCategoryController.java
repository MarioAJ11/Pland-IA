package com.plandai.coreservice.controller;

import com.plandai.coreservice.dto.ExpenseCategoryCreateDto;
import com.plandai.coreservice.model.ExpenseCategory;
import com.plandai.coreservice.service.ExpenseCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expense-categories")
@RequiredArgsConstructor
@Slf4j
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    @PostMapping
    public ResponseEntity<ExpenseCategory> createCategory(@Valid @RequestBody ExpenseCategoryCreateDto createDto) {
        ExpenseCategory category = new ExpenseCategory();
        category.setName(createDto.getName());
        category.setDescription(createDto.getDescription());
        category.setColor(createDto.getColor());
        category.setIcon(createDto.getIcon());
        category.setUserId(createDto.getUserId());

        ExpenseCategory created = expenseCategoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseCategory>> getCategories(@RequestParam UUID userId) {
        List<ExpenseCategory> categories = expenseCategoryService.getCategoriesByUserId(userId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseCategory> getCategoryById(@PathVariable UUID id) {
        ExpenseCategory category = expenseCategoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseCategory> updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody ExpenseCategoryCreateDto updateDto
    ) {
        ExpenseCategory categoryDetails = new ExpenseCategory();
        categoryDetails.setName(updateDto.getName());
        categoryDetails.setDescription(updateDto.getDescription());
        categoryDetails.setColor(updateDto.getColor());
        categoryDetails.setIcon(updateDto.getIcon());

        ExpenseCategory updated = expenseCategoryService.updateCategory(id, categoryDetails);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        expenseCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
