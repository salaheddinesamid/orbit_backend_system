package com.orbit.orbit_banking.modules.banking.service.implementation;

import com.orbit.orbit_banking.modules.banking.dto.TransactionResponse;
import com.orbit.orbit_banking.modules.banking.model.Transaction;
import com.orbit.orbit_banking.modules.banking.repository.TransactionRepository;
import com.orbit.orbit_banking.modules.banking.service.TransactionQueryService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TransactionQueryServiceImpl implements TransactionQueryService {
    private final TransactionRepository transactionRepository;

    public TransactionQueryServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<TransactionResponse> getAllTransactions(String nationalId) {
        List<Transaction> transactions =
                transactionRepository.findByBeneficiaryAccount_Customer_NationalID(nationalId);

        return transactions
                .stream().map(transaction -> new TransactionResponse(
                   transaction.getTransactionId(),
                   transaction.getReference(),
                   transaction.getTransactionType(),
                   transaction.getAmount(),
                   transaction.getBalanceBefore(),
                   transaction.getBalanceAfter(),
                   transaction.getStatus(),
                   transaction.getIssuedAt()
                )).toList();
    }
}
