package com.safetynet.alerts.api.dao;

import com.safetynet.alerts.api.config.DataSource;
import com.safetynet.alerts.api.config.DataSourceTest;
import com.safetynet.alerts.api.model.Firestation;
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
public class FirestationDaoImplTest {

    @Mock
    DataSource dataSource;

    @InjectMocks
    FirestationDaoImpl firestationDao;

    @InjectMocks
    DataSourceTest dataSourceTest;

    @BeforeEach
    void clear() {
        dataSourceTest.clearFirestationsMocked();
    }

    @Test
    @DisplayName("Get the list of all the firestations present in the data source")
    void getAllFirestationTest() {

        // GIVEN
        Mockito.when(dataSource.getAllFirestation()).thenReturn(dataSourceTest.getAllFirestationMocked());

        // WHEN
        List<Firestation> firestationList = firestationDao.findFirestations();

        // THEN
        assertEquals(firestationList, dataSourceTest.getAllFirestationMocked());
    }

    @Test
    @DisplayName("Get an existing firestation found by address")
    void findExistingFirestationFoundByAddressTest() {

        // GIVEN
        Mockito.when(dataSource.getAllFirestation()).thenReturn(dataSourceTest.getAllFirestationMocked());

        // WHEN
        Firestation firestation = firestationDao.findByAddress("address 1");

        // THEN
        assertEquals(firestation, dataSourceTest.getFirestationsMocked().get(0));
    }

    @Test
    @DisplayName("Get an unknown firestation found by address return null")
    void findUnknownFirestationFoundByAddressTest() {

        // GIVEN
        Mockito.when(dataSource.getAllFirestation()).thenReturn(dataSourceTest.getAllFirestationMocked());

        // WHEN
        Firestation firestation = firestationDao.findByAddress("address 5");

        // THEN
        assertNull(firestation);
    }

    @Test
    @DisplayName("Save a new firestation which does not exist in the data source")
    void saveNewFirestationTest() {

        // GIVEN
        Mockito.when(dataSource.getAllFirestation()).thenReturn(dataSourceTest.getAllFirestationMocked());
        int sizeListBeforeSave = dataSourceTest.getFirestationsMocked().size();


        // WHEN
        Firestation firestation = Firestation.builder()
                .address("address")
                .stationNumber("number")
                .build();
        Firestation saveFirestation = firestationDao.save(firestation);
        int sizeListAfterSave = dataSourceTest.getFirestationsMocked().size();

        // THEN
        Firestation firestationTest = dataSourceTest.getFirestationsMocked().get(3);
        assertEquals(saveFirestation.getAddress(), firestationTest.getAddress());
        assertEquals(saveFirestation.getStationNumber(), firestationTest.getStationNumber());
        assertTrue(sizeListBeforeSave < sizeListAfterSave);
        assertEquals(sizeListBeforeSave + 1, sizeListAfterSave);
    }

    @Test
    @DisplayName("Save a firestation already existing in the data source")
    void saveFirestationAlreadyExistingTest() {

        // GIVEN
        Mockito.when(dataSource.getAllFirestation()).thenReturn(dataSourceTest.getAllFirestationMocked());
        int sizeListBeforeSave = dataSourceTest.getFirestationsMocked().size();


        // WHEN
        Firestation firestation = Firestation.builder()
                .address("address 1")
                .stationNumber("number 5")
                .build();
        Firestation saveFirestation = firestationDao.save(firestation);
        int sizeListAfterSave = dataSourceTest.getFirestationsMocked().size();

        // THEN
        Firestation firestationTest = dataSourceTest.getFirestationsMocked().get(0);
        assertEquals(saveFirestation.getAddress(), firestationTest.getAddress());
        assertEquals("number 5", firestationTest.getStationNumber());
        assertEquals(sizeListBeforeSave, sizeListAfterSave);
    }


    @Test
    @DisplayName("Delete a firestation found by address")
    void deleteFirestationFoundByAddressTest() {

        // GIVEN
        Mockito.when(dataSource.getAllFirestation()).thenReturn(dataSourceTest.getAllFirestationMocked());
        int sizeListBeforeDelete = dataSourceTest.getFirestationsMocked().size();

        // WHEN
        Firestation firestationToDelete = dataSourceTest.getFirestationsMocked().get(0);
        firestationDao.delete(firestationToDelete.getAddress());
        int sizeListAfterDelete = dataSourceTest.getFirestationsMocked().size();

        // THEN
        assertTrue(sizeListBeforeDelete > sizeListAfterDelete);
        assertEquals(sizeListBeforeDelete - 1, sizeListAfterDelete);
    }
}