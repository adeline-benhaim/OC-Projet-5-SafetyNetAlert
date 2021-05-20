package com.safetynet.alerts.api.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PersonInfoFireDto {

    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private String medications;
    private String allergies;

}
