package com.plandai.coreservice.services;

import com.plandai.coreservice.entities.Project;
import com.plandai.coreservice.entities.Workspace;
import com.plandai.coreservice.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Servicio de negocio para Project.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final WorkspaceService workspaceService;  // Inyectamos otro servicio

    /**
     * Crea un nuevo proyecto dentro de un workspace.
     * 
     * Validaciones:
     * 1. El workspace debe existir
     * 2. No puede haber 2 proyectos con el mismo nombre en el mismo workspace
     */
    @Transactional
    public Project createProject(UUID workspaceId, Project project) {
        log.info("📝 Creando proyecto: {} en workspace: {}", project.getName(), workspaceId);

        // 1. Verificar que el workspace existe
        Workspace workspace = workspaceService.getWorkspaceById(workspaceId);

        // 2. Validar no duplicados
        if (projectRepository.existsByNameAndWorkspaceId(project.getName(), workspaceId)) {
            log.warn("⚠️ Intento de crear proyecto duplicado: {} en workspace: {}", 
                     project.getName(), workspaceId);
            throw new IllegalArgumentException("Ya existe un proyecto con ese nombre en este workspace");
        }

        // 3. Asignar workspace al proyecto
        project.setWorkspace(workspace);

        Project savedProject = projectRepository.save(project);
        log.info("✅ Proyecto creado exitosamente con ID: {}", savedProject.getId());
        return savedProject;
    }

    /**
     * Obtiene un proyecto por ID.
     */
    @Transactional(readOnly = true)
    public Project getProjectById(UUID id) {
        log.info("🔍 Buscando proyecto con ID: {}", id);
        return projectRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("❌ Proyecto no encontrado: {}", id);
                    return new IllegalArgumentException("Proyecto no encontrado");
                });
    }

    /**
     * Obtiene todos los proyectos de un workspace.
     */
    @Transactional(readOnly = true)
    public List<Project> getProjectsByWorkspaceId(UUID workspaceId) {
        log.info("📋 Obteniendo proyectos del workspace: {}", workspaceId);
        return projectRepository.findByWorkspaceId(workspaceId);
    }

    /**
     * Obtiene todos los proyectos (admin).
     */
    @Transactional(readOnly = true)
    public List<Project> getAllProjects() {
        log.info("📋 Obteniendo todos los proyectos");
        return projectRepository.findAll();
    }

    /**
     * Actualiza un proyecto existente.
     */
    @Transactional
    public Project updateProject(UUID id, Project updatedProject) {
        log.info("🔄 Actualizando proyecto: {}", id);

        Project existingProject = getProjectById(id);

        // Actualizar campos
        existingProject.setName(updatedProject.getName());
        existingProject.setDescription(updatedProject.getDescription());

        Project saved = projectRepository.save(existingProject);
        log.info("✅ Proyecto actualizado: {}", saved.getName());
        return saved;
    }

    /**
     * Elimina un proyecto.
     * 
     * Cascade: Si el proyecto tiene tareas, también se eliminan.
     */
    @Transactional
    public void deleteProject(UUID id) {
        log.info("🗑️ Eliminando proyecto: {}", id);

        if (!projectRepository.existsById(id)) {
            log.error("❌ Intento de eliminar proyecto inexistente: {}", id);
            throw new IllegalArgumentException("Proyecto no encontrado");
        }

        projectRepository.deleteById(id);
        log.info("✅ Proyecto eliminado exitosamente");
    }

    /**
     * Cuenta cuántos proyectos tiene un workspace.
     * 
     * Útil para estadísticas o validaciones (ej: "máximo 10 proyectos por workspace").
     */
    @Transactional(readOnly = true)
    public long countProjectsByWorkspace(UUID workspaceId) {
        return projectRepository.countByWorkspaceId(workspaceId);
    }
}
