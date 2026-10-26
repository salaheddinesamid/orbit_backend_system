package com.orbit.orbit_banking.modules.accounts.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document("client_contracts")
@CompoundIndex(
        name = "app_type_idx",
        def = "{'contractId' : 1, 'documentType' : 1}", unique = true
)
public class ClientContract {

    @Id
    private String id;
    private String originalFileName;
    private long size;
    private String sha256;
    private Instant createdAt;
    private Instant updatedAt;
}
