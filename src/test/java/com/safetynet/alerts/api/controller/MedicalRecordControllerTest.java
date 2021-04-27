package com.safetynet.alerts.api.controller;

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

import static com.safetynet.alerts.api.controller.PersonControllerTest.asJsonString;
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

    @MockBean
    private MedicalRecord medicalRecord;


    @Test
    @DisplayName("GET request (/medicalRecord) must return an HTTP 200 response")
    public void testGetMedicalRecords() throws Exception {
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/medicalRecord/{firstname}/{lastname}) with an exiting medical record must return an HTTP 200 response")
    public void testGetMedicalRecordByFirstAndLastNameIsOk() throws Exception {

        mockMvc.perform(get("/medicalRecord/firstname/lastname"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/medicalRecord/{firstname}/{lastname}) with an unknown medical record must return an HTTP 404 response")
    public void testGetMedicalRecordNotFound() throws Exception {
        when(medicalRecordService.findMedicalRecordByFirstNameAndLastName("firstname","lastname")).thenThrow(MedicalRecordNotFoundException.class);
        given(medicalRecordService.findMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(),medicalRecord.getLastName())).willThrow(new MedicalRecordNotFoundException("Get Get medical record error because this person is not found"));
        mockMvc.perform(get("/medicalRecord/firstname/lastname"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST request (/medicalRecord) must return an HTTP 200 response")
    public void testPostMedicalRecord() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord("firstname","lastname",null,null,null);

        medicalRecordService.createNewMedicalRecord(medicalRecord);

        mockMvc.perform( MockMvcRequestBuilders
                .post("/medicalRecord")
                .content(asJsonString(medicalRecord))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT request (/medicalRecord) must return an HTTP 200 response")
    public void testPutMedicalRecord() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord("firstname","lastname",null,null,null);

        medicalRecordService.updateMedicalRecord(medicalRecord);

        mockMvc.perform( MockMvcRequestBuilders
                .put("/medicalRecord")
                .content(asJsonString(medicalRecord))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    @Test
//    @DisplayName("DELETE request (/medicalRecord) must return an HTTP 200 response")
//    public void testDeleteMedicalRecord() throws Exception {
//
//        MedicalRecord medicalRecord = new MedicalRecord("firstname","lastname",null,null,null);
//
//        medicalRecordService.deleteMedicalRecord(medicalRecord);
//
//        mockMvc.perform( MockMvcRequestBuilders
//                .delete("/medicalRecord/firstname/lastname")
//                .content(asJsonString(medicalRecord))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
}
