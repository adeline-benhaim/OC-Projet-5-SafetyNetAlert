package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.model.dto.*;

import java.util.List;

public interface InformationService {

    /**
     * Find a list of adults and a list of children from a list of persons
     *
     * @param personList a list of persons included adults and children
     * @return a list of adults and a list of children
     */
    PersonAdultChildListDto findChildrenListAndAdultList(List<Person> personList);

    /**
     * Find a list of persons covered by the station number sought and a count of adults and children concerned
     *
     * @param stationNumber of firestation for which persons are sought
     * @return a list of persons covered by the station number and a count of adults and children concerned
     */
    PersonInfoByFirestationDto findPersonsInfoByStationNumber(String stationNumber);

    /**
     * Find a list of children living at the address sought with a list of other house hold members
     *
     * @param address for which child is sought
     * @return a list of children (firstname, lastname, age) living at the address sought with a list of other house hold members
     */
    List<ChildAlertDto> findChildrenByAddress(String address);

    /**
     * Find a list of phone numbers of persons covered by the firestation number sought
     *
     * @param firestationNumber for which the phone numbers are sought
     * @return a list with phone numbers of all persons covered by the firestation number sought
     */
    List<String> findListOfPhoneNumbersByFirestationNumber(String firestationNumber);

    /**
     * Find a list of person with name, phone, age, medications and allergies, and firestation number corresponding to a given address
     *
     * @param address for which person list is sought
     * @return a list of person with name, phone, age, medications and allergies, and firestation number
     */
    FireDto findListOfFirePersonByAddress(String address);

    /**
     * Find a list of all persons served by the station number, sorted by address
     * @param stationNumber for which person list is sought
     * @return a list of persons with name, phone, age, medications and allergies, sorted by address
     */
    List<FloodDto> findListOfFloodPersonByStationNumber(String stationNumber);

    /**
     * Find a person info or a list of person info if if several persons have the same name
     * @param firstName of person info sought
     * @param lastName of person info sought
     * @return person info with name, address, age, email medications and allergies
     */
    List<PersonInfoDto> findPersonInfoByFirstnameAndLastname(String firstName, String lastName);
}
