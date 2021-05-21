package com.safetynet.alerts.api.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PersonTest {

    private Person person;

    @Test
    @DisplayName("Get firstname of person")
    void testPersonGetFirstName() {

        //GIVEN
        String firstName = "firstName";

        //WHEN
        person = Person.builder()
                .firstName("firstName")
                .build();


        //THEN
        assertThat(firstName).isEqualTo(person.getFirstName());
    }

    @Test
    @DisplayName("Get lastname of person")
    void testPersonGetLastName() {

        //GIVEN
        String lastName = "lastName";

        //WHEN
        person = Person.builder()
                .lastName("lastName")
                .build();

        //THEN
        assertThat(lastName).isEqualTo(person.getLastName());
    }

    @Test
    @DisplayName("Get address of person")
    void testPersonGetAddress() {

        //GIVEN
        String address = "address";

        //WHEN
        person = Person.builder()
                .address("address")
                .build();

        //THEN
        assertThat(address).isEqualTo(person.getAddress());
    }

    @Test
    @DisplayName("Get city of person")
    void testPersonGetCity() {

        //GIVEN
        String city = "city";

        //WHEN
        person = Person.builder()
                .city("city")
                .build();

        //THEN
        assertThat(city).isEqualTo(person.getCity());
    }

    @Test
    @DisplayName("Get zip of person")
    void testPersonGetZip() {

        //GIVEN
        String zip = "zip";

        //WHEN
        person = Person.builder()
                .zip("zip")
                .build();

        //THEN
        assertThat(zip).isEqualTo(person.getZip());
    }

    @Test
    @DisplayName("Get phone  of person")
    void testPersonGetPhone() {

        //GIVEN
        String phone = "phone";

        //WHEN
        person = Person.builder()
                .phone("phone")
                .build();

        //THEN
        assertThat(phone).isEqualTo(person.getPhone());
    }

    @Test
    @DisplayName("Get email  of person")
    void testPersonGetEmail() {

        //GIVEN
        String email = "email";

        //WHEN
        person = Person.builder()
                .email("email")
                .build();

        //THEN
        assertThat(email).isEqualTo(person.getEmail());
    }

    @Test
    @DisplayName("Get birthdate of person")
    void testPersonGetBirthdate() {

        //GIVEN
        String birthdate = "birthdate";

        //WHEN
        person = Person.builder()
                .birthdate("birthdate")
                .build();

        //THEN
        assertThat(birthdate).isEqualTo(person.getBirthdate());
    }

    @Test
    @DisplayName("Get age of person")
    void testPersonGetAge() {

        //GIVEN
        int age = 2;

        //WHEN
        person = Person.builder()
                .age(2)
                .build();

        //THEN
        assertThat(age).isEqualTo(person.getAge());
    }

    @Test
    @DisplayName("Get medications of person")
    void testPersonGetMedications() {

        //GIVEN
        String medications = "medications";

        //WHEN
        person = Person.builder()
                .medications("medications")
                .build();

        //THEN
        assertThat(medications).isEqualTo(person.getMedications());
    }

    @Test
    @DisplayName("Get allergies of person")
    void testPersonGetAllergies() {

        //GIVEN
        String allergies = "allergies";

        //WHEN
        person = Person.builder()
                .allergies("allergies")
                .build();

        //THEN
        assertThat(allergies).isEqualTo(person.getAllergies());
    }

    @Test
    @DisplayName("Get UniqueID of person")
    void testPersonGetUniqueID() {

        //GIVEN
        String UniqueID = "UniqueID";

        //WHEN
        person = Person.builder()
                .uniqueID("UniqueID")
                .build();

        //THEN
        assertThat(UniqueID).isEqualTo(person.getUniqueID());
    }

    @Test
    @DisplayName("Set a person")
    void testPersonSetAPerson() {

        //GIVEN
        person = Person.builder()
                .build();

        //WHEN
        person.setFirstName("firstName");
        person.setLastName("lastName");
        person.setPhone("phone");
        person.setZip("zip");
        person.setAddress("address");
        person.setCity("city");
        person.setEmail("email");
        person.setBirthdate("birthdate");
        person.setAge(0);
        person.setMedications("medications");
        person.setAllergies("allergies");
        person.setUniqueID("UniqueID");

        //THEN
        assertThat(person.getFirstName()).isEqualTo("firstName");
        assertThat(person.getLastName()).isEqualTo("lastName");
        assertThat(person.getPhone()).isEqualTo("phone");
        assertThat(person.getZip()).isEqualTo("zip");
        assertThat(person.getAddress()).isEqualTo("address");
        assertThat(person.getCity()).isEqualTo("city");
        assertThat(person.getEmail()).isEqualTo("email");
        assertThat(person.getBirthdate()).isEqualTo("birthdate");
        assertThat(person.getAge()).isEqualTo(0);
        assertThat(person.getMedications()).isEqualTo("medications");
        assertThat(person.getAllergies()).isEqualTo("allergies");
        assertThat(person.getUniqueID()).isEqualTo("UniqueID");
    }
}
