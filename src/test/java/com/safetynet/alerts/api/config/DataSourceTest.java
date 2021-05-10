package com.safetynet.alerts.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.api.model.Firestation;
import com.safetynet.alerts.api.model.MedicalRecord;
import com.safetynet.alerts.api.model.Person;
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
        personsMocked.add(person1);
        personsMocked.add(person2);
        personsMocked.add(person3);
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
        firestationsMocked.add(firestation1);
        firestationsMocked.add(firestation2);
        firestationsMocked.add(firestation3);
        return firestationsMocked;
    }

    public List<MedicalRecord> getAllMedicalRecordMocked() {
        MedicalRecord medicalRecord1 = MedicalRecord.builder()
                .firstName("first name 1")
                .lastName("last name 1")
                .birthdate("birthdate 1")
                .medications("medications 1")
                .allergies("allergies 1")
                .build();
        MedicalRecord medicalRecord2 = MedicalRecord.builder()
                .firstName("first name 2")
                .lastName("last name 2")
                .birthdate("birthdate 2")
                .medications("medications 2")
                .allergies("allergies 2")
                .build();
        MedicalRecord medicalRecord3 = MedicalRecord.builder()
                .firstName("first name 3")
                .lastName("last name 3")
                .birthdate("birthdate 3")
                .medications("medications 3")
                .allergies("allergies 3")
                .build();
        medicalRecordsMocked.add(medicalRecord1);
        medicalRecordsMocked.add(medicalRecord2);
        medicalRecordsMocked.add(medicalRecord3);
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