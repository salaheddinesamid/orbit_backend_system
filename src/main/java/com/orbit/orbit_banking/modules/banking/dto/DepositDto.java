package com.orbit.orbit_banking.modules.banking.dto;

import java.math.BigDecimal;

public record DepositDto(
        String accountNumber,
        BigDecimal amount,
        String channel

) {}
