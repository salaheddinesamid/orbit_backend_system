package com.orbit.orbit_banking.modules.customer.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String nationalID;
}
