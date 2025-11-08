package com.plandai.coreservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

/**
 * DTO para crear un nuevo workspace
 */
@Data
public class WorkspaceCreateDto {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;
    
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String description;
    
    @NotNull(message = "El ID de usuario es obligatorio")
    private UUID userId;
}
