package com.safetynet.alerts.api.dao;

import com.safetynet.alerts.api.config.DataSource;
import com.safetynet.alerts.api.model.Firestation;
import com.safetynet.alerts.api.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonDaoImpl implements PersonDao {

    @Autowired
    private DataSource dataSource;

    /**
     * Find all persons with their firstname, lastname, address, city, zip, email
     *
     * @return list with all persons with their firstname, lastname, address, city, zip, email
     */
    @Override
    public List<Person> findPersons() {
        return dataSource.getAllPersons();
    }

    /**
     * Find a list of persons found by address
     *
     * @param address for which persons are sought
     * @return a list of persons living at the address sought
     */
    @Override
    public List<Person> findByAddress(String address) {
        List<Person> personList = dataSource.getAllPersons();
        List<Person> personListByAddress = new ArrayList<>();
        for (Person person : personList) {
            if (person.getAddress().equalsIgnoreCase(address)) {
                personListByAddress.add(person);
            }
        }
        if (personListByAddress.isEmpty()) {
            return null;
        }
        return personListByAddress;
    }

    /**
     * Find a list of persons covered by the station number sought
     *
     * @param stationNumber of firestation for which persons are sought
     * @return a global list of persons covered by the station number
     */
    @Override
    public List<Person> findByStationNumber(String stationNumber) {
        List<Firestation> firestationList = dataSource.getAllFirestation();
        List<String> addresses = firestationList
                .stream()
                .filter(firestation -> firestation.getStationNumber().equals(stationNumber))
                .map(Firestation::getAddress)
                .collect(Collectors.toList());
        if (!addresses.isEmpty()) {
            List<Person> persons = dataSource.getAllPersons();
            List<Person> personsByStationNumber = new ArrayList<>();
            for (Person person : persons) {
                if (addresses.contains(person.getAddress())) personsByStationNumber.add(person);
            }
            return personsByStationNumber;
        } else {
            return null;
        }
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
     * Find a list of persons living in the city sought
     * @param city for which persons are sought
     * @return a list of persons living at the city sought
     */
    @Override
    public List<Person> findByCity(String city) {
        List<Person> personList = dataSource.getAllPersons();
        List<Person> personListByCity = new ArrayList<>();
        for (Person person : personList) {
            if (person.getCity().equalsIgnoreCase(city)) {
                personListByCity.add(person);
            }
        }
        if (personListByCity.isEmpty()) return null;
        return personListByCity;
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
     * @param lastName  of the person to delete
     */
    @Override
    public void delete(String firstName, String lastName) {
        List<Person> persons = dataSource.getAllPersons();
        persons.removeIf(elem -> elem.getFirstName().equalsIgnoreCase(firstName) && elem.getLastName().equalsIgnoreCase(lastName));
    }
}
