package com.orbit.orbit_banking.modules.accounts.model;

import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import com.orbit.orbit_banking.modules.customer.model.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "account_application_requests")
public class ApplicationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private AccountProduct accountProduct;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "source_of_funds")
    @Enumerated(EnumType.STRING)
    private FundSource sourceOfFunds;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplicationRequestStatus status;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(
            name = "reviewed_by",
            nullable = true
    )
    private UserAccount reviewedBy;

    @OneToOne
    @JoinColumn(name = "resulted_bank_account_id")
    private BankAccount resultedBankAccount;


}
