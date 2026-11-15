package com.orbit.orbit_banking.modules.banking.service.implementation;

import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import com.orbit.orbit_banking.modules.accounts.repository.BankAccountRepository;
import com.orbit.orbit_banking.modules.banking.dto.TransactionResponse;
import com.orbit.orbit_banking.modules.banking.dto.TransferDto;
import com.orbit.orbit_banking.modules.banking.exceptions.BankAccountNotFoundException;
import com.orbit.orbit_banking.modules.banking.exceptions.InsufficientBalanceException;
import com.orbit.orbit_banking.modules.banking.model.Transaction;
import com.orbit.orbit_banking.modules.banking.model.TransactionType;
import com.orbit.orbit_banking.modules.banking.repository.TransactionRepository;
import com.orbit.orbit_banking.modules.banking.service.TransferService;
import com.orbit.orbit_banking.modules.banking.utils.AccountNumberExtractor;
import com.orbit.orbit_banking.modules.banking.utils.TransactionReferenceGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InternalTransferServiceImpl implements TransferService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final AccountNumberExtractor accountNumberExtractor;
    private final TransactionReferenceGenerator transactionReferenceGenerator;

    public InternalTransferServiceImpl(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository, AccountNumberExtractor accountNumberExtractor, TransactionReferenceGenerator transactionReferenceGenerator) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
        this.accountNumberExtractor = accountNumberExtractor;
        this.transactionReferenceGenerator = transactionReferenceGenerator;
    }

    @Override
    public boolean supports(TransactionType transactionType) {
        return transactionType.equals(TransactionType.INTERNAL);
    }

    @Override
    @Transactional
    public TransactionResponse transfer(TransactionType type, TransferDto transferDto) {
        // Extract the beneficiary account number
        String beneficiaryAccountNumber = accountNumberExtractor
                .extractAccountNumber(transferDto.getBeneficiaryRIB());

        BankAccount sender = bankAccountRepository
                .findByAccountNumber(transferDto.getSenderAccountNumber()).orElseThrow(()-> new BankAccountNotFoundException(transferDto.getSenderAccountNumber()));
        // Check the beneficiary bank account
        BankAccount beneficiary = bankAccountRepository
                .findByAccountNumber(beneficiaryAccountNumber).orElseThrow(()-> new BankAccountNotFoundException(beneficiaryAccountNumber));

        // Check if the sender is the receiver
        if(sender.getId().equals(beneficiary.getId())){
            throw new IllegalArgumentException(("Cannot transfer the money to the same account"));
        }
        // Check if the sender bank account has sufficient balance:
        if(sender.getBalance().compareTo(transferDto.getAmount()) < 0){
            throw new InsufficientBalanceException("Insufficient balance");
        }

        // In case the sender has sufficient balance
        Transaction transaction = new Transaction();
        transaction.setAmount(transferDto.getAmount());


        // Update sender bank account balance
        sender.setBalance(sender.getBalance().subtract(transferDto.getAmount()));

        // Update beneficiary bank account
        beneficiary.setBalance(beneficiary.getBalance().add(transferDto.getAmount()));

        // Update transaction entity
        transaction.setReference(transactionReferenceGenerator.generateTransactionReference());
        transaction.setBeneficiaryAccount(beneficiary);
        transaction.setSenderAccount(sender);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return new TransactionResponse(

        );
    }
}
