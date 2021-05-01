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

        // WHEN
        MedicalRecord medicalRecord = new MedicalRecord("first name", "last name", "birthdate", "medications", "allergies");
        medicalRecordDao.save(medicalRecord);

        // THEN
        assertEquals("first name", dataSourceTest.getMedicalRecordsMocked().get(3).getFirstName());
        assertEquals("last name", dataSourceTest.getMedicalRecordsMocked().get(3).getLastName());
        assertEquals("birthdate", dataSourceTest.getMedicalRecordsMocked().get(3).getBirthdate());
        assertEquals("medications", dataSourceTest.getMedicalRecordsMocked().get(3).getMedications());
        assertEquals("allergies", dataSourceTest.getMedicalRecordsMocked().get(3).allergies);
    }

    @Test
    @DisplayName("Save a medical record already existing in the data source")
    void saveMedicalRecordAlreadyExistingTest() {

        // GIVEN
        Mockito.when(dataSource.getAllMedicalRecords()).thenReturn(dataSourceTest.getAllMedicalRecordMocked());

        // WHEN
        MedicalRecord medicalRecord = new MedicalRecord("first name 1", "last name 1", "birthdate 5", "medications", "allergies");
        medicalRecordDao.save(medicalRecord);


        // THEN
        assertEquals("first name 1", dataSourceTest.getMedicalRecordsMocked().get(0).getFirstName());
        assertEquals("last name 1", dataSourceTest.getMedicalRecordsMocked().get(0).getLastName());
        assertEquals("birthdate 5", dataSourceTest.getMedicalRecordsMocked().get(0).getBirthdate());
    }

    @Test
    @DisplayName("Delete a medical record found by first and last name")
    void deleteMedicalRecordFoundByFirstNameAndLastNameTest() {

        // GIVEN
        Mockito.when(dataSource.getAllMedicalRecords()).thenReturn(dataSourceTest.getAllMedicalRecordMocked());
        MedicalRecord medicalRecord = new MedicalRecord("first name 5", "last name 5", null, null, null);
        medicalRecordDao.save(medicalRecord);
        int sizeListBeforeDelete = dataSourceTest.getMedicalRecordsMocked().size();

        // WHEN
        medicalRecordDao.delete(medicalRecord.getFirstName(), medicalRecord.getLastName());
        int sizeListAfterDelete = dataSourceTest.getMedicalRecordsMocked().size();

        // THEN
        assertEquals(4, sizeListBeforeDelete);
        assertEquals(3, sizeListAfterDelete);
    }
}
