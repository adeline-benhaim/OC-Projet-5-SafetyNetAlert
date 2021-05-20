package com.safetynet.alerts.api.model.dto;

import com.safetynet.alerts.api.model.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FloodDto {

    private String address;
    private List<PersonInfoFireDto> personList;
}
