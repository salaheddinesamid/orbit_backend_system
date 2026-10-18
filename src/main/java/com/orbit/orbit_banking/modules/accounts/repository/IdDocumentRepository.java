package com.orbit.orbit_banking.modules.accounts.repository;

import com.orbit.orbit_banking.modules.accounts.model.IdDocument;
import org.hibernate.internal.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface IdDocumentRepository extends MongoRepository<IdDocument, String> {

    List<IdDocument> findByApplicationRequestIdIn(Collection<UUID> uuids);
}
