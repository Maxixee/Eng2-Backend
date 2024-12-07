package com.example.eng2.domain.dtos;

import java.time.LocalDate;

public class EventCreateDto {

    private String name;
    private LocalDate date;
    private String local;
    private String description;
    private Long organizerId; // Apenas o ID do organizador, assumindo que ele já está registrado

    public EventCreateDto() {
    }

    public EventCreateDto(String name, LocalDate date, String local, String description, Long organizerId) {
        this.name = name;
        this.date = date;
        this.local = local;
        this.description = description;
        this.organizerId = organizerId;
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

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
    }
}

