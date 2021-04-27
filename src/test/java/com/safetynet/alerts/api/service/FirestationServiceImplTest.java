package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.config.DataSourceTest;
import com.safetynet.alerts.api.dao.FirestationDao;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceImplTest {

    @Mock
    FirestationDao firestationDao;

    @InjectMocks
    FirestationServiceImpl firestationService;

    @InjectMocks
    DataSourceTest dataSourceTest;

    @BeforeEach
    void clear() {
        dataSourceTest.clearFirestationsMocked();
    }

    @Test
    @DisplayName("Get the list of all the firestations present in the data source")
    void testGetAllFirestations() {

        //GIVEN
        Mockito.when(firestationDao.findFirestations()).thenReturn(dataSourceTest.getAllFirestationMocked());

        //WHEN
        List<Firestation> firestationList = firestationService.getAllFirestations();

        //THEN
        assertEquals(firestationList, dataSourceTest.getAllFirestationMocked());
    }

    @Test
    @DisplayName("Get an existing firestation found by address")
    void findExistingFirestationFoundByAddressTest() {

        // GIVEN
        Firestation firestationFounded = dataSourceTest.getAllFirestationMocked().get(0);
        Mockito.when(firestationDao.findByAddress("address 1")).thenReturn(firestationFounded);

        // WHEN
        Firestation firestation = firestationService.findFirestationByAddress("address 1");
        //Firestation firestation1 = firestationDao.findByAddress("address 1");

        // THEN
        assertEquals(firestation, firestationFounded);
    }

    @Test
    @DisplayName("Create a new firestation")
    void createANewFirestationTest() {

        // GIVEN
        //Firestation newFirestation = new Firestation("address 8","number 8");
        Firestation newFirestation = new Firestation("1509 Culver St","3");

        // WHEN
        firestationService.createNewFirestation(newFirestation);

        // THEN
        verify(firestationDao, Mockito.times(1)).save(newFirestation);
    }

//    @Test
//    @DisplayName("Delete a firestation by address")
//    void deleteAFirestationByAddressTest() {
//
//        // GIVEN
//        Firestation newFirestation = new Firestation("address 8","number 8");
//
//        // WHEN
//        firestationService.deleteFirestationByAddress(newFirestation);
//
//        // THEN
//        verify(firestationDao, Mockito.times(1)).delete(newFirestation);
//    }


}
