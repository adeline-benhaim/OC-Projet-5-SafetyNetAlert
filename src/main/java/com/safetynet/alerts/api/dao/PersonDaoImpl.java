package com.safetynet.alerts.api.dao;

import com.safetynet.alerts.api.config.DataSource;
import com.safetynet.alerts.api.model.Firestation;
import com.safetynet.alerts.api.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonDaoImpl implements PersonDao {
    @Autowired
    private DataSource dataSource;


    /**
     * Find all persons with their firstname, lastname, address, city, zip, email
     * @return list with all persons with their firstname, lastname, address, city, zip, email
     */
    @Override
    public List<Person> findPersons() {
        return dataSource.getAllPersons();
    }

    /**
     * Find person by firstname and lastname
     *
     * @param firstName of the wanted person
     * @param lastName  of the wanted person
     * @return the information of the person sought (firstname, lastname, address, city, zip, email)
     */
    @Override
    public Person findByFirstNameAndLastName(String firstName, String lastName) {
        List<Person> persons = dataSource.getAllPersons();
        for (Person person : persons) {
            if (person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName)) {
                return person;
            }
        }
        return null;
    }

    /**
     * Save a person with their information (firstname, lastname, address, city, zip, email)
     *
     * @param person information (firstname, lastname, address, city, zip, email)
     * @return information of the saved person
     */
    @Override
    public Person save(Person person) {
        List<Person> allPersons = dataSource.getAllPersons();
        Person person1 = findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (person1 == null) allPersons.add(person);
        else {
            int of = allPersons.indexOf(person1);
            allPersons.remove(of);
            allPersons.add(of, person);
        }
        return person;
    }

    /**
     * Delete a person found by firstname and lastname
     *
     * @param firstName of the person to delete
     * @param lastName of the person to delete
     */
    @Override
    public void delete(String firstName, String lastName) {
        List<Person> persons = dataSource.getAllPersons();
        persons.removeIf(elem -> elem.getFirstName().equalsIgnoreCase(firstName) && elem.getLastName().equalsIgnoreCase(lastName));
    }
}
