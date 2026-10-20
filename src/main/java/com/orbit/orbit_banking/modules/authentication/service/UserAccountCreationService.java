package com.orbit.orbit_banking.modules.authentication.service;

import com.orbit.orbit_banking.modules.authentication.model.UserAccount;

import java.util.List;

public interface UserAccountCreationService {

    UserAccount createUserAccount(
            String username,
            String password,
            List<String> roles
    );
}
