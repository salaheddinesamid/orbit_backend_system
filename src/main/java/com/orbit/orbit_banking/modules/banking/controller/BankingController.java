package com.orbit.orbit_banking.modules.banking.controller;

import com.orbit.orbit_banking.common.dto.ApiResponse;
import com.orbit.orbit_banking.modules.accounts.model.AccountProduct;
import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import com.orbit.orbit_banking.modules.accounts.service.implementation.AccountQueryServiceImpl;
import com.orbit.orbit_banking.modules.banking.dto.TransactionResponse;
import com.orbit.orbit_banking.modules.banking.dto.TransferDto;
import com.orbit.orbit_banking.modules.banking.model.Deposit;
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
    private final AccountQueryServiceImpl accountQueryService;
    private final List<TransferService> transferServices;

    public BankingController(BankingProductQueryServiceImpl bankingProductQueryService, AccountQueryServiceImpl accountQueryService, List<TransferService> transferServices) {
        this.bankingProductQueryService = bankingProductQueryService;
        this.accountQueryService = accountQueryService;
        this.transferServices = transferServices;
    }

    @GetMapping("accounts/get")
    public ResponseEntity<ApiResponse<BankAccount>> getAccount(@RequestParam String nationalId){
        BankAccount bankAccount = accountQueryService
                .getAccount(nationalId);

        return ResponseEntity.status(200)
                .body(ApiResponse.ok(bankAccount));
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

    @PutMapping("deposit")
    public ResponseEntity<ApiResponse<Deposit>> deposit(){
        return null;
    }
}
