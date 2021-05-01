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
        Person person1 = new Person("first name 1", "last name 1", "phone 1", "zip 1", "address 1", "city 1", "email 1");
        Person person2 = new Person("first name 2", "last name 2", "phone 2", "zip 2", "address 2", "city 2", "email 2");
        Person person3 = new Person("first name 3", "last name 3", "phone 3", "zip 3", "address 3", "city 3", "email 3");
        personsMocked.add(person1);
        personsMocked.add(person2);
        personsMocked.add(person3);
        return personsMocked;
    }

    public List<Firestation> getAllFirestationMocked() {
        Firestation firestation1 = new Firestation("address 1", "number 1");
        Firestation firestation2 = new Firestation("address 2", "number 2");
        Firestation firestation3 = new Firestation("address 1", "number 3");
        firestationsMocked.add(firestation1);
        firestationsMocked.add(firestation2);
        firestationsMocked.add(firestation3);
        return firestationsMocked;
    }

    public List<MedicalRecord> getAllMedicalRecordMocked() {
        MedicalRecord medicalRecord1 = new MedicalRecord("first name 1", "last name 1", "birthdate 1", "medications 1", "allergies 1");
        MedicalRecord medicalRecord2 = new MedicalRecord("first name 2", "last name 2", "birthdate 2", "medications 2", "allergies 2");
        MedicalRecord medicalRecord3 = new MedicalRecord("first name 3", "last name 3", "birthdate 3", "medications 3", "allergies 3");
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

//        Person person1 = Person.builder()
//                .firstName("first name 1")
//                .lastName("last name 1")
//                .email("email1@localhost.com")
//                .build();
//        Person person2 = Person.builder()
//                .firstName("first name 2")
//                .lastName("last name 2")
//                .email("email2@localhost.com")
//                .build();
//        Person person3 = Person.builder()
//                .firstName("first name 3")
//                .lastName("last name 3")
//                .email("email3@localhost.com")
//                .build();
//        List<Person> personsMocked = Arrays.asList(person1, person2, person3);
//        Mockito.when(dataSource.getAllPersons()).thenReturn(personsMocked);