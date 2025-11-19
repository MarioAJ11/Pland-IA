package com.plandai.coreservice.service;

import com.plandai.coreservice.exception.ResourceNotFoundException;
import com.plandai.coreservice.model.ExpenseCategory;
import com.plandai.coreservice.repository.ExpenseCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseCategoryService {

    private final ExpenseCategoryRepository expenseCategoryRepository;

    @Transactional
    public ExpenseCategory createCategory(ExpenseCategory category) {
        // Verificar que no exista otra categoría con el mismo nombre para este usuario
        if (expenseCategoryRepository.existsByUserIdAndName(category.getUserId(), category.getName())) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + category.getName());
        }
        log.info("Creating expense category: {} for user: {}", category.getName(), category.getUserId());
        return expenseCategoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public ExpenseCategory getCategoryById(UUID id) {
        return expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExpenseCategory", id));
    }

    @Transactional(readOnly = true)
    public List<ExpenseCategory> getCategoriesByUserId(UUID userId) {
        return expenseCategoryRepository.findByUserIdOrderByNameAsc(userId);
    }

    @Transactional
    public ExpenseCategory updateCategory(UUID id, ExpenseCategory categoryDetails) {
        ExpenseCategory category = getCategoryById(id);
        
        if (categoryDetails.getName() != null && !categoryDetails.getName().equals(category.getName())) {
            // Verificar que el nuevo nombre no esté en uso
            if (expenseCategoryRepository.existsByUserIdAndName(category.getUserId(), categoryDetails.getName())) {
                throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + categoryDetails.getName());
            }
            category.setName(categoryDetails.getName());
        }
        if (categoryDetails.getDescription() != null) {
            category.setDescription(categoryDetails.getDescription());
        }
        if (categoryDetails.getColor() != null) {
            category.setColor(categoryDetails.getColor());
        }
        if (categoryDetails.getIcon() != null) {
            category.setIcon(categoryDetails.getIcon());
        }

        log.info("Updating expense category: {}", id);
        return expenseCategoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(UUID id) {
        if (!expenseCategoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("ExpenseCategory", id);
        }
        log.info("Deleting expense category: {}", id);
        expenseCategoryRepository.deleteById(id);
    }
}
