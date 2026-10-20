package com.orbit.orbit_banking.modules.accounts.utils;

import org.springframework.stereotype.Component;

@Component
public class RibGenerator {

    private final static String BANK_CODE = "0091";
    private final static String RIB_KEY = "32";

    public String generateRib(
            String accountNumber
    ) {
        return String.format("%s%s%s", BANK_CODE, accountNumber, RIB_KEY);
    }
}
