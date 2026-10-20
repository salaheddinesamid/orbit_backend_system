package com.orbit.orbit_banking.modules.authentication.exceptions;

public class UserAccountAlreadyExistsException extends RuntimeException {
    public UserAccountAlreadyExistsException(String message) {
        super(message);
    }
}
