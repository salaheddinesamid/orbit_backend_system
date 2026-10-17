package com.orbit.orbit_banking.modules.accounts.dto;

import com.orbit.orbit_banking.modules.customer.dto.CustomerDto;
import com.orbit.orbit_banking.modules.customer.model.Customer;
import lombok.Data;
import lombok.Getter;

@Data
public class NewAccountRequestDto {
    private CustomerDto customerDetails;
    private String fundingSource;
}

