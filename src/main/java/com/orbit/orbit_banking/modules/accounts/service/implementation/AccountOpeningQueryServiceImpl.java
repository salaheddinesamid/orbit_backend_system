package com.orbit.orbit_banking.modules.accounts.service.implementation;

import com.orbit.orbit_banking.modules.accounts.dto.ApplicationRequestDetails;
import com.orbit.orbit_banking.modules.accounts.dto.IdDocumentDetails;
import com.orbit.orbit_banking.modules.accounts.model.ApplicationRequest;
import com.orbit.orbit_banking.modules.accounts.model.IdDocument;
import com.orbit.orbit_banking.modules.accounts.repository.ApplicationRequestRepository;
import com.orbit.orbit_banking.modules.accounts.repository.IdDocumentRepository;
import com.orbit.orbit_banking.modules.accounts.service.AccountOpeningQueryService;
import com.orbit.orbit_banking.modules.customer.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountOpeningQueryServiceImpl implements AccountOpeningQueryService {
    private final ApplicationRequestRepository applicationRequestRepository;
    private final IdDocumentRepository idDocumentRepository;

    public AccountOpeningQueryServiceImpl(ApplicationRequestRepository applicationRequestRepository, IdDocumentRepository idDocumentRepository) {
        this.applicationRequestRepository = applicationRequestRepository;
        this.idDocumentRepository = idDocumentRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ApplicationRequestDetails> getAllAccountOpenings(int page, int size) {
        Page<ApplicationRequest> requests = applicationRequestRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));

        log.info("Available application requests :{}", requests);
        List<UUID> ids = requests.getContent().stream().map(ApplicationRequest::getId)
                .toList();
        Map<UUID, IdDocument> docsByApps = idDocumentRepository.findByApplicationRequestIdIn(ids)
                .stream().collect(Collectors.toMap(IdDocument::getApplicationRequestId, doc-> doc, (a,b)-> a));

        return requests.map(r -> toDto(r, docsByApps.get(r.getId())));
    }


    private ApplicationRequestDetails toDto(ApplicationRequest r, IdDocument d) {
        IdDocumentDetails info = d == null ? null : new IdDocumentDetails(
                d.getDocumentType().name(), d.getOriginalFilename(), d.getContentType(),
                d.getSize(), d.getUploadedAt(),
                "/api/v1/applications/" + r.getId() + "/id-document");
        return new ApplicationRequestDetails(
                r,
                info
        );
    }
}
