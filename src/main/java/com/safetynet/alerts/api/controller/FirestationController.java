package com.safetynet.alerts.api.controller;

import com.safetynet.alerts.api.exceptions.FirestationAlreadyExistException;
import com.safetynet.alerts.api.exceptions.FirestationNotFoundException;
import com.safetynet.alerts.api.model.Firestation;
import com.safetynet.alerts.api.service.FirestationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("API for CRUD operations for firestations")
@RestController
public class FirestationController {
    private static final Logger logger = LoggerFactory.getLogger(FirestationController.class);

    @Autowired
    private FirestationService firestationService;


    @ApiOperation("Get the list of all firestations")
    @GetMapping("/firestations")
    public List<Firestation> getFirestations() {
        logger.info("REST : Get all firestations");
        return firestationService.getAllFirestations();
    }


    @ApiOperation("Get a firestation find by address")
    @GetMapping("firestation/{address}")
    public ResponseEntity<Firestation> getFirestationsByAddress(@PathVariable("address") String address) {
        logger.info("REST : Get firestation by address : {}", address);
        try {
            Firestation firestation = firestationService.findFirestationByAddress(address);
            return ResponseEntity.ok(firestation);
        } catch (FirestationNotFoundException e) {
            logger.error("REST : Get firestation by address error because address : {}" + " is not found", address);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("Create a new firestation if the address is not already existing")
    @PostMapping("/firestation")
    public ResponseEntity<Firestation> createFirestation(@RequestBody Firestation firestation) {
        logger.info("REST : Create new mapping firestation/address : {}" + " " + "{} ", firestation.getStationNumber(), firestation.getAddress());
        try {
            Firestation newFirestation = firestationService.createNewFirestation(firestation);
            return ResponseEntity.ok(newFirestation);
        } catch (FirestationAlreadyExistException e) {
            logger.error("REST : Create new mapping error because address : {} already exist", firestation.getAddress());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation("Update a firestation if the address is already existing")
    @PutMapping("/firestation")
    public ResponseEntity<Firestation> updateFirestationByAddress(@RequestBody Firestation firestation) {
        logger.info("REST : Update number of firestation  with address : {}" + " ", firestation.getAddress());
        try {
            Firestation firestationUpdated = firestationService.updateFirestation(firestation);
            return ResponseEntity.ok(firestationUpdated);
        } catch (FirestationNotFoundException e) {
            logger.error("REST : Trying to update non existing firestation for given address");
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("Delete firestation by address")
    @DeleteMapping("/firestation/{address}")
    public void deleteFirestationByAddress(@PathVariable("address") String address) {
        logger.info("REST : Delete firestation address: {} ", address);
        firestationService.deleteFirestation(address);
    }
}
