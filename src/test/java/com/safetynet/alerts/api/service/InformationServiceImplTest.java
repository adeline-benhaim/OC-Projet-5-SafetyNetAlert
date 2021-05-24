package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.config.DataSourceTest;
import com.safetynet.alerts.api.dao.FirestationDao;
import com.safetynet.alerts.api.dao.MedicalRecordDao;
import com.safetynet.alerts.api.dao.PersonDao;
import com.safetynet.alerts.api.exceptions.FirestationNotFoundException;
import com.safetynet.alerts.api.exceptions.PersonNotFoundException;
import com.safetynet.alerts.api.model.MedicalRecord;
import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.model.dto.PersonAdultChildListDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InformationServiceImplTest {

    @Mock
    MedicalRecordDao medicalRecordDao;
    @Mock
    PersonDao personDao;
    @Mock
    FirestationDao firestationDao;
    @InjectMocks
    InformationServiceImpl informationService;
    @InjectMocks
    DataSourceTest dataSourceTest;

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
        when(medicalRecordDao.findByFirstNameAndLastName("firstname", "lastname")).thenReturn(medicalRecordAdult);
        when(medicalRecordDao.findByFirstNameAndLastName("firstname1", "lastname1")).thenReturn(medicalRecordChild);

        //WHEN
        PersonAdultChildListDto personList1 = informationService.findChildrenListAndAdultList(personList);

        //THEN
        assertTrue(personList1.getListOfAdult().contains(person));
        assertTrue(personList1.getListOfChild().contains(person1));
        assertTrue(medicalRecordList.get(0).getBirthdate().contains(person.getBirthdate()));
        assertTrue(person.getAge() > 18);
        assertTrue(person1.getAge() < 18);
    }

    @Test
    @DisplayName("Find a list of children and a list of adults from a empty list of person return not found exception")
    void findListsAdultAndChildFromEmptyListOfPersonTest() {

        //WHEN

        //THEN
        assertThrows(FirestationNotFoundException.class, () -> informationService.findChildrenListAndAdultList(null));
    }

    @Test
    @DisplayName("Find a list of persons covered by the station number sought and a count of adults and children concerned from a empty list of person return not found exception")
    void personInfoByFirestationFromEmptyListOfPersonTest() {

        //WHEN
        when(personDao.findByStationNumber("stationNumber")).thenReturn(null);

        // THEN
        assertThrows(FirestationNotFoundException.class, () -> informationService.findPersonsInfoByStationNumber("stationNumber"));
    }

    @Test
    @DisplayName("Find a list of children living at the address sought with a list of other house hold members from a empty list of person return not found exception")
    void findChildrenByAddressFromEmptyListOfPersonTest() {

        //WHEN
        when(personDao.findByAddress("address")).thenReturn(null);

        //THEN
        assertThrows(PersonNotFoundException.class, () -> informationService.findChildrenByAddress("address"));
    }

    @Test
    @DisplayName("Find a list of phone numbers of persons covered by the firestation number sought")
    void findListOfPhoneNumbersByFirestationNumberTest() {

        //GIVEN
        when(personDao.findByStationNumber("number")).thenReturn(dataSourceTest.getAllPersonMocked());
        List<String> phonesMocked = new ArrayList<>();
        Collections.addAll(phonesMocked, "phone 1", "phone 2", "phone 3", "phone 4", "phone 5", "phone 6", "phone 7");

        //WHEN
        List<String> phones = informationService.findListOfPhoneNumbersByFirestationNumber("number");

        //THEN
        assertEquals(phonesMocked, phones);
    }

    @Test
    @DisplayName("Find a list of phone numbers of persons covered by the firestation number sought from a empty list of person return not found exception")
    void findListOfPhoneNumbersByFirestationNumberFromEmptyListOfPersonTest() {

        //WHEN
        when(personDao.findByStationNumber("number")).thenReturn(null);

        //THEN
        assertThrows(FirestationNotFoundException.class, () -> informationService.findListOfPhoneNumbersByFirestationNumber("number"));
    }

    @Test
    @DisplayName("Find a list of person, and firestation number corresponding to a given unknown address return not found exception")
    void findListOfFirePersonByUnknownAddressTest() {

        //WHEN
        when(personDao.findByAddress("address")).thenReturn(null);

        //THEN
        assertThrows(PersonNotFoundException.class, () -> informationService.findListOfFirePersonByAddress("address"));
    }

    @Test
    @DisplayName("Find a list of all persons served by an unknown station number, sorted by address return not found exception")
    void findListOfFloodPersonByUnknownStationNumberTest() {

        //WHEN
        when(firestationDao.findByStationNumber("5")).thenReturn(null);

        //THEN
        assertThrows(FirestationNotFoundException.class, () -> informationService.findListOfFloodPersonByStationNumber("5"));
    }

    @Test
    @DisplayName("Find a person info by unknown firstname and lastname return not found exception")
    void findPersonInfoByUnknownFirstnameAndLastnameTest() {

        //WHEN
        when(personDao.findPersons()).thenReturn(dataSourceTest.getAllPersonMocked());

        //THEN
        assertThrows(PersonNotFoundException.class, () -> informationService.findPersonInfoByFirstnameAndLastname("first name 8","last name 8"));
    }

    @Test
    @DisplayName("Find a list of email of all persons living in the city sought")
    void findListOfEmailOfPersonLivingInCitySoughtTest() {

        //GIVEN
        List<Person> personList = new ArrayList<>();
        Person person = Person.builder()
                .email("email")
                .city("city").build();
        personList.add(person);
        Person person1 = Person.builder()
                .email("email1")
                .city("city").build();
        personList.add(person1);
        when(personDao.findByCity("city")).thenReturn(personList);

        //WHEN
        List<String> listEmails = informationService.findEmailByCity("city");

        //THEN
        assertEquals(2, listEmails.size());
        assertEquals("email", listEmails.get(0));
        assertEquals("email1", listEmails.get(1));
    }

    @Test
    @DisplayName("Find a list of email of all persons by unknown city return not found exception")
    void findListOfEmailOfPersonByUnknownCityTest() {

        //WHEN
        when(personDao.findByCity("city")).thenReturn(null);

        //THEN
        assertThrows(PersonNotFoundException.class, () -> informationService.findEmailByCity("city"));
    }
}
