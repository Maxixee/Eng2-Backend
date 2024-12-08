package com.example.eng2.domain.service;

import com.example.eng2.domain.entities.Organizer;
import com.example.eng2.domain.exceptions.EntityAlreadyExistsException;
import com.example.eng2.domain.exceptions.EntityNotFoundException;
import com.example.eng2.domain.repository.OrganizersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrganizersServiceTest {

    @Autowired
    private OrganizersService organizersService;

    @MockBean
    private OrganizersRepository organizersRepository;

    @Test
    @DisplayName("Should save a new organizer successfully")
    public void shouldSaveOrganizerSuccessfully() {
        Organizer organizer = new Organizer();
        organizer.setName("Test Organizer");
        organizer.setEmail("test@organization.com");
        organizer.setOrganization("Test Organization");
        organizer.setPhone("123456789");

        when(organizersRepository.findByEmail(organizer.getEmail())).thenReturn(Optional.empty());
        when(organizersRepository.save(organizer)).thenReturn(organizer);

        Organizer savedOrganizer = organizersService.save(organizer);

        assertNotNull(savedOrganizer);
        assertEquals("Test Organizer", savedOrganizer.getName());
        verify(organizersRepository, times(1)).save(organizer);
    }

    @Test
    @DisplayName("Should throw exception when trying to save organizer with existing email")
    public void shouldThrowExceptionWhenSavingDuplicateEmail() {
        Organizer organizer = new Organizer();
        organizer.setName("Test Organizer");
        organizer.setEmail("duplicate@organization.com");
        organizer.setOrganization("Test Organization");

        when(organizersRepository.findByEmail(organizer.getEmail()))
                .thenReturn(Optional.of(organizer));

        assertThrows(EntityAlreadyExistsException.class, () -> organizersService.save(organizer));
        verify(organizersRepository, never()).save(any(Organizer.class));
    }


    @Test
    @DisplayName("Should update an existing organizer successfully")
    public void shouldUpdateOrganizerSuccessfully() {
        Organizer existingOrganizer = new Organizer();
        existingOrganizer.setId(1L);
        existingOrganizer.setName("Existing Organizer");
        existingOrganizer.setEmail("existing@organization.com");
        existingOrganizer.setOrganization("Existing Organization");

        Organizer updatedOrganizer = new Organizer();
        updatedOrganizer.setName("Updated Organizer");
        updatedOrganizer.setEmail("updated@organization.com");
        updatedOrganizer.setOrganization("Updated Organization");
        updatedOrganizer.setPhone("987654321");

        when(organizersRepository.findById(1L)).thenReturn(Optional.of(existingOrganizer));
        when(organizersRepository.save(any(Organizer.class))).thenReturn(existingOrganizer);

        Organizer result = organizersService.update(1L, updatedOrganizer);

        assertNotNull(result);
        assertEquals("Updated Organizer", result.getName());
        verify(organizersRepository, times(1)).save(existingOrganizer);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existing organizer")
    public void shouldThrowExceptionWhenUpdatingNonExistingOrganizer() {
        Organizer updatedOrganizer = new Organizer();
        updatedOrganizer.setName("Updated Organizer");
        updatedOrganizer.setEmail("updated@organization.com");
        updatedOrganizer.setOrganization("Updated Organization");

        when(organizersRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> organizersService.update(1L, updatedOrganizer));
        verify(organizersRepository, never()).save(any(Organizer.class));
    }

    @Test
    @DisplayName("Should find an organizer by ID")
    public void shouldFindOrganizerById() {
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        organizer.setName("Organizer");
        organizer.setEmail("organizer@organization.com");
        organizer.setOrganization("Organization");

        when(organizersRepository.findById(1L)).thenReturn(Optional.of(organizer));

        Organizer foundOrganizer = organizersService.findById(1L);

        assertNotNull(foundOrganizer);
        assertEquals(1L, foundOrganizer.getId());
        verify(organizersRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when organizer ID is not found")
    public void shouldThrowExceptionWhenOrganizerIdNotFound() {
        when(organizersRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> organizersService.findById(1L));
        verify(organizersRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should delete an organizer by ID")
    public void shouldDeleteOrganizerById() {
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        organizer.setName("Organizer");
        organizer.setEmail("organizer@organization.com");

        when(organizersRepository.findById(1L)).thenReturn(Optional.of(organizer));
        doNothing().when(organizersRepository).delete(organizer);

        organizersService.delete(1L);

        verify(organizersRepository, times(1)).delete(organizer);
    }

    @Test
    @DisplayName("Should throw exception when trying to delete non-existing organizer")
    public void shouldThrowExceptionWhenDeletingNonExistingOrganizer() {
        when(organizersRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> organizersService.delete(1L));
        verify(organizersRepository, never()).delete(any(Organizer.class));
    }
}

