package com.orbit.orbit_banking.modules.accounts.controller;

import com.orbit.orbit_banking.common.dto.ApiResponse;
import com.orbit.orbit_banking.modules.accounts.dto.NewAccountRequestDto;
import com.orbit.orbit_banking.modules.accounts.model.ApplicationRequest;
import com.orbit.orbit_banking.modules.accounts.service.AccountOpening;
import com.orbit.orbit_banking.modules.accounts.service.implementation.AccountOpeningQueryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registrations")
public class ApplicationRequestController {

    private final List<AccountOpening> accountOpeningServices;
    private final AccountOpeningQueryServiceImpl accountOpeningQueryService;

    public ApplicationRequestController(List<AccountOpening> accountOpeningServices, AccountOpeningQueryServiceImpl accountOpeningQueryService) {
        this.accountOpeningServices = accountOpeningServices;
        this.accountOpeningQueryService = accountOpeningQueryService;
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
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(
                        accountOpeningQueryService.getAllAccountOpenings(page, size)
                ));
    }

}
