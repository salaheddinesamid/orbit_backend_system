package com.orbit.orbit_banking.accounts.unit;

import com.orbit.orbit_banking.modules.accounts.mapper.BankAccountMapper;
import com.orbit.orbit_banking.modules.accounts.model.*;
import com.orbit.orbit_banking.modules.accounts.repository.ApplicationRequestRepository;
import com.orbit.orbit_banking.modules.accounts.service.implementation.CurrentAccountApprovalServiceImpl;
import com.orbit.orbit_banking.modules.accounts.utils.UserCredentialsGenerator;
import com.orbit.orbit_banking.modules.authentication.service.implementation.UserAccountCreationServiceImpl;
import com.orbit.orbit_banking.modules.customer.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CurrentAccountOpeningApprovalUnitTest {

    @Mock
    private ApplicationRequestRepository applicationRequestRepository;

    @Mock
    private BankAccountMapper bankAccountMapper;

    @Mock
    private UserCredentialsGenerator userCredentialsGenerator;

    @Mock
    private UserAccountCreationServiceImpl userAccountCreationService;

    @InjectMocks
    private CurrentAccountApprovalServiceImpl currentAccountApprovalService;

    private Customer customer;
    private ApplicationRequest applicationRequest;
    private AccountProduct currentAccount;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        // Set up an opening request
        applicationRequest = new ApplicationRequest();
        applicationRequest.setStatus(ApplicationRequestStatus.CREATED);
        applicationRequest.setId(UUID.randomUUID());

        // Set up account product
        currentAccount = new AccountProduct();
        currentAccount.setCurrency(Currency.MAD);
        currentAccount.setAccountType(AccountType.CURRENT);

        // Set up an customer
        customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setNationalID("JD38201");
    }

    @Test
    void testAccountOpeningPreApprovalSuccess(){

        when(applicationRequestRepository.existsByCustomer_NationalIDAndAccountProduct_AccountType("", AccountType.CURRENT))
                .thenReturn(false);

        when(applicationRequestRepository.findById(applicationRequest.getId()))
                .thenReturn(Optional.of(applicationRequest));

        currentAccountApprovalService
                .preApprove(applicationRequest.getId());

        assertThat(applicationRequest.getStatus())
                .isEqualTo(ApplicationRequestStatus.WAITING_FOR_CLIENT_SIGNATURE);

    }

    @Test
    void testAccountOpeningApprovalSuccess(){
        applicationRequest.setStatus(ApplicationRequestStatus.WAITING_FOR_CLIENT_SIGNATURE);
        applicationRequest.setAccountProduct(currentAccount);
        applicationRequest.setCustomer(customer);

        when(applicationRequestRepository.findById(applicationRequest.getId()))
                .thenReturn(Optional.of(applicationRequest));

        BankAccount result = currentAccountApprovalService.approve(applicationRequest.getId());

        assertEquals(applicationRequest.getStatus(), ApplicationRequestStatus.APPROVED);
        assertNotNull(result);
    }

    @Test
    void testAccountOpeningPostApprovalSuccess(){}

    @Test
    void testAccountApprovalThrowsApplicationRequiresPreApprovalException(){}
}
