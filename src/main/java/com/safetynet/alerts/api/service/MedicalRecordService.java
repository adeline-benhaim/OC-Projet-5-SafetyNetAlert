package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {

    /**
     * Get all medical records with all person's information (firstname, lastname, birthdate, medications, allergies)
     *
     * @return a list with all medical records
     */
    List<MedicalRecord> getAllMedicalRecords();

    /**
     * Find a medical record  by firstname and lastname of the person concerned
     *
     * @param firstName of the person concerned
     * @param lastName  of the person concerned
     * @return the medical record of the person concerned with all information (birthdate, medications, allergies)
     */
    MedicalRecord findMedicalRecordByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Update the information of a medical record existing
     *
     * @param medicalRecord to update
     * @return medical record with information's updated
     */
    MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

    /**
     * Create a new medical record for a new person
     *
     * @param medicalRecord information for of the person concerned
     * @return information of the medical record created (firstname, lastname, birthdate, medications, allergies)
     */
    MedicalRecord createNewMedicalRecord(MedicalRecord medicalRecord);

    /**
     * Delete a medical record for a person found by firstname and lastname
     *
     * @param firstName of medicalRecord to delete
     * @param lastName  of medicalRecord to delete
     */
    void deleteMedicalRecord(String firstName, String lastName);
}
