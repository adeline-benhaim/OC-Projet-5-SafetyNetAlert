package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.config.DataSource;
import com.safetynet.alerts.api.config.DataSourceTest;
import com.safetynet.alerts.api.dao.FirestationDao;
import com.safetynet.alerts.api.dao.MedicalRecordDao;
import com.safetynet.alerts.api.dao.MedicalRecordDaoImpl;
import com.safetynet.alerts.api.dao.PersonDao;
import com.safetynet.alerts.api.exceptions.FirestationNotFoundException;
import com.safetynet.alerts.api.exceptions.PersonNotFoundException;
import com.safetynet.alerts.api.mapper.PersonMapper;
import com.safetynet.alerts.api.model.Firestation;
import com.safetynet.alerts.api.model.MedicalRecord;
import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.model.dto.*;
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


//    @Test
//    @DisplayName("Find a list of persons covered by the station number sought and a count of adults and children concerned")
//    void personInfoByFirestationTest() {
//
//        // GIVEN
//        when(personDao.findByStationNumber("number")).thenReturn(dataSourceTest.getAllPersonMocked());
//        List<Person> personList = personDao.findByStationNumber("number");
//        List<PersonDto> personDtoList = personList
//                .stream()
//                .map(PersonMapper::convertToPersonDto)
//                .collect(Collectors.toList());
//        when(informationService.findPersonsInfoByStationNumber("number").getPersonDto()).thenReturn(personDtoList);
//
//        List<MedicalRecord> medicalRecordList = dataSourceTest.getAllMedicalRecordMocked();
//        List<Person> childrenList = new ArrayList<>();
//        List<Person> adultList = new ArrayList<>();
//        for (Person person : personList) {
//            for (MedicalRecord medicalRecord : medicalRecordList) {
//                when(medicalRecordDao.findByFirstNameAndLastName(person.getFirstName(), person.getLastName())).thenReturn(medicalRecord);
//                medicalRecordDao.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
//                String birthdate = medicalRecord.getBirthdate();
//                person.setBirthdate(birthdate);
//                person.calculateAge(birthdate);
//                if (person.getAge() <= 18) {
//                    childrenList.add(person);
//                } else {
//                    adultList.add(person);
//                }
//            }
//        }
//        when(informationService.findChildrenListAndAdultList(personList)).thenReturn(PersonAdultChildListDto.builder()
//                .listOfAdult(adultList)
//                .listOfChild(childrenList)
//                .build());
//
//
//        //WHEN
//        PersonInfoByFirestationDto personInfoByFirestationDto = informationService.findPersonsInfoByStationNumber("number");
//        when(personInfoByFirestationDto.getNumberOfChildren()).thenReturn(informationService.findChildrenListAndAdultList(personList).getListOfChild().size());
//        when(personInfoByFirestationDto.getNumberOfAdults()).thenReturn(informationService.findChildrenListAndAdultList(personList).getListOfAdult().size());
//        when(personInfoByFirestationDto.getPersonDto()).thenReturn(personDtoList);
//        informationService.findPersonsInfoByStationNumber("number");
//
//        // THEN
//        assertEquals(2, personInfoByFirestationDto.getNumberOfAdults());
//        assertEquals(4, personInfoByFirestationDto.getNumberOfChildren());
//        assertEquals(6, personInfoByFirestationDto.getPersonDto().size());
//    }

    @Test
    @DisplayName("Find a list of persons covered by the station number sought and a count of adults and children concerned from a empty list of person return not found exception")
    void personInfoByFirestationFromEmptyListOfPersonTest() {

        //WHEN
        when(personDao.findByStationNumber("stationNumber")).thenReturn(null);

        // THEN
        assertThrows(FirestationNotFoundException.class, () -> informationService.findPersonsInfoByStationNumber("stationNumber"));
    }

//    @Test
//    @DisplayName("Find a list of children living at the address sought with a list of other house hold members")
//    void findChildrenByAddressTest() {
//
//        //GIVEN
//        when(personDao.findByAddress("address")).thenReturn(dataSourceTest.getAllPersonMocked());
//
//        //WHEN
//        List<ChildAlertDto> childAlertDtoList = informationService.findChildrenByAddress("address");
//
//        //THEN
//        assertEquals(6, childAlertDtoList.get(2).getAge());
//        assertEquals("Pierre", childAlertDtoList.get(2).getFirstName());
//        assertEquals("Dupond", childAlertDtoList.get(2).getLastName());
//        assertEquals("Dupond", childAlertDtoList.get(2).getHouseHoldMembers().get(0).getLastName());
//        assertEquals("address", childAlertDtoList.get(2).getHouseHoldMembers().get(0).getAddress());
//    }

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
//
//    @Test
//    @DisplayName("Find a list of person, and firestation number corresponding to a given address")
//    void findListOfFirePersonByAddressTest() {
//
//        //GIVEN
//        Firestation firestationMock = Firestation.builder()
//                .address("address")
//                .stationNumber("number")
//                .build();
//        MedicalRecord medicalRecordMock = MedicalRecord.builder()
//                .firstName("dsf")
//                .lastName("dfd")
//                .build();
//        when(personDao.findByAddress("address")).thenReturn(dataSourceTest.getAllPersonMocked());
//        when(medicalRecordDao.findMedicalRecords()).thenReturn(dataSourceTest.getAllMedicalRecordMocked());
//        when(firestationDao.findByAddress("address")).thenReturn(firestationMock);
//        Firestation firestation = firestationDao.findByAddress("address");
//        List<Person> personList = personDao.findByAddress("address");
//        List<PersonInfoFireDto> personInfoFireDtoList = personList
//                .stream()
//                .map(PersonMapper::convertToPersonInfoFireDto)
//                .collect(Collectors.toList());
//        when(informationService.findListOfFirePersonByAddress("address").getPersonInfoFireDtoList()).thenReturn(personInfoFireDtoList);
//
//        //WHEN
//        FireDto fireDto = informationService.findListOfFirePersonByAddress("address");
//
//        //THEN
//        assertEquals(firestation.getStationNumber(), fireDto.getFirestationNumber());
//    }
}
