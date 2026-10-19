package com.orbit.orbit_banking.modules.authentication.controller;

import com.orbit.orbit_banking.common.dto.ApiResponse;
import com.orbit.orbit_banking.modules.authentication.dto.LoginDto;
import com.orbit.orbit_banking.modules.authentication.dto.LoginResponseDto;
import com.orbit.orbit_banking.modules.authentication.service.implementation.PasswordFullAuthenticationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final PasswordFullAuthenticationServiceImpl authenticationService;

    public AuthenticationController(PasswordFullAuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<LoginResponseDto>> authenticate(
            @RequestBody LoginDto loginDto
    ){
        LoginResponseDto loginResponseDto = authenticationService.authenticate(loginDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(
                    loginResponseDto
                ));
    }
}
