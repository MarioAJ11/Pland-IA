package com.plandai.coreservice.service;

import com.plandai.coreservice.exception.ResourceNotFoundException;
import com.plandai.coreservice.model.MealPlan;
import com.plandai.coreservice.repository.MealPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MealPlanService {

    private final MealPlanRepository mealPlanRepository;

    @Transactional
    public MealPlan createMealPlan(MealPlan mealPlan) {
        log.info("Creating meal plan for user: {} on date: {} ({})", 
                mealPlan.getUserId(), mealPlan.getMealDate(), mealPlan.getMealType());
        return mealPlanRepository.save(mealPlan);
    }

    @Transactional(readOnly = true)
    public MealPlan getMealPlanById(UUID id) {
        return mealPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MealPlan", id));
    }

    @Transactional(readOnly = true)
    public List<MealPlan> getMealPlansByDateRange(UUID userId, LocalDate startDate, LocalDate endDate) {
        return mealPlanRepository.findByUserIdAndMealDateBetweenOrderByMealDateAsc(userId, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<MealPlan> getMealPlansByDate(UUID userId, LocalDate date) {
        return mealPlanRepository.findByUserIdAndMealDateOrderByMealTypeAsc(userId, date);
    }

    @Transactional
    public MealPlan updateMealPlan(UUID id, MealPlan mealPlanDetails) {
        MealPlan mealPlan = getMealPlanById(id);
        
        if (mealPlanDetails.getMeal() != null) {
            mealPlan.setMeal(mealPlanDetails.getMeal());
        }
        if (mealPlanDetails.getNotes() != null) {
            mealPlan.setNotes(mealPlanDetails.getNotes());
        }
        if (mealPlanDetails.getIsCompleted() != null) {
            mealPlan.setIsCompleted(mealPlanDetails.getIsCompleted());
        }
        if (mealPlanDetails.getMealDate() != null) {
            mealPlan.setMealDate(mealPlanDetails.getMealDate());
        }
        if (mealPlanDetails.getMealType() != null) {
            mealPlan.setMealType(mealPlanDetails.getMealType());
        }

        log.info("Updating meal plan: {}", id);
        return mealPlanRepository.save(mealPlan);
    }

    @Transactional
    public void deleteMealPlan(UUID id) {
        if (!mealPlanRepository.existsById(id)) {
            throw new ResourceNotFoundException("MealPlan", id);
        }
        log.info("Deleting meal plan: {}", id);
        mealPlanRepository.deleteById(id);
    }
}
