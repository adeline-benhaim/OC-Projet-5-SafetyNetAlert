package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.dao.MedicalRecordDao;
import com.safetynet.alerts.api.exceptions.MedicalRecordAlreadyExist;
import com.safetynet.alerts.api.exceptions.MedicalRecordNotFoundException;
import com.safetynet.alerts.api.model.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordServiceImpl.class);

    @Autowired
    private MedicalRecordDao medicalRecordDao;

    /**
     * Get all medical records with all person's information (firstname, lastname, birthdate, medications, allergies)
     *
     * @return a list with all medical records
     */
    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        logger.info("Get all medical records");
        return medicalRecordDao.findMedicalRecords();
    }

    /**
     * Find a medical record  by firstname and lastname of the person concerned
     *
     * @param firstName of the person concerned
     * @param lastName  of the person concerned
     * @return the medical record of the person concerned with all information (birthdate, medications, allergies)
     */
    @Override
    public MedicalRecord findMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
        logger.info("Get medical record of a person found by firstname and lastname : {}" + " " + "{}", firstName, lastName);
        MedicalRecord medicalRecord = medicalRecordDao.findByFirstNameAndLastName(firstName, lastName);
        if (medicalRecord != null) return medicalRecord;
        logger.error("Get medical record for : {}" + "{}" + " error because this person is not found", firstName, lastName);
        throw new MedicalRecordNotFoundException("Get medical record for : " + firstName + " " + lastName + " error because this person is not found");
    }

    /**
     * Update the information of a medical record existing
     *
     * @param medicalRecord to update
     * @return medical record with information's updated
     */
    @Override
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        logger.info("Update medical record for : {}" + " " + "{} ", medicalRecord.getFirstName(), medicalRecord.getLastName());
        MedicalRecord currentMedicalRecord = medicalRecordDao.findByFirstNameAndLastName(medicalRecord.firstName, medicalRecord.lastName);
        if (currentMedicalRecord == null) throw new MedicalRecordNotFoundException("Trying to update non existing medical record");
        medicalRecordDao.save(medicalRecord);
        return medicalRecord;
    }

    /**
     * Create a new medical record for a new person
     *
     * @param medicalRecord information for of the person concerned
     * @return information of the medical record created (firstname, lastname, birthdate, medications, allergies)
     */
    @Override
    public MedicalRecord createNewMedicalRecord(MedicalRecord medicalRecord) {
        logger.info("Create new medical record for : {}" + " " + "{} ", medicalRecord.getFirstName(), medicalRecord.getLastName());
        MedicalRecord newMedicalRecord = medicalRecordDao.findByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (newMedicalRecord == null) {
            return medicalRecordDao.save(medicalRecord);
        } else {
            logger.error("Create medical record error because medical record for : {} {} already exist", medicalRecord.getFirstName(), medicalRecord.getLastName());
            throw new MedicalRecordAlreadyExist("Create medical record error because medical record for " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " already exist");
        }
    }

    /**
     * Delete a medical record for a person found by firstname and lastname
     *
     * @param firstName of medicalRecord to delete
     * @param lastName of medicalRecord to delete
     */
    @Override
    public void deleteMedicalRecord(String firstName, String lastName) {
        logger.info("Delete medical record for : {}" + " " + "{} ", firstName, lastName);
        medicalRecordDao.delete(firstName, lastName);
    }
}
