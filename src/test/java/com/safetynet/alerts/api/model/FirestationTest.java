package com.safetynet.alerts.api.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class FirestationTest {

    private Firestation firestation;

    @Test
    @DisplayName("Get firestation station number")
    void testFirestationGetStationNumber() {

        //GIVEN
        String stationNumber = "stationNumber";

        //WHEN
        firestation = new Firestation(null,"stationNumber");

        //THEN
        assertThat(stationNumber).isEqualTo(firestation.getStationNumber());
    }

    @Test
    @DisplayName("Get firestation station number")
    void testFirestationGetAddress() {

        //GIVEN
        String address = "address";

        //WHEN
        firestation = new Firestation("address",null);

        //THEN
        assertThat(address).isEqualTo(firestation.getAddress());
    }

    @Test
    @DisplayName("Set a firestation ")
    void testFirestationSet() {

        //GIVEN
        firestation = new Firestation(null,null);

        //WHEN
        firestation.setAddress("address");
        firestation.setStationNumber("stationNumber");

        //THEN
        assertThat(firestation.getAddress()).isEqualTo("address");
        assertThat(firestation.getStationNumber()).isEqualTo("stationNumber");
    }
}
