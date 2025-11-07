package com.plandai.coreservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad que representa una Task (Tarea) en Pland-IA.
 * Una tarea pertenece a un proyecto y puede tener estado, prioridad y fecha límite.
 * 
 * Analogía: Es como un archivo dentro de una subcarpeta (Project).
 * Ejemplo: "Diseñar mockups", "Implementar login", "Escribir tests"
 */
@Entity
@Table(name = "tasks", schema = "core_schema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    /**
     * ID único de la tarea.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /**
     * Título de la tarea.
     * Ejemplo: "Diseñar mockups del login"
     */
    @NotBlank(message = "El título de la tarea es obligatorio")
    @Size(min = 3, max = 200, message = "El título debe tener entre 3 y 200 caracteres")
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    /**
     * Descripción detallada de la tarea (opcional).
     */
    @Size(max = 2000, message = "La descripción no puede exceder 2000 caracteres")
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * Estado de la tarea.
     * Usamos un Enum en lugar de String para tener valores controlados.
     * 
     * Ventaja: No puedes poner "COMPLETO" por error (solo acepta TO_DO, IN_PROGRESS, DONE)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TaskStatus status = TaskStatus.TO_DO;  // Por defecto: TO_DO

    /**
     * Prioridad de la tarea.
     * También usamos Enum para control estricto.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 20)
    private TaskPriority priority = TaskPriority.MEDIUM;  // Por defecto: MEDIUM

    /**
     * Fecha límite de la tarea (opcional).
     * Ejemplo: 2025-12-31
     */
    @Column(name = "due_date")
    private LocalDate dueDate;

    /**
     * ID del usuario asignado a esta tarea (opcional).
     * Referencia al usuario en auth_schema (otro microservicio).
     * 
     * Si es null = tarea sin asignar.
     */
    @Column(name = "assigned_to")
    private UUID assignedTo;

    /**
     * Relación con Project (MUCHAS tareas → 1 proyecto).
     * 
     * @ManyToOne: Esta tarea pertenece a UN proyecto
     * nullable = false: Toda tarea DEBE estar en un proyecto
     * @JsonBackReference: Evita serialización infinita (no serializa project al convertir a JSON)
     * 
     * Analogía: Todo archivo DEBE estar dentro de una subcarpeta.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonBackReference
    private Project project;

    /**
     * Fecha de creación automática.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Fecha de última actualización automática.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Enum para los estados posibles de una tarea.
     */
    public enum TaskStatus {
        TO_DO,        // Por hacer
        IN_PROGRESS,  // En progreso
        DONE          // Completada
    }

    /**
     * Enum para las prioridades posibles de una tarea.
     */
    public enum TaskPriority {
        LOW,      // Baja
        MEDIUM,   // Media
        HIGH,     // Alta
        URGENT    // Urgente
    }
}
