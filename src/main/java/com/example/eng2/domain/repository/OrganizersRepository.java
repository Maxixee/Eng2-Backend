package com.example.eng2.domain.repository;

import com.example.eng2.domain.entities.Organizer;
import com.example.eng2.domain.repository.projections.OrganizerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizersRepository extends JpaRepository<Organizer, Long> {

    Optional<Organizer> findByEmail(String email);

    @Query("select c from Organizer c")
    Page<OrganizerProjection> findAllPageable(Pageable pageable);
}
