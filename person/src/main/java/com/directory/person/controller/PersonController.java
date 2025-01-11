package com.directory.person.controller;

import com.directory.person.model.ContactInfoDto;
import com.directory.person.model.ContactInfoRequest;
import com.directory.person.model.PersonDto;
import com.directory.person.model.PersonRequest;
import com.directory.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonRequest personRequest) {
        return ResponseEntity.ok(personService.createPerson(personRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePerson(@PathVariable UUID id) {
        return ResponseEntity.ok(personService.deletePerson(id));
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        return ResponseEntity.ok(personService.getAllPersons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable UUID id) {
        return ResponseEntity.ok(personService.getPersonById(id));
    }

    @PostMapping("/{id}/contact-info")
    public ResponseEntity<ContactInfoDto> addContactInfo(@PathVariable UUID id, @RequestBody ContactInfoRequest contactInfoRequest) {
        return ResponseEntity.ok(personService.addContactInfo(id, contactInfoRequest));
    }

    @DeleteMapping("/contact-info/{contactInfoId}")
    public ResponseEntity<Boolean> deleteContactInfo(@PathVariable UUID contactInfoId) {
        return ResponseEntity.ok(personService.deleteContactInfo(contactInfoId));
    }

    @GetMapping("/{id}/contact-info")
    public ResponseEntity<List<ContactInfoDto>> getContactInfos(@PathVariable UUID id) {
        return ResponseEntity.ok(personService.getContactInfos(id));
    }
}
