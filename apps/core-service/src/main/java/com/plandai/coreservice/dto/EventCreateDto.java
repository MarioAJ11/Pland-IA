package com.plandai.coreservice.dto;

import com.plandai.coreservice.model.Event;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EventCreateDto {
    
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String title;
    
    @Size(max = 2000, message = "La descripción no puede exceder 2000 caracteres")
    private String description;
    
    @NotNull(message = "La fecha y hora de inicio es obligatoria")
    private LocalDateTime startTime;
    
    @NotNull(message = "La fecha y hora de fin es obligatoria")
    private LocalDateTime endTime;
    
    @NotNull(message = "El tipo de evento es obligatorio")
    private Event.EventType eventType;
    
    @Size(max = 200, message = "La ubicación no puede exceder 200 caracteres")
    private String location;
    
    private Boolean isAllDay = false;
    
    @NotNull(message = "El ID de usuario es obligatorio")
    private UUID userId;
    
    private UUID projectId;
}
