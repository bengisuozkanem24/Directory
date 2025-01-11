package com.directory.person.service.impl;

import com.directory.person.model.*;
import com.directory.person.repository.ContactInfoRepository;
import com.directory.person.repository.PersonRepository;
import com.directory.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ContactInfoRepository contactInfoRepository;


    @Override
    public PersonDto createPerson(PersonRequest personRequest) {
        Person person = new Person();
        person.setFirstName(personRequest.getFirstName());
        person.setLastName(personRequest.getLastName());
        person.setCompany(personRequest.getCompany());

        Person savedPerson = personRepository.save(person);
        return toPersonDto(savedPerson);
    }

    @Override
    public Boolean deletePerson(UUID personId) {
        Optional<Person> person = personRepository.findById(personId);
        if(person.isPresent()) {
            personRepository.deleteById(personId);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonDto> getAllPersons() {
        return toPersonDtoList(personRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto getPersonById(UUID personId) {
        Optional<Person> person = personRepository.findById(personId);
        if(person.isPresent()) {
            return toPersonDto(person.get());
        } else {
            throw new RuntimeException("Person not found.");
        }
    }

    @Override
    public ContactInfoDto addContactInfo(UUID personId, ContactInfoRequest contactInfoRequest) {
        Optional<Person> personOpt = personRepository.findById(personId);
        if(personOpt.isPresent()) {
            Person person = personOpt.get();
            ContactInfo contactInfo = new ContactInfo();
            contactInfo.setPerson(person);
            contactInfo.setContactType(contactInfoRequest.getContactType());
            contactInfo.setContent(contactInfoRequest.getContent());
            ContactInfo savedContactInfo = contactInfoRepository.save(contactInfo);
            return toContactInfoDto(savedContactInfo);
        } else {
            throw new RuntimeException("Person not found.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactInfoDto> getContactInfos(UUID personId) {
        List<ContactInfo> contactInfoList = contactInfoRepository.findByPersonId(personId);
        return toContactInfoDtoList(contactInfoList);
    }

    @Override
    public Boolean deleteContactInfo(UUID contactInfoId) {
        Optional<ContactInfo> contactInfo = contactInfoRepository.findById(contactInfoId);
        if(contactInfo.isPresent()) {
            contactInfoRepository.delete(contactInfo.get());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public PersonDto toPersonDto(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setCompany(person.getCompany());
        List<ContactInfo> contactInfoList = person.getContactInfoList();
        personDto.setContactInfoList(toContactInfoDtoList(contactInfoList));
        return personDto;
    }

    public ContactInfoDto toContactInfoDto(ContactInfo contactInfo) {
        ContactInfoDto contactInfoDto = new ContactInfoDto();
        contactInfoDto.setId(contactInfo.getId());
        contactInfoDto.setContactType(contactInfo.getContactType());
        contactInfoDto.setContent(contactInfo.getContent());
        return contactInfoDto;
    }

    public List<PersonDto> toPersonDtoList(List<Person> personList) {
        return personList.stream().map(this::toPersonDto).collect(Collectors.toList());
    }

    public List<ContactInfoDto> toContactInfoDtoList(List<ContactInfo> contactInfoList) {
        return contactInfoList.stream().map(this::toContactInfoDto).collect(Collectors.toList());
    }
}
