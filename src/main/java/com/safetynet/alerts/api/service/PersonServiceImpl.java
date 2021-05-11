package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.dao.FirestationDao;
import com.safetynet.alerts.api.dao.MedicalRecordDao;
import com.safetynet.alerts.api.dao.PersonDao;
import com.safetynet.alerts.api.exceptions.FirestationNotFoundException;
import com.safetynet.alerts.api.exceptions.PersonAlreadyExistException;
import com.safetynet.alerts.api.exceptions.PersonNotFoundException;
import com.safetynet.alerts.api.mapper.PersonMapper;
import com.safetynet.alerts.api.model.Firestation;
import com.safetynet.alerts.api.model.MedicalRecord;
import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.model.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonDao personDao;
    @Autowired
    MedicalRecordDao medicalRecordDao;
    @Autowired
    FirestationDao firestationDao;


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

    /**
     * Find a list of persons covered by the station number sought
     *
     * @param stationNumber of firestation for which persons are sought
     * @return a global list of persons covered by the station number
     */
    @Override
    public List<Person> findGlobalListOfPersonsByStationNumber(String stationNumber) {
        logger.info("Get list of persons covered by the firestation number : {}", stationNumber);
        List<Firestation> firestationList = firestationDao.findFirestations();
        List<String> addresses = firestationList
                .stream()
                .filter(firestation -> firestation.getStationNumber().equals(stationNumber))
                .map(Firestation::getAddress)
                .collect(Collectors.toList());
        if (!addresses.isEmpty()) {
            List<Person> persons = personDao.findPersons();
            List<Person> personsByStationNumber = new ArrayList<>();
            for (Person person : persons) {
                for (Object address : addresses) {
                    if (person.getAddress().equals(address)) {
                        personsByStationNumber.add(person);
                    }
                }
            }
            return personsByStationNumber;
        } else {
            logger.error("Get list of persons covered by the firestation number : {}" + " is not found", stationNumber);
            throw new FirestationNotFoundException("Trying to get non existing firestation for given station number");
        }
    }

    /**
     * Find a list of adults and a list of children from a list of persons
     *
     * @param personList a list of persons included adults and children
     * @return a list of adults and a list of children
     */
    @Override
    public ListPersonAdultChildDto findChildrenListAndAdultList(List<Person> personList) {
        logger.info("Get a list of children and a list of adults");
        List<Person> childrenList = new ArrayList<>();
        List<Person> adultList = new ArrayList<>();
        List<MedicalRecord> medicalRecordList = medicalRecordDao.findMedicalRecords();
        if (!personList.isEmpty()) {
            for (Person person : personList) {
                for (MedicalRecord medicalRecord : medicalRecordList) {
                    if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
                        String birthdate = medicalRecord.getBirthdate();
                        person.setBirthdate(birthdate);
                        person.calculateAge(birthdate);
                        if (person.getAge() <= 18) {
                            childrenList.add(person);
                        } else {
                            adultList.add(person);
                        }
                    }
                }
            }
            return ListPersonAdultChildDto.builder()
                    .listOfAdult(adultList)
                    .listOfChild(childrenList)
                    .build();
        } else {
            logger.error("Error to get a list of adults and children because list of person is empty");
            throw new FirestationNotFoundException("Error to get a list of adults and children because list of person is empty");
        }
    }

    /**
     * Find a list of persons living at the address sought
     *
     * @param address for which persons are sought
     * @return a global list of persons living at the address sought
     */
    @Override
    public List<Person> findGlobalListOfPersonsByAddress(String address) {
        logger.info("Get list of persons living at the address : {}", address);
        List<Person> personList = personDao.findPersons();
        List<Person> personListByAddress = new ArrayList<>();
        for (Person person : personList) {
            if (person.getAddress().equalsIgnoreCase(address)) {
                personListByAddress.add(person);
            }
        }
        if (personListByAddress.isEmpty()) {
            logger.error("Get a list of persons by address error because the address :  {}  is not found", address);
            throw new PersonNotFoundException("Get a list of persons by address error because the address :" + address + " is not found");
        }
        return personListByAddress;
    }

    /**
     * Find a list of children living at the address sought with a list of other house hold members
     *
     * @param address for which child is sought
     * @return a list of children (firstname, lastname, age) living at the address sought with a list of other house hold members
     */
    @Override
    public List<ChildAlertDto> findChildrenByAddress(String address) {
        logger.info("Get child alert info list by address : {}", address);
        List<Person> personList = findGlobalListOfPersonsByAddress(address);
        if (!personList.isEmpty()) {
            ListPersonAdultChildDto adultAndChildList = findChildrenListAndAdultList(personList);
            List<Person> childList = adultAndChildList.getListOfChild();
            List<Person> adultList = adultAndChildList.getListOfAdult();
            for (Person child : childList) {
                List<Person> houseHoldMembersList = new ArrayList<>();
                for (Person adult : adultList) {
                    if (child.getLastName().equals(adult.getLastName())) {
                        houseHoldMembersList.add(adult);
                        child.setHouseHoldMembers(houseHoldMembersList);
                    }
                }
            }
            return childList
                    .stream()
                    .map(PersonMapper::convertToChildAlertDto)
                    .collect(Collectors.toList());
        }
        logger.error("Get child alert info list by address error because the address :  {}  is not found", address);
        throw new PersonNotFoundException("Get child alert info list by address error because the address : " + address + " is not found");
    }
}

