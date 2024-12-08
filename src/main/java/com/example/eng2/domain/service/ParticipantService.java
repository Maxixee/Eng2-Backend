package com.example.eng2.domain.service;

import com.example.eng2.domain.entities.Organizer;
import com.example.eng2.domain.entities.Participant;
import com.example.eng2.domain.exceptions.EntityAlreadyExistsException;
import com.example.eng2.domain.exceptions.EntityNotFoundException;
import com.example.eng2.domain.exceptions.InvalidRegistrationInformationException;
import com.example.eng2.domain.repository.ParticipantRepository;
import com.example.eng2.domain.repository.projections.ParticipantProjection;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public Participant save(Participant participant) {

        Optional<Participant> participantOptional = this.repository.findByEmail(participant.getEmail());
        if(participantOptional.isPresent()){
            throw new EntityAlreadyExistsException(String.format("Participant with email %s already exists", participant.getEmail()));
        }
        if(participant.getName().isBlank() || participant.getPhone().isBlank() || participant.getEmail().isBlank()){
            throw new InvalidRegistrationInformationException("Invalid registration credentials");
        }

        return this.repository.save(participant);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Participant update(Long id, Participant updatedParticipant) {

        Participant existingOrganizer = this.findById(id);
        if(updatedParticipant.getName().isBlank() || updatedParticipant.getEmail().isBlank() || updatedParticipant.getPhone().isBlank()){
            throw new InvalidRegistrationInformationException("Invalid update credentials");
        }

        existingOrganizer.setName(updatedParticipant.getName());
        existingOrganizer.setEmail(updatedParticipant.getEmail());
        existingOrganizer.setPhone(updatedParticipant.getPhone());

        //log.info("Updating organizer id={} : {}", id, existingOrganizer);
        return this.repository.save(existingOrganizer);
    }

    @Transactional(readOnly = true)
    public Participant findById(Long id){
        return this.repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Participant with id= %s not found", id))
        );
    }

    @Transactional(readOnly = true)
    public Page<ParticipantProjection> findAll(Pageable pageable) {
        return this.repository.findAllPageable(pageable);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        Participant participant = this.findById(id);
        this.repository.delete(participant);
    }
}
