package com.plandai.coreservice.dto;

import com.plandai.coreservice.model.Meal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class MealCreateDto {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String name;
    
    private String description;
    
    private Meal.MealType mealType;
    
    private Integer calories;
    
    private BigDecimal proteins;
    
    private BigDecimal carbs;
    
    private BigDecimal fats;
    
    private String[] ingredients;
    
    private String instructions;
    
    private Integer prepTimeMinutes;
    
    private Integer servingSize = 1;
    
    private UUID userId; // null = receta pública
    
    private Boolean isFavorite = false;
}
