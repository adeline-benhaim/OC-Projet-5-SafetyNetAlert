package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.config.DataSourceTest;
import com.safetynet.alerts.api.dao.FirestationDao;
import com.safetynet.alerts.api.dao.MedicalRecordDao;
import com.safetynet.alerts.api.dao.PersonDao;
import com.safetynet.alerts.api.exceptions.FirestationNotFoundException;
import com.safetynet.alerts.api.exceptions.PersonAlreadyExistException;
import com.safetynet.alerts.api.exceptions.PersonNotFoundException;
import com.safetynet.alerts.api.model.Firestation;
import com.safetynet.alerts.api.model.MedicalRecord;
import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.model.dto.ListPersonAdultChildDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    @Mock
    PersonDao personDao;

    @Mock
    FirestationDao firestationDao;

    @Mock
    MedicalRecordDao medicalRecordDao;

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
        when(personDao.findByFirstNameAndLastName(personPresent.getFirstName(),personPresent.getLastName())).thenReturn(dataSourceTest.getAllPersonMocked().get(0));

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

    @Test
    @DisplayName("Find a global list of persons found by station number")
    void findListPersonsByStationNumberTest() {

        //GIVEN
        String stationNumber = "0";
        List<Person> personList = new ArrayList<>();
        Person person = Person.builder()
                .address("address").build();
        personList.add(person);
        Person person1 = Person.builder()
                .address("address1").build();
        personList.add(person1);
        List<Firestation> firestationList = new ArrayList<>();
        Firestation firestation = Firestation.builder().stationNumber("0").address("address").build();
        firestationList.add(firestation);
        Firestation firestation1 = Firestation.builder().stationNumber("1").address("address1").build();
        firestationList.add(firestation1);
        when(firestationDao.findFirestations()).thenReturn(firestationList);
        when(personDao.findPersons()).thenReturn(personList);

        //WHEN
        List<Person> personListTest = personService.findGlobalListOfPersonsByStationNumber(stationNumber);
        List<Person> personListTest1 = personService.findGlobalListOfPersonsByStationNumber("1");

        //THEN
            assertTrue(personListTest.contains(person));
            assertEquals(personListTest.get(0).getAddress(), person.getAddress());
            assertEquals(firestation.getAddress(), person.getAddress());
            assertThrows(FirestationNotFoundException.class, () -> personService.findGlobalListOfPersonsByStationNumber("2"));
            assertTrue(personListTest1.contains(person1));
            assertEquals(personListTest1.get(0).getAddress(), person1.getAddress());
            assertEquals(firestation1.getAddress(), person1.getAddress());
    }

    @Test
    @DisplayName("Find a global list of persons by an unknown station number return not found exception")
    void findListPersonsByUnknownStationNumberTest() {

        //WHEN
        String stationNumber = "number 11";

        //THEN
        assertThrows(FirestationNotFoundException.class, () -> personService.findGlobalListOfPersonsByStationNumber(stationNumber));
    }

    @Test
    @DisplayName("Find a list of children and a list of adults from a list of persons")
    void findChildrenListAndAdultListTest() {

        //GIVEN
        List<MedicalRecord> medicalRecordList = new ArrayList<>();
        MedicalRecord medicalRecordAdult = MedicalRecord.builder()
                .firstName("firstname").lastName("lastname").birthdate("12/28/1950").build();
        medicalRecordList.add(medicalRecordAdult);
        MedicalRecord medicalRecordChild = MedicalRecord.builder()
                .firstName("firstname1").lastName("lastname1").birthdate("12/28/2015").build();
        medicalRecordList.add(medicalRecordChild);
        List<Person> personList = new ArrayList<>();
        Person person = Person.builder()
                .firstName("firstname").lastName("lastname").build();
        personList.add(person);
        Person person1 = Person.builder()
                .firstName("firstname1").lastName("lastname1").build();
        personList.add(person1);
        when(medicalRecordDao.findMedicalRecords()).thenReturn(medicalRecordList);

        //WHEN
        ListPersonAdultChildDto personList1 = personService.findChildrenListAndAdultList(personList);

        //THEN
        assertTrue(personList1.getListOfAdult().contains(person));
        assertTrue(personList1.getListOfChild().contains(person1));
    }

    @Test
    @DisplayName("Find a list of children and a list of adults from a empty list of person return not found exception")
    void findListsAdultAndChildFromEmptyListOfPersonTest() {

        //WHEN
        List<Person> personList = new ArrayList<>();

        //THEN
        assertThrows(FirestationNotFoundException.class, () -> personService.findChildrenListAndAdultList(personList));
    }

}

