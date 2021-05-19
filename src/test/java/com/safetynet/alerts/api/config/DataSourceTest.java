package com.safetynet.alerts.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.api.model.Firestation;
import com.safetynet.alerts.api.model.MedicalRecord;
import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.model.dto.PersonAdultChildListDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DataSourceTest {

    List<Person> personsMocked = new ArrayList<>();
    List<Firestation> firestationsMocked = new ArrayList<>();
    List<MedicalRecord> medicalRecordsMocked = new ArrayList<>();

    public List<Person> getAllPersonMocked() {
        Person person1 = Person.builder()
                .firstName("first name 1")
                .lastName("last name 1")
                .phone("phone 1")
                .zip("zip 1")
                .address("address 1")
                .city("city 1")
                .email("email 1")
                .build();
        Person person2 = Person.builder()
                .firstName("first name 2")
                .lastName("last name 2")
                .phone("phone 2")
                .zip("zip 2")
                .address("address 2")
                .city("city 2")
                .email("email 2")
                .build();
        Person person3 = Person.builder()
                .firstName("first name 3")
                .lastName("last name 3")
                .phone("phone 3")
                .zip("zip 3")
                .address("address 3")
                .city("city 3")
                .email("email 3")
                .build();
        Person person4 = Person.builder()
                .firstName("Pierre")
                .lastName("Dupond")
                .phone("phone 4")
                .zip("zip 4")
                .address("address")
                .city("city 4")
                .email("email 4")
                .build();
        Person person5 = Person.builder()
                .firstName("Paul")
                .lastName("Dupond")
                .phone("phone 5")
                .zip("zip 4")
                .address("address")
                .city("city 4")
                .email("email 5")
                .build();
        Person person6 = Person.builder()
                .firstName("Marie")
                .lastName("Dupond")
                .phone("phone 6")
                .zip("zip 4")
                .address("address")
                .city("city 4")
                .email("email 6")
                .build();
        personsMocked.add(person1);
        personsMocked.add(person2);
        personsMocked.add(person3);
        personsMocked.add(person4);
        personsMocked.add(person5);
        personsMocked.add(person6);
        return personsMocked;
    }

    public List<Firestation> getAllFirestationMocked() {
        Firestation firestation1 = Firestation.builder()
                .address("address 1")
                .stationNumber("number 1")
                .build();
        Firestation firestation2 = Firestation.builder()
                .address("address 2")
                .stationNumber("number 2")
                .build();
        Firestation firestation3 = Firestation.builder()
                .address("address 3")
                .stationNumber("number 3")
                .build();
        Firestation firestation4 = Firestation.builder()
                .address("address")
                .stationNumber("number")
                .build();
        firestationsMocked.add(firestation1);
        firestationsMocked.add(firestation2);
        firestationsMocked.add(firestation3);
        firestationsMocked.add(firestation4);
        return firestationsMocked;
    }

    public List<MedicalRecord> getAllMedicalRecordMocked() {
        MedicalRecord medicalRecord1 = MedicalRecord.builder()
                .firstName("first name 1")
                .lastName("last name 1")
                .birthdate("01/01/2015")
                .medications("medications 1")
                .allergies("allergies 1")
                .build();
        MedicalRecord medicalRecord2 = MedicalRecord.builder()
                .firstName("first name 2")
                .lastName("last name 2")
                .birthdate("01/01/2010")
                .medications("medications 2")
                .allergies("allergies 2")
                .build();
        MedicalRecord medicalRecord3 = MedicalRecord.builder()
                .firstName("first name 3")
                .lastName("last name 3")
                .birthdate("01/01/1952")
                .medications("medications 3")
                .allergies("allergies 3")
                .build();
        MedicalRecord medicalRecord4 = MedicalRecord.builder()
                .firstName("Pierre")
                .lastName("Dupond")
                .birthdate("01/01/2015")
                .medications("medications 1")
                .allergies("allergies 1")
                .build();
        MedicalRecord medicalRecord5 = MedicalRecord.builder()
                .firstName("Paul")
                .lastName("Dupond")
                .birthdate("01/01/2010")
                .medications("medications 2")
                .allergies("allergies 2")
                .build();
        MedicalRecord medicalRecord6 = MedicalRecord.builder()
                .firstName("Marie")
                .lastName("Dupond")
                .birthdate("01/01/1952")
                .medications("medications 3")
                .allergies("allergies 3")
                .build();
        medicalRecordsMocked.add(medicalRecord1);
        medicalRecordsMocked.add(medicalRecord2);
        medicalRecordsMocked.add(medicalRecord3);
        medicalRecordsMocked.add(medicalRecord4);
        medicalRecordsMocked.add(medicalRecord5);
        medicalRecordsMocked.add(medicalRecord6);
        return medicalRecordsMocked;
    }

    public void clearPersonsMocked() {
        personsMocked.clear();
    }

    public void clearFirestationsMocked() {
        firestationsMocked.clear();
    }

    public void clearMedicalRecordsMocked() {
        medicalRecordsMocked.clear();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}