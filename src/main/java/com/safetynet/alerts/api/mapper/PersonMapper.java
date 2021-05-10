package com.safetynet.alerts.api.mapper;

import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.model.dto.PersonDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PersonMapper {

    public PersonDto convertToPersonDto(Person person){
        return PersonDto.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .address(person.getAddress() + " " + person.getZip() + " " + person.getCity() )
                .phone(person.getPhone())
                .age(person.getAge())
                .build();
    }
}