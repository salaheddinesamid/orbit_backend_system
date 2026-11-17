package com.orbit.orbit_banking.modules.accounts.mapper;

import com.orbit.orbit_banking.modules.accounts.model.AccountType;
import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import com.orbit.orbit_banking.modules.accounts.model.BankAccountStatus;
import com.orbit.orbit_banking.modules.accounts.utils.AccountNumberGenerator;
import com.orbit.orbit_banking.modules.accounts.utils.BankAccountDetailsGenerator;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {

    private final AccountNumberGenerator accountNumberGenerator;
    private final BankAccountDetailsGenerator ribGenerator;

    public BankAccountMapper(AccountNumberGenerator accountNumberGenerator, BankAccountDetailsGenerator ribGenerator) {
        this.accountNumberGenerator = accountNumberGenerator;
        this.ribGenerator = ribGenerator;
    }

    public BankAccount toBankAccount(
            AccountType accountType,
            BankAccountStatus status
    ) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountType(accountType);
        bankAccount.setStatus(status);
        bankAccount.setAccountNumber(
                accountNumberGenerator.generateAccountNumber()
        );
        bankAccount.setCurrency("MAD");

        return bankAccount;
    }
}
