package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.model.dto.ChildAlertDto;
import com.safetynet.alerts.api.model.dto.ListPersonAdultChildDto;

import java.util.List;

public interface PersonService {

    /**
     * Find all persons with their firstname, lastname, address, city, zip, email
     * @return list with all persons with their firstname, lastname, address, city, zip, email
     */
    List<Person> getAllPersons();

    /**
     * Find person by firstname and lastname
     * @param firstName of the wanted person
     * @param lastName of the wanted person
     * @return the information of the person sought (firstname, lastname, address, city, zip, email)
     */
    Person findPersonByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Update a person
     * @param person to update
     * @return person with information's updated
     */
    Person updatePerson(Person person);

    /**
     * Create a new person with information (firstname, lastname, address, city, zip, email)
     * @param person information (firstname, lastname)
     * @return information of the person created (firstname, lastname, address, city, zip, email)
     */
    Person createNewPerson(Person person);

    /**
     * Delete a person found by firstname and lastname
     * @param firstName of the person to delete
     * @param lastName of the person to delete
     * @return null
     */
    void deletePerson(String firstName, String lastName);

    /**
     * Find a list of persons covered by the station number sought
     * @param stationNumber of firestation for which persons are sought
     * @return a global list of persons (included adults and children) covered by the station number
     */
    List <Person> findGlobalListOfPersonsByStationNumber(String stationNumber);

    /**
     * Find a list of adults and a list of children from a list of persons
     * @param personList a list of persons included adults and children
     * @return a list of adults and a list of children
     */
    ListPersonAdultChildDto findChildrenListAndAdultList(List<Person> personList);

    /**
     * Find a list of persons covered by the address sought
     * @param address for which persons are sought
     * @return a global list of persons living at the address sought
     */
    List<Person> findGlobalListOfPersonsByAddress(String address);

    /**
     *  Find a list of children living at the address sought with a list of other house hold members
     * @param address for which child is sought
     * @return a list of children (firstname, lastname, age) living at the address sought with a list of other house hold members
     */
    List<ChildAlertDto> findChildrenByAddress(String address);
}
