package com.plandai.coreservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class ExpenseCategoryCreateDto {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String name;
    
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String description;
    
    @Size(max = 20, message = "El color no puede exceder 20 caracteres")
    private String color = "#6366f1";
    
    @Size(max = 50, message = "El ícono no puede exceder 50 caracteres")
    private String icon;
    
    @NotNull(message = "El ID de usuario es obligatorio")
    private UUID userId;
}
