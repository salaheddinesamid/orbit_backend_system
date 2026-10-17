package com.orbit.orbit_banking.modules.accounts.exceptions;

public class AccountOpeningRequestAlreadyExistsException extends RuntimeException {

    public AccountOpeningRequestAlreadyExistsException(String message) {
        super(message);
    }
}
