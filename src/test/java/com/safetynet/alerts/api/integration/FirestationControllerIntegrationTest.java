package com.safetynet.alerts.api.integration;

import com.safetynet.alerts.api.controller.FirestationController;
import com.safetynet.alerts.api.exceptions.FirestationNotFoundException;
import com.safetynet.alerts.api.model.Firestation;
import com.safetynet.alerts.api.service.FirestationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.safetynet.alerts.api.controller.PersonControllerTest.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = FirestationController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


//    @MockBean
//    private FirestationService firestationService;
//    @MockBean
//    private Firestation firestation;


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

}
