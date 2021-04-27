package com.safetynet.alerts.api.dao;

import com.safetynet.alerts.api.config.DataSource;
import com.safetynet.alerts.api.config.DataSourceTest;
import com.safetynet.alerts.api.model.Person;
import org.junit.jupiter.api.*;
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
    @DisplayName("Save a new person which does not exist in the data source")
    void saveNewPersonTest() {

        // GIVEN
        Mockito.when(dataSource.getAllPersons()).thenReturn(dataSourceTest.getAllPersonMocked());

        // WHEN
        Person person = new Person("first name", "last name", "phone",null,null,null,null);
        Person createdPerson = personDao.save(person);

        // THEN
        assertEquals("first name", createdPerson.getFirstName());
        assertEquals("last name", createdPerson.getLastName());
        assertEquals("phone", createdPerson.getPhone());
    }

    @Test
    @DisplayName("Save a person already existing in the data source")
    void savePersonAlreadyExistingTest() {

        // GIVEN
        Mockito.when(dataSource.getAllPersons()).thenReturn(dataSourceTest.getAllPersonMocked());

        // WHEN
        Person person = new Person("first name 1", "last name 1", "phone 5",null,null,null,null);
        Person createdPerson = personDao.save(person);

        // THEN
        assertEquals("first name 1", createdPerson.getFirstName());
        assertEquals("last name 1", createdPerson.getLastName());
        assertEquals("phone 5", createdPerson.getPhone());
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

//    @Test
//    @DisplayName("Delete a person found by first and last name")
//    void deletePersonFoundByFirstNameAndLastNameTest() {
//
//        // GIVEN
//        Mockito.when(dataSource.getAllPersons()).thenReturn(dataSourceTest.getAllPersonMocked());
//        Person personToDelete = new Person("first name 1", "last name 1", null, null, null, null, null);
//        // WHEN
//        personDao.delete(dataSourceTest.getAllPersonMocked().get(2));
//
//        // THEN
//        assertEquals(dataSourceTest.getPersonsMocked().get(2).getFirstName(), personToDelete.getFirstName());
//        for(int i = 0 ; i < dataSourceTest.getPersonsMocked().size(); i++)
//            System.out.println(dataSourceTest.getPersonsMocked().get(i));
//    }

}