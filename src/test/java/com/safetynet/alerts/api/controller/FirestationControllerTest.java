package com.safetynet.alerts.api.controller;

import com.safetynet.alerts.api.exceptions.FirestationAlreadyExistException;
import com.safetynet.alerts.api.exceptions.FirestationNotFoundException;
import com.safetynet.alerts.api.model.Firestation;
import com.safetynet.alerts.api.service.FirestationService;
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

@WebMvcTest(controllers = FirestationController.class)
public class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirestationService firestationService;

    private Firestation firestation;


    @Test
    @DisplayName("GET request (/firestations) must return an HTTP 200 response")
    public void testGetFirestations() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/firestation/{address}) with an exiting firestation must return an HTTP 200 response")
    public void testGetFirestationByAddressIsOk() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/firestation/address 1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/firestation/{address}) with an unknown firestation address must return an HTTP 404 response")
    public void testGetFirestationByAddressNotFound() throws Exception {

        //GIVEN
        given(firestationService.findFirestationByAddress("address 8")).willThrow(new FirestationNotFoundException("Get firestation by address error because address is not found"));

        //THEN
        mockMvc.perform(get("/firestation/address 8"))
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
        when(firestationService.createNewFirestation(firestation)).thenReturn(null);

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/firestation")
                .content(asJsonString(firestation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("POST request (/firestation) with a firestation already existing in data source must return an HTTP 400 response")
    public void testPostFirestationAlreadyExisting() throws Exception {

        //GIVEN
        given(firestationService.createNewFirestation(firestation)).willThrow(new FirestationAlreadyExistException("Create firestation error because person already exist"));

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
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
                .address("address")
                .stationNumber("number")
                .build();

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .put("/firestation")
                .content(asJsonString(firestation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    @Test
//    @DisplayName("PUT request (/firestation) with a unknown firestation must return an HTTP 404 response")
//    public void testPutUnknownFirestation() throws Exception {
//
//        //GIVEN
//        given(firestationService.updateFirestation(firestation)).willThrow(new FirestationNotFoundException("Trying to update non existing firestation"));
//
//        //THEN
//        mockMvc.perform( MockMvcRequestBuilders
//                .put("/firestation")
//                .content(asJsonString(firestation))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }

    @Test
    @DisplayName("DELETE request (/firestation) by address must return an HTTP 200 response")
    public void testDeleteFirestationByAddress() throws Exception {

        //GIVEN
        Firestation firestation = Firestation.builder()
                .address("address")
                .stationNumber("number")
                .build();

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/firestation/address")
                .content(asJsonString(firestation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
