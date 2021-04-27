package com.safetynet.alerts.api.dao;

import com.safetynet.alerts.api.config.DataSource;
import com.safetynet.alerts.api.config.DataSourceTest;
import com.safetynet.alerts.api.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordDaoImplTest {

    @Mock
    DataSource dataSource;

    @InjectMocks
    MedicalRecordDaoImpl medicalRecordDao;

    @InjectMocks
    DataSourceTest dataSourceTest;

    @BeforeEach
    void clear() {
        dataSourceTest.clearMedicalRecordsMocked();
    }

    @Test
    @DisplayName("Get the list of all medical records present in the data source")
    void getAllMedicalRecordsTest() {

        // GIVEN
        Mockito.when(dataSource.getAllMedicalRecords()).thenReturn(dataSourceTest.getAllMedicalRecordMocked());

        // WHEN
        List<MedicalRecord> medicalRecordsList = medicalRecordDao.findMedicalRecords();

        // THEN
        assertEquals(medicalRecordsList, dataSourceTest.getMedicalRecordsMocked());
    }

    @Test
    @DisplayName("Save a new medical record which does not exist in the data source")
    void saveNewMedicalRecordTest() {

        // GIVEN
        Mockito.when(dataSource.getAllMedicalRecords()).thenReturn(dataSourceTest.getAllMedicalRecordMocked());

        // WHEN
        MedicalRecord medicalRecord = new MedicalRecord("first name", "last name", "birthdate", "medications", "allergies");
        MedicalRecord createdMedicalRecord = medicalRecordDao.save(medicalRecord);

        // THEN
        assertEquals("first name", createdMedicalRecord.getFirstName());
        assertEquals("last name", createdMedicalRecord.getLastName());
        assertEquals("birthdate", createdMedicalRecord.getBirthdate());
        assertEquals("medications", createdMedicalRecord.getMedications());
        assertEquals("allergies", createdMedicalRecord.getAllergies());
    }

    @Test
    @DisplayName("Save a medical record already existing in the data source")
    void saveMedicalRecordAlreadyExistingTest() {

        // GIVEN
        Mockito.when(dataSource.getAllMedicalRecords()).thenReturn(dataSourceTest.getAllMedicalRecordMocked());

        // WHEN
        MedicalRecord medicalRecord = new MedicalRecord("first name 1", "last name 1", "birthdate 5", "medications", "allergies");
        MedicalRecord createdMedicalRecord = medicalRecordDao.save(medicalRecord);


        // THEN
        assertEquals("first name 1", createdMedicalRecord.getFirstName());
        assertEquals("last name 1", createdMedicalRecord.getLastName());
        assertEquals("birthdate 5", createdMedicalRecord.getBirthdate());
    }

    @Test
    @DisplayName("Get an existing medical record found by first and last name")
    void findExistingMedicalRecordByFirstNameAndLastNameTest() {

        // GIVEN
        Mockito.when(dataSource.getAllMedicalRecords()).thenReturn(dataSourceTest.getAllMedicalRecordMocked());

        // WHEN
        MedicalRecord medicalRecord = medicalRecordDao.findByFirstNameAndLastName("first name 1", "last name 1");

        // THEN
        assertEquals(medicalRecord, dataSourceTest.getMedicalRecordsMocked().get(0));
    }

    @Test
    @DisplayName("Get an unknown medical record found by first and last name return null")
    void findUnknownMedicalRecordByFirstNameAndLastNameTest() {

        // GIVEN
        Mockito.when(dataSource.getAllMedicalRecords()).thenReturn(dataSourceTest.getAllMedicalRecordMocked());

        // WHEN
        MedicalRecord medicalRecord = medicalRecordDao.findByFirstNameAndLastName("first name 5", "last name 5");

        // THEN
        assertNull(medicalRecord);
    }

//    @Test
//    @DisplayName("Delete a medical record found by first and last name")
//    void deleteMedicalRecordFoundByFirstNameAndLastNameTest() {
//
//        // GIVEN
//        Mockito.when(dataSource.getAllMedicalRecords()).thenReturn(dataSourceTest.getAllMedicalRecordMocked());
//        MedicalRecord medicalRecordToDelete = new MedicalRecord("first name 1", "last name 1", null, null, null);
//        // WHEN
//        medicalRecordDao.delete(dataSourceTest.getAllMedicalRecordMocked().get(2));
//
//        // THEN
//        assertEquals(dataSourceTest.getMedicalRecordsMocked().get(2).getFirstName(), medicalRecordToDelete.getFirstName());
//    }
}
