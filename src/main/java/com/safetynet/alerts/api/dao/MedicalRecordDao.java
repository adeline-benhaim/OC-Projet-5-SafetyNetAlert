package com.safetynet.alerts.api.dao;

import com.safetynet.alerts.api.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordDao {

    /**
     * Find all medical records with all person's information (firstname, lastname, birthdate, medications, allergies)
     *
     * @return a list with all medical records
     */
    List<MedicalRecord> findMedicalRecords();

    /**
     * Find a medical record  by firstname and lastname of the person concerned
     *
     * @param firstName of the person concerned
     * @param lastName  of the person concerned
     * @return the medical record of the person concerned with all information (birthdate, medications, allergies)
     */
    MedicalRecord findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Find a medical record by ID
     *
     * @param uniqueID of the person concerned
     * @return the medical record of the person concerned
     */
    MedicalRecord findByUniqueID(String uniqueID);

    /**
     * Save a medical record
     *
     * @param medicalRecord information (firstname, lastname, birthdate, medications, allergies)
     * @return the information of medical record saved
     */
    MedicalRecord save(MedicalRecord medicalRecord);

    /**
     * Delete a medical record found by firstname and lastname
     *
     * @param firstName of medicalRecord to delete
     * @param lastName  of medicalRecord to delete
     */
    void delete(String firstName, String lastName);
}
