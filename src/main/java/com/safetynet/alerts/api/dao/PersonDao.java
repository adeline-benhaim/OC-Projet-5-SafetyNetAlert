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
     * Find person by firstname and lastname
     * @param firstName of the wanted person
     * @param lastName of the wanted person
     * @return the information of the person sought (firstname, lastname, address, city, zip, email)
     */
    Person findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Save a person with their information (firstname, lastname, address, city, zip, email)
     * @param person information (firstname, lastname, address, city, zip, email)
     * @return information of the saved person
     */
    Person save(Person person);

    /**
     * Delete a person found by firstname and lastname
     * @param firstName of the person to delete
     * @param lastName of the person to delete
     */
    void delete(String firstName, String lastName);
}
