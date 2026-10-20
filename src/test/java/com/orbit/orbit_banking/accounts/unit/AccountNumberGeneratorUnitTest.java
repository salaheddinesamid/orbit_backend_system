package com.orbit.orbit_banking.accounts.unit;

import com.orbit.orbit_banking.modules.accounts.repository.BankAccountRepository;
import com.orbit.orbit_banking.modules.accounts.utils.AccountNumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AccountNumberGeneratorUnitTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private AccountNumberGenerator accountNumberGenerator;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateAccountNumberSuccess(){
        when(bankAccountRepository
                .existsByAccountNumber(anyString()))
                .thenReturn(false);

        String accountNumber =
                accountNumberGenerator.generateAccountNumber();

        assertTrue(accountNumber.startsWith("002516"));
    }

    @Test
    void testGenerateAccountNumberThrowsAccountNumberAlreadyExistsException(){}
}
