package com.orbit.orbit_banking.modules.banking.exceptions;

import com.orbit.orbit_banking.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BankAccountNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleBankAccountNotFoundException(BankAccountNotFoundException ex) {
        return ResponseEntity
                .status(404)
                .body(ApiResponse.failure(
                        "NOT_FOUND",
                        ex.getMessage()
                ));
    }
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiResponse<?>> handleInsufficientBalanceException(InsufficientBalanceException ex) {
        return ResponseEntity
                .status(409)
                .body(ApiResponse.failure(
                        "409_CONFLICT",
                        ex.getMessage()
                ));
    }
}
