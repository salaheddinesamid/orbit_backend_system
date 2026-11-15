package com.orbit.orbit_banking.utils;

import com.orbit.orbit_banking.modules.banking.utils.AccountNumberExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountNumberExtractorUniTest {

    @InjectMocks
    private AccountNumberExtractor accountNumberExtractor;

    private String randomRIB;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        randomRIB = "986827637637501713464519";
    }
    @Test
    void testExtractAccountNumberSuccess() {
        String currentAccountNumber = accountNumberExtractor
                .extractAccountNumber(randomRIB);
        assertEquals("6376375017134645",currentAccountNumber, "");
    }

    @Test
    void testExtractBankCodeSuccess() {
        String currentBankCode =
                accountNumberExtractor.extractBankCode(randomRIB);
        assertEquals("986",currentBankCode, "");
    }
}
