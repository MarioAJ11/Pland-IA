package com.plandai.coreservice.repositories;

import com.plandai.coreservice.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para la entidad Project.
 * 
 * Spring Data genera la implementación automáticamente.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    /**
     * Busca todos los proyectos de un workspace específico.
     * 
     * SQL generado:
     * SELECT * FROM projects WHERE workspace_id = ?
     * 
     * Nota: workspace.id se traduce a workspace_id por la convención de JPA
     */
    List<Project> findByWorkspaceId(UUID workspaceId);

    /**
     * Busca un proyecto por nombre dentro de un workspace.
     * 
     * SQL generado:
     * SELECT * FROM projects WHERE name = ? AND workspace_id = ?
     */
    Optional<Project> findByNameAndWorkspaceId(String name, UUID workspaceId);

    /**
     * Verifica si existe un proyecto con ese nombre en el workspace.
     * 
     * Útil para evitar nombres duplicados dentro del mismo workspace.
     */
    boolean existsByNameAndWorkspaceId(String name, UUID workspaceId);

    /**
     * Cuenta cuántos proyectos tiene un workspace.
     * 
     * SQL generado:
     * SELECT COUNT(*) FROM projects WHERE workspace_id = ?
     */
    long countByWorkspaceId(UUID workspaceId);
}
