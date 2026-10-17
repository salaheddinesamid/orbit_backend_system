package com.orbit.orbit_banking.modules.accounts.service;

import com.orbit.orbit_banking.modules.accounts.dto.NewAccountRequestDto;
import com.orbit.orbit_banking.modules.accounts.exceptions.AccountOpeningRequestAlreadyExistsException;
import com.orbit.orbit_banking.modules.accounts.model.ApplicationRequest;

public interface AccountOpening {
    /**
     *
     * @param type
     * @return
     */
    boolean supports(String type);

    /**
     *
     * @param applicationRequest
     * @return
     * @throws AccountOpeningRequestAlreadyExistsException
     */
    ApplicationRequest openAccount(NewAccountRequestDto applicationRequest) throws AccountOpeningRequestAlreadyExistsException;
}
