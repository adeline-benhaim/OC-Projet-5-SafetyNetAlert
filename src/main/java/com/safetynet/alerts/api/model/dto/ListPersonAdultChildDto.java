package com.safetynet.alerts.api.model.dto;

import com.safetynet.alerts.api.model.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ListPersonAdultChildDto {
    public List<Person> listOfAdult;
    public List<Person> listOfChild;
}
