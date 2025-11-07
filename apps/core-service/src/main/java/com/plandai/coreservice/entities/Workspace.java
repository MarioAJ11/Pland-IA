package com.plandai.coreservice.entities;

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
 * Entidad que representa un Workspace (Espacio de Trabajo) en Pland-IA.
 * Un workspace agrupa múltiples proyectos y pertenece a un usuario.
 * 
 * Analogía: Es como una carpeta principal que contiene proyectos.
 * Ejemplo: "Trabajo Personal", "Proyectos Freelance", "Universidad"
 */
@Entity
@Table(name = "workspaces", schema = "core_schema")
@Data  // Genera getters, setters, toString, equals, hashCode automáticamente
@NoArgsConstructor  // Constructor vacío (requerido por JPA)
@AllArgsConstructor  // Constructor con todos los parámetros
public class Workspace {

    /**
     * ID único del workspace.
     * UUID genera automáticamente un identificador único universal.
     * Ejemplo: "550e8400-e29b-41d4-a716-446655440000"
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /**
     * Nombre del workspace.
     * @NotBlank: No puede ser null, vacío ni solo espacios
     * @Size: Mínimo 3 caracteres, máximo 100
     */
    @NotBlank(message = "El nombre del workspace es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Descripción opcional del workspace.
     * Puede ser null (por eso no tiene @NotBlank)
     */
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Column(name = "description", length = 500)
    private String description;

    /**
     * ID del usuario al que pertenece este workspace.
     * Referencia al usuario en auth_schema (otro microservicio).
     * 
     * IMPORTANTE: No usamos @ManyToOne aquí porque el usuario está en OTRO servicio.
     * Solo guardamos su ID para saber a quién pertenece.
     */
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    /**
     * Fecha y hora de creación.
     * @CreationTimestamp: Hibernate asigna automáticamente la fecha actual al crear.
     * updatable = false: Una vez creado, no se puede modificar esta fecha.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Fecha y hora de última actualización.
     * @UpdateTimestamp: Hibernate actualiza automáticamente esta fecha al modificar.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Lista de proyectos que pertenecen a este workspace.
     * 
     * @OneToMany: 1 Workspace tiene MUCHOS Projects
     * mappedBy = "workspace": El campo "workspace" en la clase Project define la relación
     * cascade = CascadeType.ALL: Si borras el workspace, se borran todos sus proyectos
     * orphanRemoval = true: Si quitas un proyecto de la lista, se borra de la DB
     * @JsonManagedReference: Permite serialización de projects (sí se incluyen en JSON)
     * 
     * Analogía: Si eliminas la carpeta principal, se eliminan todas sus subcarpetas.
     */
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Project> projects;
}
