package com.orbit.orbit_banking.modules.accounts.service;

import com.orbit.orbit_banking.modules.accounts.dto.BankAccountDetails;
import com.orbit.orbit_banking.modules.accounts.model.BankAccount;

import java.util.UUID;

public interface AccountApprovalService {
    /**
     *
     * @param requestId
     */
    void preApprove(UUID requestId);

    /**
     *
     * @param accountId
     */
    BankAccountDetails approve(UUID accountId);

    /**
     *
     * @param requestId
     * @return
     */
    BankAccount postApprove(UUID requestId);
}
