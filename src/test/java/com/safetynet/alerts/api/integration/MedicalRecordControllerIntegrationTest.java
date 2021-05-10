package com.safetynet.alerts.api.integration;

import com.safetynet.alerts.api.config.DataSource;
import com.safetynet.alerts.api.model.MedicalRecord;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    private void setUpPerTest() throws IOException {
        DataSource dataSource = new DataSource();
        dataSource.medicalRecords.clear();
        dataSource.init();
    }

    @Test
    @DisplayName("GET request (/medicalRecord) must return an HTTP 200 response")
    public void testGetMedicalRecords() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("John")));
    }

    @Test
    @DisplayName("GET request (/medicalRecord/{firstname}/{lastname}) with an exiting medical record must return an HTTP 200 response")
    public void testGetMedicalRecordByFirstAndLastNameIsOk() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/medicalRecord/Jacob/Boyd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("birthdate", is("03/06/1989")));
    }

    @Test
    @DisplayName("GET request (/medicalRecord/{firstname}/{lastname}) with an unknown medical record must return an HTTP 404 response")
    public void testGetMedicalRecordNotFound() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/medicalRecord/unknownFirstName/unknownLastName"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST request (/medicalRecord) must return an HTTP 200 response")
    public void testPostMedicalRecord() throws Exception {

        //GIVEN
        MedicalRecord medicalRecord = MedicalRecord.builder()
                .firstName("first name")
                .lastName("last name")
                .birthdate("birthdate")
                .build();

        //THEN
        mockMvc.perform( MockMvcRequestBuilders
                .post("/medicalRecord")
                .content(asJsonString(medicalRecord))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("birthdate", is("birthdate")));
    }

    @Test
    @DisplayName("POST request (/medicalRecord) with a medical record already existing in data source must return an HTTP 400 response")
    public void testPostMedicalRecordAlreadyExisting() throws Exception {

        //GIVEN
        MedicalRecord medicalRecord = MedicalRecord.builder()
                .firstName("John")
                .lastName("Boyd")
                .build();


        //THEN
        mockMvc.perform( MockMvcRequestBuilders
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
                .firstName("John")
                .lastName("Boyd")
                .birthdate("newBirthdate")
                .build();

        //THEN
        mockMvc.perform( MockMvcRequestBuilders
                .put("/medicalRecord")
                .content(asJsonString(medicalRecord))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("birthdate", is("newBirthdate")));
    }

    @Test
    @DisplayName("PUT request (/medicalRecord) with unknown medical record must return an HTTP 400 response")
    public void testPutUnknownMedicalRecord() throws Exception {

        //GIVEN
        MedicalRecord medicalRecord = MedicalRecord.builder()
                .firstName("Unknown name")
                .lastName("Boyd")
                .birthdate("newBirthdate")
                .build();

        //THEN
        mockMvc.perform( MockMvcRequestBuilders
                .put("/medicalRecord")
                .content(asJsonString(medicalRecord))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE request (/medicalRecord) must return an HTTP 200 response")
    public void testDeleteMedicalRecord() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform( MockMvcRequestBuilders
                .delete("/medicalRecord/John/Boyd")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
