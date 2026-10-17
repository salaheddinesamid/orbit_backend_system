package com.orbit.orbit_banking.modules.accounts.service.implementation;

import com.orbit.orbit_banking.modules.accounts.dto.NewAccountRequestDto;
import com.orbit.orbit_banking.modules.accounts.exceptions.AccountOpeningRequestAlreadyExistsException;
import com.orbit.orbit_banking.modules.accounts.model.*;
import com.orbit.orbit_banking.modules.accounts.repository.AccountProductRepository;
import com.orbit.orbit_banking.modules.accounts.repository.ApplicationRequestRepository;
import com.orbit.orbit_banking.modules.accounts.service.AccountOpening;
import com.orbit.orbit_banking.modules.customer.model.Customer;
import com.orbit.orbit_banking.modules.customer.service.implementation.CustomerAdderServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CurrentAccountOpeningImpl implements AccountOpening {

    private final ApplicationRequestRepository applicationRequestRepository;
    private final CustomerAdderServiceImpl customerAdderService;
    private final AccountProductRepository accountProductRepository;

    public CurrentAccountOpeningImpl(ApplicationRequestRepository applicationRequestRepository, CustomerAdderServiceImpl customerAdderService, AccountProductRepository accountProductRepository) {
        this.applicationRequestRepository = applicationRequestRepository;
        this.customerAdderService = customerAdderService;
        this.accountProductRepository = accountProductRepository;
    }

    @Override
    public boolean supports(String type) {
        return type.equalsIgnoreCase("current");
    }

    @Transactional
    @Override
    public ApplicationRequest openAccount(NewAccountRequestDto dto) throws AccountOpeningRequestAlreadyExistsException {
        // Check if the customer is allowed to create new account: same account type
        if (!isEligible(
                dto.getCustomerDetails().getNationalID(),
                "CURRENT"
        )){
            throw new AccountOpeningRequestAlreadyExistsException(
                    "An open request for this account type already exists"
            );
        }

        // Create new customer:
        Customer customer = customerAdderService.addCustomer(
                dto.getCustomerDetails()
        );

        AccountProduct accountProduct = accountProductRepository
                .findByAccountType(AccountType.CURRENT).orElseThrow();

        ApplicationRequest newApplicationRequest = new ApplicationRequest();
        newApplicationRequest.setCustomer(customer);
        newApplicationRequest.setAccountProduct(accountProduct);
        newApplicationRequest.setSourceOfFunds(FundSource.valueOf(dto.getFundingSource()));
        newApplicationRequest.setStatus(ApplicationRequestStatus.CREATED);

        // Save the account application request
        return applicationRequestRepository.save(newApplicationRequest);


    }

    private boolean isEligible(
            String nationalID,
            String accountType
    ){
        return applicationRequestRepository.existsByCustomer_NationalIDAndAccountProduct_AccountType(
                nationalID,
                AccountType.valueOf(accountType)
        );
    }
}
