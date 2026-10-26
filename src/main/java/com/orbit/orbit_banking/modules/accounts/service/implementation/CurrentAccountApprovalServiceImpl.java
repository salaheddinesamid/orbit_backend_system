package com.orbit.orbit_banking.modules.accounts.service.implementation;

import com.orbit.orbit_banking.modules.accounts.exceptions.ApplicationOpeningRequiresPreApprovalException;
import com.orbit.orbit_banking.modules.accounts.mapper.BankAccountMapper;
import com.orbit.orbit_banking.modules.accounts.model.*;
import com.orbit.orbit_banking.modules.accounts.repository.ApplicationRequestRepository;
import com.orbit.orbit_banking.modules.accounts.repository.BankAccountRepository;
import com.orbit.orbit_banking.modules.accounts.service.AccountApprovalService;
import com.orbit.orbit_banking.modules.accounts.utils.UserCredentialsGenerator;
import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import com.orbit.orbit_banking.modules.authentication.service.implementation.UserAccountCreationServiceImpl;
import com.orbit.orbit_banking.modules.customer.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CurrentAccountApprovalServiceImpl implements AccountApprovalService {

    private final ApplicationRequestRepository appRequestRepository;
    private final BankAccountMapper bankAccountMapper;
    private final UserCredentialsGenerator userCredentialsGenerator;
    private final UserAccountCreationServiceImpl userAccountCreationService;
    private final BankAccountRepository bankAccountRepository;

    public CurrentAccountApprovalServiceImpl(ApplicationRequestRepository appRequestRepository, BankAccountMapper bankAccountMapper, UserCredentialsGenerator userCredentialsGenerator, UserAccountCreationServiceImpl userAccountCreationService, BankAccountRepository bankAccountRepository) {
        this.appRequestRepository = appRequestRepository;
        this.bankAccountMapper = bankAccountMapper;
        this.userCredentialsGenerator = userCredentialsGenerator;
        this.userAccountCreationService = userAccountCreationService;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public void preApprove(UUID requestId) {
        // Fetch the request from the database:
        ApplicationRequest request = appRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        // Set new status for the application request:
        request.setStatus(ApplicationRequestStatus.WAITING_FOR_CLIENT_SIGNATURE);

        // Save the updated request
        appRequestRepository.save(request);
    }

    @Override
    @Transactional
    public BankAccount approve(UUID requestId) {

        // Fetch the approved request from the database
        ApplicationRequest request = appRequestRepository
                .findById(requestId).orElseThrow(() -> new IllegalArgumentException("Request not found"));

        // Verify it's approved
        if(request.getStatus() != ApplicationRequestStatus.WAITING_FOR_CLIENT_SIGNATURE) {
            throw new ApplicationOpeningRequiresPreApprovalException("");
        }

        // Change the application status
        request.setStatus(ApplicationRequestStatus.APPROVED);
        // Post-approval
        return postApprove(requestId);

    }

    @Transactional
    @Override
    public BankAccount postApprove(UUID requestId) {
        // Fetch the request from the database:
        ApplicationRequest request = appRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        // Get the customer details
        Customer customer = request.getCustomer();

        // Get the account product
        AccountProduct product = request.getAccountProduct();

        // Verify the status
        if(request.getStatus() != ApplicationRequestStatus.APPROVED){
            throw new ApplicationOpeningRequiresPreApprovalException(
                    "This request has not been pre-approved. Please try again later."
            );
        }

        // Change the status
        request.setStatus(ApplicationRequestStatus.APPROVED);
        // Create new bank account
        BankAccount bankAccount = bankAccountMapper
                .toBankAccount(
                        product.getAccountType(),
                        BankAccountStatus.ACTIVE
                );
        // Generate user credentials for the client
        Map<String, String> userCredentials = userCredentialsGenerator
                .generateUserCredentials(
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getNationalID()
                );
        UserAccount userAccount = userAccountCreationService
            .createUserAccount(
                    userCredentials.get("username"),
                    userCredentials.get("password"),
                    List.of("CUSTOMER")
            );

        // Set the owner ID
        bankAccount.setOwnerId(userAccount);
        // Set the product ID
        bankAccount.setProduct(product);


        return bankAccountRepository.save(bankAccount);
    }
}
