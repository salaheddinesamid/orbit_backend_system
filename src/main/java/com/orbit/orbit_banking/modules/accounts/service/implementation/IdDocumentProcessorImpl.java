package com.orbit.orbit_banking.modules.accounts.service.implementation;

import com.orbit.orbit_banking.modules.accounts.model.IdDocument;
import com.orbit.orbit_banking.modules.accounts.model.IdDocumentType;
import com.orbit.orbit_banking.modules.accounts.repository.IdDocumentRepository;
import com.orbit.orbit_banking.modules.accounts.service.IdDocumentProcessor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@Service
public class IdDocumentProcessorImpl implements IdDocumentProcessor {


    private static final long MAX_SIZE = 5L * 1024 * 1024;

    private final GridFsTemplate gridFsTemplate;
    private final IdDocumentRepository idDocumentRepository;

    public IdDocumentProcessorImpl(GridFsTemplate gridFsTemplate, IdDocumentRepository idDocumentRepository) {
        this.gridFsTemplate = gridFsTemplate;
        this.idDocumentRepository = idDocumentRepository;
    }

    @Override
    public IdDocument processAndSaveIdDocument(UUID requestID, IdDocumentType type, MultipartFile file) {
        if(file.isEmpty()) {
            throw new IllegalArgumentException("File is null or empty");
        }

        try{
            byte[] bytes = file.getBytes();
            String contentType = file.getContentType();

            ObjectId objectId = gridFsTemplate
                    .store(
                            new ByteArrayInputStream(bytes),
                            UUID.randomUUID().toString(),
                            contentType
                    );

            IdDocument idDocument = new IdDocument();
            idDocument.setApplicationRequestId(requestID);
            idDocument.setFileId(objectId.toHexString());
            idDocument.setContentType(contentType);
            idDocument.setSize(bytes.length);
            idDocument.setDocumentType(type);
            idDocument.setUploadedAt(Instant.now());

            // Save the document details in the database
            return idDocumentRepository.save(idDocument);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
