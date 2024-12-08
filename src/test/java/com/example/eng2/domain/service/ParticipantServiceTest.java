package com.example.eng2.domain.service;

import com.example.eng2.domain.entities.Organizer;
import com.example.eng2.domain.entities.Participant;
import com.example.eng2.domain.exceptions.EntityAlreadyExistsException;
import com.example.eng2.domain.exceptions.EntityNotFoundException;
import com.example.eng2.domain.repository.OrganizersRepository;
import com.example.eng2.domain.repository.ParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ParticipantServiceTest {

    @Autowired
    private ParticipantService participantService;

    @MockBean
    private ParticipantRepository participantRepository;

    @Test
    @DisplayName("Should save a new participant successfully")
    public void shouldSaveOrganizerSuccessfully() {
        Participant participant = new Participant();
        participant.setName("Test Participant");
        participant.setEmail("test@organization.com");
        participant.setPhone("123456789");

        when(participantRepository.findByEmail(participant.getEmail())).thenReturn(Optional.empty());
        when(participantRepository.save(participant)).thenReturn(participant);

        Participant savedParticipant = participantService.save(participant);

        assertNotNull(savedParticipant);
        assertEquals("Test Participant", savedParticipant.getName());
        verify(participantRepository, times(1)).save(participant);
    }

    @Test
    @DisplayName("Should throw exception when trying to save participant with existing email")
    public void shouldThrowExceptionWhenSavingDuplicateEmail() {
        Participant participant = new Participant();
        participant.setName("Test Participant");
        participant.setEmail("duplicate@organization.com");
        participant.setPhone("0123456789");

        when(participantRepository.findByEmail(participant.getEmail()))
                .thenReturn(Optional.of(participant));

        assertThrows(EntityAlreadyExistsException.class, () -> participantService.save(participant));
        verify(participantRepository, never()).save(any(Participant.class));
    }

    @Test
    @DisplayName("Should update an existing participant successfully")
    public void shouldUpdateParticipantSuccessfully() {
        Participant existingParticipant = new Participant();
        existingParticipant.setId(1L);
        existingParticipant.setName("Existing Participant");
        existingParticipant.setEmail("existing@organization.com");
        existingParticipant.setPhone("74918276123");

        Participant updatedParticipant = new Participant();
        updatedParticipant.setName("Updated Participant");
        updatedParticipant.setEmail("updated@organization.com");
        updatedParticipant.setPhone("987654321");

        when(participantRepository.findById(1L)).thenReturn(Optional.of(existingParticipant));
        when(participantRepository.save(any(Participant.class))).thenReturn(existingParticipant);

        Participant result = participantService.update(1L, updatedParticipant);

        assertNotNull(result);
        assertEquals("Updated Participant", result.getName());
        verify(participantRepository, times(1)).save(existingParticipant);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existing participant")
    public void shouldThrowExceptionWhenUpdatingNonExistingParticipant() {
        Participant updatedParticipant = new Participant();
        updatedParticipant.setName("Updated Organizer");
        updatedParticipant.setEmail("updated@organization.com");
        updatedParticipant.setPhone("7499999999");

        when(participantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> participantService.update(1L, updatedParticipant));
        verify(participantRepository, never()).save(any(Participant.class));
    }

    @Test
    @DisplayName("Should find an participant by ID")
    public void shouldFindParticipantById() {
        Participant participant = new Participant();
        participant.setId(1L);
        participant.setName("Existing Participant");
        participant.setEmail("existing@organization.com");
        participant.setPhone("74918276123");

        when(participantRepository.findById(1L)).thenReturn(Optional.of(participant));

        Participant foundParticipant = participantService.findById(1L);

        assertNotNull(foundParticipant);
        assertEquals(1L, foundParticipant.getId());
        verify(participantRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when participant ID is not found")
    public void shouldThrowExceptionWhenParticipantIdNotFound() {
        when(participantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> participantService.findById(1L));
        verify(participantRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should delete an participant by ID")
    public void shouldDeleteParticipantById() {
        Participant participant = new Participant();
        participant.setId(1L);
        participant.setName("Existing Participant");
        participant.setEmail("existing@organization.com");
        participant.setPhone("74918276123");

        when(participantRepository.findById(1L)).thenReturn(Optional.of(participant));
        doNothing().when(participantRepository).delete(participant);

        participantService.delete(1L);

        verify(participantRepository, times(1)).delete(participant);
    }

    @Test
    @DisplayName("Should throw exception when trying to delete non-existing participant")
    public void shouldThrowExceptionWhenDeletingNonExistingParticipant() {
        when(participantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> participantService.delete(1L));
        verify(participantRepository, never()).delete(any(Participant.class));
    }

}
