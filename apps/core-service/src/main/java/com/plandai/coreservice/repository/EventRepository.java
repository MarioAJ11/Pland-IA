package com.plandai.coreservice.repository;

import com.plandai.coreservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    
    List<Event> findByUserIdOrderByStartTimeAsc(UUID userId);
    
    List<Event> findByUserIdAndStartTimeBetweenOrderByStartTimeAsc(
            UUID userId, LocalDateTime start, LocalDateTime end);
    
    List<Event> findByProjectIdOrderByStartTimeAsc(UUID projectId);
}
