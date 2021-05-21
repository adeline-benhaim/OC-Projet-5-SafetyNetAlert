package com.safetynet.alerts.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Builder
@Getter
@Setter
public class Person {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
    @JsonIgnore
    private String uniqueID;
    @JsonIgnore
    private int age;
    @JsonIgnore
    private String birthdate;
    @JsonIgnore
    private List<Person> houseHoldMembers;
    @JsonIgnore
    private String medications;
    @JsonIgnore
    private String allergies;


    public void calculateAge(String birthdate) {
            Locale.setDefault(Locale.FRANCE);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate birthdateConvert = LocalDate.parse(birthdate, formatter);
            LocalDate currentDate = LocalDate.now();
            int age = Period.between(birthdateConvert, currentDate).getYears();
            this.setAge(age);
    }

}

