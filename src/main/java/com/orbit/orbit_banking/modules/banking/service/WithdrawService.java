package com.orbit.orbit_banking.modules.banking.service;

import com.orbit.orbit_banking.modules.banking.dto.TransactionResponse;
import com.orbit.orbit_banking.modules.banking.dto.WithdrawDto;
import com.orbit.orbit_banking.modules.banking.exceptions.InsufficientBalanceException;

public interface WithdrawService {

    /**
     *
     * @param withdrawDto
     * @return
     * @throws InsufficientBalanceException
     */
    TransactionResponse withdraw(WithdrawDto withdrawDto) throws InsufficientBalanceException;
}


