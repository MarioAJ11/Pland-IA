package com.plandai.coreservice.services;

import com.plandai.coreservice.entities.Project;
import com.plandai.coreservice.entities.Task;
import com.plandai.coreservice.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Servicio de negocio para Task.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectService projectService;

    /**
     * Crea una nueva tarea dentro de un proyecto.
     * 
     * Validaciones:
     * 1. El proyecto debe existir
     * 2. Si hay assignedTo, verificar que el usuario existe (futuro: integración con Auth Service)
     */
    @Transactional
    public Task createTask(UUID projectId, Task task) {
        log.info("📝 Creando tarea: {} en proyecto: {}", task.getTitle(), projectId);

        // 1. Verificar que el proyecto existe
        Project project = projectService.getProjectById(projectId);

        // 2. Asignar proyecto a la tarea
        task.setProject(project);

        // 3. Establecer valores por defecto si no se proporcionaron
        if (task.getStatus() == null) {
            task.setStatus(Task.TaskStatus.TO_DO);
        }
        if (task.getPriority() == null) {
            task.setPriority(Task.TaskPriority.MEDIUM);
        }

        Task savedTask = taskRepository.save(task);
        log.info("✅ Tarea creada exitosamente con ID: {}", savedTask.getId());
        return savedTask;
    }

    /**
     * Obtiene una tarea por ID.
     */
    @Transactional(readOnly = true)
    public Task getTaskById(UUID id) {
        log.info("🔍 Buscando tarea con ID: {}", id);
        return taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("❌ Tarea no encontrada: {}", id);
                    return new IllegalArgumentException("Tarea no encontrada");
                });
    }

    /**
     * Obtiene todas las tareas de un proyecto.
     */
    @Transactional(readOnly = true)
    public List<Task> getTasksByProjectId(UUID projectId) {
        log.info("📋 Obteniendo tareas del proyecto: {}", projectId);
        return taskRepository.findByProjectId(projectId);
    }

    /**
     * Obtiene todas las tareas asignadas a un usuario.
     */
    @Transactional(readOnly = true)
    public List<Task> getTasksByAssignedUser(UUID userId) {
        log.info("📋 Obteniendo tareas del usuario: {}", userId);
        return taskRepository.findByAssignedTo(userId);
    }

    /**
     * Obtiene tareas por estado.
     */
    @Transactional(readOnly = true)
    public List<Task> getTasksByStatus(Task.TaskStatus status) {
        log.info("📋 Obteniendo tareas con estado: {}", status);
        return taskRepository.findByStatus(status);
    }

    /**
     * Obtiene tareas urgentes sin terminar.
     */
    @Transactional(readOnly = true)
    public List<Task> getUrgentIncompleteTasks() {
        log.info("⚠️ Obteniendo tareas urgentes sin terminar");
        return taskRepository.findUrgentIncompleteTasks();
    }

    /**
     * Obtiene tareas que vencen en los próximos N días.
     */
    @Transactional(readOnly = true)
    public List<Task> getTasksDueSoon(int days) {
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(days);
        log.info("📅 Obteniendo tareas que vencen entre {} y {}", today, futureDate);
        return taskRepository.findByDueDateBetween(today, futureDate);
    }

    /**
     * Obtiene todas las tareas (admin).
     */
    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        log.info("📋 Obteniendo todas las tareas");
        return taskRepository.findAll();
    }

    /**
     * Actualiza una tarea existente.
     */
    @Transactional
    public Task updateTask(UUID id, Task updatedTask) {
        log.info("🔄 Actualizando tarea: {}", id);

        Task existingTask = getTaskById(id);

        // Actualizar campos
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setAssignedTo(updatedTask.getAssignedTo());

        Task saved = taskRepository.save(existingTask);
        log.info("✅ Tarea actualizada: {}", saved.getTitle());
        return saved;
    }

    /**
     * Cambia el estado de una tarea.
     * 
     * Método específico para cambio de estado (común en APIs de tareas).
     */
    @Transactional
    public Task updateTaskStatus(UUID id, Task.TaskStatus newStatus) {
        log.info("🔄 Cambiando estado de tarea {} a {}", id, newStatus);

        Task task = getTaskById(id);
        task.setStatus(newStatus);

        Task saved = taskRepository.save(task);
        log.info("✅ Estado actualizado a: {}", saved.getStatus());
        return saved;
    }

    /**
     * Asigna una tarea a un usuario.
     */
    @Transactional
    public Task assignTask(UUID taskId, UUID userId) {
        log.info("👤 Asignando tarea {} al usuario {}", taskId, userId);

        Task task = getTaskById(taskId);
        task.setAssignedTo(userId);

        Task saved = taskRepository.save(task);
        log.info("✅ Tarea asignada exitosamente");
        return saved;
    }

    /**
     * Elimina una tarea.
     */
    @Transactional
    public void deleteTask(UUID id) {
        log.info("🗑️ Eliminando tarea: {}", id);

        if (!taskRepository.existsById(id)) {
            log.error("❌ Intento de eliminar tarea inexistente: {}", id);
            throw new IllegalArgumentException("Tarea no encontrada");
        }

        taskRepository.deleteById(id);
        log.info("✅ Tarea eliminada exitosamente");
    }

    /**
     * Cuenta cuántas tareas tiene un proyecto.
     */
    @Transactional(readOnly = true)
    public long countTasksByProject(UUID projectId) {
        return taskRepository.countByProjectId(projectId);
    }
}
