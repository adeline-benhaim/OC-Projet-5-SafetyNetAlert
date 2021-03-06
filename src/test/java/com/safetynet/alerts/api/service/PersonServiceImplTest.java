package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.config.DataSourceTest;
import com.safetynet.alerts.api.dao.PersonDao;
import com.safetynet.alerts.api.exceptions.PersonAlreadyExistException;
import com.safetynet.alerts.api.exceptions.PersonNotFoundException;
import com.safetynet.alerts.api.model.Person;
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
import static org.mockito.Mockito.when;


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
        when(personDao.findPersons()).thenReturn(dataSourceTest.getAllPersonMocked());

        //WHEN
        List<Person> personList = personService.getAllPersons();

        //THEN
        assertEquals(personList, dataSourceTest.getPersonsMocked());
    }

    @Test
    @DisplayName("Get an existing person found by first and last name")
    void findExistingPersonByFirstNameAndLastNameTest() {

        // GIVEN
        when(personDao.findByFirstNameAndLastName("first name 1", "last name 1")).thenReturn(dataSourceTest.getAllPersonMocked().get(0));

        // WHEN
        Person person = personService.findPersonByFirstNameAndLastName("first name 1", "last name 1");

        // THEN
        assertEquals(person, dataSourceTest.getPersonsMocked().get(0));
    }

    @Test
    @DisplayName("Get an unknown person found by first and last name")
    void findUnknownPersonByFirstNameAndLastNameTest() {

        // GIVEN

        // WHEN
        when(personDao.findByFirstNameAndLastName("first name 5", "last name 5")).thenReturn(null);

        // THEN
        assertThrows(PersonNotFoundException.class, () -> personService.findPersonByFirstNameAndLastName("first name 5", "last name 5"));
    }

    @Test
    @DisplayName("Update an unknown person found by first and last name")
    void updateUnknownPersonByFirstNameAndLastNameTest() {

        //GIVEN
        Person person = Person.builder()
                .firstName("firstname 5")
                .lastName("lastname 5")
                .build();

        //WHEN
        when(personDao.findByFirstNameAndLastName(person.getFirstName(), person.getLastName())).thenReturn(null);

        //THEN
        assertThrows(PersonNotFoundException.class, () -> personService.updatePerson(person));
    }

    @Test
    @DisplayName("Update a person who is already present in DataSource")
    void updateAPersonFoundInDataSourceTest() {

        // GIVEN
        Person personPresent = Person.builder()
                .firstName("firstname 1")
                .lastName("lastname 1")
                .build();
        when(personDao.findByFirstNameAndLastName(personPresent.getFirstName(), personPresent.getLastName())).thenReturn(dataSourceTest.getAllPersonMocked().get(0));

        // WHEN
        personService.updatePerson(personPresent);

        // THEN
        verify(personDao, Mockito.times(1)).save(personPresent);
    }

    @Test
    @DisplayName("Create a new person")
    void createANewPersonTest() {

        // GIVEN
        Person newPerson = Person.builder()
                .firstName("firstname 8")
                .lastName("lastname 8")
                .build();
        when(personDao.findByFirstNameAndLastName(newPerson.getFirstName(), newPerson.getLastName())).thenReturn(null);

        // WHEN
        personService.createNewPerson(newPerson);

        // THEN
        verify(personDao, Mockito.times(1)).save(newPerson);
    }

    @Test
    @DisplayName("Try to create a person who already exist in data source return exception already exist")
    void createAPersonAlreadyExistingInDataSourceTest() {

        // GIVEN
        Person newPerson = Person.builder()
                .firstName("firstname 1")
                .lastName("lastname 1")
                .build();
        when(personDao.findByFirstNameAndLastName(newPerson.getFirstName(), newPerson.getLastName())).thenReturn(dataSourceTest.getAllPersonMocked().get(0));

        // WHEN

        // THEN
        assertThrows(PersonAlreadyExistException.class, () -> personService.createNewPerson(newPerson));
    }

    @Test
    @DisplayName("Delete a person")
    void deleteAPersonTest() {

        // GIVEN


        // WHEN
        personService.deletePerson("firstName", "lastName");

        // THEN
        verify(personDao, Mockito.times(1)).delete("firstName", "lastName");
    }
}

