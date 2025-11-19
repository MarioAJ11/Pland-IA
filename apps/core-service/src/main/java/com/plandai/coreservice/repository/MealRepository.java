package com.plandai.coreservice.repository;

import com.plandai.coreservice.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MealRepository extends JpaRepository<Meal, UUID> {
    
    // Recetas públicas (userId = null) o del usuario
    List<Meal> findByUserIdIsNullOrUserIdOrderByNameAsc(UUID userId);
    
    List<Meal> findByUserIdAndMealTypeOrderByNameAsc(UUID userId, Meal.MealType mealType);
    
    List<Meal> findByUserIdAndIsFavoriteTrueOrderByNameAsc(UUID userId);
    
    // Solo recetas públicas
    List<Meal> findByUserIdIsNullOrderByNameAsc();
    
    // Solo recetas del usuario
    List<Meal> findByUserIdOrderByNameAsc(UUID userId);
}
