package com.orbit.orbit_banking.modules.accounts.dto;


import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDetails {

    private UUID uuid;
    private String accountNumber;
    private String IBAN;
    private String RIB;
    private String accountType;
    private String status;
    private BigDecimal balance;
    private Instant openedAt;

    public BankAccountDetails(
            BankAccount bankAccount
    ){
        this.uuid = bankAccount.getId();
        this.accountNumber = bankAccount.getAccountNumber();
        this.IBAN = bankAccount.getIban();
        this.RIB = bankAccount.getRIB();
        this.accountType = String.valueOf(bankAccount.getAccountType());
        this.status = bankAccount.getStatus().toString();
        this.balance = bankAccount.getBalance();
        this.openedAt = bankAccount.getOpenedAt();
    }
}
