package com.safetynet.alerts.api.controller;

import com.safetynet.alerts.api.exceptions.PersonAlreadyExistException;
import com.safetynet.alerts.api.exceptions.PersonNotFoundException;
import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.service.PersonServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.safetynet.alerts.api.config.DataSourceTest.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonServiceImpl personService;

    private Person person;


    @Test
    @DisplayName("GET request (/person) must return an HTTP 200 response")
    public void testGetPersons() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/person/{firstName}/{lastName}) with an exiting person must return an HTTP 200 response")
    public void testGetPersonByFirstnameAndLastNameIsOk() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/person/first name 1/last name 1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/person/{firstName}/{lastName}) with an unknown person must return an HTTP 404 response")
    public void testGetPersonByFirstnameAndLastNameNotFound() throws Exception {

        //GIVEN
        given(personService.findPersonByFirstNameAndLastName("first name 8", "last name 8")).willThrow(new PersonNotFoundException("Get person by firstname and lastname error because person is not found"));

        //THEN
        mockMvc.perform(get("/person/first name 8/last name 8"))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("POST request (/person) with a new person must return an HTTP 200 response")
    public void testPostANewPerson() throws Exception {

        //GIVEN
        Person person = Person.builder()
                .firstName("first name")
                .lastName("last name")
                .build();
        when(personService.createNewPerson(person)).thenReturn(null);

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/person")
                .content(asJsonString(person))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST request (/person) with a person already existing in data source must return an HTTP 400 response")
    public void testPostPersonAlreadyExisting() throws Exception {

        //GIVEN
        given(personService.createNewPerson(person)).willThrow(new PersonAlreadyExistException("Create person error because person already exist"));

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
                .firstName("first name 1")
                .lastName("last name 1")
                .build();

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .put("/person")
                .content(asJsonString(person))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE request (/person/{firstName}/{lastName}) must return an HTTP 200 response")
    public void testDeletePerson() throws Exception {

        //GIVEN
        Person person = Person.builder()
                .firstName("firstname")
                .lastName("lastname")
                .build();

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/person/firstname/lastname")
                .content(asJsonString(person))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
