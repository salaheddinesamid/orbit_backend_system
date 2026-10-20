package com.orbit.orbit_banking.modules.accounts.exceptions;

public class AccountNumberAlreadyExistsException extends RuntimeException {

    public AccountNumberAlreadyExistsException(String message) {
        super(message);
    }
}
