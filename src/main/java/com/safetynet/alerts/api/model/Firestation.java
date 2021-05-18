package com.safetynet.alerts.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

