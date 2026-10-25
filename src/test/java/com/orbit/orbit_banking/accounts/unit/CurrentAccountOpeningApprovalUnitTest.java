package com.orbit.orbit_banking.accounts.unit;

import com.orbit.orbit_banking.modules.accounts.model.AccountType;
import com.orbit.orbit_banking.modules.accounts.model.ApplicationRequest;
import com.orbit.orbit_banking.modules.accounts.model.ApplicationRequestStatus;
import com.orbit.orbit_banking.modules.accounts.repository.ApplicationRequestRepository;
import com.orbit.orbit_banking.modules.accounts.service.implementation.CurrentAccountApprovalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CurrentAccountOpeningApprovalUnitTest {

    @Mock
    private ApplicationRequestRepository applicationRequestRepository;


    @InjectMocks
    private CurrentAccountApprovalServiceImpl currentAccountApprovalService;

    private ApplicationRequest applicationRequest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        applicationRequest = new ApplicationRequest();
        applicationRequest.setStatus(ApplicationRequestStatus.CREATED);
        applicationRequest.setId(UUID.randomUUID());
    }

    @Test
    void testAccountOpeningPreApprovalSuccess(){

        when(applicationRequestRepository.existsByCustomer_NationalIDAndAccountProduct_AccountType("", AccountType.CURRENT))
                .thenReturn(false);

        when(applicationRequestRepository.findById(applicationRequest.getId()))
                .thenReturn(Optional.of(applicationRequest));

        currentAccountApprovalService
                .preApprove(applicationRequest.getId());

        assertThat(applicationRequest.getStatus())
                .isEqualTo(ApplicationRequestStatus.WAITING_FOR_CLIENT_SIGNATURE);

    }

    @Test
    void testAccountOpeningApprovalSuccess(){}

    @Test
    void testAccountOpeningPostApprovalSuccess(){}

    @Test
    void testAccountApprovalThrowsApplicationRequiresPreApprovalException(){}
}
