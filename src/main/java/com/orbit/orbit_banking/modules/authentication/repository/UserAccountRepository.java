package com.orbit.orbit_banking.modules.authentication.repository;

import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByUsername(String username);
}
