package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.config.DataSourceTest;
import com.safetynet.alerts.api.dao.MedicalRecordDao;
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
import static org.mockito.Mockito.verify;

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
        Mockito.when(medicalRecordDao.findMedicalRecords()).thenReturn(dataSourceTest.getAllMedicalRecordMocked());

        //WHEN
        List<MedicalRecord> medicalRecordList = medicalRecordService.getAllMedicalRecords();

        //THEN
        assertEquals(medicalRecordList, medicalRecordDao.findMedicalRecords());
    }

    @Test
    @DisplayName("Get an existing medical record found by first and last name")
    void findExistingMedicalRecordFoundByFirstNameAndLastNameTest() {

        // GIVEN
        Mockito.when(medicalRecordDao.findByFirstNameAndLastName("first name 1", "last name 1")).thenReturn(dataSourceTest.getAllMedicalRecordMocked().get(0));

        // WHEN
        MedicalRecord medicalRecord = medicalRecordService.findMedicalRecordByFirstNameAndLastName("first name 1", "last name 1");
        MedicalRecord medicalRecord1 = medicalRecordDao.findByFirstNameAndLastName("first name 1", "last name 1");

        // THEN
        assertEquals(medicalRecord, medicalRecord1);
    }

    @Test
    @DisplayName("Create a new medical record")
    void createANewMedicalRecordTest() {

        // GIVEN
        MedicalRecord newMedicalRecord = new MedicalRecord("firstname 8","lastname 8",null,null,null);

        // WHEN
        medicalRecordService.createNewMedicalRecord(newMedicalRecord);

        // THEN
        verify(medicalRecordDao, Mockito.times(1)).save(newMedicalRecord);
    }

//    @Test
//    @DisplayName("Delete a medical record")
//    void deleteAMedicalRecordTest() {
//
//        // GIVEN
//        MedicalRecord newMedicalRecord = new MedicalRecord("firstname 8","lastname 8",null,null,null);
//
//        // WHEN
//        medicalRecordService.deleteMedicalRecord(newMedicalRecord);
//
//        // THEN
//        verify(medicalRecordDao, Mockito.times(1)).delete(newMedicalRecord);
//    }
}
