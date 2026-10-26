package com.orbit.orbit_banking.modules.accounts.repository;

import com.orbit.orbit_banking.modules.accounts.model.ClientContract;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientContractRepository extends MongoRepository<ClientContract, String> {
}
