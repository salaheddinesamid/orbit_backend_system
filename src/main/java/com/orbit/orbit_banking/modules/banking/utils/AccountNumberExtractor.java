package com.orbit.orbit_banking.modules.banking.utils;

import org.springframework.stereotype.Component;

@Component
public class AccountNumberExtractor {

    public String extractAccountNumber(String RIB) {
        return RIB.substring(6, RIB.length() - 2);
    }

    public String extractBankCode(String RIB) {
        return
                RIB.substring(0, 3);
    }
}
