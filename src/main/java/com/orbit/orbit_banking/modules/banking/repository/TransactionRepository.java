package com.orbit.orbit_banking.modules.banking.repository;

import com.orbit.orbit_banking.modules.banking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByBeneficiaryAccount_Customer_NationalID(String nationalId);
}
