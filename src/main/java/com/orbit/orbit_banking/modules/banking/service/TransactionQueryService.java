package com.orbit.orbit_banking.modules.banking.service;

import com.orbit.orbit_banking.modules.banking.dto.TransactionResponse;

import java.util.List;

public interface TransactionQueryService {

    /**
     *
     * @param nationalId
     * @return
     */
    List<TransactionResponse> getAllTransactions(String nationalId);
}
