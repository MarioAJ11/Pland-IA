package com.plandai.coreservice.repositories;

import com.plandai.coreservice.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Repositorio para la entidad Task.
 * 
 * Incluye métodos personalizados con Query Methods y @Query (JPQL).
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    /**
     * Busca todas las tareas de un proyecto.
     * 
     * SQL generado:
     * SELECT * FROM tasks WHERE project_id = ?
     */
    List<Task> findByProjectId(UUID projectId);

    /**
     * Busca tareas asignadas a un usuario específico.
     * 
     * SQL generado:
     * SELECT * FROM tasks WHERE assigned_to = ?
     */
    List<Task> findByAssignedTo(UUID userId);

    /**
     * Busca tareas por estado.
     * 
     * SQL generado:
     * SELECT * FROM tasks WHERE status = ?
     * 
     * Puedes pasar: TaskStatus.TO_DO, TaskStatus.IN_PROGRESS, TaskStatus.DONE
     */
    List<Task> findByStatus(Task.TaskStatus status);

    /**
     * Busca tareas por prioridad.
     * 
     * SQL generado:
     * SELECT * FROM tasks WHERE priority = ?
     */
    List<Task> findByPriority(Task.TaskPriority priority);

    /**
     * Busca tareas asignadas a un usuario con un estado específico.
     * 
     * SQL generado:
     * SELECT * FROM tasks WHERE assigned_to = ? AND status = ?
     */
    List<Task> findByAssignedToAndStatus(UUID userId, Task.TaskStatus status);

    /**
     * Busca tareas que vencen antes de una fecha.
     * 
     * SQL generado:
     * SELECT * FROM tasks WHERE due_date < ?
     * 
     * Útil para: "Tareas que vencen esta semana"
     */
    List<Task> findByDueDateBefore(LocalDate date);

    /**
     * Busca tareas con fecha límite entre dos fechas.
     * 
     * SQL generado:
     * SELECT * FROM tasks WHERE due_date BETWEEN ? AND ?
     */
    List<Task> findByDueDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Cuenta cuántas tareas tiene un proyecto.
     * 
     * SQL generado:
     * SELECT COUNT(*) FROM tasks WHERE project_id = ?
     */
    long countByProjectId(UUID projectId);

    /**
     * Busca tareas de un usuario dentro de un proyecto específico.
     * 
     * Aquí usamos @Query con JPQL (Java Persistence Query Language).
     * JPQL trabaja con entidades Java, no con tablas SQL.
     * 
     * Ventaja: Queries más complejas con JOIN
     */
    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND t.assignedTo = :userId")
    List<Task> findTasksByProjectAndUser(@Param("projectId") UUID projectId, @Param("userId") UUID userId);

    /**
     * Busca tareas urgentes (prioridad HIGH o URGENT) que están sin terminar.
     * 
     * Ejemplo de query con múltiples condiciones y OR.
     */
    @Query("SELECT t FROM Task t WHERE (t.priority = 'HIGH' OR t.priority = 'URGENT') AND t.status != 'DONE'")
    List<Task> findUrgentIncompleteTasks();
}
