package com.orbit.orbit_banking.accounts.unit;

import com.orbit.orbit_banking.modules.accounts.dto.BankAccountDetails;
import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import com.orbit.orbit_banking.modules.accounts.model.BankAccountStatus;
import com.orbit.orbit_banking.modules.accounts.repository.BankAccountRepository;
import com.orbit.orbit_banking.modules.accounts.service.implementation.AccountQueryServiceImpl;
import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import com.orbit.orbit_banking.modules.customer.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class BankAccountQueryServiceUnitTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private AccountQueryServiceImpl accountQueryService;

    private BankAccount bankAccount;
    private Customer customer;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        customer = new Customer();
        customer.setId(1L);
        customer.setNationalID("RANDOM");

        bankAccount = new BankAccount();
        bankAccount.setId(UUID.randomUUID());
        bankAccount.setCustomer(customer);
        bankAccount.setStatus(BankAccountStatus.ACTIVE);


    }
    @Test
    void testFetchBankAccountByNationalId(){

        when(bankAccountRepository.findByCustomer_NationalID("RANDOM"))
                .thenReturn(Optional.of(bankAccount));

        BankAccountDetails result = accountQueryService.getAccount("RANDOM", null);
        assertNotNull(result);
    }
}
