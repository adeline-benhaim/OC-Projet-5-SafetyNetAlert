package com.safetynet.alerts.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
public class MedicalRecord {
    private String firstName;
    private String lastName;
    private String birthdate;
    private String medications;
    private String allergies;
    @JsonIgnore
//    @NotNull
    private String uniqueID;
}
