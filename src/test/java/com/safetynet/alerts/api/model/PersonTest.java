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
        person = new Person("firstName", null, null, null, null, null, null);

        //THEN
        assertThat(firstName).isEqualTo(person.getFirstName());
    }

    @Test
    @DisplayName("Get lastname of person")
    void testPersonGetLastName() {

        //GIVEN
        String lastName = "lastName";

        //WHEN
        person = new Person(null, "lastName", null, null, null, null, null);

        //THEN
        assertThat(lastName).isEqualTo(person.getLastName());
    }

    @Test
    @DisplayName("Get address of person")
    void testPersonGetAddress() {

        //GIVEN
        String address = "address";

        //WHEN
        person = new Person(null, null, null, null, "address", null, null);

        //THEN
        assertThat(address).isEqualTo(person.getAddress());
    }

    @Test
    @DisplayName("Get city of person")
    void testPersonGetCity() {

        //GIVEN
        String city = "city";

        //WHEN
        person = new Person(null, null, null, null, null, "city", null);

        //THEN
        assertThat(city).isEqualTo(person.getCity());
    }

    @Test
    @DisplayName("Get zip of person")
    void testPersonGetZip() {

        //GIVEN
        String zip = "zip";

        //WHEN
        person = new Person(null, null, null, "zip", null, null, null);

        //THEN
        assertThat(zip).isEqualTo(person.getZip());
    }

    @Test
    @DisplayName("Get phone  of person")
    void testPersonGetPhone() {

        //GIVEN
        String phone = "phone";

        //WHEN
        person = new Person(null, null, "phone", null, null, null, null);

        //THEN
        assertThat(phone).isEqualTo(person.getPhone());
    }

    @Test
    @DisplayName("Get email  of person")
    void testPersonGetEmail() {

        //GIVEN
        String email = "email";

        //WHEN
        person = new Person(null, null, null, null, null, null, "email");

        //THEN
        assertThat(email).isEqualTo(person.getEmail());
    }

    @Test
    @DisplayName("Set a person")
    void testPersonSetAPerson() {

        //GIVEN
        person = new Person(null, null, null, null, null, null, null);

        //WHEN
        person.setFirstName("firstName");
        person.setLastName("lastName");
        person.setPhone("phone");
        person.setZip("zip");
        person.setAddress("address");
        person.setCity("city");
        person.setEmail("email");

        //THEN
        assertThat(person.getFirstName()).isEqualTo("firstName");
        assertThat(person.getLastName()).isEqualTo("lastName");
        assertThat(person.getPhone()).isEqualTo("phone");
        assertThat(person.getZip()).isEqualTo("zip");
        assertThat(person.getAddress()).isEqualTo("address");
        assertThat(person.getCity()).isEqualTo("city");
        assertThat(person.getEmail()).isEqualTo("email");
    }
}
