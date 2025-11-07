package com.plandai.coreservice.controllers;

import com.plandai.coreservice.entities.Workspace;
import com.plandai.coreservice.services.WorkspaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para Workspace.
 * 
 * Expone endpoints HTTP que cualquier cliente puede consumir:
 * - Tauri Desktop (puerto 1420)
 * - React Web (puerto 3000/5173)
 * - App Móvil (futuro)
 * - Swagger UI (testing)
 * - Postman (testing)
 * 
 * @RestController: Combina @Controller + @ResponseBody (respuestas JSON automáticas)
 * @RequestMapping: Define la ruta base (/api/workspaces)
 * @CrossOrigin: Ya está configurado globalmente en application.properties
 */
@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
@Slf4j
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    /**
     * GET /api/workspaces
     * Obtiene todos los workspaces (admin) o filtrados por usuario.
     * 
     * Ejemplo: GET http://localhost:8080/api/workspaces
     * Respuesta: [{"id": "uuid", "name": "Trabajo", ...}, ...]
     */
    @GetMapping
    public ResponseEntity<List<Workspace>> getAllWorkspaces(
            @RequestParam(required = false) UUID userId
    ) {
        log.info("📥 GET /api/workspaces - userId: {}", userId);

        List<Workspace> workspaces = userId != null
                ? workspaceService.getWorkspacesByUserId(userId)
                : workspaceService.getAllWorkspaces();

        return ResponseEntity.ok(workspaces);
    }

    /**
     * GET /api/workspaces/{id}
     * Obtiene un workspace específico por ID.
     * 
     * Ejemplo: GET http://localhost:8080/api/workspaces/550e8400-e29b-41d4-a716-446655440000
     * Respuesta: {"id": "uuid", "name": "Mi workspace", ...}
     * 
     * @PathVariable: Captura el {id} de la URL
     */
    @GetMapping("/{id}")
    public ResponseEntity<Workspace> getWorkspaceById(@PathVariable UUID id) {
        log.info("📥 GET /api/workspaces/{}", id);
        Workspace workspace = workspaceService.getWorkspaceById(id);
        return ResponseEntity.ok(workspace);
    }

    /**
     * POST /api/workspaces
     * Crea un nuevo workspace.
     * 
     * Ejemplo: POST http://localhost:8080/api/workspaces
     * Body: {"name": "Trabajo Personal", "description": "...", "userId": "uuid"}
     * Respuesta: 201 Created + {"id": "uuid-generado", ...}
     * 
     * @RequestBody: Lee el JSON del body y lo convierte a Workspace
     * @Valid: Activa validaciones (@NotBlank, @Size, etc.)
     * ResponseEntity.status(201): Devuelve HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<Workspace> createWorkspace(@Valid @RequestBody Workspace workspace) {
        log.info("📥 POST /api/workspaces - name: {}", workspace.getName());
        Workspace created = workspaceService.createWorkspace(workspace);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/workspaces/{id}
     * Actualiza un workspace existente.
     * 
     * Ejemplo: PUT http://localhost:8080/api/workspaces/550e8400-e29b-41d4-a716-446655440000
     * Body: {"name": "Nuevo nombre", "description": "Nueva desc"}
     * Respuesta: 200 OK + workspace actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Workspace> updateWorkspace(
            @PathVariable UUID id,
            @Valid @RequestBody Workspace workspace
    ) {
        log.info("📥 PUT /api/workspaces/{}", id);
        Workspace updated = workspaceService.updateWorkspace(id, workspace);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/workspaces/{id}
     * Elimina un workspace (y todos sus proyectos por cascade).
     * 
     * Ejemplo: DELETE http://localhost:8080/api/workspaces/550e8400-e29b-41d4-a716-446655440000
     * Respuesta: 204 No Content (sin body)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable UUID id) {
        log.info("📥 DELETE /api/workspaces/{}", id);
        workspaceService.deleteWorkspace(id);
        return ResponseEntity.noContent().build();
    }
}
