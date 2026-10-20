package com.orbit.orbit_banking.modules.accounts.controller;

import com.orbit.orbit_banking.common.dto.ApiResponse;
import com.orbit.orbit_banking.modules.accounts.dto.NewAccountRequestDto;
import com.orbit.orbit_banking.modules.accounts.model.ApplicationRequest;
import com.orbit.orbit_banking.modules.accounts.service.AccountOpening;
import com.orbit.orbit_banking.modules.accounts.service.implementation.AccountOpeningQueryServiceImpl;
import com.orbit.orbit_banking.modules.accounts.service.implementation.CurrentAccountApprovalServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/registrations")
public class ApplicationRequestController {

    private final List<AccountOpening> accountOpeningServices;
    private final AccountOpeningQueryServiceImpl accountOpeningQueryService;
    private final CurrentAccountApprovalServiceImpl currentAccountApprovalService;

    public ApplicationRequestController(List<AccountOpening> accountOpeningServices, AccountOpeningQueryServiceImpl accountOpeningQueryService, CurrentAccountApprovalServiceImpl currentAccountApprovalService) {
        this.accountOpeningServices = accountOpeningServices;
        this.accountOpeningQueryService = accountOpeningQueryService;
        this.currentAccountApprovalService = currentAccountApprovalService;
    }

    @PostMapping("new")
    public ResponseEntity<ApiResponse<?>> newAccount(
            @RequestPart(name = "data") NewAccountRequestDto requestDto,
            @RequestParam String accountType,
            @RequestPart(name = "idDocument") MultipartFile file
            ){
        AccountOpening accountOpeningService =
                accountOpeningServices.stream().filter(service-> service.supports(accountType)).findFirst().orElse(null);
        assert accountOpeningService != null;
        ApplicationRequest response = accountOpeningService
                .openAccount(requestDto, file);

        return ResponseEntity.ok()
                .body(ApiResponse.ok(response));
    }

    @GetMapping("/requests/get_all")
    public ResponseEntity<ApiResponse<?>> getAllRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(
                        accountOpeningQueryService.getAllAccountOpenings(page, size)
                ));
    }

    @PatchMapping("/requests/pre-approve")
    public ResponseEntity<ApiResponse<?>> preApproveAccount(
            @RequestParam UUID id
    ){

        currentAccountApprovalService.preApprove(id);
        return ResponseEntity
                .ok()
                .body(ApiResponse.ok(
                        String.format("Successfully pre-approved account %s", id)
                ));
    }

    @PatchMapping("/requests/client-signature/sign")
    public ResponseEntity<ApiResponse<?>> clientSignatureSign(){
        return null;
    }

}
