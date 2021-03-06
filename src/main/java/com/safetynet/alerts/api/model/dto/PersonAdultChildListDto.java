package com.safetynet.alerts.api.model.dto;

import com.safetynet.alerts.api.model.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class PersonAdultChildListDto {
    private List<Person> listOfAdult;
    private List<Person> listOfChild;
}
