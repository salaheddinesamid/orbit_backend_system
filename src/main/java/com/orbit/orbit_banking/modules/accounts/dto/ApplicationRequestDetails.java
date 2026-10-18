package com.orbit.orbit_banking.modules.accounts.dto;

import com.orbit.orbit_banking.modules.accounts.model.ApplicationRequest;
import com.orbit.orbit_banking.modules.customer.dto.CustomerDto;
import com.orbit.orbit_banking.modules.customer.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRequestDetails {
    ApplicationRequest applicationDetails;
    IdDocumentDetails idDocumentDetails;

}

