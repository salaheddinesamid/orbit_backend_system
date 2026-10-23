package com.orbit.orbit_banking.modules.authentication.service.implementation;

import com.orbit.orbit_banking.modules.authentication.exceptions.UserAccountAlreadyExistsException;
import com.orbit.orbit_banking.modules.authentication.model.Role;
import com.orbit.orbit_banking.modules.authentication.model.RoleName;
import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import com.orbit.orbit_banking.modules.authentication.repository.RoleRepository;
import com.orbit.orbit_banking.modules.authentication.repository.UserAccountRepository;
import com.orbit.orbit_banking.modules.authentication.service.UserAccountCreationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@Service
public class UserAccountCreationServiceImpl implements UserAccountCreationService {
    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountCreationServiceImpl(UserAccountRepository userAccountRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserAccount createUserAccount(String username, String password, List<String> roles) {
        if(userAccountRepository.existsByUsername(username)){
            throw new UserAccountAlreadyExistsException(
                    String.format("User account with username %s already exists", username)
            );
        }

        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(username);
        userAccount.setHashedPassword(passwordEncoder.encode(password));

        // Fetch and assign roles
        roles
                .forEach(role ->{
                    try {
                        Role userRole = roleRepository.findByRoleName(RoleName.valueOf(role))
                                .orElseThrow(() -> new RoleNotFoundException(role));

                        userAccount.getRoles().add(userRole);
                    } catch (RoleNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });

        // Save and return user account
        return userAccountRepository.save(userAccount);
    }
}
