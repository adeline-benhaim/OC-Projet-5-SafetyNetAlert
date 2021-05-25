package com.safetynet.alerts.api.dao;

import com.safetynet.alerts.api.model.Person;

import java.util.List;

public interface PersonDao {

    /**
     * Find all persons with their firstname, lastname, address, city, zip, email
     *
     * @return list with all persons with their firstname, lastname, address, city, zip, email
     */
    List<Person> findPersons();

    /**
     * Find a list of persons found by address
     *
     * @param address for which persons are sought
     * @return a list of persons living at the address sought
     */
    List<Person> findByAddress(String address);

    /**
     * Find a list of persons covered by the station number sought
     *
     * @param stationNumber of firestation for which persons are sought
     * @return a global list of persons covered by the station number
     */
    List<Person> findByStationNumber(String stationNumber);

    /**
     * Find person by firstname and lastname
     *
     * @param firstName of the wanted person
     * @param lastName  of the wanted person
     * @return the information of the person sought (firstname, lastname, address, city, zip, email)
     */
    Person findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Find a list of persons living in the city sought
     *
     * @param city for which persons are sought
     * @return a list of persons living at the city sought
     */
    List<Person> findByCity(String city);

    /**
     * Save a person with their information (firstname, lastname, address, city, zip, email)
     *
     * @param person information (firstname, lastname, address, city, zip, email)
     * @return information of the saved person
     */
    Person save(Person person);

    /**
     * Delete a person found by firstname and lastname
     *
     * @param firstName of the person to delete
     * @param lastName  of the person to delete
     */
    void delete(String firstName, String lastName);
}
