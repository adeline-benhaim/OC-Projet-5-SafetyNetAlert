package com.safetynet.alerts.api.controller;

import com.safetynet.alerts.api.exceptions.FirestationAlreadyExistException;
import com.safetynet.alerts.api.exceptions.FirestationNotFoundException;
import com.safetynet.alerts.api.model.Firestation;
import com.safetynet.alerts.api.service.FirestationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("API for CRUD operations for firestations")
@RestController
public class FirestationController {

    @Autowired
    FirestationService firestationService;

    @ApiOperation("Get the list of all firestations")
    @GetMapping("/firestation")
    public List<Firestation> getFirestations () {
        return firestationService.getAllFirestations();
    }


    @ApiOperation("Get a firestation find by address")
    @GetMapping("firestation/{address}")
    public ResponseEntity<Firestation> getFirestationsByAddress (@PathVariable("address") String address) {
        try{
            Firestation firestation = firestationService.findFirestationByAddress(address);
            return ResponseEntity.ok(firestation);
        } catch (FirestationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("Create a new firestation if the address is not already existing")
    @PostMapping("/firestation")
    public ResponseEntity<Firestation> createFirestation(@RequestBody Firestation firestation) {
        try {
            Firestation newFirestation = firestationService.createNewFirestation(firestation);
            return ResponseEntity.ok(newFirestation);
        } catch (FirestationAlreadyExistException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation("Update a firestation if the address is already existing")
    @PutMapping("/firestation")
    public ResponseEntity<Firestation> updateFirestationByAddress(@RequestBody Firestation firestation) {
        try {
            Firestation firestationUpdated = firestationService.updateFirestation(firestation);
            return ResponseEntity.ok(firestationUpdated);
        } catch (FirestationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("Delete firestation by address")
    @DeleteMapping("/firestation/address/{address}")
    public void deleteFirestationByAddress(@PathVariable("address") String address) {
        firestationService.deleteFirestationByAddress(address);
    }

}
