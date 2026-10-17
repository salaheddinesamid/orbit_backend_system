package com.orbit.orbit_banking.modules.accounts.controller;

import com.orbit.orbit_banking.common.dto.ApiResponse;
import com.orbit.orbit_banking.modules.accounts.dto.NewAccountRequestDto;
import com.orbit.orbit_banking.modules.accounts.model.ApplicationRequest;
import com.orbit.orbit_banking.modules.accounts.service.AccountOpening;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registrations")
public class ApplicationRequestController {

    private final List<AccountOpening> accountOpeningServices;

    public ApplicationRequestController(List<AccountOpening> accountOpeningServices) {
        this.accountOpeningServices = accountOpeningServices;
    }

    @PostMapping("new")
    public ResponseEntity<ApiResponse<?>> newAccount(
            @RequestBody NewAccountRequestDto requestDto,
            @RequestParam String accountType
            ){
        AccountOpening accountOpeningService =
                accountOpeningServices.stream().filter(service-> service.supports(accountType)).findFirst().orElse(null);
        assert accountOpeningService != null;
        ApplicationRequest response = accountOpeningService
                .openAccount(requestDto);

        return ResponseEntity.ok()
                .body(ApiResponse.ok(response));
    }
}
