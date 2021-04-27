package com.safetynet.alerts.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Person {
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String zip;
    public String phone;
    public String email;

//    public Person() {
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }

    public Person(String firstName, String lastName, String phone, String zip, String address, String city, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.zip = zip;
        this.address = address;
        this.city = city;
        this.email = email;
    }

}

