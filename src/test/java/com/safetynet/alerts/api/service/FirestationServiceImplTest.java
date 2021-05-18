package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.config.DataSourceTest;
import com.safetynet.alerts.api.dao.FirestationDao;
import com.safetynet.alerts.api.exceptions.FirestationAlreadyExistException;
import com.safetynet.alerts.api.exceptions.FirestationNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Mockito.when(firestationDao.findByAddress("address 1")).thenReturn(dataSourceTest.getAllFirestationMocked().get(0));

        // WHEN
        Firestation firestation = firestationService.findFirestationByAddress("address 1");

        // THEN
        assertEquals(firestation, dataSourceTest.getFirestationsMocked().get(0));
    }

    @Test
    @DisplayName("Get an unknown firestation found by address")
    void findUnknownFirestationFoundByAddressTest() {

        // GIVEN

        // WHEN
        when(firestationDao.findByAddress("address 5")).thenReturn(null);

        // THEN
        assertThrows(FirestationNotFoundException.class, () -> firestationService.findFirestationByAddress("address 5"));
    }

    @Test
    @DisplayName("Update an unknown firestation find by address")
    void updateUnknownFirestationByAddressTest() {

        //GIVEN
        Firestation firestation = Firestation.builder()
                .address("address 5")
                .stationNumber("number 5")
                .build();

        //WHEN
        when(firestationDao.findByAddress(firestation.getAddress())).thenReturn(null);

        //THEN
        assertThrows(FirestationNotFoundException.class, () -> firestationService.updateFirestation(firestation));
    }

    @Test
    @DisplayName("Update a firestation already present in datasource")
    void updateFirestationFoundInDataSource() {

        //GIVEN
        Firestation firestation = Firestation.builder()
                .address("address 1")
                .stationNumber("number 1")
                .build();
        when(firestationDao.findByAddress(firestation.getAddress())).thenReturn(dataSourceTest.getAllFirestationMocked().get(0));

        //WHEN
       firestationService.updateFirestation(firestation);

        //THEN
        verify(firestationDao, Mockito.times(1)).save(firestation);
    }

    @Test
    @DisplayName("Create a new firestation")
    void createANewFirestationTest() {

        // GIVEN
        Firestation newFirestation = Firestation.builder()
                .address("1509 Culver St")
                .stationNumber("number 3")
                .build();
        when(firestationDao.findByAddress(newFirestation.getAddress())).thenReturn(null);

        // WHEN
        firestationService.createNewFirestation(newFirestation);

        // THEN
        verify(firestationDao, Mockito.times(1)).save(newFirestation);
    }

    @Test
    @DisplayName("Try to create a firestation already existing in data source return exception already exist")
    void createAFirestationAlreadyExistingInDataSourceTest() {

        // GIVEN
        Firestation newFirestation = Firestation.builder()
                .address("address 1")
                .stationNumber("number 1")
                .build();
        when(firestationDao.findByAddress(newFirestation.getAddress())).thenReturn(dataSourceTest.getAllFirestationMocked().get(0));

        // WHEN

        // THEN
        assertThrows(FirestationAlreadyExistException.class, () -> firestationService.createNewFirestation(newFirestation));
    }

    @Test
    @DisplayName("Delete a firestation by address")
    void deleteAFirestationByAddressTest() {

        // GIVEN

        // WHEN
        firestationService.deleteFirestation("address 1");

        // THEN
        verify(firestationDao, Mockito.times(1)).delete("address 1");
    }
}
