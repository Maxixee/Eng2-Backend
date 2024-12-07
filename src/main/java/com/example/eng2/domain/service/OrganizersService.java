package com.example.eng2.domain.service;

import com.example.eng2.domain.entities.Organizer;
import com.example.eng2.domain.exceptions.EntityAlreadyExistsException;
import com.example.eng2.domain.exceptions.EntityNotFoundException;
import com.example.eng2.domain.exceptions.InvalidRegistrationInformationException;
import com.example.eng2.domain.repository.OrganizersRepository;
import com.example.eng2.domain.repository.projections.OrganizerProjection;
import lombok.RequiredArgsConstructor;
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
public class OrganizersService {

    @Autowired
    private OrganizersRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public Organizer save(Organizer organizer){

        Optional<Organizer> projectOptional = this.repository.findByEmail(organizer.getEmail());
        if(projectOptional.isPresent()){
            throw new EntityAlreadyExistsException(String.format("Project with email %s already exists", organizer.getEmail()));
        }
        if(organizer.getName().isBlank() || organizer.getOrganization().isBlank() || organizer.getEmail().isBlank()){
            throw new InvalidRegistrationInformationException("Invalid registration credentials");
        }

        //log.info("Saving project: {}", organizer);
        return this.repository.save(organizer);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Organizer update(Long id, Organizer updatedOrganizer){
        Organizer existingOrganizer = this.findById(id);
        if(updatedOrganizer.getName().isBlank() || updatedOrganizer.getEmail().isBlank() || updatedOrganizer.getOrganization().isBlank()){
            throw new InvalidRegistrationInformationException("Invalid update credentials");
        }

        existingOrganizer.setName(updatedOrganizer.getName());
        existingOrganizer.setEmail(updatedOrganizer.getEmail());
        existingOrganizer.setOrganization(updatedOrganizer.getOrganization());
        existingOrganizer.setPhone(updatedOrganizer.getPhone());

        //log.info("Updating organizer id={} : {}", id, existingOrganizer);
        return this.repository.save(existingOrganizer);
    }

    @Transactional(readOnly = true)
    public Organizer findById(Long id){
        return this.repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Organizer with id= %s not found", id))
        );
    }

    @Transactional(readOnly = true)
    public Page<OrganizerProjection> findAll(Pageable pageable){
        return this.repository.findAllPageable(pageable);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id){
        Organizer organizer = this.findById(id);
        this.repository.delete(organizer);
        //log.info("Deleting project id= {} : {}", id, organizer);
    }
}
