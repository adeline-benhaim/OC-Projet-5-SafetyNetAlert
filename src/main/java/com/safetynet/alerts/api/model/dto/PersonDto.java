package com.safetynet.alerts.api.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PersonDto {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    @JsonIgnore
    private int age;

}



