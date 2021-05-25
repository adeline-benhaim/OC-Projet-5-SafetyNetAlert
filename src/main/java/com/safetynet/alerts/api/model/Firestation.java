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
}

