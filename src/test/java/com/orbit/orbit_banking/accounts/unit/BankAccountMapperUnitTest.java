package com.orbit.orbit_banking.accounts.unit;

import com.orbit.orbit_banking.modules.accounts.mapper.BankAccountMapper;
import com.orbit.orbit_banking.modules.accounts.model.AccountType;
import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import com.orbit.orbit_banking.modules.accounts.model.BankAccountStatus;
import com.orbit.orbit_banking.modules.accounts.utils.AccountNumberGenerator;
import com.orbit.orbit_banking.modules.accounts.utils.RibGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BankAccountMapperUnitTest {

    @Mock
    private AccountNumberGenerator accountNumberGenerator;

    @InjectMocks
    private BankAccountMapper bankAccountMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toBankAccount_shouldCreateAccountWithExpectedFields() {
        AccountNumberGenerator accountNumberGenerator =
                mock(AccountNumberGenerator.class);
        RibGenerator ribGenerator = mock(RibGenerator.class);

        when(accountNumberGenerator.generateAccountNumber())
                .thenReturn("123456789012");

        BankAccountMapper mapper =
                new BankAccountMapper(accountNumberGenerator, ribGenerator);

        BankAccount result = mapper.toBankAccount(
                AccountType.CURRENT,
                BankAccountStatus.ACTIVE
        );

        assertNotNull(result);
        assertEquals(AccountType.CURRENT, result.getAccountType());
        assertEquals(BankAccountStatus.ACTIVE, result.getStatus());
        assertEquals("123456789012", result.getAccountNumber());
        assertEquals("MAD", result.getCurrency());
    }
}
