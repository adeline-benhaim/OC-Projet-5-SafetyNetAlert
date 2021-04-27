package com.safetynet.alerts.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Firestation {
    private String address;
    private String stationNumber;

    public Firestation(String address, String stationNumber) {
        this.address = address;
        this.stationNumber = stationNumber;
    }
}

