package com.safetynet.alerts.api.controller;

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

import static com.safetynet.alerts.api.controller.PersonControllerTest.asJsonString;
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
    @MockBean
    private Firestation firestation;


    @Test
    @DisplayName("GET request (/firestation) must return an HTTP 200 response")
    public void testGetFirestations() throws Exception {
        mockMvc.perform(get("/firestation"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/firestation/{address}) with an exiting firestation must return an HTTP 200 response")
    public void testGetFirestationByAddressIsOk() throws Exception {

        mockMvc.perform(get("/firestation/address"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/firestation/{address}) with an unknown firestation address must return an HTTP 404 response")
    public void testGetFirestationByAddressNotFound() throws Exception {
        when(firestationService.findFirestationByAddress("address")).thenThrow(FirestationNotFoundException.class);
        given(firestationService.findFirestationByAddress(firestation.getAddress())).willThrow(new FirestationNotFoundException("Get firestation by address error because address is not found"));
        mockMvc.perform(get("/firestation/address"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST request (/firestation) must return an HTTP 200 response")
    public void testPostFirestation() throws Exception {

        Firestation firestation = new Firestation("address","number");

        firestationService.createNewFirestation(firestation);

        mockMvc.perform( MockMvcRequestBuilders
                .post("/firestation")
                .content(asJsonString(firestation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT request (/firestation) must return an HTTP 200 response")
    public void testPutFirestation() throws Exception {

        Firestation firestation = new Firestation("address","number");

        firestationService.updateFirestation(firestation);

        mockMvc.perform( MockMvcRequestBuilders
                .put("/firestation")
                .content(asJsonString(firestation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    @Test
//    @DisplayName("DELETE request (/firestation) by address must return an HTTP 200 response")
//    public void testDeleteFirestationByAddress() throws Exception {
//
//        Firestation firestation = new Firestation("address","number");
//
//        firestationService.deleteFirestationByAddress(firestation);
//
//        mockMvc.perform( MockMvcRequestBuilders
//                .delete("/firestation/address/address")
//                .content(asJsonString(firestation))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }


}
