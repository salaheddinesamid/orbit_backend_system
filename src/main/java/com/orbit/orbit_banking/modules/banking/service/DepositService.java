package com.orbit.orbit_banking.modules.banking.service;

import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import com.orbit.orbit_banking.modules.banking.dto.DepositDto;
import com.orbit.orbit_banking.modules.banking.dto.TransactionResponse;

public interface DepositService {
    /**
     *
     * @param depositDto
     * @param issuer
     * @return
     */
    TransactionResponse deposit(DepositDto depositDto, UserAccount issuer);
}
