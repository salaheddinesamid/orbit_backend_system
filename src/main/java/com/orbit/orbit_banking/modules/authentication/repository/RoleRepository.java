package com.orbit.orbit_banking.modules.authentication.repository;

import com.orbit.orbit_banking.modules.authentication.model.Role;
import com.orbit.orbit_banking.modules.authentication.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleName name);
}
