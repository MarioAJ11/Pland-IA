package com.plandai.coreservice.services;

import com.plandai.coreservice.entities.Workspace;
import com.plandai.coreservice.exception.ResourceNotFoundException;
import com.plandai.coreservice.repositories.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Servicio de negocio para Workspace.
 * 
 * Responsabilidades:
 * - Validar reglas de negocio (ej: no duplicados)
 * - Orquestar llamadas al repositorio
 * - Manejar transacciones con @Transactional
 * - Logging de operaciones importantes
 */
@Service
@RequiredArgsConstructor  // Genera constructor con campos final (inyección de dependencias)
@Slf4j  // Genera logger automáticamente: log.info(), log.error(), etc.
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    /**
     * Crea un nuevo workspace.
     * 
     * @Transactional: Si algo falla, hace ROLLBACK automático.
     * readOnly = false: Permite escritura en DB (INSERT/UPDATE/DELETE)
     */
    @Transactional
    public Workspace createWorkspace(Workspace workspace) {
        log.info("📝 Creando workspace: {} para usuario: {}", workspace.getName(), workspace.getUserId());

        // Validación de negocio: No permitir duplicados por usuario
        if (workspaceRepository.existsByNameAndUserId(workspace.getName(), workspace.getUserId())) {
            log.warn("⚠️ Intento de crear workspace duplicado: {}", workspace.getName());
            throw new IllegalArgumentException("Ya existe un workspace con ese nombre");
        }

        Workspace savedWorkspace = workspaceRepository.save(workspace);
        log.info("✅ Workspace creado exitosamente con ID: {}", savedWorkspace.getId());
        return savedWorkspace;
    }

    /**
     * Obtiene un workspace por ID.
     * 
     * @Transactional(readOnly = true): Optimización para queries de solo lectura.
     * Hibernate no necesita hacer flush ni dirty-checking.
     */
    @Transactional(readOnly = true)
    public Workspace getWorkspaceById(UUID id) {
        log.info("🔍 Buscando workspace con ID: {}", id);
        return workspaceRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("❌ Workspace no encontrado: {}", id);
                    return new ResourceNotFoundException("Workspace", id);
                });
    }

    /**
     * Obtiene todos los workspaces de un usuario.
     */
    @Transactional(readOnly = true)
    public List<Workspace> getWorkspacesByUserId(UUID userId) {
        log.info("📋 Obteniendo workspaces del usuario: {}", userId);
        return workspaceRepository.findByUserId(userId);
    }

    /**
     * Obtiene todos los workspaces (admin).
     */
    @Transactional(readOnly = true)
    public List<Workspace> getAllWorkspaces() {
        log.info("📋 Obteniendo todos los workspaces");
        return workspaceRepository.findAll();
    }

    /**
     * Actualiza un workspace existente.
     */
    @Transactional
    public Workspace updateWorkspace(UUID id, Workspace updatedWorkspace) {
        log.info("🔄 Actualizando workspace: {}", id);

        Workspace existingWorkspace = getWorkspaceById(id);

        // Actualizar solo los campos permitidos
        existingWorkspace.setName(updatedWorkspace.getName());
        existingWorkspace.setDescription(updatedWorkspace.getDescription());

        // JPA detecta automáticamente los cambios y hace UPDATE en DB
        // No necesitas llamar a save() explícitamente dentro de @Transactional
        Workspace saved = workspaceRepository.save(existingWorkspace);
        
        log.info("✅ Workspace actualizado: {}", saved.getName());
        return saved;
    }

    /**
     * Elimina un workspace.
     * 
     * Cascade: Si el workspace tiene proyectos, también se eliminan (definido en entidad).
     */
    @Transactional
    public void deleteWorkspace(UUID id) {
        log.info("🗑️ Eliminando workspace: {}", id);

        if (!workspaceRepository.existsById(id)) {
            log.error("❌ Intento de eliminar workspace inexistente: {}", id);
            throw new ResourceNotFoundException("Workspace", id);
        }

        workspaceRepository.deleteById(id);
        log.info("✅ Workspace eliminado exitosamente");
    }
}
