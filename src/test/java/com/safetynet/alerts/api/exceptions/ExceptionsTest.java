package com.safetynet.alerts.api.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExceptionsTest {

    @Test
    @DisplayName("Firestation not found exception contain string not found ")
    void firestationNotFoundExceptionTest() {
        Exception exception = assertThrows(
                FirestationNotFoundException.class,
                this::findByStationNumber);

        assertTrue(exception.getMessage().contains("not found"));
    }

    void findByStationNumber() throws FirestationNotFoundException {
        throw new FirestationNotFoundException("address" + " not found!");
    }

    @Test
    @DisplayName("Firestation already exist exception contain string already exist ")
    void firestationAlreadyExistTest() {
        Exception exception = assertThrows(
                FirestationAlreadyExistException.class,
                this::createNewFirestation);

        assertTrue(exception.getMessage().contains("already exist"));
    }

    void createNewFirestation() throws FirestationNotFoundException {
        throw new FirestationAlreadyExistException("firestation" + " already exist");
    }

    @Test
    @DisplayName("Person not found exception contain string not found ")
    void personNotFoundExceptionTest() {
        Exception exception = assertThrows(
                PersonNotFoundException.class,
                this::findByFirstNameAndLastName);

        assertTrue(exception.getMessage().contains("not found"));
    }

    void findByFirstNameAndLastName() throws PersonNotFoundException {
        throw new PersonNotFoundException("first name" + "last name" + " not found!");
    }

    @Test
    @DisplayName("Person already exist exception contain string already exist ")
    void personAlreadyExistExceptionTest() {
        Exception exception = assertThrows(
                PersonAlreadyExistException.class,
                this::createNewPerson);

        assertTrue(exception.getMessage().contains("already exist"));
    }

    void createNewPerson() throws PersonAlreadyExistException {
        throw new PersonAlreadyExistException("first name" + "last name" + " already exist!");
    }

    @Test
    @DisplayName("Medical record not found exception contain string not found ")
    void medicalRecordNotFoundExceptionTest() {
        Exception exception = assertThrows(
                MedicalRecordNotFoundException.class,
                this::findMedicalRecordByFirstNameAndLastName);

        assertTrue(exception.getMessage().contains("not found"));
    }

    void findMedicalRecordByFirstNameAndLastName() throws MedicalRecordNotFoundException {
        throw new MedicalRecordNotFoundException("first name" + "last name" + " not found!");
    }

    @Test
    @DisplayName("Medical record already exist exception contain string already exist ")
    void medicalRecordAlreadyExistExceptionTest() {
        Exception exception = assertThrows(
                MedicalRecordAlreadyExistException.class,
                this::createNewMedicalRecord);

        assertTrue(exception.getMessage().contains("already exist"));
    }

    void createNewMedicalRecord() throws MedicalRecordAlreadyExistException {
        throw new MedicalRecordAlreadyExistException("first name" + "last name" + " already exist!");
    }
}

