package com.orbit.orbit_banking.modules.accounts.init;

import com.orbit.orbit_banking.modules.accounts.model.AccountProduct;
import com.orbit.orbit_banking.modules.accounts.model.AccountType;
import com.orbit.orbit_banking.modules.accounts.model.Currency;
import com.orbit.orbit_banking.modules.accounts.repository.AccountProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class AccountProductInitializer implements CommandLineRunner {

    private final AccountProductRepository accountProductRepository;

    public AccountProductInitializer(AccountProductRepository accountProductRepository) {
        this.accountProductRepository = accountProductRepository;
    }

    void init(){
        if(accountProductRepository.count() == 0){
            AccountProduct current = new AccountProduct();
            current.setAccountType(AccountType.CURRENT);
            current.setActive(true);
            current.setMinDeposit(BigDecimal.valueOf(100));
            current.setCurrency(Currency.MAD);

            AccountProduct savings = new AccountProduct();
            savings.setAccountType(AccountType.SAVINGS);
            savings.setActive(true);
            savings.setMinDeposit(BigDecimal.valueOf(100));
            savings.setCurrency(Currency.MAD);

            accountProductRepository.save(current);
            accountProductRepository.save(savings);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        init();
    }
}
