package com.safetynet.alerts.api.controller;

import com.safetynet.alerts.api.exceptions.MedicalRecordAlreadyExistException;
import com.safetynet.alerts.api.exceptions.MedicalRecordNotFoundException;
import com.safetynet.alerts.api.model.MedicalRecord;
import com.safetynet.alerts.api.service.MedicalRecordService;
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

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    private MedicalRecord medicalRecord;


    @Test
    @DisplayName("GET request (/medicalRecord) must return an HTTP 200 response")
    public void testGetMedicalRecords() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/medicalRecord/{firstname}/{lastname}) with an exiting medical record must return an HTTP 200 response")
    public void testGetMedicalRecordByFirstAndLastNameIsOk() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/medicalRecord/firstname/lastname"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/medicalRecord/{firstname}/{lastname}) with an unknown medical record must return an HTTP 404 response")
    public void testGetMedicalRecordNotFound() throws Exception {

        //GIVEN
        given(medicalRecordService.findMedicalRecordByFirstNameAndLastName("firstname 8", "lastname 8")).willThrow(new MedicalRecordNotFoundException("Get medical record error because this person is not found"));

        //THEN
        mockMvc.perform(get("/medicalRecord/firstname 8/lastname 8"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST request (/medicalRecord) must return an HTTP 200 response")
    public void testPostMedicalRecord() throws Exception {

        //GIVEN
        MedicalRecord medicalRecord = MedicalRecord.builder()
                .firstName("first name")
                .lastName("last name")
                .build();
        when(medicalRecordService.createNewMedicalRecord(medicalRecord)).thenReturn(null);

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/medicalRecord")
                .content(asJsonString(medicalRecord))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST request (/medicalRecord) with a medical record already existing in data source must return an HTTP 400 response")
    public void testPostMedicalRecordAlreadyExisting() throws Exception {

        //GIVEN
        given(medicalRecordService.createNewMedicalRecord(medicalRecord)).willThrow(new MedicalRecordAlreadyExistException("Create medical record error because medical record already exist"));

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/medicalRecord")
                .content(asJsonString(medicalRecord))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT request (/medicalRecord) must return an HTTP 200 response")
    public void testPutMedicalRecord() throws Exception {

        //GIVEN
        MedicalRecord medicalRecord = MedicalRecord.builder()
                .firstName("first name")
                .lastName("last name")
                .build();

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .put("/medicalRecord")
                .content(asJsonString(medicalRecord))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE request (/medicalRecord) must return an HTTP 200 response")
    public void testDeleteMedicalRecord() throws Exception {

        //GIVEN
        MedicalRecord medicalRecord = MedicalRecord.builder()
                .firstName("first name")
                .lastName("last name")
                .build();

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/medicalRecord/firstname/lastname")
                .content(asJsonString(medicalRecord))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
