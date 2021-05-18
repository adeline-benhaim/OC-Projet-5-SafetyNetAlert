package com.safetynet.alerts.api.integration;

import com.safetynet.alerts.api.config.DataSource;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.sql.ResultSet;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InformationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    private void setUpPerTest() throws Exception {
        DataSource dataSource = new DataSource();
        dataSource.persons.clear();
        dataSource.init();
    }

    @Test
    @DisplayName("GET request (firestation?stationNumber=<station_number) with an exiting station number must return an HTTP 200 response")
    public void testGetPersonInfoByStationNumber() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/firestation?stationNumber=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personDto[0].firstName", is("Peter")))
                .andExpect(jsonPath("$.personDto[0].lastName", is("Duncan")))
                .andExpect(jsonPath("$.personDto[0].address", is("644 Gershwin Cir 97451 Culver")))
                .andExpect(jsonPath("$.personDto[0].phone", is("841-874-6512")))
                .andExpect(jsonPath("$.countPersonAdultChildDto", Matchers.hasEntry("Number of children",1)))
                .andExpect(jsonPath("$.countPersonAdultChildDto", Matchers.hasEntry("Number of adults",5)));
    }

    @Test
    @DisplayName("GET request (firestation?stationNumber=<station_number) with an unknown station number must return an HTTP 404 response")
    public void testGetPersonInfoByUnknownStationNumber() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/firestation?stationNumber=6"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET request (childAlert?address=<address>) with an exiting address must return an HTTP 200 response")
    public void testGetChildAlertByAddress() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/childAlert?address=1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Tenley")))
                .andExpect(jsonPath("$[0].lastName", is("Boyd")))
                .andExpect(jsonPath("$[0].age", is(9)))
                .andExpect(jsonPath("$[0].houseHoldMembers[0].lastName", is("Boyd")))
                .andExpect(jsonPath("$[0].houseHoldMembers[0].address", is("1509 Culver St")));
    }

    @Test
    @DisplayName("GET request (childAlert?address=<address>) with an unknown address must return an HTTP 404 response")
    public void testGetChildAlertByUnknownAddress() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/childAlert?address=unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET request (phoneAlert?firestation=<firestationNumber) with an exiting firestation number must return an HTTP 200 response")
    public void testGetPhoneAlertByFirestationNumber() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/phoneAlert?firestation=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is("841-874-6512")));

    }

    @Test
    @DisplayName("GET request (phoneAlert?firestation=<firestationNumber) with an unknown firestation number must return an HTTP 404 response")
    public void testGetPhoneAlertByUnknownFirestationNumber() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/phoneAlert?firestation=10"))
                .andExpect(status().isNotFound());
    }

}
