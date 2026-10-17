package com.orbit.orbit_banking.modules.customer.service.implementation;

import com.orbit.orbit_banking.modules.customer.dto.CustomerDto;
import com.orbit.orbit_banking.modules.customer.exceptions.CustomerAlreadyExistsException;
import com.orbit.orbit_banking.modules.customer.mapper.CustomerMapper;
import com.orbit.orbit_banking.modules.customer.model.Customer;
import com.orbit.orbit_banking.modules.customer.repository.CustomerRepository;
import com.orbit.orbit_banking.modules.customer.service.CustomerAdderService;
import org.springframework.stereotype.Service;

@Service
public class CustomerAdderServiceImpl implements CustomerAdderService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerAdderServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Customer addCustomer(CustomerDto dto) throws CustomerAlreadyExistsException {
        // Check if the customer already exists
        if(customerRepository.existsByNationalID(
                dto.getNationalID()
        )){
            throw new CustomerAlreadyExistsException("Customer already exists");
        }

        // Create new customer
        Customer newCustomer = customerMapper.map(dto);

        // Save the customer
        return customerRepository.save(newCustomer);
    }
}
