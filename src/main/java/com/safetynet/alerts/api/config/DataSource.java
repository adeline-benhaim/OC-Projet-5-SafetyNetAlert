package com.safetynet.alerts.api.config;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.safetynet.alerts.api.model.Firestation;
import com.safetynet.alerts.api.model.MedicalRecord;
import com.safetynet.alerts.api.model.Person;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Service
public class DataSource {

    public List<Person> persons = new ArrayList<>();

    public List<Person> getAllPersons() {
        return persons;
    }

    public List<Firestation> firestations = new ArrayList<>();

    public List<Firestation> getAllFirestation() {
        List firestationsWithoutDuplicate = firestations.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(Firestation::getAddress))), ArrayList::new));
        return new ArrayList<>(firestationsWithoutDuplicate);
    }

    public List<MedicalRecord> medicalRecords = new ArrayList<>();

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecords;
    }


    @PostConstruct
    public void init() throws IOException {
        String filePath = "src/main/resources/data.json";
        byte[] bytesFile = Files.readAllBytes(new File(filePath).toPath());
        JsonIterator iter = JsonIterator.parse(bytesFile);
        Any any = iter.readAny();

        Any personAny = any.get("persons");
        personAny.forEach(a -> persons.add(Person.builder()
                .firstName(a.get("firstName").toString())
                .address(a.get("address").toString())
                .city(a.get("city").toString())
                .lastName(a.get("lastName").toString())
                .phone(a.get("phone").toString())
                .zip(a.get("zip").toString())
                .email(a.get("email").toString())
                .build()));

        Any firestationAny = any.get("firestations");
        firestationAny.forEach(b -> firestations.add(Firestation.builder()
                .address(b.get("address").toString())
                .stationNumber(b.get("station").toString())
                .build()));

        Any medicalrecordsAny = any.get("medicalrecords");
        medicalrecordsAny.forEach(m -> medicalRecords.add(MedicalRecord.builder()
                .firstName(m.get("firstName").toString())
                .lastName(m.get("lastName").toString())
                .birthdate(m.get("birthdate").toString())
                .medications(m.get("medications").asList().toString())
                .allergies(m.get("allergies").asList().toString())
                .build()));

    }

}
