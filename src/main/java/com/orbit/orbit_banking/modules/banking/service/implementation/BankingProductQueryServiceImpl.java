package com.orbit.orbit_banking.modules.banking.service.implementation;

import com.orbit.orbit_banking.modules.accounts.model.AccountProduct;
import com.orbit.orbit_banking.modules.accounts.repository.AccountProductRepository;
import com.orbit.orbit_banking.modules.banking.service.BankingProductQueryService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BankingProductQueryServiceImpl implements BankingProductQueryService {
    private final AccountProductRepository accountProductRepository;

    public BankingProductQueryServiceImpl(AccountProductRepository accountProductRepository) {
        this.accountProductRepository = accountProductRepository;
    }

    @Override
    @Cacheable("products")
    public List<AccountProduct> getAllAccountProducts() {
        return accountProductRepository.findAll();
    }
}
