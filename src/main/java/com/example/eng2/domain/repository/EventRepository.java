package com.example.eng2.domain.repository;

import com.example.eng2.domain.entities.Event;
import com.example.eng2.domain.repository.projections.EventProjection;
import com.example.eng2.domain.repository.projections.OrganizerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByNameAndDate(String name, LocalDate date);

    @Query("select c from Event c")
    Page<EventProjection> findAllPageable(Pageable pageable);
}
