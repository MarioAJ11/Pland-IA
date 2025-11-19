package com.plandai.coreservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "meals", schema = "core_schema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type", length = 20)
    private MealType mealType;

    private Integer calories;

    @Column(precision = 5, scale = 2)
    private BigDecimal proteins; // gramos

    @Column(precision = 5, scale = 2)
    private BigDecimal carbs; // gramos

    @Column(precision = 5, scale = 2)
    private BigDecimal fats; // gramos

    @Column(columnDefinition = "TEXT[]")
    private String[] ingredients;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @Column(name = "prep_time_minutes")
    private Integer prepTimeMinutes;

    @Column(name = "serving_size")
    private Integer servingSize = 1;

    @Column(name = "user_id")
    private UUID userId; // NULL = receta pública

    @Column(name = "is_favorite")
    private Boolean isFavorite = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum MealType {
        BREAKFAST,
        LUNCH,
        DINNER,
        SNACK
    }
}
