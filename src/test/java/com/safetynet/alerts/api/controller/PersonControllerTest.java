package com.safetynet.alerts.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.api.config.DataSource;
import com.safetynet.alerts.api.dao.PersonDao;
import com.safetynet.alerts.api.exceptions.PersonNotFoundException;
import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;
    @MockBean
    private Person person;
    @MockBean
    private PersonDao personDao;
    @MockBean
    private DataSource dataSource;


    @Test
    @DisplayName("GET request (/person) must return an HTTP 200 response")
    public void testGetPersons() throws Exception {
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/person/{firstName}/{lastName}) with an exiting person must return an HTTP 200 response")
    public void testGetPersonByFirstnameAndLastNameIsOk() throws Exception {

        mockMvc.perform(get("/person/firstName/lastName"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/person/{firstName}/{lastName}) with an unknown person must return an HTTP 404 response")
    public void testGetPersonByFirstnameAndLastNameNotFound() throws Exception {
        given(personService.findPersonByFirstNameAndLastName("f","f")).willThrow(new PersonNotFoundException("Get person by firstname and lastname error because person is not found"));
        mockMvc.perform(get("/person/firstName/"))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("POST request (/person) must return an HTTP 200 response")
    public void testPostPerson() throws Exception {

        Person person = new Person("first name","last name",null,null,null,null,null);

        personService.createNewPerson(person);

        mockMvc.perform( MockMvcRequestBuilders
                .post("/person")
                .content(asJsonString(person))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT request (/person) must return an HTTP 200 response")
    public void testPutPerson() throws Exception {

        Person person = new Person("first name","last name",null,null,null,null,null);

        personService.updatePerson(person);

        mockMvc.perform( MockMvcRequestBuilders
                .put("/person")
                .content(asJsonString(person))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    @Test
//    @DisplayName("DELETE request (/person/{firstName}/{lastName}) must return an HTTP 200 response")
//    public void testDeletePerson() throws Exception {
//
//        Person person = new Person("first name","last name",null,null,null,null,null);
//
//        personService.deletePerson(person);
//
//        mockMvc.perform( MockMvcRequestBuilders
//                .delete("/person/firstname/lastname")
//                .content(asJsonString(person))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }



//    @Test
//    @DisplayName("POST request (/person) without firstname must return an HTTP 400 response")
//    public void testPostPersonWithoutFirstName() throws Exception {

//        Person person = new Person("first name","last name",null,null,null,null,null);
//        DataSourceTest dataSourceTest = new DataSourceTest();
//        Mockito.when(dataSource.getAllPersons()).thenReturn(dataSourceTest.getAllPersonMocked());
//
//        personService.createNewPerson(person);
//
//        mockMvc.perform( MockMvcRequestBuilders
//                .post("/person")
//                .content(asJsonString(person))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
