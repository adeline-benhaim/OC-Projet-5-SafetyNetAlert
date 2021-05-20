package com.safetynet.alerts.api.controller;

import com.safetynet.alerts.api.exceptions.FirestationNotFoundException;
import com.safetynet.alerts.api.exceptions.PersonNotFoundException;
import com.safetynet.alerts.api.service.InformationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InformationController.class)
public class InformationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InformationServiceImpl informationService;


    @Test
    @DisplayName("GET request (firestation?stationNumber=<station_number>) with an exiting station number must return an HTTP 200 response")
    public void testGetPersonInfoByStationNumber() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/firestation?stationNumber=1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (firestation?stationNumber=<station_number>) with an unknown station number must return an HTTP 404 response")
    public void testGetPersonInfoByUnknownStationNumber() throws Exception {

        //GIVEN
        given(informationService.findPersonsInfoByStationNumber("6")).willThrow(new FirestationNotFoundException("REST : Get a list of persons and a count of adults and children covered by firestation found by a station number error because station number is not found"));

        //THEN
        mockMvc.perform(get("/firestation?stationNumber=6"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET request (childAlert?address=<address>) with an exiting address must return an HTTP 200 response")
    public void testGetChildAlertByAddress() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/childAlert?address=address"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (childAlert?address=<address>) with an unknown address must return an HTTP 404 response")
    public void testGetChildAlertByUnknownAddress() throws Exception {

        //GIVEN
        given(informationService.findChildrenByAddress("unknown")).willThrow(new PersonNotFoundException("REST : Get a list of children and a list of others household members found by address error because address is not found"));

        //THEN
        mockMvc.perform(get("/childAlert?address=unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET request (phoneAlert?firestation=<firestationNumber>) with an exiting firestation number must return an HTTP 200 response")
    public void testGetPhoneAlertByFirestationNumber() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/phoneAlert?firestation=1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (phoneAlert?firestation=<firestationNumber>) with an unknown firestation number must return an HTTP 404 response")
    public void testGetPhoneAlertByUnknownFirestationNumber() throws Exception {

        //GIVEN
        given(informationService.findListOfPhoneNumbersByFirestationNumber("10")).willThrow(new FirestationNotFoundException("REST : Get a list of phone numbers covered by the firestation number error because firestation number is not found"));

        //THEN
        mockMvc.perform(get("/phoneAlert?firestation=10"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET request (fire?address=<address>) with an exiting address must return an HTTP 200 response")
    public void testGetListOfFirePersonByAddress() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/fire?address=address"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (fire?address=<address>) with an unknown address must return an HTTP 404 response")
    public void testGetListOfFirePersonByUnknownAddress() throws Exception {

        //GIVEN
        given(informationService.findListOfFirePersonByAddress("unknown")).willThrow(new PersonNotFoundException("REST : Get a list of fire person covered find by address error because address is not found"));

        //THEN
        mockMvc.perform(get("/fire?address=unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET request (flood/stations?stations=<station_numbers>) with an exiting station number must return an HTTP 200 response")
    public void findListOfFloodPersonByStationNumberTest() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/flood/stations?stations=1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (flood/stations?stations=<station_numbers>) with an unknown station number must return an HTTP 404 response")
    public void findListOfFloodPersonByUnknownStationNumberTest() throws Exception {

        //GIVEN
        given(informationService.findListOfFloodPersonByStationNumber("unknown")).willThrow(new FirestationNotFoundException("REST : Get a list of flood person covered find by address error because station number is not found"));

        //THEN
        mockMvc.perform(get("/flood/stations?stations=unknown"))
                .andExpect(status().isNotFound());
    }

}
