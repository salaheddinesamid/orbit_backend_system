package com.orbit.orbit_banking.modules.banking.repository;

import com.orbit.orbit_banking.modules.banking.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepositRepository extends JpaRepository<Deposit, UUID> {
}
