package com.plandai.coreservice.dto;

import com.plandai.coreservice.model.Meal;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class MealPlanCreateDto {
    
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate mealDate;
    
    @NotNull(message = "El tipo de comida es obligatorio")
    private Meal.MealType mealType;
    
    @NotNull(message = "El ID de la receta es obligatorio")
    private UUID mealId;
    
    @NotNull(message = "El ID de usuario es obligatorio")
    private UUID userId;
    
    private String notes;
    
    private Boolean isCompleted = false;
}
