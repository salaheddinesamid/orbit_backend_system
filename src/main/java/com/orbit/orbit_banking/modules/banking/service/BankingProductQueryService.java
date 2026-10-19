package com.orbit.orbit_banking.modules.banking.service;

import com.orbit.orbit_banking.modules.accounts.model.AccountProduct;

import java.util.List;

public interface BankingProductQueryService {

    List<AccountProduct> getAllAccountProducts();
}
