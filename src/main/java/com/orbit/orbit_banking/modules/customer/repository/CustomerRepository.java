package com.orbit.orbit_banking.modules.customer.repository;

import com.orbit.orbit_banking.modules.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByNationalID(String nationalID);
}
