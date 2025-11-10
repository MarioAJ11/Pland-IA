package com.plandai.coreservice.controllers;

import com.plandai.coreservice.dto.WorkspaceCreateDto;
import com.plandai.coreservice.dto.WorkspaceUpdateDto;
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
 * API REST completa para gestión de workspaces con validación y DTOs.
 * 
 * @RestController: Combina @Controller + @ResponseBody (respuestas JSON automáticas)
 * @RequestMapping: Define la ruta base (/api/workspaces)
 */
@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
@Slf4j
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    /**
     * POST /api/workspaces
     * Crea un nuevo workspace.
     * 
     * Body: WorkspaceCreateDto con validaciones
     * Respuesta: 201 Created + workspace creado
     */
    @PostMapping
    public ResponseEntity<Workspace> createWorkspace(@Valid @RequestBody WorkspaceCreateDto createDto) {
        log.info("📥 POST /api/workspaces - name: {}, userId: {}", createDto.getName(), createDto.getUserId());
        
        Workspace workspace = new Workspace();
        workspace.setName(createDto.getName());
        workspace.setDescription(createDto.getDescription());
        workspace.setUserId(createDto.getUserId());
        
        Workspace created = workspaceService.createWorkspace(workspace);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * GET /api/workspaces/{id}
     * Obtiene un workspace específico por ID.
     * 
     * Respuesta: 200 OK o 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Workspace> getWorkspaceById(@PathVariable UUID id) {
        log.info("📥 GET /api/workspaces/{}", id);
        Workspace workspace = workspaceService.getWorkspaceById(id);
        return ResponseEntity.ok(workspace);
    }

    /**
     * GET /api/workspaces?userId={uuid}
     * Obtiene workspaces filtrados por userId (query param opcional).
     * Si no se proporciona userId, retorna todos (admin).
     * 
     * Respuesta: 200 OK + lista de workspaces
     */
    @GetMapping
    public ResponseEntity<List<Workspace>> getAllWorkspaces(
            @RequestParam(required = false) UUID userId) {
        log.info("📥 GET /api/workspaces - userId: {}", userId);
        
        List<Workspace> workspaces;
        if (userId != null) {
            workspaces = workspaceService.getWorkspacesByUserId(userId);
        } else {
            workspaces = workspaceService.getAllWorkspaces();
        }
        
        return ResponseEntity.ok(workspaces);
    }

    /**
     * PUT /api/workspaces/{id}
     * Actualiza un workspace existente.
     * 
     * Body: WorkspaceUpdateDto (campos opcionales)
     * Respuesta: 200 OK o 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Workspace> updateWorkspace(
            @PathVariable UUID id,
            @Valid @RequestBody WorkspaceUpdateDto updateDto) {
        log.info("📥 PUT /api/workspaces/{}", id);
        
        Workspace existing = workspaceService.getWorkspaceById(id);
        
        if (updateDto.getName() != null) {
            existing.setName(updateDto.getName());
        }
        if (updateDto.getDescription() != null) {
            existing.setDescription(updateDto.getDescription());
        }
        
        Workspace updated = workspaceService.updateWorkspace(id, existing);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/workspaces/{id}
     * Elimina un workspace.
     * 
     * Respuesta: 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable UUID id) {
        log.info("📥 DELETE /api/workspaces/{}", id);
        workspaceService.deleteWorkspace(id);
        return ResponseEntity.noContent().build();
    }
}
