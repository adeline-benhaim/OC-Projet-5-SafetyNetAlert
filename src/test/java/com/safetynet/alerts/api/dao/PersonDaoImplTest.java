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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

        // WHEN
        Person person = new Person("first name", "last name", "address",null,null,null,null); // doesn't exist on dataSource
        personDao.save(person);

        // THEN
        assertEquals("first name", dataSourceTest.getPersonsMocked().get(3).getFirstName());
        assertEquals("last name", dataSourceTest.getPersonsMocked().get(3).getLastName());
        assertEquals("address", dataSourceTest.getPersonsMocked().get(3).getAddress());
    }

    @Test
    @DisplayName("Save a person already existing in the data source")
    void savePersonAlreadyExistingTest() {

        // GIVEN
        Mockito.when(dataSource.getAllPersons()).thenReturn(dataSourceTest.getAllPersonMocked());

        // WHEN
        Person person1 = new Person("first name 1", "last name 1", "address 5",null,null,null,null); // already exist on index 0 in dataSource
        personDao.save(person1);

        // THEN
        assertEquals("first name 1", dataSourceTest.getPersonsMocked().get(0).getFirstName());
        assertEquals("last name 1", dataSourceTest.getPersonsMocked().get(0).getLastName());
        assertEquals("address 5", dataSourceTest.getPersonsMocked().get(0).getAddress());
    }

    @Test
    @DisplayName("Delete a person existing in data Source found by first and last name")
    void deletePersonFoundByFirstNameAndLastNameTest() {

        // GIVEN
        Mockito.when(dataSource.getAllPersons()).thenReturn(dataSourceTest.getAllPersonMocked());
        Person person = new Person("first name 5", "last name 5", "phone 5",null,null,null,null);
        personDao.save(person);
        int sizeListBeforeDelete = dataSourceTest.getPersonsMocked().size();

        // WHEN
        personDao.delete(person.firstName,person.lastName);
        int sizeListAfterDelete = dataSourceTest.getPersonsMocked().size();

        // THEN
        assertEquals(4, sizeListBeforeDelete);
        assertEquals(3, sizeListAfterDelete);

    }


}