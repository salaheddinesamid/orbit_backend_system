package com.orbit.orbit_banking.modules.accounts.dto;

import lombok.Data;

@Data
public class ApplicationRequestUpdateDto {

    private String newStatus;
    private String comment;
}
