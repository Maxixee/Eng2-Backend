package com.example.eng2.domain.repository.projections;

import java.time.LocalDate;

public interface EventProjection {

    Long getId();
    String getName();
    LocalDate getDate();
    String getLocal();
    String getDescription();
    OrganizerProjection getOrganizer();
}

