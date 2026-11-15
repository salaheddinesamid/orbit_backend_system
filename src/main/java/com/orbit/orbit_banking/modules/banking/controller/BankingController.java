package com.orbit.orbit_banking.modules.banking.controller;

import com.orbit.orbit_banking.common.dto.ApiResponse;
import com.orbit.orbit_banking.modules.accounts.model.AccountProduct;
import com.orbit.orbit_banking.modules.banking.dto.TransactionResponse;
import com.orbit.orbit_banking.modules.banking.dto.TransferDto;
import com.orbit.orbit_banking.modules.banking.model.TransactionType;
import com.orbit.orbit_banking.modules.banking.service.TransferService;
import com.orbit.orbit_banking.modules.banking.service.implementation.BankingProductQueryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/banking")
public class BankingController {

    private final BankingProductQueryServiceImpl bankingProductQueryService;
    private final List<TransferService> transferServices;

    public BankingController(BankingProductQueryServiceImpl bankingProductQueryService, List<TransferService> transferServices) {
        this.bankingProductQueryService = bankingProductQueryService;
        this.transferServices = transferServices;
    }

    @GetMapping("/products/get")
    public ResponseEntity<ApiResponse<List<?>>> getProducts() {
        return ResponseEntity
                .ok()
                .body(ApiResponse.ok(
                        bankingProductQueryService.getAllAccountProducts()
                ));
    }

    @PostMapping("transfer")
    public ResponseEntity<ApiResponse<TransactionResponse>> transfer(@RequestBody TransferDto transferDto){
            TransferService transferService =
                    transferServices.stream().filter(service-> service.supports(TransactionType.valueOf(transferDto.getType())))
                            .findFirst().orElseGet(null);

            TransactionResponse transaction = transferService.transfer(
                    TransactionType.valueOf(transferDto.getType()), transferDto
            );

            return ResponseEntity
                    .status(200)
                    .body(ApiResponse.ok(transaction));
    }
}
