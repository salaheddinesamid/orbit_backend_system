package com.orbit.orbit_banking.modules.banking.controller;

import com.orbit.orbit_banking.common.dto.ApiResponse;
import com.orbit.orbit_banking.modules.accounts.dto.BankAccountDetails;
import com.orbit.orbit_banking.modules.accounts.service.implementation.AccountQueryServiceImpl;
import com.orbit.orbit_banking.modules.banking.dto.DepositDto;
import com.orbit.orbit_banking.modules.banking.dto.TransactionResponse;
import com.orbit.orbit_banking.modules.banking.dto.TransferDto;
import com.orbit.orbit_banking.modules.banking.model.Transaction;
import com.orbit.orbit_banking.modules.banking.model.TransactionType;
import com.orbit.orbit_banking.modules.banking.service.TransferService;
import com.orbit.orbit_banking.modules.banking.service.implementation.BankingProductQueryServiceImpl;
import com.orbit.orbit_banking.modules.banking.service.implementation.DepositServiceImpl;
import com.orbit.orbit_banking.modules.banking.service.implementation.TransactionQueryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/banking")
public class BankingController {

    private final BankingProductQueryServiceImpl bankingProductQueryService;
    private final AccountQueryServiceImpl accountQueryService;
    private final List<TransferService> transferServices;
    private final DepositServiceImpl depositService;
    private final TransactionQueryServiceImpl transactionQueryService;

    public BankingController(BankingProductQueryServiceImpl bankingProductQueryService, AccountQueryServiceImpl accountQueryService, List<TransferService> transferServices, DepositServiceImpl depositService, TransactionQueryServiceImpl transactionQueryService) {
        this.bankingProductQueryService = bankingProductQueryService;
        this.accountQueryService = accountQueryService;
        this.transferServices = transferServices;
        this.depositService = depositService;
        this.transactionQueryService = transactionQueryService;
    }

    @GetMapping("accounts/get")
    public ResponseEntity<ApiResponse<BankAccountDetails>> getAccount(@RequestParam String nationalId){
        BankAccountDetails bankAccount = accountQueryService
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
    public ResponseEntity<ApiResponse<TransactionResponse>> deposit(@RequestBody DepositDto depositDto){
        TransactionResponse deposit = depositService
                .deposit(depositDto, null);

        return ResponseEntity.status(200)
                .body(ApiResponse.ok(deposit));
    }

    @GetMapping("transactions")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getAllTransactions(
            @RequestParam String nationalId
    ){
        return ResponseEntity
                .status(200)
                .body(ApiResponse.ok(transactionQueryService.getAllTransactions(nationalId)));
    }
}
