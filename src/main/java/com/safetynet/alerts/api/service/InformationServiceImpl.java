package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.dao.FirestationDao;
import com.safetynet.alerts.api.dao.MedicalRecordDao;
import com.safetynet.alerts.api.dao.PersonDao;
import com.safetynet.alerts.api.exceptions.FirestationNotFoundException;
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
public class InformationServiceImpl implements InformationService {
    private static final Logger logger = LoggerFactory.getLogger(InformationServiceImpl.class);

    @Autowired
    private PersonDao personDao;
    @Autowired
    private MedicalRecordDao medicalRecordDao;
    @Autowired
    private FirestationDao firestationDao;


    /**
     * Find a list of adults and a list of children from a list of persons
     *
     * @param personList a list of persons included adults and children
     * @return a list of adults and a list of children
     */
    PersonAdultChildListDto findChildrenListAndAdultList(List<Person> personList) {
        logger.info("Get a list of children and a list of adults");
        List<Person> childrenList = new ArrayList<>();
        List<Person> adultList = new ArrayList<>();
        if (personList != null) {
            for (Person person : personList) {
                MedicalRecord medicalRecord = medicalRecordDao.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                String birthdate = medicalRecord.getBirthdate();
                person.setBirthdate(birthdate);
                person.calculateAge(birthdate);
                if (person.getAge() <= 18) {
                    childrenList.add(person);
                } else {
                    adultList.add(person);
                }
            }
            return PersonAdultChildListDto.builder()
                    .listOfAdult(adultList)
                    .listOfChild(childrenList)
                    .build();
        }
        logger.error("Error to get a list of adults and children because list of person is empty");
        throw new FirestationNotFoundException("Error to get a list of adults and children because list of person is empty");
    }

    /**
     * Find a list of persons covered by the station number sought and a count of adults and children concerned
     *
     * @param stationNumber of firestation for which persons are sought
     * @return a list of persons covered by the station number and a count of adults and children concerned
     */
    @Override
    public PersonInfoByFirestationDto findPersonsInfoByStationNumber(String stationNumber) {
        logger.info("Get persons info by station number : {}", stationNumber);
        List<Person> personsByStationNumber = personDao.findByStationNumber(stationNumber);
        if (personsByStationNumber != null) {
            List<PersonDto> personDtoList = personsByStationNumber
                    .stream()
                    .map(PersonMapper::convertToPersonDto)
                    .collect(Collectors.toList());
            PersonAdultChildListDto lists = findChildrenListAndAdultList(personsByStationNumber);
            return PersonInfoByFirestationDto.builder()
                    .personDto(personDtoList)
                    .numberOfChildren(lists.getListOfChild().size())
                    .numberOfAdults(lists.getListOfAdult().size())
                    .build();
        }
        logger.error("Get a list of persons and a count of adults and children covered by firestation found by a station number error because station number : {}" + " is not found", stationNumber);
        throw new FirestationNotFoundException("Trying to get non existing firestation for given station number");
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
        List<Person> personList = personDao.findByAddress(address);
        if (personList != null) {
            PersonAdultChildListDto adultAndChildList = findChildrenListAndAdultList(personList);
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

    /**
     * Find a list of phone numbers of persons covered by the firestation number sought
     *
     * @param firestationNumber for which the phone numbers are sought
     * @return a list with phone numbers of all persons covered by the firestation number sought
     */
    @Override
    public List<String> findListOfPhoneNumbersByFirestationNumber(String firestationNumber) {
        logger.info("Get a list of phone numbers covered by the firestation number : {}", firestationNumber);
        List<Person> personsList = personDao.findByStationNumber(firestationNumber);
        if (personsList != null) {
            return personsList
                    .stream().map(Person::getPhone).distinct().collect(Collectors.toList());
        } else {
            logger.error("Get a list of phone numbers covered by the firestation number : {}" + " is not found", firestationNumber);
            throw new FirestationNotFoundException("Trying to get a phone number list for an unknown firestation number");
        }
    }

    /**
     * Find a list of person with name, phone, age, medications and allergies, and firestation number corresponding to a given address
     *
     * @param address for which person list is sought
     * @return a list of person with name, phone, age, medications and allergies, and firestation number
     */
    @Override
    public FireDto findListOfFirePersonByAddress(String address) {
        logger.info("Get list of fire person by address : {}", address);
        List<Person> personList = personDao.findByAddress(address);
        if (personList != null) {
            List<PersonInfoFireDto> personInfoFireDtoList = personList
                    .stream()
                    .map(person -> {
                        MedicalRecord medicalRecord = medicalRecordDao.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                        person.setMedications(medicalRecord.getMedications());
                        person.setAllergies(medicalRecord.getAllergies());
                        person.setBirthdate(medicalRecord.getBirthdate());
                        person.calculateAge(medicalRecord.getBirthdate());
                        return PersonMapper.convertToPersonInfoFireDto(person);
                    })
                    .collect(Collectors.toList());
            Firestation firestation = firestationDao.findByAddress(address);
            return FireDto.builder()
                    .personInfoFireDtoList(personInfoFireDtoList)
                    .firestationNumber(firestation.getStationNumber())
                    .build();
        }
        logger.error("Get a list of fire person by address error because the address :  {}  is not found", address);
        throw new PersonNotFoundException("Get a list of fire person by address error because the address : " + address + " is not found");
    }

    /**
     * Find a list of all persons served by the station number, sorted by address
     *
     * @param stationNumber for which person list is sought
     * @return a list of persons with name, phone, age, medications and allergies, sorted by address
     */
    @Override
    public List<FloodDto> findListOfFloodPersonByStationNumber(String stationNumber) {
        logger.info("Get list of fire person by station number : {}", stationNumber);
        List<Firestation> firestationList = firestationDao.findByStationNumber(stationNumber);
        if (firestationList != null) {
            List<FloodDto> floodDtoList = new ArrayList<>();
            FloodDto floodDto = null;
            for (Firestation firestation : firestationList) {
                List<PersonInfoFireDto> personListByAddress = new ArrayList<>();
                List<PersonInfoFireDto> personInfoFireDtoList = findListOfFirePersonByAddress(firestation.getAddress()).getPersonInfoFireDtoList();
                for (PersonInfoFireDto personInfoFireDto : personInfoFireDtoList) {
                    personListByAddress.add(personInfoFireDto);
                    floodDto = FloodDto.builder()
                            .address(firestation.getAddress())
                            .personList(personListByAddress)
                            .build();
                }
                floodDtoList.add(floodDto);
            }
            return floodDtoList;
        }
        logger.error("Get a list of flood person by station number error because station number : {}" + " is not found", stationNumber);
        throw new FirestationNotFoundException("Trying to get non existing station number for given station number");
    }

    /**
     * Find a person info or a list of person info if several persons have the same name
     *
     * @param firstName of person info sought
     * @param lastName  of person info sought
     * @return person info with name, address, age, email medications and allergies
     */
    @Override
    public List<PersonInfoDto> findPersonInfoByFirstnameAndLastname(String firstName, String lastName) {
        logger.info("Get person info by firstname : {} and lastname : {}", firstName, lastName);
        List<Person> persons = personDao.findPersons();
        List<Person> personList = new ArrayList<>();
        for (Person person1 : persons) {
            if (person1.getFirstName().equalsIgnoreCase(firstName) && person1.getLastName().equalsIgnoreCase(lastName)) {
                personList.add(person1);
            }
        }
        if (!personList.isEmpty()) {
            return personList
                    .stream()
                    .map(person -> {
                        MedicalRecord medicalRecord;
                        if (personList.size() == 1) {
                            medicalRecord = medicalRecordDao.findByFirstNameAndLastName(firstName, lastName);
                        } else {
                            medicalRecord = medicalRecordDao.findByUniqueID(person.getUniqueID());
                        }
                        person.setMedications(medicalRecord.getMedications());
                        person.setAllergies(medicalRecord.getAllergies());
                        person.setBirthdate(medicalRecord.getBirthdate());
                        person.calculateAge(medicalRecord.getBirthdate());
                        return PersonMapper.convertToPersonInfoDto(person);
                    })
                    .collect(Collectors.toList());
        }
        logger.error("Get person info by firstname and lastname error because {}" + " " + "{} is not found", firstName, lastName);
        throw new PersonNotFoundException("Get person info by firstname and lastname error because " + firstName + " " + lastName + " is not found");
    }

    /**
     * Find a list of persons living in the city sought
     *
     * @param city for which persons are sought
     * @return a list of persons living at the city sought
     */
    @Override
    public List<String> findEmailByCity(String city) {
        logger.info("Get list of email of all persons living in : {} ", city);
        List<Person> personList = personDao.findByCity(city);
        if (personList != null) {
            return personList
                    .stream().map(Person::getEmail).collect(Collectors.toList());
        } else {
            logger.error("Get a list of email for city : {}" + " is not found", city);
            throw new PersonNotFoundException("Trying to get a email list for an unknown city");
        }
    }

}
