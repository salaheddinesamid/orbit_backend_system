package com.orbit.orbit_banking.modules.banking.mapper;

import com.orbit.orbit_banking.modules.banking.model.Transaction;
import com.orbit.orbit_banking.modules.banking.model.TransactionChannel;
import com.orbit.orbit_banking.modules.banking.model.TransactionStatus;
import com.orbit.orbit_banking.modules.banking.model.TransactionType;
import com.orbit.orbit_banking.modules.banking.utils.TransactionReferenceGenerator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionMapper {

    private final TransactionReferenceGenerator transactionReferenceGenerator;

    public TransactionMapper(TransactionReferenceGenerator transactionReferenceGenerator) {
        this.transactionReferenceGenerator = transactionReferenceGenerator;
    }

    public Transaction mapToTransaction(
            BigDecimal amount,
            TransactionType type,
            String channel
    ){
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setReference(transactionReferenceGenerator.generateTransactionReference());
        transaction.setTransactionType(type);
        transaction.setChannel(TransactionChannel.valueOf(channel));
        transaction.setStatus(TransactionStatus.EXECUTED);

        return transaction;
    }
}
