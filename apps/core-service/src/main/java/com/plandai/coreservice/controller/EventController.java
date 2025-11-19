package com.plandai.coreservice.controller;

import com.plandai.coreservice.dto.EventCreateDto;
import com.plandai.coreservice.model.Event;
import com.plandai.coreservice.entities.Project;
import com.plandai.coreservice.service.EventService;
import com.plandai.coreservice.services.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final EventService eventService;
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody EventCreateDto createDto) {
        Event event = new Event();
        event.setTitle(createDto.getTitle());
        event.setDescription(createDto.getDescription());
        event.setStartTime(createDto.getStartTime());
        event.setEndTime(createDto.getEndTime());
        event.setEventType(createDto.getEventType());
        event.setLocation(createDto.getLocation());
        event.setIsAllDay(createDto.getIsAllDay());
        event.setUserId(createDto.getUserId());
        
        if (createDto.getProjectId() != null) {
            Project project = projectService.getProjectById(createDto.getProjectId());
            event.setProject(project);
        }

        Event created = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getEvents(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID projectId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ) {
        List<Event> events;

        if (projectId != null) {
            events = eventService.getEventsByProjectId(projectId);
        } else if (userId != null && startTime != null && endTime != null) {
            events = eventService.getEventsByUserIdAndDateRange(userId, startTime, endTime);
        } else if (userId != null) {
            events = eventService.getEventsByUserId(userId);
        } else {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable UUID id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable UUID id,
            @Valid @RequestBody EventCreateDto updateDto
    ) {
        Event eventDetails = new Event();
        eventDetails.setTitle(updateDto.getTitle());
        eventDetails.setDescription(updateDto.getDescription());
        eventDetails.setStartTime(updateDto.getStartTime());
        eventDetails.setEndTime(updateDto.getEndTime());
        eventDetails.setEventType(updateDto.getEventType());
        eventDetails.setLocation(updateDto.getLocation());
        eventDetails.setIsAllDay(updateDto.getIsAllDay());

        Event updated = eventService.updateEvent(id, eventDetails);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
