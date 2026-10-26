package com.orbit.orbit_banking.accounts.unit;

import com.orbit.orbit_banking.modules.accounts.mapper.BankAccountMapper;
import com.orbit.orbit_banking.modules.accounts.model.*;
import com.orbit.orbit_banking.modules.accounts.repository.ApplicationRequestRepository;
import com.orbit.orbit_banking.modules.accounts.repository.BankAccountRepository;
import com.orbit.orbit_banking.modules.accounts.service.implementation.CurrentAccountApprovalServiceImpl;
import com.orbit.orbit_banking.modules.accounts.utils.UserCredentialsGenerator;
import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import com.orbit.orbit_banking.modules.authentication.service.implementation.UserAccountCreationServiceImpl;
import com.orbit.orbit_banking.modules.customer.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CurrentAccountOpeningApprovalUnitTest {

    @Mock
    private ApplicationRequestRepository applicationRequestRepository;

    @Mock
    private BankAccountMapper bankAccountMapper;

    @Mock
    private UserCredentialsGenerator userCredentialsGenerator;

    @Mock
    private UserAccountCreationServiceImpl userAccountCreationService;

    @Mock
    private BankAccountRepository bankAccountRepository;

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
    void postApprove_shouldCreateAndReturnBankAccount() {
        // Arrange
        UUID requestId = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setNationalID("AB123456");

        AccountProduct product = new AccountProduct();
        // Set any required account type on the product here.

        ApplicationRequest request = new ApplicationRequest();
        request.setCustomer(customer);
        request.setAccountProduct(product);
        request.setStatus(ApplicationRequestStatus.APPROVED);

        BankAccount bankAccount = new BankAccount();

        UserAccount userAccount = mock(UserAccount.class);

        when(applicationRequestRepository.findById(requestId))
                .thenReturn(Optional.of(request));

        when(bankAccountMapper.toBankAccount(
                any(), eq(BankAccountStatus.ACTIVE)))
                .thenReturn(bankAccount);

        when(userCredentialsGenerator.generateUserCredentials(
                "John", "Doe", "AB123456"))
                .thenReturn(Map.of(
                        "username", "john.doe",
                        "password", "TestPassword123!"
                ));

        when(userAccountCreationService.createUserAccount(
                eq("john.doe"),
                eq("TestPassword123!"),
                eq(List.of("CUSTOMER"))
        )).thenReturn(userAccount);

        // Critical: make the mocked repository return the saved entity.
        when(bankAccountRepository.save(any(BankAccount.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        BankAccount result =
                currentAccountApprovalService.postApprove(requestId);

        // Assert
        assertNotNull(result);
        assertSame(bankAccount, result);
        assertSame(userAccount, result.getOwnerId());
        assertSame(product, result.getProduct());
        assertEquals(ApplicationRequestStatus.APPROVED,
                request.getStatus());

        verify(bankAccountMapper).toBankAccount(
                any(), eq(BankAccountStatus.ACTIVE));

        verify(bankAccountRepository).save(same(bankAccount));
    }

    @Test
    void testAccountOpeningPostApprovalSuccess(){}

    @Test
    void testAccountApprovalThrowsApplicationRequiresPreApprovalException(){}
}
