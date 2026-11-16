package com.orbit.orbit_banking.modules.accounts.service;

import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import com.orbit.orbit_banking.modules.banking.exceptions.BankAccountNotFoundException;

public interface AccountQueryService {

    /**
     *
     * @param nationalId
     * @return
     */
    BankAccount getAccount(String nationalId) throws BankAccountNotFoundException;
}
