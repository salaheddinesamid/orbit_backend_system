package com.orbit.orbit_banking.banking.unit;

import com.orbit.orbit_banking.modules.accounts.repository.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class DepositServiceUnitTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Test
    void testDepositSuccess(){}

    @Test
    void testDepositThrowsIllegalArgumentException(){}
}
