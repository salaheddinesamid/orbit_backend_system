package com.orbit.orbit_banking.modules.accounts.dto;

import java.time.Instant;

public record IdDocumentDetails(
        String documentType,
        String originalFilename,
        String contentType,
        long size,
        Instant uploadedAt,
        String downloadUrl
){}
