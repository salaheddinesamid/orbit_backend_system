package com.orbit.orbit_banking.modules.accounts.service.implementation;

import com.orbit.orbit_banking.modules.accounts.model.ApplicationRequest;
import com.orbit.orbit_banking.modules.accounts.repository.ApplicationRequestRepository;
import com.orbit.orbit_banking.modules.accounts.service.AccountOpeningQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountOpeningQueryServiceImpl implements AccountOpeningQueryService {
    private final ApplicationRequestRepository applicationRequestRepository;

    public AccountOpeningQueryServiceImpl(ApplicationRequestRepository applicationRequestRepository) {
        this.applicationRequestRepository = applicationRequestRepository;
    }

    @Override
    public List<ApplicationRequest> getAllAccountOpenings(int page, int size) {
        //Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return applicationRequestRepository.findAll();
    }
}
