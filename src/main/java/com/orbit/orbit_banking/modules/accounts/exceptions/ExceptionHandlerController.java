package com.orbit.orbit_banking.modules.accounts.exceptions;

import com.orbit.orbit_banking.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(AccountOpeningRequestAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccountOpeningRequestAlreadyExistsException(AccountOpeningRequestAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.failure(
                        "ACCOUNT_REQUEST_ALREADY_EXISTS",
                        "An open request for this account type already exists"
                ));
    }
}
