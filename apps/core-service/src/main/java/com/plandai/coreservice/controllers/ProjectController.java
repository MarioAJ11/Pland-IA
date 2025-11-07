package com.plandai.coreservice.controllers;

import com.plandai.coreservice.entities.Project;
import com.plandai.coreservice.services.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para Project.
 * 
 * Endpoints disponibles para todos los clientes (Tauri Desktop, React Web, Móvil).
 */
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    /**
     * GET /api/projects
     * Obtiene todos los proyectos o filtrados por workspace.
     * 
     * Ejemplos:
     * - GET /api/projects → Todos los proyectos
     * - GET /api/projects?workspaceId=uuid → Proyectos de un workspace
     */
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(
            @RequestParam(required = false) UUID workspaceId
    ) {
        log.info("📥 GET /api/projects - workspaceId: {}", workspaceId);

        List<Project> projects = workspaceId != null
                ? projectService.getProjectsByWorkspaceId(workspaceId)
                : projectService.getAllProjects();

        return ResponseEntity.ok(projects);
    }

    /**
     * GET /api/projects/{id}
     * Obtiene un proyecto específico.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable UUID id) {
        log.info("📥 GET /api/projects/{}", id);
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    /**
     * POST /api/projects
     * Crea un nuevo proyecto dentro de un workspace.
     * 
     * Body: {"name": "Rediseño Web", "description": "...", "workspaceId": "uuid"}
     * 
     * Nota: workspaceId puede ir en el body o como query param.
     */
    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestParam UUID workspaceId,
            @Valid @RequestBody Project project
    ) {
        log.info("📥 POST /api/projects - workspaceId: {}, name: {}", workspaceId, project.getName());
        Project created = projectService.createProject(workspaceId, project);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/projects/{id}
     * Actualiza un proyecto existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(
            @PathVariable UUID id,
            @Valid @RequestBody Project project
    ) {
        log.info("📥 PUT /api/projects/{}", id);
        Project updated = projectService.updateProject(id, project);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/projects/{id}
     * Elimina un proyecto (y todas sus tareas por cascade).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID id) {
        log.info("📥 DELETE /api/projects/{}", id);
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/projects/count
     * Cuenta cuántos proyectos tiene un workspace.
     * 
     * Útil para dashboards: "Tienes 5 proyectos en este workspace".
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countProjects(@RequestParam UUID workspaceId) {
        log.info("📥 GET /api/projects/count - workspaceId: {}", workspaceId);
        long count = projectService.countProjectsByWorkspace(workspaceId);
        return ResponseEntity.ok(count);
    }
}
