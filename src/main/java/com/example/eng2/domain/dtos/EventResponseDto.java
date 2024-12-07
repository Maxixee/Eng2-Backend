package com.example.eng2.domain.dtos;

import java.time.LocalDate;

public class EventResponseDto {

    private Long id;
    private String name;
    private LocalDate date;
    private String local;
    private String description;
    private OrganizerResponseDto organizer; // Informações completas do organizador

    public EventResponseDto() {
    }

    public EventResponseDto(Long id, String name, LocalDate date, String local, String description, OrganizerResponseDto organizer) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.local = local;
        this.description = description;
        this.organizer = organizer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrganizerResponseDto getOrganizer() {
        return organizer;
    }

    public void setOrganizer(OrganizerResponseDto organizer) {
        this.organizer = organizer;
    }
}

