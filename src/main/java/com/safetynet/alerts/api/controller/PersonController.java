package com.safetynet.alerts.api.controller;

import com.safetynet.alerts.api.exceptions.FirestationNotFoundException;
import com.safetynet.alerts.api.exceptions.PersonAlreadyExistException;
import com.safetynet.alerts.api.exceptions.PersonNotFoundException;
import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.model.dto.ChildAlertDto;
import com.safetynet.alerts.api.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("API for CRUD operations for persons")
@RestController
public class PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);


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
        logger.info("REST : Get all persons");
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
        logger.info("REST : Get person by firstname and lastname : {}" + " " + "{}", firstName, lastName);
        try {
            Person person = personService.findPersonByFirstNameAndLastName(firstName, lastName);
            return ResponseEntity.ok(person);
        } catch (PersonNotFoundException e) {
            logger.error("REST : Get person by firstname and lastname error because {}" + " " + "{} is not found", firstName, lastName);
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
        logger.info("REST : Create new person : {}" + " " + "{} ", person.getFirstName(), person.getLastName());
        try {
            Person newPerson = personService.createNewPerson(person);
            return ResponseEntity.ok(newPerson);
        } catch (PersonAlreadyExistException e) {
            logger.error("REST : Create person error because person : {} {} already exist", person.getFirstName(), person.getLastName());
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
        logger.info("REST : Update person : {}" + " " + "{} ", person.getFirstName(), person.getLastName());
        try {
            Person personToUpdate = personService.updatePerson(person);
            return ResponseEntity.ok(personToUpdate);
        } catch (PersonNotFoundException e) {
            logger.error("REST : Trying to update non existing person");
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
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
        logger.info("REST : Delete person : {}" + " " + "{} ", firstName, lastName);
        personService.deletePerson(firstName, lastName);
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
            List<ChildAlertDto> childAlertDtoList = personService.findChildrenByAddress(address);
            return ResponseEntity.ok(childAlertDtoList);
        }catch (PersonNotFoundException e) {
            logger.error("REST : Get a list of children and a list of others household members found by address error because address : {}" + " is not found", address);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
