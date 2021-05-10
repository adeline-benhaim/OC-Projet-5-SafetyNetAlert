package com.safetynet.alerts.api.integration;

import com.safetynet.alerts.api.config.DataSource;
import com.safetynet.alerts.api.exceptions.FirestationNotFoundException;
import com.safetynet.alerts.api.model.Firestation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static com.safetynet.alerts.api.config.DataSourceTest.asJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUpPerTest() throws IOException {
        DataSource dataSource = new DataSource();
        dataSource.firestations.clear();
        dataSource.init();
    }

    @AfterEach
    public void tearDown(){

    }

    @Test
    @DisplayName("GET request (/firestations) must return an HTTP 200 response")
    public void testGetFirestations() throws Exception {

        //GIVEN

        //WHEN
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address", is("112 Steppes Pl")));
    }

    @Test
    @DisplayName("GET request (/firestation/{address}) with an exiting firestation must return an HTTP 200 response")
    public void testGetFirestationByAddressIsOk() throws Exception {

        //GIVEN

        //WHEN
        mockMvc.perform(get("/firestation/1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("stationNumber", is("3")));
    }

    @Test
    @DisplayName("GET request (/firestation/{address}) with an unknown firestation address must return an HTTP 404 response")
    public void testGetFirestationByAddressNotFound() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/firestation/unknown address"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST request (/firestation) must return an HTTP 200 response")
    public void testPostFirestation() throws Exception {

        //GIVEN
        Firestation firestation = Firestation.builder()
                .address("address")
                .stationNumber("number")
                .build();

        //THEN
        mockMvc.perform( MockMvcRequestBuilders
                .post("/firestation")
                .content(asJsonString(firestation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("stationNumber", is("number")));
    }


    @Test
    @DisplayName("POST request (/firestation) with a firestation already existing in data source must return an HTTP 400 response")
    public void testPostFirestationAlreadyExisting() throws Exception {

        //GIVEN
        Firestation firestation = Firestation.builder()
                .address("1509 Culver St")
                .stationNumber("number")
                .build();

        //THEN
        mockMvc.perform( MockMvcRequestBuilders
                .post("/firestation")
                .content(asJsonString(firestation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT request (/firestation) must return an HTTP 200 response")
    public void testPutFirestation() throws Exception {

        //GIVEN
        Firestation firestation = Firestation.builder()
                .address("29 15th St")
                .stationNumber("newNumber")
                .build();

        //THEN
        mockMvc.perform( MockMvcRequestBuilders
                .put("/firestation")
                .content(asJsonString(firestation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("stationNumber", is("newNumber")));
    }

    @Test
    @DisplayName("PUT request (/firestation) with a unknown firestation must return an HTTP 404 response")
    public void testPutUnknownFirestation() throws Exception {

        //GIVEN
        Firestation firestation = Firestation.builder()
                .address("newAddress")
                .stationNumber("newNumber")
                .build();
        //THEN
        mockMvc.perform( MockMvcRequestBuilders
                .put("/firestation")
                .content(asJsonString(firestation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE request (/firestation) by address must return an HTTP 200 response")
    public void testDeleteFirestationByAddress() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform( MockMvcRequestBuilders
                .delete("/firestation/834 Binoc Ave")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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
                .andExpect(jsonPath("countPersonAdultChildDto.numberOfAdults", is(5)))
                .andExpect(jsonPath("countPersonAdultChildDto.numberOfChildren", is(1)));
    }

    @Test
    @DisplayName("GET request (firestation?stationNumber=<station_number) with an unknown station number must return an HTTP 404 response")
    public void testGetPersonInfoByUnknownStationNumber() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/firestation?stationNumber=6"))
                .andExpect(status().isNotFound());
    }

}
