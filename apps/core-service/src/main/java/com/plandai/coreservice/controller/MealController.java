package com.plandai.coreservice.controller;

import com.plandai.coreservice.dto.MealCreateDto;
import com.plandai.coreservice.model.Meal;
import com.plandai.coreservice.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
@Slf4j
public class MealController {

    private final MealService mealService;

    @PostMapping
    public ResponseEntity<Meal> createMeal(@Valid @RequestBody MealCreateDto createDto) {
        Meal meal = new Meal();
        meal.setName(createDto.getName());
        meal.setDescription(createDto.getDescription());
        meal.setMealType(createDto.getMealType());
        meal.setCalories(createDto.getCalories());
        meal.setProteins(createDto.getProteins());
        meal.setCarbs(createDto.getCarbs());
        meal.setFats(createDto.getFats());
        meal.setIngredients(createDto.getIngredients());
        meal.setInstructions(createDto.getInstructions());
        meal.setPrepTimeMinutes(createDto.getPrepTimeMinutes());
        meal.setServingSize(createDto.getServingSize());
        meal.setUserId(createDto.getUserId());
        meal.setIsFavorite(createDto.getIsFavorite());

        Meal created = mealService.createMeal(meal);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Meal>> getMeals(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) Meal.MealType mealType,
            @RequestParam(required = false, defaultValue = "false") boolean favoritesOnly,
            @RequestParam(required = false, defaultValue = "false") boolean publicOnly
    ) {
        List<Meal> meals;

        if (publicOnly) {
            meals = mealService.getPublicMeals();
        } else if (userId != null && favoritesOnly) {
            meals = mealService.getFavoriteMeals(userId);
        } else if (userId != null && mealType != null) {
            meals = mealService.getMealsByType(userId, mealType);
        } else if (userId != null) {
            meals = mealService.getAllAvailableMeals(userId);
        } else {
            meals = mealService.getPublicMeals();
        }

        return ResponseEntity.ok(meals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> getMealById(@PathVariable UUID id) {
        Meal meal = mealService.getMealById(id);
        return ResponseEntity.ok(meal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meal> updateMeal(
            @PathVariable UUID id,
            @Valid @RequestBody MealCreateDto updateDto
    ) {
        Meal mealDetails = new Meal();
        mealDetails.setName(updateDto.getName());
        mealDetails.setDescription(updateDto.getDescription());
        mealDetails.setMealType(updateDto.getMealType());
        mealDetails.setCalories(updateDto.getCalories());
        mealDetails.setProteins(updateDto.getProteins());
        mealDetails.setCarbs(updateDto.getCarbs());
        mealDetails.setFats(updateDto.getFats());
        mealDetails.setIngredients(updateDto.getIngredients());
        mealDetails.setInstructions(updateDto.getInstructions());
        mealDetails.setPrepTimeMinutes(updateDto.getPrepTimeMinutes());
        mealDetails.setServingSize(updateDto.getServingSize());
        mealDetails.setIsFavorite(updateDto.getIsFavorite());

        Meal updated = mealService.updateMeal(id, mealDetails);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable UUID id) {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }
}
