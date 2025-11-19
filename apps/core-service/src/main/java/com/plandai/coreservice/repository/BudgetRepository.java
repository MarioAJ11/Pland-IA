package com.plandai.coreservice.repository;

import com.plandai.coreservice.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, UUID> {
    
    List<Budget> findByUserIdAndMonthYearOrderByCategoryNameAsc(UUID userId, String monthYear);
    
    Optional<Budget> findByUserIdAndCategoryIdAndMonthYear(UUID userId, UUID categoryId, String monthYear);
    
    List<Budget> findByUserIdOrderByMonthYearDesc(UUID userId);
}
