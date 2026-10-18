package com.orbit.orbit_banking.modules.accounts.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Data
@Document("id_documents")
@CompoundIndex(name = "app_type_idx", def = "{'applicationRequestId': 1, 'documentType': 1}", unique = true)
public class IdDocument {

    @Id
    private String id;
    private UUID applicationRequestId;
    private IdDocumentType documentType;
    private String fileId;            // GridFS ObjectId (hex)
    private String originalFilename;
    private String contentType;
    private long size;
    private String sha256;
    private Instant uploadedAt;
}
