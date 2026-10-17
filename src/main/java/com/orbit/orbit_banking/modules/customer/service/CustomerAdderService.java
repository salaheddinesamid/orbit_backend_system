package com.orbit.orbit_banking.modules.customer.service;

import com.orbit.orbit_banking.modules.customer.dto.CustomerDto;
import com.orbit.orbit_banking.modules.customer.exceptions.CustomerAlreadyExistsException;
import com.orbit.orbit_banking.modules.customer.model.Customer;

public interface CustomerAdderService {

    /**
     *
     * @param dto
     * @return
     * @throws CustomerAlreadyExistsException
     */
    Customer addCustomer(CustomerDto dto) throws CustomerAlreadyExistsException;
}
