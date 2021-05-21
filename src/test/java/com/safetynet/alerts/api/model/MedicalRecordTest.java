package com.safetynet.alerts.api.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MedicalRecordTest {

    private MedicalRecord medicalRecord;

    @Test
    @DisplayName("Get firstname of a medical record")
    void testMedicalRecordGetFirstName() {

        //GIVEN
        String firstName = "firstName";

        //WHEN
        medicalRecord = MedicalRecord.builder()
                .firstName("firstName")
                .build();

        //THEN
        assertThat(firstName).isEqualTo(medicalRecord.getFirstName());
    }

    @Test
    @DisplayName("Get lastname of a medical record")
    void testMedicalRecordGetLastName() {

        //GIVEN
        String lastName = "lastName";

        //WHEN
        medicalRecord = MedicalRecord.builder()
                .lastName("lastName")
                .build();

        //THEN
        assertThat(lastName).isEqualTo(medicalRecord.getLastName());
    }

    @Test
    @DisplayName("Get birthdate of a medical record")
    void testMedicalRecordGetBirthdate() {

        //GIVEN
        String birthdate = "birthdate";

        //WHEN
        medicalRecord = MedicalRecord.builder()
                .birthdate("birthdate")
                .build();

        //THEN
        assertThat(birthdate).isEqualTo(medicalRecord.getBirthdate());
    }

    @Test
    @DisplayName("Get medications of a medical record")
    void testMedicalRecordGetMedications() {

        //GIVEN
        String medications = "medications";

        //WHEN
        medicalRecord = MedicalRecord.builder()
                .medications("medications")
                .build();

        //THEN
        assertThat(medications).isEqualTo(medicalRecord.getMedications());
    }

    @Test
    @DisplayName("Get allergies of a medical record")
    void testMedicalRecordGetAllergies() {

        //GIVEN
        String allergies = "allergies";

        //WHEN
        medicalRecord = MedicalRecord.builder()
                .allergies("allergies")
                .build();

        //THEN
        assertThat(allergies).isEqualTo(medicalRecord.getAllergies());
    }

    @Test
    @DisplayName("Get uniqueID of a medical record")
    void testMedicalRecordGetUniqueID() {

        //GIVEN
        String uniqueID = "uniqueID";

        //WHEN
        medicalRecord = MedicalRecord.builder()
                .uniqueID("uniqueID")
                .build();

        //THEN
        assertThat(uniqueID).isEqualTo(medicalRecord.getUniqueID());
    }

    @Test
    @DisplayName("Set a medical record")
    void testMedicalRecordSet() {

        //GIVEN
        medicalRecord = MedicalRecord.builder()
                .firstName(null)
                .lastName(null)
                .birthdate(null)
                .medications(null)
                .allergies(null)
                .build();

        //WHEN
        medicalRecord.setFirstName("firstName");
        medicalRecord.setLastName("lastName");
        medicalRecord.setBirthdate("birthdate");
        medicalRecord.setMedications("medications");
        medicalRecord.setAllergies("allergies");
        medicalRecord.setUniqueID("uniqueID");

        //THEN
        assertThat(medicalRecord.getFirstName()).isEqualTo("firstName");
        assertThat(medicalRecord.getLastName()).isEqualTo("lastName");
        assertThat(medicalRecord.getBirthdate()).isEqualTo("birthdate");
        assertThat(medicalRecord.getMedications()).isEqualTo("medications");
        assertThat(medicalRecord.getAllergies()).isEqualTo("allergies");
        assertThat(medicalRecord.getUniqueID()).isEqualTo("uniqueID");
    }
}
