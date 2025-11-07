package com.plandai.coreservice.repositories;

import com.plandai.coreservice.entities.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para la entidad Workspace.
 * 
 * Spring Data JPA genera automáticamente la implementación de esta interfaz.
 * NO necesitas escribir código, solo declaras los métodos que necesitas.
 * 
 * Métodos heredados de JpaRepository (ya disponibles sin código):
 * - save(workspace)          → INSERT o UPDATE
 * - findById(id)             → SELECT WHERE id = ?
 * - findAll()                → SELECT * FROM workspaces
 * - deleteById(id)           → DELETE WHERE id = ?
 * - existsById(id)           → SELECT COUNT(*) WHERE id = ?
 * - count()                  → SELECT COUNT(*) FROM workspaces
 */
@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {

    /**
     * Busca todos los workspaces de un usuario específico.
     * 
     * Spring Data genera automáticamente el SQL:
     * SELECT * FROM workspaces WHERE user_id = ?
     * 
     * Naming convention: findBy + NombreDelCampo
     */
    List<Workspace> findByUserId(UUID userId);

    /**
     * Busca un workspace por nombre y usuario.
     * 
     * SQL generado automáticamente:
     * SELECT * FROM workspaces WHERE name = ? AND user_id = ?
     * 
     * Optional<T> = Puede devolver un resultado o estar vacío (no null)
     */
    Optional<Workspace> findByNameAndUserId(String name, UUID userId);

    /**
     * Verifica si existe un workspace con ese nombre para un usuario.
     * 
     * SQL generado:
     * SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END 
     * FROM workspaces WHERE name = ? AND user_id = ?
     * 
     * Útil para validaciones: "¿Ya existe un workspace con este nombre?"
     */
    boolean existsByNameAndUserId(String name, UUID userId);
}
