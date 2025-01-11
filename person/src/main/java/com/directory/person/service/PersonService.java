package com.directory.person.service;

import com.directory.person.model.ContactInfoDto;
import com.directory.person.model.ContactInfoRequest;
import com.directory.person.model.PersonDto;
import com.directory.person.model.PersonRequest;

import java.util.List;
import java.util.UUID;

public interface PersonService {

    PersonDto createPerson(PersonRequest personRequest);
    Boolean deletePerson(UUID personId);
    List<PersonDto> getAllPersons();
    PersonDto getPersonById(UUID personId);
    ContactInfoDto addContactInfo(UUID personId, ContactInfoRequest contactInfoRequest);
    List<ContactInfoDto> getContactInfos(UUID personId);
    Boolean deleteContactInfo(UUID contactInfoId);

}
