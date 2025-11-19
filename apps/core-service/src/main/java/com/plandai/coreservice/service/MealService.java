package com.plandai.coreservice.service;

import com.plandai.coreservice.exception.ResourceNotFoundException;
import com.plandai.coreservice.model.Meal;
import com.plandai.coreservice.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MealService {

    private final MealRepository mealRepository;

    @Transactional
    public Meal createMeal(Meal meal) {
        log.info("Creating meal: {} ({})", meal.getName(), 
                meal.getUserId() != null ? "personal" : "public");
        return mealRepository.save(meal);
    }

    @Transactional(readOnly = true)
    public Meal getMealById(UUID id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meal", id));
    }

    @Transactional(readOnly = true)
    public List<Meal> getAllAvailableMeals(UUID userId) {
        // Retorna recetas públicas + recetas del usuario
        return mealRepository.findByUserIdIsNullOrUserIdOrderByNameAsc(userId);
    }

    @Transactional(readOnly = true)
    public List<Meal> getPublicMeals() {
        return mealRepository.findByUserIdIsNullOrderByNameAsc();
    }

    @Transactional(readOnly = true)
    public List<Meal> getUserMeals(UUID userId) {
        return mealRepository.findByUserIdOrderByNameAsc(userId);
    }

    @Transactional(readOnly = true)
    public List<Meal> getMealsByType(UUID userId, Meal.MealType mealType) {
        return mealRepository.findByUserIdAndMealTypeOrderByNameAsc(userId, mealType);
    }

    @Transactional(readOnly = true)
    public List<Meal> getFavoriteMeals(UUID userId) {
        return mealRepository.findByUserIdAndIsFavoriteTrueOrderByNameAsc(userId);
    }

    @Transactional
    public Meal updateMeal(UUID id, Meal mealDetails) {
        Meal meal = getMealById(id);
        
        if (mealDetails.getName() != null) {
            meal.setName(mealDetails.getName());
        }
        if (mealDetails.getDescription() != null) {
            meal.setDescription(mealDetails.getDescription());
        }
        if (mealDetails.getMealType() != null) {
            meal.setMealType(mealDetails.getMealType());
        }
        if (mealDetails.getCalories() != null) {
            meal.setCalories(mealDetails.getCalories());
        }
        if (mealDetails.getProteins() != null) {
            meal.setProteins(mealDetails.getProteins());
        }
        if (mealDetails.getCarbs() != null) {
            meal.setCarbs(mealDetails.getCarbs());
        }
        if (mealDetails.getFats() != null) {
            meal.setFats(mealDetails.getFats());
        }
        if (mealDetails.getIngredients() != null) {
            meal.setIngredients(mealDetails.getIngredients());
        }
        if (mealDetails.getInstructions() != null) {
            meal.setInstructions(mealDetails.getInstructions());
        }
        if (mealDetails.getPrepTimeMinutes() != null) {
            meal.setPrepTimeMinutes(mealDetails.getPrepTimeMinutes());
        }
        if (mealDetails.getServingSize() != null) {
            meal.setServingSize(mealDetails.getServingSize());
        }
        if (mealDetails.getIsFavorite() != null) {
            meal.setIsFavorite(mealDetails.getIsFavorite());
        }

        log.info("Updating meal: {}", id);
        return mealRepository.save(meal);
    }

    @Transactional
    public void deleteMeal(UUID id) {
        if (!mealRepository.existsById(id)) {
            throw new ResourceNotFoundException("Meal", id);
        }
        log.info("Deleting meal: {}", id);
        mealRepository.deleteById(id);
    }
}
