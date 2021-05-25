package com.safetynet.alerts.api.integration;

import com.safetynet.alerts.api.config.DataSource;
import com.safetynet.alerts.api.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.safetynet.alerts.api.config.DataSourceTest.asJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    private void setUpPerTest() throws Exception {
        DataSource dataSource = new DataSource();
        dataSource.persons.clear();
        dataSource.init();
    }

    @Test
    @DisplayName("GET request (/person) must return an HTTP 200 response")
    public void testGetPersons() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("John")));
    }

    @Test
    @DisplayName("GET request (/person/{firstName}/{lastName}) with an exiting person must return an HTTP 200 response")
    public void testGetPersonByFirstnameAndLastNameIsOk() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/person/Jacob/Boyd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("address", is("1509 Culver St")));
    }

    @Test
    @DisplayName("GET request (/person/{firstName}/{lastName}) with an unknown person must return an HTTP 404 response")
    public void testGetPersonByFirstnameAndLastNameNotFound() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/person/unknownFirstName/unknownLastName"))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("POST request (/person) with a new person must return an HTTP 200 response")
    public void testPostANewPerson() throws Exception {

        //GIVEN
        Person person = Person.builder()
                .firstName("first name")
                .lastName("last name")
                .address("1509 Culver St")
                .build();

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/person")
                .content(asJsonString(person))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("address", is("1509 Culver St")));
    }

    @Test
    @DisplayName("POST request (/person) with a person already existing in data source must return an HTTP 400 response")
    public void testPostPersonAlreadyExisting() throws Exception {

        //GIVEN
        Person person = Person.builder()
                .firstName("Tenley")
                .lastName("Boyd")
                .build();

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/person")
                .content(asJsonString(person))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT request (/person) with a existing person must return an HTTP 200 response")
    public void testPutExistingPerson() throws Exception {

        //GIVEN
        Person person = Person.builder()
                .firstName("Tenley")
                .lastName("Boyd")
                .phone("new phone")
                .zip("new zip")
                .address("new address")
                .city("new city")
                .email("new email")
                .build();

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .put("/person")
                .content(asJsonString(person))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("address", is("new address")));
    }

    @Test
    @DisplayName("PUT request (/person) with a unknown person must return an HTTP 404 response")
    public void testPutAUnknownPerson() throws Exception {

        //GIVEN
        Person person = Person.builder()
                .firstName("Unknown person")
                .lastName("Boyd")
                .phone("new phone")
                .zip("new zip")
                .address("new address")
                .city("new city")
                .email("new email")
                .build();


        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .put("/person")
                .content(asJsonString(person))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE request (/person/{firstName}/{lastName}) must return an HTTP 200 response")
    public void testDeletePerson() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/person/John/Boyd")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
