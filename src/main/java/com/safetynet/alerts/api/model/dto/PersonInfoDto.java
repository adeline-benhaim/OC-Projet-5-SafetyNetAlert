package com.safetynet.alerts.api.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PersonInfoDto {

    private String name;
    private String address;
    private int age;
    private String email;
    private String medications;
    private String allergies;
}
