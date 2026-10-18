package com.orbit.orbit_banking.modules.accounts.service;

import com.orbit.orbit_banking.modules.accounts.model.IdDocument;
import com.orbit.orbit_banking.modules.accounts.model.IdDocumentType;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IdDocumentProcessor {

    /**
     *
     * @param requestID
     * @param type
     * @return
     */
    IdDocument processAndSaveIdDocument(
            UUID requestID,
            IdDocumentType type,
            MultipartFile file
    );
}
