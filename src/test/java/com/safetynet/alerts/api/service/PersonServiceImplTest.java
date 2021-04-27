package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.config.DataSourceTest;
import com.safetynet.alerts.api.dao.PersonDao;
import com.safetynet.alerts.api.exceptions.PersonNotFoundException;
import com.safetynet.alerts.api.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    @Mock
    PersonDao personDao;

    @InjectMocks
    PersonServiceImpl personService;

    @InjectMocks
    DataSourceTest dataSourceTest;

    @BeforeEach
    void clear() {
        dataSourceTest.clearPersonsMocked();
    }


    @Test
    @DisplayName("Get the list of all the persons present in the data source")
    void testGetAllPersons() {

        //GIVEN
        Mockito.when(personDao.findPersons()).thenReturn(dataSourceTest.getAllPersonMocked());

        //WHEN
        List<Person> personList = personService.getAllPersons();

        //THEN
        assertEquals(personList, personDao.findPersons());
    }

    @Test
    @DisplayName("Get an existing person found by first and last name")
    void findExistingPersonByFirstNameAndLastNameTest() {

        // GIVEN
        Mockito.when(personDao.findByFirstNameAndLastName("first name 1", "last name 1")).thenReturn(dataSourceTest.getAllPersonMocked().get(0));

        // WHEN
        Person person = personService.findPersonByFirstNameAndLastName("first name 1", "last name 1");
        Person person1 = personDao.findByFirstNameAndLastName("first name 1", "last name 1");

        // THEN
        assertEquals(person, person1);
    }

    @Test
    @DisplayName("Create a new person")
    void createANewPersonTest() {

        // GIVEN
        Person newPerson = new Person("firstname 8", "lastname 8", null, null, null, null, null);

        // WHEN
        personService.createNewPerson(newPerson);

        // THEN
        verify(personDao, Mockito.times(1)).save(newPerson);
    }

    @Test
    @DisplayName("Delete a person")
    void deleteAPersonTest() {

        // GIVEN
        Person newPerson = new Person("firstname 8", "lastname 8", null, null, null, null, null);

        // WHEN
        personService.deletePerson(newPerson.firstName, newPerson.lastName);

        // THEN
        verify(personDao, Mockito.times(1)).delete(newPerson.firstName,newPerson.lastName);
    }

    @Test
    @DisplayName("Get an unknown person found by first and last name")
    void findUnknownPersonByFirstNameAndLastNameTest() {

        // GIVEN

        // WHEN
        Mockito.when(personDao.findByFirstNameAndLastName("first name 5", "last name 5")).thenThrow(PersonNotFoundException.class);

        // THEN
        assertThrows(PersonNotFoundException.class, () -> personService.findPersonByFirstNameAndLastName("first name 5", "last name 5"));
    }

    @Test
    @DisplayName("Update an unknown person found by first and last name")
    void updateUnknownPersonByFirstNameAndLastNameTest() {

        //GIVEN
        //Mockito.when(personDao.findPersons()).thenReturn(dataSourceTest.getAllPersonMocked());
        Person person = new Person("first name 5","last name 5", null,null,null,null,null);
        Mockito.when(personDao.findByFirstNameAndLastName(person.firstName, person.getLastName())).thenReturn(null);

        //WHEN
        //Person person = personService.updatePerson(person1);

        //THEN
        assertThrows(PersonNotFoundException.class, () -> personService.updatePerson(person));
    }
//
//    @Test
//    @DisplayName("Update a new person")
//    void updateAPersonFoundTest() {
//
//        // GIVEN
//        Person newPerson = new Person("firstname 8","lastname 8",null,null,null,null,null);
//
//        // WHEN
//        personService.updatePerson(newPerson);
//
//        // THEN
//        verify(personDao, Mockito.times(1)).save(newPerson);
//    }

}

