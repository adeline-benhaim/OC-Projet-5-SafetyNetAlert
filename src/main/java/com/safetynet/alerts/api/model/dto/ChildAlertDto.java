package com.safetynet.alerts.api.model.dto;

import com.safetynet.alerts.api.model.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ChildAlertDto {

    private String firstName;
    private String lastName;
    private int age;
    private List<Person> houseHoldMembers;
}
