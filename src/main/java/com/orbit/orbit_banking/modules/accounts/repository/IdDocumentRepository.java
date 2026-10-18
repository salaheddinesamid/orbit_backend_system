package com.orbit.orbit_banking.modules.accounts.repository;

import com.orbit.orbit_banking.modules.accounts.model.IdDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IdDocumentRepository extends MongoRepository<IdDocument, String> {
}
