package com.example.eng2.domain.repository;

import com.example.eng2.domain.entities.Participant;
import com.example.eng2.domain.repository.projections.OrganizerProjection;
import com.example.eng2.domain.repository.projections.ParticipantProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Optional<Participant> findByEmail(String email);

    @Query("select c from Participant c")
    Page<ParticipantProjection> findAllPageable(Pageable pageable);

}
