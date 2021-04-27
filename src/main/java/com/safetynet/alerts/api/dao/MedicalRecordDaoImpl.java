package com.safetynet.alerts.api.dao;

import com.safetynet.alerts.api.config.DataSource;
import com.safetynet.alerts.api.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordDaoImpl implements MedicalRecordDao {
    @Autowired
    private DataSource dataSource;

    /**
     * Find all medical records with all person's information (firstname, lastname, birthdate, medications, allergies)
     *
     * @return a list with all medical records
     */
    @Override
    public List<MedicalRecord> findMedicalRecords() {
        return dataSource.getAllMedicalRecords();
    }

    /**
     * Find a medical record  by firstname and lastname of the person concerned
     *
     * @param firstName of the person concerned
     * @param lastName  of the person concerned
     * @return the medical record of the person concerned with all information (birthdate, medications, allergies)
     */
    @Override
    public MedicalRecord findByFirstNameAndLastName(String firstName, String lastName) {
        List<MedicalRecord> medicalRecordList = dataSource.getAllMedicalRecords();
        for (MedicalRecord medicalRecord : medicalRecordList) {
            if (medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName)) {
                return medicalRecord;
            }
        }
        return null;
    }

    /**
     * Save a medical record
     *
     * @param medicalRecord information (firstname, lastname, birthdate, medications, allergies)
     * @return the information of medical record saved
     */
    @Override
    public MedicalRecord save(MedicalRecord medicalRecord) {
        List<MedicalRecord> allMedicalRecords = dataSource.getAllMedicalRecords();
        MedicalRecord medicalRecord1 = findByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (medicalRecord1 == null) allMedicalRecords.add(medicalRecord);
        else {
            int of = allMedicalRecords.indexOf(medicalRecord1);
            allMedicalRecords.remove(of);
            allMedicalRecords.add(of, medicalRecord);
        }
        return medicalRecord;
    }

    /**
     * Delete a medical record found by firstname and lastname
     *
     * @param firstName of medicalRecord to delete
     * @param lastName of medicalRecord to delete
     */
    @Override
    public void delete(String firstName, String lastName) {
    List<MedicalRecord> medicalRecords = dataSource.getAllMedicalRecords();
    medicalRecords.removeIf(elem -> elem.firstName.equalsIgnoreCase(firstName) && elem.lastName.equalsIgnoreCase(lastName));
    }
}
