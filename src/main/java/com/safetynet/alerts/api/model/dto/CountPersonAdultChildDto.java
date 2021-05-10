package com.safetynet.alerts.api.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CountPersonAdultChildDto {
    public int numberOfAdults;
    public int numberOfChildren;
}
