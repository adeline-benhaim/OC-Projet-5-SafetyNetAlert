package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.model.Person;

import java.util.List;

public interface PersonService {

    /**
     * Find all persons with their firstname, lastname, address, city, zip, email
     *
     * @return list with all persons with their firstname, lastname, address, city, zip, email
     */
    List<Person> getAllPersons();

    /**
     * Find person by firstname and lastname
     *
     * @param firstName of the wanted person
     * @param lastName  of the wanted person
     * @return the information of the person sought (firstname, lastname, address, city, zip, email)
     */
    Person findPersonByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Update a person
     *
     * @param person to update
     * @return person with information's updated
     */
    Person updatePerson(Person person);

    /**
     * Create a new person with information (firstname, lastname, address, city, zip, email)
     *
     * @param person information (firstname, lastname)
     * @return information of the person created (firstname, lastname, address, city, zip, email)
     */
    Person createNewPerson(Person person);

    /**
     * Delete a person found by firstname and lastname
     *
     * @param firstName of the person to delete
     * @param lastName  of the person to delete
     * @return null
     */
    void deletePerson(String firstName, String lastName);
}
