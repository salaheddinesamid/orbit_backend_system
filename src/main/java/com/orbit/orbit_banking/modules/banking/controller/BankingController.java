package com.orbit.orbit_banking.modules.banking.controller;

import com.orbit.orbit_banking.common.dto.ApiResponse;
import com.orbit.orbit_banking.modules.accounts.model.AccountProduct;
import com.orbit.orbit_banking.modules.banking.service.implementation.BankingProductQueryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/banking")
public class BankingController {

    private final BankingProductQueryServiceImpl bankingProductQueryService;

    public BankingController(BankingProductQueryServiceImpl bankingProductQueryService) {
        this.bankingProductQueryService = bankingProductQueryService;
    }

    @GetMapping("/products/get")
    public ResponseEntity<ApiResponse<List<?>>> getProducts() {
        return ResponseEntity
                .ok()
                .body(ApiResponse.ok(
                        bankingProductQueryService.getAllAccountProducts()
                ));
    }
}
