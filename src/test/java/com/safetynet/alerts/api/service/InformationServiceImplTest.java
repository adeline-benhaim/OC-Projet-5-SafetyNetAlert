package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.config.DataSourceTest;
import com.safetynet.alerts.api.dao.MedicalRecordDao;
import com.safetynet.alerts.api.dao.PersonDao;
import com.safetynet.alerts.api.exceptions.FirestationNotFoundException;
import com.safetynet.alerts.api.exceptions.PersonNotFoundException;
import com.safetynet.alerts.api.mapper.PersonMapper;
import com.safetynet.alerts.api.model.MedicalRecord;
import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.model.dto.ChildAlertDto;
import com.safetynet.alerts.api.model.dto.PersonAdultChildListDto;
import com.safetynet.alerts.api.model.dto.PersonDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InformationServiceImplTest {

    @Mock
    MedicalRecordDao medicalRecordDao;
    @Mock
    PersonDao personDao;
    @InjectMocks
    InformationServiceImpl informationService;
    @InjectMocks
    DataSourceTest dataSourceTest;
    @Mock
    PersonMapper personMapper;
    PersonAdultChildListDto personAdultChildListDto;


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
        PersonAdultChildListDto personList1 = informationService.findChildrenListAndAdultList(personList);

        //THEN
        assertTrue(personList1.getListOfAdult().contains(person));
        assertTrue(personList1.getListOfChild().contains(person1));
    }

    @Test
    @DisplayName("Find a list of children and a list of adults from a empty list of person return not found exception")
    void findListsAdultAndChildFromEmptyListOfPersonTest() {

        //WHEN
        List<Person> personList = null;

        //THEN
        assertThrows(FirestationNotFoundException.class, () -> informationService.findChildrenListAndAdultList(personList));
    }


    @Test
    @DisplayName("Find a list of persons covered by the station number sought and a count of adults and children concerned")
    void personInfoByFirestationTest() {

        // GIVEN
        when(personDao.findByStationNumber("number")).thenReturn(dataSourceTest.getAllPersonMocked());
        List<Person> personList = personDao.findByStationNumber("number");
        List<PersonDto> personDtoList = personList
                .stream()
                .map(PersonMapper::convertToPersonDto)
                .collect(Collectors.toList());
        when(informationService.findPersonsInfoByStationNumber("number").getPersonDto()).thenReturn(personDtoList);
        when(medicalRecordDao.findMedicalRecords()).thenReturn(dataSourceTest.getAllMedicalRecordMocked());

        //WHEN
        informationService.findPersonsInfoByStationNumber("number");

        // THEN
        assertEquals(2, informationService.findPersonsInfoByStationNumber("number").getCountPersonAdultChildDto().get("Number of adults"));
        assertEquals(4, informationService.findPersonsInfoByStationNumber("number").getCountPersonAdultChildDto().get("Number of children"));
        assertEquals(6, informationService.findPersonsInfoByStationNumber("number").getPersonDto().size());
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
    @DisplayName("Find a list of children living at the address sought with a list of other house hold members")
    void findChildrenByAddressTest() {

        //GIVEN
        when(personDao.findByAddress("address")).thenReturn(dataSourceTest.getAllPersonMocked());
        when(medicalRecordDao.findMedicalRecords()).thenReturn(dataSourceTest.getAllMedicalRecordMocked());

        //WHEN
        List<ChildAlertDto> childAlertDtoList = informationService.findChildrenByAddress("address");

        //THEN
        assertEquals(6, childAlertDtoList.get(2).getAge());
        assertEquals("Pierre", childAlertDtoList.get(2).getFirstName());
        assertEquals("Dupond", childAlertDtoList.get(2).getLastName());
        assertEquals("Dupond", childAlertDtoList.get(2).getHouseHoldMembers().get(0).getLastName());
        assertEquals("address", childAlertDtoList.get(2).getHouseHoldMembers().get(0).getAddress());
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
        Collections.addAll(phonesMocked, "phone 1", "phone 2", "phone 3", "phone 4", "phone 5", "phone 6");

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
}
