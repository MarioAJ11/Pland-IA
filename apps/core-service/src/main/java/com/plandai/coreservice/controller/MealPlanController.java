package com.plandai.coreservice.controller;

import com.plandai.coreservice.dto.MealPlanCreateDto;
import com.plandai.coreservice.model.Meal;
import com.plandai.coreservice.model.MealPlan;
import com.plandai.coreservice.service.MealPlanService;
import com.plandai.coreservice.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/meal-plans")
@RequiredArgsConstructor
@Slf4j
public class MealPlanController {

    private final MealPlanService mealPlanService;
    private final MealService mealService;

    @PostMapping
    public ResponseEntity<MealPlan> createMealPlan(@Valid @RequestBody MealPlanCreateDto createDto) {
        Meal meal = mealService.getMealById(createDto.getMealId());
        
        MealPlan mealPlan = new MealPlan();
        mealPlan.setMealDate(createDto.getMealDate());
        mealPlan.setMealType(createDto.getMealType());
        mealPlan.setMeal(meal);
        mealPlan.setUserId(createDto.getUserId());
        mealPlan.setNotes(createDto.getNotes());
        mealPlan.setIsCompleted(createDto.getIsCompleted());

        MealPlan created = mealPlanService.createMealPlan(mealPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<MealPlan>> getMealPlans(
            @RequestParam UUID userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<MealPlan> mealPlans;

        if (date != null) {
            mealPlans = mealPlanService.getMealPlansByDate(userId, date);
        } else if (startDate != null && endDate != null) {
            mealPlans = mealPlanService.getMealPlansByDateRange(userId, startDate, endDate);
        } else {
            // Por defecto: semana actual
            LocalDate now = LocalDate.now();
            LocalDate weekStart = now.minusDays(now.getDayOfWeek().getValue() - 1);
            LocalDate weekEnd = weekStart.plusDays(6);
            mealPlans = mealPlanService.getMealPlansByDateRange(userId, weekStart, weekEnd);
        }

        return ResponseEntity.ok(mealPlans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealPlan> getMealPlanById(@PathVariable UUID id) {
        MealPlan mealPlan = mealPlanService.getMealPlanById(id);
        return ResponseEntity.ok(mealPlan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealPlan> updateMealPlan(
            @PathVariable UUID id,
            @Valid @RequestBody MealPlanCreateDto updateDto
    ) {
        MealPlan mealPlanDetails = new MealPlan();
        
        if (updateDto.getMealId() != null) {
            Meal meal = mealService.getMealById(updateDto.getMealId());
            mealPlanDetails.setMeal(meal);
        }
        if (updateDto.getMealDate() != null) {
            mealPlanDetails.setMealDate(updateDto.getMealDate());
        }
        if (updateDto.getMealType() != null) {
            mealPlanDetails.setMealType(updateDto.getMealType());
        }
        if (updateDto.getNotes() != null) {
            mealPlanDetails.setNotes(updateDto.getNotes());
        }
        if (updateDto.getIsCompleted() != null) {
            mealPlanDetails.setIsCompleted(updateDto.getIsCompleted());
        }

        MealPlan updated = mealPlanService.updateMealPlan(id, mealPlanDetails);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealPlan(@PathVariable UUID id) {
        mealPlanService.deleteMealPlan(id);
        return ResponseEntity.noContent().build();
    }
}
