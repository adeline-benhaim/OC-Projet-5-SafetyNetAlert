package com.safetynet.alerts.api.mapper;

import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.model.dto.ChildAlertDto;
import com.safetynet.alerts.api.model.dto.PersonDto;
import com.safetynet.alerts.api.model.dto.PersonInfoDto;
import com.safetynet.alerts.api.model.dto.PersonInfoFireDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PersonMapper {

    public static PersonDto convertToPersonDto(Person person) {
        return PersonDto.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .address(person.getAddress() + " " + person.getZip() + " " + person.getCity())
                .phone(person.getPhone())
                .age(person.getAge())
                .build();
    }

    public static ChildAlertDto convertToChildAlertDto(Person person) {
        return ChildAlertDto.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .age(person.getAge())
                .houseHoldMembers(person.getHouseHoldMembers())
                .build();
    }

    public static PersonInfoFireDto convertToPersonInfoFireDto(Person person) {
        return PersonInfoFireDto.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .phone(person.getPhone())
                .age(person.getAge())
                .medications(person.getMedications())
                .allergies(person.getAllergies())
                .build();
    }

    public static PersonInfoDto convertToPersonInfoDto(Person person) {
        return PersonInfoDto.builder()
                .name(person.getFirstName() + " " + person.getLastName())
                .address(person.getAddress() + " " + person.getZip() + " " + person.getCity())
                .age(person.getAge())
                .email(person.getEmail())
                .medications(person.getMedications())
                .allergies(person.getAllergies())
                .build();
    }
}
