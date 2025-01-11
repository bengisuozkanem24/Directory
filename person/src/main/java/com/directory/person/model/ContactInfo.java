package com.directory.person.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "CONTACT_INFO")
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ContactType contactType;

    private String content;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

}
