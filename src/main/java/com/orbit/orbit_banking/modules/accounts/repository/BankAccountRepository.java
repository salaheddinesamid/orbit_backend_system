package com.orbit.orbit_banking.modules.accounts.repository;

import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
}
