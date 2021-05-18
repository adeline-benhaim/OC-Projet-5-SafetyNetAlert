package com.safetynet.alerts.api.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
public class PersonInfoByFirestationDto {

    private List<PersonDto> personDto;
    private Map<String, Integer> countPersonAdultChildDto;
}
