package com.orbit.orbit_banking.modules.banking.service;

import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import com.orbit.orbit_banking.modules.banking.dto.DepositDto;
import com.orbit.orbit_banking.modules.banking.model.Deposit;
import com.orbit.orbit_banking.modules.banking.model.Transaction;

public interface DepositService {
    /**
     *
     * @param depositDto
     * @param issuer
     * @return
     */
    Deposit deposit(DepositDto depositDto, UserAccount issuer);
}
