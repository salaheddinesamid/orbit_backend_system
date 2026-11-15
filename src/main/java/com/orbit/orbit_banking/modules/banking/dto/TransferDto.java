package com.orbit.orbit_banking.modules.banking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransferDto {

    @NotNull(message = "")
    private String beneficiaryRIB;
    @NotNull(message = "")
    private String senderAccountNumber;
    @NotNull(message = "")
    private BigDecimal amount;
    private Date transferDate;
}
