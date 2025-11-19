package com.plandai.coreservice.service;

import com.plandai.coreservice.exception.ResourceNotFoundException;
import com.plandai.coreservice.model.Event;
import com.plandai.coreservice.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventRepository eventRepository;

    @Transactional
    public Event createEvent(Event event) {
        log.info("Creating new event: {}", event.getTitle());
        return eventRepository.save(event);
    }

    @Transactional(readOnly = true)
    public Event getEventById(UUID id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", id));
    }

    @Transactional(readOnly = true)
    public List<Event> getEventsByUserId(UUID userId) {
        return eventRepository.findByUserIdOrderByStartTimeAsc(userId);
    }

    @Transactional(readOnly = true)
    public List<Event> getEventsByUserIdAndDateRange(UUID userId, LocalDateTime start, LocalDateTime end) {
        return eventRepository.findByUserIdAndStartTimeBetweenOrderByStartTimeAsc(userId, start, end);
    }

    @Transactional(readOnly = true)
    public List<Event> getEventsByProjectId(UUID projectId) {
        return eventRepository.findByProjectIdOrderByStartTimeAsc(projectId);
    }

    @Transactional
    public Event updateEvent(UUID id, Event eventDetails) {
        Event event = getEventById(id);
        
        if (eventDetails.getTitle() != null) {
            event.setTitle(eventDetails.getTitle());
        }
        if (eventDetails.getDescription() != null) {
            event.setDescription(eventDetails.getDescription());
        }
        if (eventDetails.getStartTime() != null) {
            event.setStartTime(eventDetails.getStartTime());
        }
        if (eventDetails.getEndTime() != null) {
            event.setEndTime(eventDetails.getEndTime());
        }
        if (eventDetails.getEventType() != null) {
            event.setEventType(eventDetails.getEventType());
        }
        if (eventDetails.getLocation() != null) {
            event.setLocation(eventDetails.getLocation());
        }
        if (eventDetails.getIsAllDay() != null) {
            event.setIsAllDay(eventDetails.getIsAllDay());
        }

        log.info("Updating event: {}", id);
        return eventRepository.save(event);
    }

    @Transactional
    public void deleteEvent(UUID id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event", id);
        }
        log.info("Deleting event: {}", id);
        eventRepository.deleteById(id);
    }
}
