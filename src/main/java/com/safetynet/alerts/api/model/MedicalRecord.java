package com.safetynet.alerts.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MedicalRecord {
    public String firstName;
    public String lastName;
    public String birthdate;
    public String medications;
    public String allergies;

    public MedicalRecord(String firstName, String lastName, String birthdate, String medications, String allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }
}
