package com.orbit.orbit_banking.modules.accounts.repository;

import com.orbit.orbit_banking.modules.accounts.model.AccountProduct;
import com.orbit.orbit_banking.modules.accounts.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountProductRepository extends JpaRepository<AccountProduct, Long> {

    Optional<AccountProduct> findByAccountType(AccountType accountType);
}
