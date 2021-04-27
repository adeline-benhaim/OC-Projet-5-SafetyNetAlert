package com.safetynet.alerts.api.controller;

import com.safetynet.alerts.api.exceptions.PersonAlreadyExistException;
import com.safetynet.alerts.api.exceptions.PersonNotFoundException;
import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("API for CRUD operations for persons")
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * Get all persons
     *
     * @return list with all persons
     */
    @ApiOperation("Get the list of all persons")
    @GetMapping("/person")
    public List<Person> getPersons() {
        return personService.getAllPersons();
    }

    /**
     * Get person by firstname and lastname
     *
     * @param firstName of the wanted person
     * @param lastName  of the wanted person
     * @return the information of the person sought
     */
    @ApiOperation("Get a person find by firstname and lastname")
    @GetMapping("/person/{firstName}/{lastName}")
    public ResponseEntity<Person> getPerson(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        try {
            Person person = personService.findPersonByFirstNameAndLastName(firstName, lastName);
            return ResponseEntity.ok(person);
        } catch (PersonNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Post a new person
     *
     * @param person information
     * @return person information
     */
    @ApiOperation("Create a new person if the first and last name is not already existing")
    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        try {
            Person newPerson = personService.createNewPerson(person);
            return ResponseEntity.ok(newPerson);
        } catch (PersonAlreadyExistException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update person information
     *
     * @param person to update
     * @return person updated
     */
    @ApiOperation("Update a person if the first and last name is already existing")
    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        try {
            Person personToUpdate = personService.updatePerson(person);
            return ResponseEntity.ok(personToUpdate);
        } catch (PersonNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Delete a person find by firstname and lastname
     *
     * @param firstName of the person to delete
     * @param lastName  of the person to delete
     */
    @ApiOperation("Delete person find by first and lastname")
    @DeleteMapping("/person/{firstName}/{lastName}")
    public void deletePerson(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        personService.deletePerson(firstName, lastName);
    }
}
