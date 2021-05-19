package com.safetynet.alerts.api.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class PersonInfoByFirestationDto {
    private List<PersonDto> personDto;
    private int numberOfChildren;
    private int numberOfAdults;
}
