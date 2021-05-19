package com.safetynet.alerts.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MedicalRecord {
    private String firstName;
    private String lastName;
    private String birthdate;
    private String medications;
    private String allergies;

}
