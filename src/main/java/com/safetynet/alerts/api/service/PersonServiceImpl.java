package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.dao.PersonDao;
import com.safetynet.alerts.api.exceptions.PersonAlreadyExistException;
import com.safetynet.alerts.api.exceptions.PersonNotFoundException;
import com.safetynet.alerts.api.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonDao personDao;


    /**
     * Find all persons
     *
     * @return list with all persons
     */
    @Override
    public List<Person> getAllPersons() {
        logger.info("Get all persons");
        return personDao.findPersons();
    }

    /**
     * Find person by firstname and lastname
     *
     * @param firstName of the wanted person
     * @param lastName  of the wanted person
     * @return the information of the person sought
     */
    @Override
    public Person findPersonByFirstNameAndLastName(String firstName, String lastName) {
        logger.info("Get person by firstname and lastname : {}" + " " + "{}", firstName, lastName);
        Person person = personDao.findByFirstNameAndLastName(firstName, lastName);
        if (person != null) return person;
        logger.error("Get person by firstname and lastname error because {}" + " " + "{} is not found", firstName, lastName);
        throw new PersonNotFoundException("Get person by firstname and lastname error because person " + firstName + " " + lastName + " is not found");
    }

    /**
     * Update a person
     *
     * @param person information of the person to update
     * @return person with information's updated
     */
    @Override
    public Person updatePerson(Person person) {
        logger.info("Update person : {}" + " " + "{} ", person.getFirstName(), person.getLastName());
        Person currentPerson = personDao.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (currentPerson == null) throw new PersonNotFoundException("Trying to update non existing person");
        personDao.save(person);
        return person;
    }

    /**
     * Create a new person
     *
     * @param person information
     * @return information of the person created
     */
    @Override
    public Person createNewPerson(Person person) {
        logger.info("Create new person : {}" + " " + "{} ", person.getFirstName(), person.getLastName());
        Person newPerson = personDao.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (newPerson == null) {
            return personDao.save(person);
        } else {
            logger.error("Create person error because person : {} {} already exist", person.getFirstName(), person.getLastName());
            throw new PersonAlreadyExistException("Create person error because person " + person.getFirstName() + " " + person.getLastName() + " already exist");
        }
    }

    /**
     * Delete a person found by firstname and lastname
     *
     * @param firstName of the person to delete
     * @param lastName  of the person to delete
     * @return null
     */
    @Override
    public void deletePerson(String firstName, String lastName) {
        logger.info("Delete person : {}" + " " + "{} ", firstName, lastName);
        personDao.delete(firstName, lastName);
    }

}

