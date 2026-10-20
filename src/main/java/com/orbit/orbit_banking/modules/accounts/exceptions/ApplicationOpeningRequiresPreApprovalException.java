package com.orbit.orbit_banking.modules.accounts.exceptions;

public class ApplicationOpeningRequiresPreApprovalException extends RuntimeException{

    public ApplicationOpeningRequiresPreApprovalException(String message) {
        super(message);
    }
}
