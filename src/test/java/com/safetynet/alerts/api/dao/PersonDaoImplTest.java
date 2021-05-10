package com.safetynet.alerts.api.dao;

import com.safetynet.alerts.api.config.DataSource;
import com.safetynet.alerts.api.config.DataSourceTest;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonDaoImplTest {

    @Mock
    DataSource dataSource;

    @InjectMocks
    PersonDaoImpl personDao;

    @InjectMocks
    DataSourceTest dataSourceTest;

    @BeforeEach
    void clear() {
        dataSourceTest.clearPersonsMocked();
    }

    @Test
    @DisplayName("Get the list of all the persons present in the data source")
    void getAllPersonTest() {

        // GIVEN
        Mockito.when(dataSource.getAllPersons()).thenReturn(dataSourceTest.getAllPersonMocked());

        // WHEN
        List<Person> personList = personDao.findPersons();

        // THEN
        assertEquals(personList, dataSourceTest.getPersonsMocked());
    }

    @Test
    @DisplayName("Get an existing person found by first and last name")
    void findExistingPersonByFirstNameAndLastNameTest() {

        // GIVEN
        Mockito.when(dataSource.getAllPersons()).thenReturn(dataSourceTest.getAllPersonMocked());

        // WHEN
        Person person = personDao.findByFirstNameAndLastName("first name 1", "last name 1");

        // THEN
        assertEquals(person, dataSourceTest.getPersonsMocked().get(0));
    }

    @Test
    @DisplayName("Get an unknown person found by first and last name return null")
    void findUnknownPersonByFirstNameAndLastNameTest() {

        // GIVEN
        Mockito.when(dataSource.getAllPersons()).thenReturn(dataSourceTest.getAllPersonMocked());

        // WHEN
        Person person = personDao.findByFirstNameAndLastName("first name 5", "last name 5");

        // THEN
        assertNull(person);
    }

    @Test
    @DisplayName("Save a new person which does not exist in the data source")
    void saveNewPersonTest() {

        // GIVEN
        Mockito.when(dataSource.getAllPersons()).thenReturn(dataSourceTest.getAllPersonMocked());
        int sizeListBeforeSave = dataSourceTest.getPersonsMocked().size();


        // WHEN
        Person person = Person.builder()
                .firstName("first name")
                .lastName("last name")
                .address("address")
                .build(); // doesn't exist on dataSource
        Person savePerson = personDao.save(person);
        int sizeListAfterSave = dataSourceTest.getPersonsMocked().size();


        // THEN
        Person personTest = dataSourceTest.getPersonsMocked().get(3);
        assertEquals(savePerson.getFirstName(), personTest.getFirstName());
        assertEquals(savePerson.getLastName(), personTest.getLastName());
        assertEquals(savePerson.getAddress(), personTest.getAddress());
        assertTrue(sizeListBeforeSave < sizeListAfterSave);
        assertEquals(sizeListBeforeSave + 1, sizeListAfterSave);
    }

    @Test
    @DisplayName("Save a person already existing in the data source")
    void savePersonAlreadyExistingTest() {

        // GIVEN
        Mockito.when(dataSource.getAllPersons()).thenReturn(dataSourceTest.getAllPersonMocked());
        int sizeListBeforeSave = dataSourceTest.getPersonsMocked().size();


        // WHEN
        Person person1 = Person.builder()
                .firstName("first name 1")
                .lastName("last name 1")
                .address("address 5")
                .phone("phone 5")
                .email("email 5")
                .zip("zip 5")
                .city("city 5")
                .build(); // already exist on index 0 in dataSource
        Person savePerson = personDao.save(person1);
        int sizeListAfterSave = dataSourceTest.getPersonsMocked().size();

        // THEN
        Person personTest = dataSourceTest.getPersonsMocked().get(0);
        assertEquals(savePerson.getFirstName(), personTest.getFirstName());
        assertEquals(savePerson.getLastName(), personTest.getLastName());
        assertEquals("address 5", personTest.getAddress());
        assertEquals("phone 5", personTest.getPhone());
        assertEquals("email 5", personTest.getEmail());
        assertEquals("zip 5", personTest.getZip());
        assertEquals("city 5", personTest.getCity());
        assertEquals(sizeListBeforeSave, sizeListAfterSave);
    }

    @Test
    @DisplayName("Delete a person existing in data Source found by first and last name")
    void deletePersonFoundByFirstNameAndLastNameTest() {

        // GIVEN
        Mockito.when(dataSource.getAllPersons()).thenReturn(dataSourceTest.getAllPersonMocked());
        int sizeListBeforeDelete = dataSourceTest.getPersonsMocked().size();

        // WHEN
        Person personToDelete = dataSourceTest.getPersonsMocked().get(0);
        personDao.delete(personToDelete.getFirstName(),personToDelete.getLastName());
        int sizeListAfterDelete = dataSourceTest.getPersonsMocked().size();

        // THEN
        assertTrue(sizeListBeforeDelete > sizeListAfterDelete);
        assertEquals(sizeListBeforeDelete - 1, sizeListAfterDelete);

    }


}