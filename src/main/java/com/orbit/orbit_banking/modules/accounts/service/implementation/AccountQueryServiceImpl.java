package com.orbit.orbit_banking.modules.accounts.service.implementation;

import com.orbit.orbit_banking.modules.accounts.dto.BankAccountDetails;
import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import com.orbit.orbit_banking.modules.accounts.repository.BankAccountRepository;
import com.orbit.orbit_banking.modules.accounts.service.AccountQueryService;
import com.orbit.orbit_banking.modules.banking.exceptions.BankAccountNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountQueryServiceImpl implements AccountQueryService {

    private final BankAccountRepository bankAccountRepository;

    public AccountQueryServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public BankAccountDetails getAccount(
            String nationalId,
            Long userId
    ) throws BankAccountNotFoundException {

        BankAccount bankAccount;

        if (userId != null) {
            bankAccount = bankAccountRepository
                    .findAllByUserId(userId)
                    .stream()
                    .findFirst()
                    .orElseThrow(() ->
                            new BankAccountNotFoundException(userId.toString())
                    );

        } else if (nationalId != null && !nationalId.isBlank()) {
            bankAccount = bankAccountRepository
                    .findByCustomer_NationalID(nationalId)
                    .orElseThrow(() ->
                            new BankAccountNotFoundException(nationalId)
                    );

        } else {
            throw new IllegalArgumentException(
                    "Either userId or nationalId must be provided"
            );
        }

        return new BankAccountDetails(bankAccount);
    }
}
