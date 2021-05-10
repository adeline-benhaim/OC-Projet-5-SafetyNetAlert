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

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("Save a new medical record which does not exist in the data source")
    void saveNewMedicalRecordTest() {

        // GIVEN
        Mockito.when(dataSource.getAllMedicalRecords()).thenReturn(dataSourceTest.getAllMedicalRecordMocked());
        int sizeListBeforeSave = dataSourceTest.getMedicalRecordsMocked().size();

        // WHEN
        MedicalRecord medicalRecord = MedicalRecord.builder()
                .firstName("first name")
                .lastName("last name")
                .birthdate("birthdate")
                .medications("medications")
                .allergies("allergies")
                .build();
        MedicalRecord saveMedicalRecord = medicalRecordDao.save(medicalRecord);
        int sizeListAfterSave = dataSourceTest.getMedicalRecordsMocked().size();

        // THEN
        MedicalRecord medicalRecordTest = dataSourceTest.getMedicalRecordsMocked().get(3);
        assertEquals(saveMedicalRecord.getFirstName(), medicalRecordTest.getFirstName());
        assertEquals(saveMedicalRecord.getLastName(), medicalRecordTest.getLastName());
        assertEquals(saveMedicalRecord.getBirthdate(), medicalRecordTest.getBirthdate());
        assertEquals("medications", medicalRecordTest.getMedications());
        assertEquals("allergies", medicalRecordTest.getAllergies());
        assertTrue(sizeListBeforeSave < sizeListAfterSave);
        assertEquals(sizeListBeforeSave + 1, sizeListAfterSave);
    }

    @Test
    @DisplayName("Save a medical record already existing in the data source")
    void saveMedicalRecordAlreadyExistingTest() {

        // GIVEN
        Mockito.when(dataSource.getAllMedicalRecords()).thenReturn(dataSourceTest.getAllMedicalRecordMocked());
        int sizeListBeforeSave = dataSourceTest.getMedicalRecordsMocked().size();

        // WHEN
        MedicalRecord medicalRecord = MedicalRecord.builder()
                .firstName("first name 1")
                .lastName("last name 1")
                .birthdate("birthdate 5")
                .medications("medications 5")
                .allergies("allergies 5")
                .build();
        MedicalRecord saveMedicalRecord = medicalRecordDao.save(medicalRecord);
        int sizeListAfterSave = dataSourceTest.getMedicalRecordsMocked().size();

        // THEN
        MedicalRecord medicalRecordTest = dataSourceTest.getMedicalRecordsMocked().get(0);
        assertEquals(saveMedicalRecord.getFirstName(), medicalRecordTest.getFirstName());
        assertEquals(saveMedicalRecord.getLastName(), medicalRecordTest.getLastName());
        assertEquals(saveMedicalRecord.getBirthdate(), medicalRecordTest.getBirthdate());
        assertEquals("medications 5", medicalRecordTest.getMedications());
        assertEquals("allergies 5", medicalRecordTest.getAllergies());
        assertEquals(sizeListBeforeSave, sizeListAfterSave);
    }

    @Test
    @DisplayName("Delete a medical record found by first and last name")
    void deleteMedicalRecordFoundByFirstNameAndLastNameTest() {

        // GIVEN
        Mockito.when(dataSource.getAllMedicalRecords()).thenReturn(dataSourceTest.getAllMedicalRecordMocked());
        int sizeListBeforeDelete = dataSourceTest.getMedicalRecordsMocked().size();

        // WHEN
        MedicalRecord medicalRecordToDelete = dataSourceTest.getMedicalRecordsMocked().get(0);
        medicalRecordDao.delete(medicalRecordToDelete.getFirstName(), medicalRecordToDelete.getLastName());
        int sizeListAfterDelete = dataSourceTest.getMedicalRecordsMocked().size();

        // THEN
        assertTrue(sizeListBeforeDelete > sizeListAfterDelete);
        assertEquals(sizeListBeforeDelete - 1, sizeListAfterDelete);
    }
}
