package com.directory.person;

import com.directory.person.model.*;
import com.directory.person.repository.ContactInfoRepository;
import com.directory.person.repository.PersonRepository;
import com.directory.person.service.PersonService;
import com.directory.person.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PersonApplicationTests {

	@Mock
	private PersonRepository personRepository;

	@Mock
	private ContactInfoRepository contactInfoRepository;

	@InjectMocks
	private PersonServiceImpl personService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreatePerson() {
		PersonRequest request = new PersonRequest();
		request.setFirstName("Jane");
		request.setLastName("Doe");
		request.setCompany("Setur");

		Person person = new Person();
		person.setFirstName("Jane");
		person.setLastName("Doe");
		person.setCompany("Setur");

		when(personRepository.save(any(Person.class))).thenReturn(person);

		PersonDto result = personService.createPerson(request);

		assertNotNull(result);
		assertEquals("Jane", result.getFirstName());
		assertEquals("Doe", result.getLastName());
		assertEquals("Setur", result.getCompany());
		verify(personRepository, times(1)).save(any(Person.class));
	}

	@Test
	void testDeletePerson() {
		UUID personId = UUID.randomUUID();
		Person person = new Person();
		person.setFirstName("Jane");
		person.setLastName("Doe");
		person.setCompany("Setur");

		when(personRepository.findById(personId)).thenReturn(Optional.of(person));
		doNothing().when(personRepository).deleteById(personId);

		Boolean result = personService.deletePerson(personId);

		assertTrue(result);
		verify(personRepository, times(1)).findById(personId);
		verify(personRepository, times(1)).deleteById(personId);
	}

	@Test
	void testGetAllPersons() {
		List<Person> persons = new ArrayList<>();
		Person person1 = new Person();
		person1.setFirstName("Jane");
		person1.setLastName("Doe");
		person1.setCompany("Setur");
		Person person2 = new Person();
		person2.setFirstName("John");
		person2.setLastName("Doe");
		person2.setCompany("ABC Company");
		persons.add(person1);
		persons.add(person2);

		when(personRepository.findAll()).thenReturn(persons);

		List<PersonDto> result = personService.getAllPersons();

		assertNotNull(result);
		assertEquals(2, result.size());
		verify(personRepository, times(1)).findAll();
	}

	@Test
	void testGetPersonById() {
		UUID personId = UUID.randomUUID();
		Person person = new Person();
		person.setId(personId);
		person.setFirstName("Jane");
		person.setLastName("Doe");
		person.setCompany("Setur");

		when(personRepository.findById(personId)).thenReturn(Optional.of(person));

		PersonDto result = personService.getPersonById(personId);

		assertNotNull(result);
		assertEquals(personId, result.getId());
		assertEquals("Jane", result.getFirstName());
		assertEquals("Doe", result.getLastName());
		assertEquals("Setur", result.getCompany());
		verify(personRepository, times(1)).findById(personId);
	}

	@Test
	void testAddContactInfo() {
		UUID personId = UUID.randomUUID();
		Person person = new Person();
		person.setId(personId);
		person.setFirstName("Jane");
		person.setLastName("Doe");
		person.setCompany("Setur");

		ContactInfoRequest contactRequest = new ContactInfoRequest();
		contactRequest.setContactType(ContactType.PHONE);
		contactRequest.setContent("5301234567");

		ContactInfo contactInfo = new ContactInfo();
		UUID contactInfoId = UUID.randomUUID();
		contactInfo.setId(contactInfoId);
		contactInfo.setContactType(ContactType.PHONE);
		contactInfo.setContent("5301234567");
		contactInfo.setPerson(person);

		when(personRepository.findById(personId)).thenReturn(Optional.of(person));
		when(contactInfoRepository.save(any(ContactInfo.class))).thenReturn(contactInfo);

		ContactInfoDto result = personService.addContactInfo(personId, contactRequest);

		assertNotNull(result);
		assertEquals(ContactType.PHONE, result.getContactType());
		assertEquals("5301234567", result.getContent());
		verify(contactInfoRepository, times(1)).save(any(ContactInfo.class));
	}

	@Test
	void testGetContactInfos() {
		UUID personId = UUID.randomUUID();
		Person person = new Person();
		person.setId(personId);
		List<ContactInfo> contactInfos = new ArrayList<>();
		ContactInfo contactInfo = new ContactInfo();
		UUID contactInfoId = UUID.randomUUID();
		contactInfo.setId(contactInfoId);
		contactInfo.setContactType(ContactType.PHONE);
		contactInfo.setContent("5301234567");
		contactInfo.setPerson(person);
		ContactInfo contactInfo2 = new ContactInfo();
		UUID contactInfoId2 = UUID.randomUUID();
		contactInfo2.setId(contactInfoId2);
		contactInfo2.setContactType(ContactType.EMAIL);
		contactInfo2.setContent("abc@example.com");
		contactInfo2.setPerson(person);
		contactInfos.add(contactInfo);
		contactInfos.add(contactInfo2);

		when(contactInfoRepository.findByPersonId(personId)).thenReturn(contactInfos);

		List<ContactInfoDto> result = personService.getContactInfos(personId);

		assertNotNull(result);
		assertEquals(2, result.size());
		verify(contactInfoRepository, times(1)).findByPersonId(personId);
	}

	@Test
	void testDeleteContactInfo() {
		UUID contactInfoId = UUID.randomUUID();
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setId(contactInfoId);
		contactInfo.setContactType(ContactType.PHONE);
		contactInfo.setContent("5301234567");

		when(contactInfoRepository.findById(contactInfoId)).thenReturn(Optional.of(contactInfo));
		doNothing().when(contactInfoRepository).delete(contactInfo);

		Boolean result = personService.deleteContactInfo(contactInfoId);

		assertTrue(result);
		verify(contactInfoRepository, times(1)).findById(contactInfoId);
		verify(contactInfoRepository, times(1)).delete(contactInfo);
	}
}
