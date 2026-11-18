package com.orbit.orbit_banking.modules.banking.model;


import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "transactions"
)
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transactionId;

    @Column(name = "reference", unique = true)
    private String reference;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balanceBefore;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balanceAfter;

    @ManyToOne
    @JoinColumn(name = "sender_account_id")
    private BankAccount senderAccount;

    @ManyToOne
    @JoinColumn(name = "beneficiary_account_id", nullable = true)
    private BankAccount beneficiaryAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, length = 50)
    private TransactionType transactionType;

    @Column(name = "transaction_channel")
    @Enumerated(EnumType.STRING)
    private TransactionChannel channel;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @PrePersist
    void update(){
        this.issuedAt = LocalDateTime.now();
    }
}
