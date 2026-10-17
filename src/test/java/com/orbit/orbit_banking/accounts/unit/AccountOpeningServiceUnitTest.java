package com.orbit.orbit_banking.accounts.unit;

import com.orbit.orbit_banking.modules.accounts.dto.CustomerData;
import com.orbit.orbit_banking.modules.accounts.dto.NewAccountRequestDto;
import com.orbit.orbit_banking.modules.accounts.exceptions.AccountOpeningRequestAlreadyExistsException;
import com.orbit.orbit_banking.modules.accounts.model.*;
import com.orbit.orbit_banking.modules.accounts.repository.AccountProductRepository;
import com.orbit.orbit_banking.modules.accounts.repository.ApplicationRequestRepository;
import com.orbit.orbit_banking.modules.accounts.service.implementation.AccountOpeningQueryServiceImpl;
import com.orbit.orbit_banking.modules.accounts.service.implementation.CurrentAccountOpeningImpl;
import com.orbit.orbit_banking.modules.customer.dto.CustomerDto;
import com.orbit.orbit_banking.modules.customer.model.Customer;
import com.orbit.orbit_banking.modules.customer.repository.CustomerRepository;
import com.orbit.orbit_banking.modules.customer.service.implementation.CustomerAdderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountOpeningServiceUnitTest {

    @Mock
    private ApplicationRequestRepository applicationRequestRepository;;

    @Mock
    private AccountProductRepository accountProductRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerAdderServiceImpl customerAdderService;

    @InjectMocks
    private CurrentAccountOpeningImpl accountOpeningService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    private static final String NATIONAL_ID = "AB123456";

    private NewAccountRequestDto dto(String fundingSource) {
        CustomerDto customer = new CustomerDto();
        customer.setFirstName("Amina");
        customer.setLastName("El Idrissi");
        customer.setEmail("amina@example.com");
        customer.setNationalID(NATIONAL_ID);

        NewAccountRequestDto dto = new NewAccountRequestDto();
        dto.setCustomerDetails(customer);
        dto.setFundingSource(fundingSource);
        return dto;
    }


    @Test
    void testCreateOpeningAccountRequestSuccess(){
        Customer customer = new Customer();
        AccountProduct product = new AccountProduct();

        when(applicationRequestRepository
                .existsByCustomer_NationalIDAndAccountProduct_AccountType(NATIONAL_ID, AccountType.CURRENT))
                .thenReturn(false);
        when(customerAdderService.addCustomer(any())).thenReturn(customer);
        when(accountProductRepository.findByAccountType(AccountType.CURRENT))
                .thenReturn(Optional.of(product));
        when(applicationRequestRepository.save(any(ApplicationRequest.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        ApplicationRequest result = accountOpeningService.openAccount(dto("BUSINESS")); // use a real FundSource value

        assertThat(result.getCustomer()).isSameAs(customer);
        assertThat(result.getAccountProduct()).isSameAs(product);
        assertThat(result.getSourceOfFunds()).isEqualTo(FundSource.valueOf("BUSINESS"));
        assertThat(result.getStatus()).isEqualTo(ApplicationRequestStatus.CREATED);
        verify(applicationRequestRepository).save(result);
    }

    @Test
    void testCreateOpeningAccountRequestDuplicate(){
        when(applicationRequestRepository
                .existsByCustomer_NationalIDAndAccountProduct_AccountType(NATIONAL_ID, AccountType.CURRENT))
                .thenReturn(true);

        assertThrows(AccountOpeningRequestAlreadyExistsException.class, () -> accountOpeningService.openAccount(dto("BUSINESS")));

    }
}
