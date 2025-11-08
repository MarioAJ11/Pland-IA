package com.plandai.coreservice.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO para actualizar un workspace existente
 */
@Data
public class WorkspaceUpdateDto {
    
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;
    
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String description;
}
