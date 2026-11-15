package com.orbit.orbit_banking.utils;

import com.orbit.orbit_banking.modules.banking.utils.TransactionReferenceGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionReferenceGeneratorUnitTest {

    @InjectMocks
    private TransactionReferenceGenerator transactionReferenceGenerator;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGenerateValidTransactionReference(){
        String randomReference = transactionReferenceGenerator
                .generateTransactionReference();

        assertNotNull(randomReference);
    }

    @Test
    void testGenerateInvalidTransactionReference(){}
}
