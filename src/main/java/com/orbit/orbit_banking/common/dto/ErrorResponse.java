package com.orbit.orbit_banking.common.dto;

public record ErrorResponse(
        int status,
        String message
) {
}
