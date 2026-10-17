package com.orbit.orbit_banking.modules.customer.mapper;

import com.orbit.orbit_banking.modules.customer.dto.CustomerDto;
import com.orbit.orbit_banking.modules.customer.model.Customer;
import com.orbit.orbit_banking.modules.customer.model.CustomerStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomerMapper {

    public Customer map(CustomerDto dto){
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
        customer.setCity(dto.getCity());
        customer.setCountry(dto.getCountry());
        customer.setZip(dto.getZip());
        customer.setState(dto.getState());

        customer.setNationalID(dto.getNationalID());
        customer.setCreatedAt(LocalDateTime.now());
        customer.setLastUpdate(LocalDateTime.now());

        customer.setStatus(CustomerStatus.NEW);

        return customer;
    }
}
