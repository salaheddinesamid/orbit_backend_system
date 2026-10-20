package com.orbit.orbit_banking.modules.accounts.init;

import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import com.orbit.orbit_banking.modules.authentication.repository.UserAccountRepository;
import com.orbit.orbit_banking.modules.authentication.service.implementation.UserAccountCreationServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserAccountCreationServiceImpl userAccountCreationService;
    private final UserAccountRepository userAccountRepository;

    public AdminInitializer(UserAccountCreationServiceImpl userAccountCreationService, UserAccountRepository userAccountRepository) {
        this.userAccountCreationService = userAccountCreationService;
        this.userAccountRepository = userAccountRepository;
    }

    private void initializeAdmin(){
        UserAccount admin = userAccountCreationService
                .createUserAccount(
                        "super_admin23390",
                        "admin2390",
                        List.of("ADMIN")
                );
    }

    @Override
    public void run(String... args) throws Exception {
        if(userAccountRepository.count() == 0) {
            initializeAdmin();
        }
    }
}
