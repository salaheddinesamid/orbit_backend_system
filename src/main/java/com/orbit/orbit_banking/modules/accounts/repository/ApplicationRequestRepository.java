package com.orbit.orbit_banking.modules.accounts.repository;

import com.orbit.orbit_banking.modules.accounts.model.AccountType;
import com.orbit.orbit_banking.modules.accounts.model.ApplicationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest, UUID> {
    boolean existsByCustomer_NationalIDAndAccountProduct_AccountType(String customer_nationalID, AccountType accountProduct_accountType);
}
