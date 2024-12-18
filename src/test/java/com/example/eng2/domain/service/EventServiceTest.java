package com.example.eng2.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.eng2.domain.entities.Event;
import com.example.eng2.domain.entities.Organizer;
import com.example.eng2.domain.exceptions.EntityAlreadyExistsException;
import com.example.eng2.domain.exceptions.EntityNotFoundException;
import com.example.eng2.domain.exceptions.InvalidRegistrationInformationException;
import com.example.eng2.domain.repository.EventRepository;
import com.example.eng2.domain.repository.projections.EventProjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @MockBean
    private EventRepository eventRepository;

    @Mock
    private EventProjection eventProjection;

    @Test
    @DisplayName("Should save a new event successfully")
    public void shouldSaveEventSuccessfully() {
        Organizer organizer = new Organizer();
        organizer.setId(5L);
        organizer.setEmail("email");
        organizer.setName("name");
        organizer.setOrganization("organization");

        Event event = new Event();
        event.setName("Test Event");
        event.setDate(LocalDate.now());
        event.setLocal("Test Local");
        event.setDescription("Test Description");
        event.setOrganizer(organizer);

        when(eventRepository.findByNameAndDate(event.getName(), event.getDate())).thenReturn(Optional.empty());
        when(eventRepository.save(event)).thenReturn(event);

        Event savedEvent = eventService.save(event);

        assertNotNull(savedEvent);
        assertEquals("Test Event", savedEvent.getName());
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    @DisplayName("Should throw exception when trying to save event with existing name and date")
    public void shouldThrowExceptionWhenSavingDuplicateEvent() {
        Event event = new Event();
        event.setName("Duplicate Event");
        event.setDate(LocalDate.now());
        event.setLocal("Test Local");
        event.setDescription("Test Description");

        when(eventRepository.findByNameAndDate(event.getName(), event.getDate()))
                .thenReturn(Optional.of(event));

        assertThrows(EntityAlreadyExistsException.class, () -> eventService.save(event));
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    @DisplayName("Should throw exception when saving event with invalid data")
    public void shouldThrowExceptionWhenSavingInvalidEvent() {
        Event event = new Event();
        event.setName("Invalid Event");
        event.setDate(null);  // Invalid date
        event.setLocal("Test Local");
        event.setDescription("Test Description");

        assertThrows(InvalidRegistrationInformationException.class, () -> eventService.save(event));
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    @DisplayName("Should update an existing event successfully")
    public void shouldUpdateEventSuccessfully() {
        Organizer organizer = new Organizer();
        organizer.setId(5L);
        organizer.setEmail("email");
        organizer.setName("name");
        organizer.setOrganization("organization");

        Event existingEvent = new Event();
        existingEvent.setId(1L);
        existingEvent.setName("Existing Event");
        existingEvent.setDate(LocalDate.now());
        existingEvent.setLocal("Existing Local");
        existingEvent.setDescription("Existing Description");
        existingEvent.setOrganizer(organizer);

        Event updatedEvent = new Event();
        updatedEvent.setName("Updated Event");
        updatedEvent.setDate(LocalDate.now().plusDays(1));
        updatedEvent.setLocal("Updated Local");
        updatedEvent.setDescription("Updated Description");
        updatedEvent.setOrganizer(organizer);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(updatedEvent);

        Event result = eventService.update(1L, updatedEvent);

        assertNotNull(result);
        assertEquals("Updated Event", result.getName());
        verify(eventRepository, times(1)).save(existingEvent);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existing event")
    public void shouldThrowExceptionWhenUpdatingNonExistingEvent() {
        Event updatedEvent = new Event();
        updatedEvent.setName("Updated Event");
        updatedEvent.setDate(LocalDate.now());
        updatedEvent.setLocal("Updated Local");
        updatedEvent.setDescription("Updated Description");

        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> eventService.update(1L, updatedEvent));
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    @DisplayName("Should find an event by ID")
    public void shouldFindEventById() {
        Event event = new Event();
        event.setId(1L);
        event.setName("Test Event");
        event.setDate(LocalDate.now());
        event.setLocal("Test Local");
        event.setDescription("Test Description");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Event foundEvent = eventService.findById(1L);

        assertNotNull(foundEvent);
        assertEquals(1L, foundEvent.getId());
        verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when event ID is not found")
    public void shouldThrowExceptionWhenEventIdNotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> eventService.findById(1L));
        verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should delete an event by ID")
    public void shouldDeleteEventById() {
        Event event = new Event();
        event.setId(1L);
        event.setName("Test Event");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        doNothing().when(eventRepository).delete(event);

        eventService.delete(1L);

        verify(eventRepository, times(1)).delete(event);
    }

    @Test
    @DisplayName("Should throw exception when trying to delete non-existing event")
    public void shouldThrowExceptionWhenDeletingNonExistingEvent() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> eventService.delete(1L));
        verify(eventRepository, never()).delete(any(Event.class));
    }

    @Test
    @DisplayName("Should return paginated list of events")
    public void shouldReturnPaginatedListOfEvents() {
        Page<EventProjection> eventPage = mock(Page.class);
        when(eventRepository.findAllPageable(PageRequest.of(0, 10))).thenReturn(eventPage);

        Page<EventProjection> result = eventService.findAll(PageRequest.of(0, 10));

        assertNotNull(result);
        verify(eventRepository, times(1)).findAllPageable(PageRequest.of(0, 10));
    }
}
