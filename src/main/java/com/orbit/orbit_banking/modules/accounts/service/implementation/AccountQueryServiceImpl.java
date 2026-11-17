package com.orbit.orbit_banking.modules.accounts.service.implementation;

import com.orbit.orbit_banking.modules.accounts.dto.BankAccountDetails;
import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import com.orbit.orbit_banking.modules.accounts.repository.BankAccountRepository;
import com.orbit.orbit_banking.modules.accounts.service.AccountQueryService;
import com.orbit.orbit_banking.modules.banking.exceptions.BankAccountNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountQueryServiceImpl implements AccountQueryService {

    private final BankAccountRepository bankAccountRepository;

    public AccountQueryServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccountDetails getAccount(String nationalId) throws BankAccountNotFoundException {
        BankAccount bankAccount =  bankAccountRepository
                .findByCustomer_NationalID(nationalId).orElseThrow(()-> new BankAccountNotFoundException(nationalId));

        return new BankAccountDetails(bankAccount);
    }
}
