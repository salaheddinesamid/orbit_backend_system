package com.orbit.orbit_banking.modules.banking.service.implementation;

import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import com.orbit.orbit_banking.modules.accounts.repository.BankAccountRepository;
import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import com.orbit.orbit_banking.modules.banking.dto.DepositDto;
import com.orbit.orbit_banking.modules.banking.dto.TransactionResponse;
import com.orbit.orbit_banking.modules.banking.exceptions.BankAccountNotFoundException;
import com.orbit.orbit_banking.modules.banking.model.*;
import com.orbit.orbit_banking.modules.banking.repository.TransactionRepository;
import com.orbit.orbit_banking.modules.banking.service.DepositService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class DepositServiceImpl implements DepositService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    public DepositServiceImpl(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    @Override
    public TransactionResponse deposit(DepositDto depositDto, UserAccount issuer) {
        // Fetch the beneficiary bank account
        BankAccount beneficiary = bankAccountRepository
                .findByAccountNumber(depositDto.accountNumber()).orElseThrow(()-> new BankAccountNotFoundException(depositDto.accountNumber()));
        // Check if the amount is non-negative and greater than 0
        if(depositDto.amount().equals(BigDecimal.ZERO)){
            throw new IllegalArgumentException("The amount must be positive and greater than 0");
        }

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setStatus(TransactionStatus.EXECUTED);
        transaction.setAmount(depositDto.amount());
        transaction.setBeneficiaryAccount(beneficiary);
        transaction.setBalanceBefore(beneficiary.getBalance());
        transaction.setChannel(TransactionChannel.valueOf(depositDto.channel()));

        // Update the account balance
        beneficiary.setBalance(beneficiary.getBalance().add(depositDto.amount()));
        bankAccountRepository.save(beneficiary);

        // Update transaction details (amount after  and status)
        transaction.setBalanceAfter(beneficiary.getBalance());
        transaction.setStatus(TransactionStatus.SUCCESS);

        Transaction savedTransaction = transactionRepository
                .save(transaction);

        return new TransactionResponse(
                savedTransaction.getTransactionId(),
                savedTransaction.getReference(),
                savedTransaction.getTransactionType(),
                savedTransaction.getAmount(),
                savedTransaction.getBalanceBefore(),
                savedTransaction.getBalanceAfter(),
                savedTransaction.getStatus(),
                savedTransaction.getIssuedAt()
        );

    }
}
