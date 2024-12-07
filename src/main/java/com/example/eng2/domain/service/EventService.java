package com.example.eng2.domain.service;

import com.example.eng2.domain.entities.Event;
import com.example.eng2.domain.exceptions.EntityAlreadyExistsException;
import com.example.eng2.domain.exceptions.EntityNotFoundException;
import com.example.eng2.domain.exceptions.InvalidRegistrationInformationException;
import com.example.eng2.domain.repository.EventRepository;
import com.example.eng2.domain.repository.projections.EventProjection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public Event save(Event event) {
        Optional<Event> eventOptional = this.repository.findByNameAndDate(event.getName(), event.getDate());
        if (eventOptional.isPresent()) {
            throw new EntityAlreadyExistsException(String.format("Event with name '%s' on date '%s' already exists", event.getName(), event.getDate()));
        }

        if (event.getName().isBlank() || event.getDate() == null || event.getLocal().isBlank() || event.getDescription().isBlank() || event.getOrganizer() == null) {
            throw new InvalidRegistrationInformationException("Invalid registration information for the event");
        }

        return this.repository.save(event);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Event update(Long id, Event updatedEvent) {
        Event existingEvent = this.findById(id);

        if (updatedEvent.getName().isBlank() || updatedEvent.getDate() == null || updatedEvent.getLocal().isBlank() || updatedEvent.getDescription().isBlank() || updatedEvent.getOrganizer() == null) {
            throw new InvalidRegistrationInformationException("Invalid update information for the event");
        }

        existingEvent.setName(updatedEvent.getName());
        existingEvent.setDate(updatedEvent.getDate());
        existingEvent.setLocal(updatedEvent.getLocal());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setOrganizer(updatedEvent.getOrganizer());

        return this.repository.save(existingEvent);
    }

    @Transactional(readOnly = true)
    public Event findById(Long id) {
        return this.repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Event with id=%s not found", id))
        );
    }

    @Transactional(readOnly = true)
    public Page<EventProjection> findAll(Pageable pageable) {
        return this.repository.findAllPageable(pageable);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        Event event = this.findById(id);
        this.repository.delete(event);
    }
}

