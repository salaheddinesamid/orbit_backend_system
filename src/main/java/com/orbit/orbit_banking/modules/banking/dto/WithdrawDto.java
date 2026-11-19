package com.orbit.orbit_banking.modules.banking.dto;

import java.math.BigDecimal;

public record WithdrawDto(
        String accountNumber,
        BigDecimal amount,
        String channel
) {}
