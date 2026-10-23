package com.orbit.orbit_banking.modules.accounts.init;

import com.orbit.orbit_banking.modules.authentication.model.Role;
import com.orbit.orbit_banking.modules.authentication.model.RoleName;
import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import com.orbit.orbit_banking.modules.authentication.repository.RoleRepository;
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
    private final RoleRepository roleRepository;


    public AdminInitializer(UserAccountCreationServiceImpl userAccountCreationService, UserAccountRepository userAccountRepository, RoleRepository roleRepository) {
        this.userAccountCreationService = userAccountCreationService;
        this.userAccountRepository = userAccountRepository;
        this.roleRepository = roleRepository;
    }

    private void initRoles(){
        Role adminRole = new Role();
        adminRole.setRoleName(RoleName.ADMIN);

        Role customer = new Role();
        customer.setRoleName(RoleName.CUSTOMER);

        Role AgencyBanker = new Role();
        AgencyBanker.setRoleName(RoleName.AGENCY_BANKER);

        Role IT = new Role();
        IT.setRoleName(RoleName.IT_ADMIN);

        // Save roles
        roleRepository.saveAll(List.of(adminRole, customer, AgencyBanker, IT));
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
        if (roleRepository.count() == 0) {
            initRoles();
        }
        if(userAccountRepository.count() == 0) {
            initializeAdmin();
        }
    }
}
