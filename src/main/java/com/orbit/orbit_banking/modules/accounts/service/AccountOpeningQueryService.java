package com.orbit.orbit_banking.modules.accounts.service;

import com.orbit.orbit_banking.modules.accounts.dto.ApplicationRequestDetails;
import com.orbit.orbit_banking.modules.accounts.model.ApplicationRequest;
import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import com.orbit.orbit_banking.modules.banking.exceptions.BankAccountNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountOpeningQueryService {
    /**
     *
     * @param page
     * @param size
     * @return
     */
    Page<ApplicationRequestDetails> getAllAccountOpenings(int page, int size);
}
