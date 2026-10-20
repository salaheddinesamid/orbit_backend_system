package com.orbit.orbit_banking.modules.accounts.service;

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
    BankAccount approve(UUID accountId);

    /**
     *
     * @param requestId
     * @return
     */
    BankAccount postApprove(UUID requestId);
}
