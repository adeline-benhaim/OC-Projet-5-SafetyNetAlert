package com.safetynet.alerts.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
