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
        medicalRecord = new MedicalRecord("firstName", null, null, null, null);

        //THEN
        assertThat(firstName).isEqualTo(medicalRecord.getFirstName());
    }

    @Test
    @DisplayName("Get lastname of a medical record")
    void testMedicalRecordGetLastName() {

        //GIVEN
        String lastName = "lastName";

        //WHEN
        medicalRecord = new MedicalRecord(null, "lastName", null, null, null);

        //THEN
        assertThat(lastName).isEqualTo(medicalRecord.getLastName());
    }

    @Test
    @DisplayName("Get birthdate of a medical record")
    void testMedicalRecordGetBirthdate() {

        //GIVEN
        String birthdate = "birthdate";

        //WHEN
        medicalRecord = new MedicalRecord(null, null, "birthdate", null, null);

        //THEN
        assertThat(birthdate).isEqualTo(medicalRecord.getBirthdate());
    }

    @Test
    @DisplayName("Get medications of a medical record")
    void testMedicalRecordGetMedications() {

        //GIVEN
        String medications = "medications";

        //WHEN
        medicalRecord = new MedicalRecord(null, null, null, "medications", null);

        //THEN
        assertThat(medications).isEqualTo(medicalRecord.getMedications());
    }

    @Test
    @DisplayName("Get allergies of a medical record")
    void testMedicalRecordGetAllergies() {

        //GIVEN
        String allergies = "allergies";

        //WHEN
        medicalRecord = new MedicalRecord(null, null, null, null, "allergies");

        //THEN
        assertThat(allergies).isEqualTo(medicalRecord.getAllergies());
    }

    @Test
    @DisplayName("Set a medical record")
    void testMedicalRecordSet() {

        //GIVEN
        medicalRecord = new MedicalRecord(null, null, null, null, null);

        //WHEN
        medicalRecord.setFirstName("firstName");
        medicalRecord.setLastName("lastName");
        medicalRecord.setBirthdate("birthdate");
        medicalRecord.setMedications("medications");
        medicalRecord.setAllergies("allergies");

        //THEN
        assertThat(medicalRecord.getFirstName()).isEqualTo("firstName");
        assertThat(medicalRecord.getLastName()).isEqualTo("lastName");
        assertThat(medicalRecord.getBirthdate()).isEqualTo("birthdate");
        assertThat(medicalRecord.getMedications()).isEqualTo("medications");
        assertThat(medicalRecord.getAllergies()).isEqualTo("allergies");
    }
}
