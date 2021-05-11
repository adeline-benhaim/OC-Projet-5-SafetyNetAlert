package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.dao.FirestationDao;
import com.safetynet.alerts.api.exceptions.FirestationAlreadyExistException;
import com.safetynet.alerts.api.exceptions.FirestationNotFoundException;
import com.safetynet.alerts.api.mapper.PersonMapper;
import com.safetynet.alerts.api.model.Firestation;
import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.model.dto.CountPersonAdultChildDto;
import com.safetynet.alerts.api.model.dto.ListPersonAdultChildDto;
import com.safetynet.alerts.api.model.dto.PersonDto;
import com.safetynet.alerts.api.model.dto.PersonInfoByFirestationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirestationServiceImpl implements FirestationService {
    private static final Logger logger = LoggerFactory.getLogger(FirestationServiceImpl.class);

    @Autowired
    FirestationDao firestationDao;
    @Autowired
    PersonMapper personMapper;
    @Autowired
    PersonServiceImpl personService;


    /**
     * Find all firestations
     *
     * @return list with all firestations
     */
    @Override
    public List<Firestation> getAllFirestations() {
        logger.info("Get all firestations");
        return firestationDao.findFirestations();
    }

    /**
     * Find firestation number by address
     *
     * @param address for which the firestation number is sought
     * @return the number of firestation associate
     */
    @Override
    public Firestation findFirestationByAddress(String address) {
        logger.info("Get firestation by address : {}", address);
        Firestation firestation = firestationDao.findByAddress(address);
        if (firestation != null) return firestation;
        logger.error("Get firestation by address error because address : {}" + " is not found", address);
        throw new FirestationNotFoundException("Get firestation by address error because address " + address + " is not found");
    }

    /**
     * Update a firestation
     *
     * @param firestation number need to update by address
     * @return firestation updated
     */
    @Override
    public Firestation updateFirestation(Firestation firestation) {
        logger.info("Update number of firestation  with address : {}" + " ", firestation.getAddress());
        Firestation currentFirestation = firestationDao.findByAddress(firestation.getAddress());
        if (currentFirestation == null) {
            logger.error("Trying to update non existing firestation for given address");
            throw new FirestationNotFoundException("Trying to update non existing firestation for given address");
        } else {
            firestationDao.save(firestation);
        }
        return firestation;
    }


    /**
     * Create firestation
     *
     * @param firestation to create
     * @return firestation created
     */
    @Override
    public Firestation createNewFirestation(Firestation firestation) {
        logger.info("Create new mapping firestation/address : {}" + " " + "{} ", firestation.getStationNumber(), firestation.getAddress());
        Firestation newFirestation = firestationDao.findByAddress(firestation.getAddress());
        if (newFirestation == null) return firestationDao.save(firestation);
        logger.error("Create new mapping error because address : {} already exist", firestation.getAddress());
        throw new FirestationAlreadyExistException("Create new mapping error because address " + firestation.getAddress() + " already exist");
    }

    /**
     * Delete firestation by address
     *
     * @param address of firestation to delete
     */
    @Override
    public void deleteFirestation(String address) {
        logger.info("Delete firestation address: {} ", address);
        firestationDao.delete(address);
    }

    /**
     * Find a list of persons covered by the station number sought and a count of adults and children concerned
     *
     * @param stationNumber of firestation for which persons are sought
     * @return a list of persons covered by the station number and a count of adults and children concerned
     */
    @Override
    public PersonInfoByFirestationDto findPersonsByStationNumber(String stationNumber) {
        logger.info("Get persons info by station number : {}", stationNumber);
        List<Person> personsByStationNumber = personService.findGlobalListOfPersonsByStationNumber(stationNumber);
        if (!personsByStationNumber.isEmpty()) {
            List<PersonDto> personDtoList = personsByStationNumber
                    .stream()
                    .map(person1 -> personMapper.convertToPersonDto(person1))
                    .collect(Collectors.toList());
            ListPersonAdultChildDto lists = personService.findChildrenListAndAdultList(personsByStationNumber);
            CountPersonAdultChildDto listSize = CountPersonAdultChildDto.builder()
                    .numberOfChildren(lists.listOfChild.size())
                    .numberOfAdults(lists.listOfAdult.size())
                    .build();
            return PersonInfoByFirestationDto.builder()
                    .personDto(personDtoList)
                    .countPersonAdultChildDto(listSize)
                    .build();
        }
        logger.error("Get a list of persons and a count of adults and children covered by firestation found by a station number error because station number : {}" + " is not found", stationNumber);
        throw new FirestationNotFoundException("Trying to get non existing firestation for given station number");
    }

}