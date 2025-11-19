package com.plandai.coreservice.repository;

import com.plandai.coreservice.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    
    List<Expense> findByUserIdOrderByExpenseDateDesc(UUID userId);
    
    List<Expense> findByUserIdAndExpenseDateBetweenOrderByExpenseDateDesc(
            UUID userId, LocalDate start, LocalDate end);
    
    List<Expense> findByUserIdAndCategoryIdOrderByExpenseDateDesc(
            UUID userId, UUID categoryId);
    
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.userId = :userId AND e.expenseDate BETWEEN :start AND :end")
    BigDecimal sumAmountByUserIdAndDateRange(UUID userId, LocalDate start, LocalDate end);
    
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.userId = :userId AND e.category.id = :categoryId AND e.expenseDate BETWEEN :start AND :end")
    BigDecimal sumAmountByCategoryAndDateRange(UUID userId, UUID categoryId, LocalDate start, LocalDate end);
}
