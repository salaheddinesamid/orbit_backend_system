package com.orbit.orbit_banking.modules.banking.service.implementation;

import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import com.orbit.orbit_banking.modules.accounts.repository.BankAccountRepository;
import com.orbit.orbit_banking.modules.banking.dto.TransactionResponse;
import com.orbit.orbit_banking.modules.banking.dto.WithdrawDto;
import com.orbit.orbit_banking.modules.banking.exceptions.InsufficientBalanceException;
import com.orbit.orbit_banking.modules.banking.mapper.TransactionMapper;
import com.orbit.orbit_banking.modules.banking.model.Transaction;
import com.orbit.orbit_banking.modules.banking.model.TransactionType;
import com.orbit.orbit_banking.modules.banking.repository.TransactionRepository;
import com.orbit.orbit_banking.modules.banking.service.WithdrawService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StandardWithdrawService implements WithdrawService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public StandardWithdrawService(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public TransactionResponse withdraw(WithdrawDto withdrawDto) throws InsufficientBalanceException {
        // Fetch the bank account:
        BankAccount bankAccount = bankAccountRepository
                .findByAccountNumber(withdrawDto.accountNumber()).orElseThrow();

        // Check if the client account has enough balance for a withdrawal
        if (bankAccount.getBalance().compareTo(withdrawDto.amount()) < 0){
            throw new InsufficientBalanceException("Insufficient balance for a withdrawal");
        }

        // Check if the amount is negative
        if (withdrawDto.amount().compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException();
        }

        // Otherwise, create the transaction record
        Transaction transaction = transactionMapper
                .mapToTransaction(withdrawDto.amount(), TransactionType.WITHDRAW, withdrawDto.channel());
        transaction.setBalanceBefore(bankAccount.getBalance());

        // Update bank account balance:
        bankAccount.setBalance(bankAccount.getBalance().subtract(withdrawDto.amount()));

        transaction.setBalanceAfter(bankAccount.getBalance());


        Transaction savedTransaction = transactionRepository
                .save(transaction);

        return transactionMapper
                .mapToDto(savedTransaction);
    }
}
