package com.plandai.coreservice.controllers;

import com.plandai.coreservice.entities.Task;
import com.plandai.coreservice.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para Task.
 * 
 * Endpoints completos para gestión de tareas desde cualquier cliente.
 */
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    /**
     * GET /api/tasks
     * Obtiene todas las tareas o filtradas.
     * 
     * Ejemplos:
     * - GET /api/tasks → Todas las tareas
     * - GET /api/tasks?projectId=uuid → Tareas de un proyecto
     * - GET /api/tasks?assignedTo=uuid → Tareas de un usuario
     * - GET /api/tasks?status=DONE → Tareas completadas
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(required = false) UUID projectId,
            @RequestParam(required = false) UUID assignedTo,
            @RequestParam(required = false) Task.TaskStatus status
    ) {
        log.info("📥 GET /api/tasks - projectId: {}, assignedTo: {}, status: {}", 
                 projectId, assignedTo, status);

        List<Task> tasks;

        if (projectId != null) {
            tasks = taskService.getTasksByProjectId(projectId);
        } else if (assignedTo != null && status != null) {
            tasks = taskService.getTasksByAssignedUser(assignedTo)
                    .stream()
                    .filter(t -> t.getStatus() == status)
                    .toList();
        } else if (assignedTo != null) {
            tasks = taskService.getTasksByAssignedUser(assignedTo);
        } else if (status != null) {
            tasks = taskService.getTasksByStatus(status);
        } else {
            tasks = taskService.getAllTasks();
        }

        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/{id}
     * Obtiene una tarea específica.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID id) {
        log.info("📥 GET /api/tasks/{}", id);
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    /**
     * GET /api/tasks/urgent
     * Obtiene tareas urgentes sin terminar.
     * 
     * Útil para dashboards: "¡Tienes 3 tareas urgentes pendientes!".
     */
    @GetMapping("/urgent")
    public ResponseEntity<List<Task>> getUrgentTasks() {
        log.info("📥 GET /api/tasks/urgent");
        List<Task> tasks = taskService.getUrgentIncompleteTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/due-soon
     * Obtiene tareas que vencen en los próximos N días.
     * 
     * Ejemplo: GET /api/tasks/due-soon?days=7 → Tareas que vencen esta semana
     */
    @GetMapping("/due-soon")
    public ResponseEntity<List<Task>> getTasksDueSoon(@RequestParam(defaultValue = "7") int days) {
        log.info("📥 GET /api/tasks/due-soon - days: {}", days);
        List<Task> tasks = taskService.getTasksDueSoon(days);
        return ResponseEntity.ok(tasks);
    }

    /**
     * POST /api/tasks
     * Crea una nueva tarea dentro de un proyecto.
     * 
     * Body: {
     *   "title": "Diseñar mockups",
     *   "description": "...",
     *   "status": "TO_DO",
     *   "priority": "HIGH",
     *   "dueDate": "2025-12-31",
     *   "assignedTo": "uuid"
     * }
     */
    @PostMapping
    public ResponseEntity<Task> createTask(
            @RequestParam UUID projectId,
            @Valid @RequestBody Task task
    ) {
        log.info("📥 POST /api/tasks - projectId: {}, title: {}", projectId, task.getTitle());
        Task created = taskService.createTask(projectId, task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/tasks/{id}
     * Actualiza una tarea existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable UUID id,
            @Valid @RequestBody Task task
    ) {
        log.info("📥 PUT /api/tasks/{}", id);
        Task updated = taskService.updateTask(id, task);
        return ResponseEntity.ok(updated);
    }

    /**
     * PATCH /api/tasks/{id}/status
     * Cambia solo el estado de la tarea.
     * 
     * Útil para drag & drop en Kanban boards:
     * PATCH /api/tasks/uuid/status?status=IN_PROGRESS
     * 
     * PATCH vs PUT:
     * - PUT: Reemplaza el recurso completo
     * - PATCH: Actualiza parcialmente (solo status)
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable UUID id,
            @RequestParam Task.TaskStatus status
    ) {
        log.info("📥 PATCH /api/tasks/{}/status - newStatus: {}", id, status);
        Task updated = taskService.updateTaskStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    /**
     * PATCH /api/tasks/{id}/assign
     * Asigna una tarea a un usuario.
     * 
     * Ejemplo: PATCH /api/tasks/uuid/assign?userId=uuid
     */
    @PatchMapping("/{id}/assign")
    public ResponseEntity<Task> assignTask(
            @PathVariable UUID id,
            @RequestParam UUID userId
    ) {
        log.info("📥 PATCH /api/tasks/{}/assign - userId: {}", id, userId);
        Task updated = taskService.assignTask(id, userId);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/tasks/{id}
     * Elimina una tarea.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        log.info("📥 DELETE /api/tasks/{}", id);
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/tasks/count
     * Cuenta cuántas tareas tiene un proyecto.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTasks(@RequestParam UUID projectId) {
        log.info("📥 GET /api/tasks/count - projectId: {}", projectId);
        long count = taskService.countTasksByProject(projectId);
        return ResponseEntity.ok(count);
    }
}
