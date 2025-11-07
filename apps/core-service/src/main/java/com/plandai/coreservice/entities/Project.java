package com.plandai.coreservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Entidad que representa un Project (Proyecto) en Pland-IA.
 * Un proyecto pertenece a un workspace y contiene múltiples tareas.
 * 
 * Analogía: Es como una subcarpeta dentro de la carpeta principal (Workspace).
 * Ejemplo: Dentro de "Trabajo Personal" puedes tener "Rediseño Web", "App Móvil", etc.
 */
@Entity
@Table(name = "projects", schema = "core_schema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    /**
     * ID único del proyecto.
     * UUID genera automáticamente un identificador único.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /**
     * Nombre del proyecto.
     * Ejemplo: "Rediseño de la Web", "App de Tareas"
     */
    @NotBlank(message = "El nombre del proyecto es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Descripción opcional del proyecto.
     */
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    @Column(name = "description", length = 1000)
    private String description;

    /**
     * Relación con Workspace (MUCHOS proyectos → 1 workspace).
     * 
     * @ManyToOne: Este proyecto pertenece a UN workspace
     * @JoinColumn: Crea la columna "workspace_id" en la tabla projects
     * nullable = false: Todo proyecto DEBE tener un workspace
     * @JsonBackReference: Evita serialización infinita (no serializa workspace al convertir a JSON)
     * 
     * Analogía: Toda subcarpeta DEBE estar dentro de una carpeta principal.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    @JsonBackReference
    private Workspace workspace;

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
     * Lista de tareas que pertenecen a este proyecto.
     * 
     * @OneToMany: 1 Project tiene MUCHAS Tasks
     * Si borras el proyecto, se borran todas sus tareas (cascade)
     * @JsonManagedReference: Permite serialización de tasks (sí se incluyen en JSON)
     * 
     * Analogía: Si eliminas la subcarpeta, se eliminan todos los archivos dentro.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Task> tasks;
}
