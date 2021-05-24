package com.safetynet.alerts.api.controller;

import com.safetynet.alerts.api.exceptions.FirestationNotFoundException;
import com.safetynet.alerts.api.exceptions.PersonNotFoundException;
import com.safetynet.alerts.api.model.dto.*;
import com.safetynet.alerts.api.service.InformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "API for information operations")
@RestController
public class InformationController {
    private static final Logger logger = LoggerFactory.getLogger(InformationController.class);

    @Autowired
    private InformationService informationService;

    @ApiOperation("Get a list of person and a count of adults and children covered by firestation found by a station number")
    @GetMapping("/firestation")
    public ResponseEntity<PersonInfoByFirestationDto> getFirestationsByStationNumber (@RequestParam("stationNumber") String stationNumber) {
        logger.info("REST : Get a list of persons and a count of adults and children covered by firestation found by a station number");
        try {
            PersonInfoByFirestationDto personsFoundByStationNumber = informationService.findPersonsInfoByStationNumber(stationNumber);
            return ResponseEntity.ok(personsFoundByStationNumber);
        }catch (FirestationNotFoundException e) {
            logger.error("REST : Get a list of persons and a count of adults and children covered by firestation found by a station number error because station number : {}" + " is not found", stationNumber);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get a list of children living at the address sought with a list of other house hold members
     *
     * @param address for which child is sought
     * @return a list of children (firstname, lastname, age) living at the address sought with a list of other house hold members
     */
    @ApiOperation("Get a list of children and a list of others household members found by address")
    @GetMapping("/childAlert")
    public ResponseEntity<List<ChildAlertDto>> getChildrenByAddress (@RequestParam("address") String address) {
        logger.info("REST : Get a list of children and a list of others household members found by address");
        try {
            List<ChildAlertDto> childAlertDtoList = informationService.findChildrenByAddress(address);
            return ResponseEntity.ok(childAlertDtoList);
        }catch (PersonNotFoundException e) {
            logger.error("REST : Get a list of children and a list of others household members found by address error because address : {}" + " is not found", address);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get a list of phone numbers of persons covered by the firestation number sought
     * @param firestationNumber for which the phone numbers are sought
     * @return a list with phone numbers of all persons covered by the firestation number sought
     */
    @ApiOperation("Get a list of phone numbers of persons covered by the firestation number sought")
    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneNumbers (@RequestParam("firestation") String firestationNumber) {
        logger.info("REST : Get a list of phone numbers covered by the firestation number");
        try {
            List<String> phoneAlertDtoList = informationService.findListOfPhoneNumbersByFirestationNumber(firestationNumber);
            return ResponseEntity.ok(phoneAlertDtoList);
        }catch (FirestationNotFoundException e) {
            logger.error("REST : Get a list of phone numbers covered by the firestation number error because firestation number : {}" + " is not found", firestationNumber);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get a list of person with name, phone, age, medications and allergies, and firestation number corresponding to a given address
     *
     * @param address for which person list is sought
     * @return a list of person with name, phone, age, medications and allergies, and firestation number
     */
    @ApiOperation("Get a list of person with name, phone, age, medications and allergies, and firestation number corresponding to a given address")
    @GetMapping("/fire")
    public ResponseEntity<FireDto> getListOfFirePersonByAddress (@RequestParam("address") String address) {
        logger.info("REST : Get a list of fire person by address");
        try {
            FireDto fireDto = informationService.findListOfFirePersonByAddress(address);
            return ResponseEntity.ok(fireDto);
        }catch (PersonNotFoundException e) {
            logger.error("REST : Get a list of fire person covered find by address error because address : {}" + " is not found", address);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get a list of all persons served by the station number, sorted by address
     * @param stationNumber for which person list is sought
     * @return a list of persons with name, phone, age, medications and allergies, sorted by address
     */
    @ApiOperation("Get a list of person with name, phone, age, medications and allergies, and firestation number corresponding to a given address")
    @GetMapping("/flood/stations")
    public ResponseEntity<List<FloodDto>> getListOfFloodPersonByStationNumber (@RequestParam("stations")String stationNumber) {
        logger.info("REST : Get list of flood person by station number");
        try {
            List<FloodDto> floodDto = informationService.findListOfFloodPersonByStationNumber(stationNumber);
            return ResponseEntity.ok(floodDto);
        }catch (FirestationNotFoundException e) {
            logger.error("REST : Get a list of flood person covered find by address error because station number : {}" + " is not found", stationNumber);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get a person info or a list of person info if several persons have the same name
     * @param firstName of person info sought
     * @param lastName of person info sought
     * @return person info with name, address, age, email medications and allergies
     */
    @ApiOperation("Get a person info with name, address, age, email, medications and allergies, found by firstname and lastname ")
    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonInfoDto>> getPersonInfoByFirstnameAndLastname (@RequestParam("firstName")String firstName, @RequestParam("lastName") String lastName) {
        logger.info("REST : Get list of person info by firstname and lastname");
        try {
            List<PersonInfoDto> personInfoDtos = informationService.findPersonInfoByFirstnameAndLastname(firstName,lastName);
            return ResponseEntity.ok(personInfoDtos);
        }catch (PersonNotFoundException e) {
            logger.error("REST : Get person info by firstname and lastname error because {} {} is not found", firstName, lastName);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("Get list of email of all persons living in the city sought")
    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getEmailByCity (@RequestParam("city")String city) {
        logger.info("REST : Get list of email of all persons living in city sought");
        try {
            List<String> email = informationService.findEmailByCity(city);
            return ResponseEntity.ok(email);
        }catch (PersonNotFoundException e) {
            logger.error("REST : Get a list of email for city : {}" + " is not found", city);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
