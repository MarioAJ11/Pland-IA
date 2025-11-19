package com.plandai.coreservice.repository;

import com.plandai.coreservice.model.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, UUID> {
    
    List<MealPlan> findByUserIdAndMealDateBetweenOrderByMealDateAsc(
            UUID userId, LocalDate start, LocalDate end);
    
    List<MealPlan> findByUserIdAndMealDateOrderByMealTypeAsc(
            UUID userId, LocalDate mealDate);
    
    Optional<MealPlan> findByUserIdAndMealDateAndMealType(
            UUID userId, LocalDate mealDate, com.plandai.coreservice.model.Meal.MealType mealType);
}
