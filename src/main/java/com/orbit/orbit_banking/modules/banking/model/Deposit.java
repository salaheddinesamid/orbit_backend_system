package com.orbit.orbit_banking.modules.banking.model;

import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "deposits")
@Getter
@Setter
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private BankAccount bankAccount;

    @ManyToOne
    private UserAccount issuedBy;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DepositStatus status;

}
