package com.orbit.orbit_banking.modules.authentication.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDto {

    @NotNull(message = "Username is required for authentication")
    private String username;
    @NotNull(message = "Password is required for authentication")
    private String password;
}
