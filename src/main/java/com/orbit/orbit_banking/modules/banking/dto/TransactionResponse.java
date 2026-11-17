package com.orbit.orbit_banking.modules.banking.dto;

import com.orbit.orbit_banking.modules.banking.model.TransactionStatus;
import com.orbit.orbit_banking.modules.banking.model.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID transactionId,
        String reference,
        TransactionType type,
        BigDecimal amount,
        BigDecimal balanceBefore,
        BigDecimal balanceAfter,
        TransactionStatus status,
        LocalDateTime createdAt
) {}
