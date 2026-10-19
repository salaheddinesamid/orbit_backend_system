package com.orbit.orbit_banking.modules.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private final String accessToken;
    private final String tokenType;
    private final long expiresIn;
    private final List<String> roles;
}
