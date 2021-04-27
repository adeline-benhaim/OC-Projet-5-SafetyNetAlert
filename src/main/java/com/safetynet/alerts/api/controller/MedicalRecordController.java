package com.safetynet.alerts.api.controller;

import com.safetynet.alerts.api.exceptions.MedicalRecordAlreadyExist;
import com.safetynet.alerts.api.exceptions.MedicalRecordNotFoundException;
import com.safetynet.alerts.api.model.MedicalRecord;
import com.safetynet.alerts.api.service.MedicalRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("API for CRUD operations for medical records")
@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * Get all medical records with all person's information (firstname, lastname, birthdate, medications, allergies)
     *
     * @return a list with all medical records
     */
    @ApiOperation("Get the list of all medical records")
    @GetMapping("/medicalRecord")
    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }

    /**
     * Get a medical record  by firstname and lastname of the person concerned
     *
     * @param firstName of the person concerned
     * @param lastName  of the person concerned
     * @return the medical record of the person concerned with all information (birthdate, medications, allergies)
     */
    @ApiOperation("Get a medical record for a person found by firstname and lastname")
    @GetMapping("/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<MedicalRecord> getMedicalRecord(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        try {
            MedicalRecord medicalRecord = medicalRecordService.findMedicalRecordByFirstNameAndLastName(firstName, lastName);
            return ResponseEntity.ok(medicalRecord);
        } catch (MedicalRecordNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Post a new medical record for a new person
     *
     * @param medicalRecord information for of the person concerned
     * @return information of the medical record created (firstname, lastname, birthdate, medications, allergies)
     */
    @ApiOperation("Create a new medical record if the first and last name of the person concerned is not already existing")
    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        try {
            MedicalRecord newMedicalRecord = medicalRecordService.createNewMedicalRecord(medicalRecord);
            return ResponseEntity.ok(newMedicalRecord);
        } catch (MedicalRecordAlreadyExist e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Put the information of a medical record existing
     *
     * @param medicalRecord to update
     * @return medical record with information's updated
     */
    @ApiOperation("Update a medical record if the first and last name of the person concerned is already existing")
    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        try {
            MedicalRecord medicalRecordToUpdate = medicalRecordService.updateMedicalRecord(medicalRecord);
            return ResponseEntity.ok(medicalRecordToUpdate);
        } catch (MedicalRecordNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation("Delete medical record of a person found by first and lastname")
    @DeleteMapping("/medicalRecord/{firstName}/{lastName}")
    public void deleteMedicalRecord(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }
}
