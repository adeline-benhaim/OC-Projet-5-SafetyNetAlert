package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.config.DataSourceTest;
import com.safetynet.alerts.api.dao.MedicalRecordDao;
import com.safetynet.alerts.api.exceptions.MedicalRecordAlreadyExistException;
import com.safetynet.alerts.api.exceptions.MedicalRecordNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceImplTest {

    @Mock
    MedicalRecordDao medicalRecordDao;

    @InjectMocks
    MedicalRecordServiceImpl medicalRecordService;

    @InjectMocks
    DataSourceTest dataSourceTest;

    @BeforeEach
    void clear() {
        dataSourceTest.clearMedicalRecordsMocked();
    }

    @Test
    @DisplayName("Get the list of all the medical records present in the data source")
    void testGetAllMedicalRecords() {

        //GIVEN
        when(medicalRecordDao.findMedicalRecords()).thenReturn(dataSourceTest.getAllMedicalRecordMocked());

        //WHEN
        List<MedicalRecord> medicalRecordList = medicalRecordService.getAllMedicalRecords();

        //THEN
        assertEquals(medicalRecordList, dataSourceTest.getMedicalRecordsMocked());
    }

    @Test
    @DisplayName("Get an existing medical record found by first and last name")
    void findExistingMedicalRecordFoundByFirstNameAndLastNameTest() {

        // GIVEN
        when(medicalRecordDao.findByFirstNameAndLastName("first name 1", "last name 1")).thenReturn(dataSourceTest.getAllMedicalRecordMocked().get(0));

        // WHEN
        MedicalRecord medicalRecord = medicalRecordService.findMedicalRecordByFirstNameAndLastName("first name 1", "last name 1");

        // THEN
        assertEquals(medicalRecord, dataSourceTest.getMedicalRecordsMocked().get(0));
    }

    @Test
    @DisplayName("Get an unknown medical record found by first and last name")
    void findUnknownMedicalRecordFoundByFirstNameAndLastNameTest() {

        // GIVEN

        // WHEN
        when(medicalRecordDao.findByFirstNameAndLastName("first name 5", "last name 5")).thenReturn(null);

        // THEN
        assertThrows(MedicalRecordNotFoundException.class, () -> medicalRecordService.findMedicalRecordByFirstNameAndLastName("first name 5", "last name 5"));
    }

    @Test
    @DisplayName("Update an unknown medical record found by first and last name")
    void updateUnknownMedicalRecordByFirstNameAndLastNameTest() {

        //GIVEN
        MedicalRecord medicalRecord = new MedicalRecord("first name 5","last name 5", null,null,null);

        //WHEN
        when(medicalRecordDao.findByFirstNameAndLastName(medicalRecord.firstName, medicalRecord.lastName)).thenReturn(null);

        //THEN
        assertThrows(MedicalRecordNotFoundException.class, () -> medicalRecordService.updateMedicalRecord(medicalRecord));
    }

    @Test
    @DisplayName("Update a medical record who is already present in DataSource")
    void updateAMedicalRecordFoundInDataSourceTest() {

        // GIVEN
        MedicalRecord presentMedicalRecord = new MedicalRecord("first name 5","last name 5", null,null,null);
        when(medicalRecordDao.findByFirstNameAndLastName(presentMedicalRecord.firstName,presentMedicalRecord.lastName)).thenReturn(dataSourceTest.getAllMedicalRecordMocked().get(0));

        // WHEN
        medicalRecordService.updateMedicalRecord(presentMedicalRecord);

        // THEN
        verify(medicalRecordDao, Mockito.times(1)).save(presentMedicalRecord);
    }

    @Test
    @DisplayName("Create a new medical record")
    void createANewMedicalRecordTest() {

        // GIVEN
        MedicalRecord newMedicalRecord = new MedicalRecord("firstname 8","lastname 8",null,null,null);
        when(medicalRecordDao.findByFirstNameAndLastName(newMedicalRecord.getFirstName(), newMedicalRecord.getLastName())).thenReturn(null);

        // WHEN
        medicalRecordService.createNewMedicalRecord(newMedicalRecord);

        // THEN
        verify(medicalRecordDao, Mockito.times(1)).save(newMedicalRecord);
    }

    @Test
    @DisplayName("Try to create a medical record who already exist in data source return exception already exist")
    void createANewMedicalRecordAlreadyExistingInDataSourceTest() {

        // GIVEN
        MedicalRecord newMedicalRecord = new MedicalRecord("firstname 1","lastname 1",null,null,null);
        when(medicalRecordDao.findByFirstNameAndLastName(newMedicalRecord.getFirstName(), newMedicalRecord.getLastName())).thenReturn(dataSourceTest.getAllMedicalRecordMocked().get(0));

        // WHEN
        medicalRecordDao.findByFirstNameAndLastName(newMedicalRecord.getFirstName(), newMedicalRecord.getLastName());

        // THEN
        assertThrows(MedicalRecordAlreadyExistException.class, () -> medicalRecordService.createNewMedicalRecord(newMedicalRecord));
    }

    @Test
    @DisplayName("Delete a medical record")
    void deleteAMedicalRecordTest() {

        // GIVEN

        // WHEN
        medicalRecordService.deleteMedicalRecord("first name", "last name");

        // THEN
        verify(medicalRecordDao, Mockito.times(1)).delete("first name", "last name");
    }
}
