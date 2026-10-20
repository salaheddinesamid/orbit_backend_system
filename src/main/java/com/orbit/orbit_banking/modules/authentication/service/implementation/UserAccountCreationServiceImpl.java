package com.orbit.orbit_banking.modules.authentication.service.implementation;

import com.orbit.orbit_banking.modules.authentication.exceptions.UserAccountAlreadyExistsException;
import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import com.orbit.orbit_banking.modules.authentication.repository.UserAccountRepository;
import com.orbit.orbit_banking.modules.authentication.service.UserAccountCreationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountCreationServiceImpl implements UserAccountCreationService {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountCreationServiceImpl(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserAccount createUserAccount(String username, String password, List<String> roles) {
        if(userAccountRepository.existsByUsername(username)){
            throw new UserAccountAlreadyExistsException(
                    String.format("User account with username %s already exists", username)
            );
        }

        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(username);
        userAccount.setHashedPassword(passwordEncoder.encode(password));



        // Save and return user account
        return userAccountRepository.save(userAccount);
    }
}
