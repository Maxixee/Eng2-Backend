package com.example.eng2.domain.entities;

import jakarta.persistence.*;
import lombok.*;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "Organizer")
public class Organizer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "organization", nullable = false)
    private String organization;


    public Organizer() {
    }

    public Organizer(Long id, String name, String email, String phone, String organization) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.organization = organization;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getOrganization() {
        return organization;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }


}
