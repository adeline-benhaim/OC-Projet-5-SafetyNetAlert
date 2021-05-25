package com.safetynet.alerts.api.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class FireDto {

    private List<PersonInfoFireDto> personInfoFireDtoList;
    private String firestationNumber;
}
