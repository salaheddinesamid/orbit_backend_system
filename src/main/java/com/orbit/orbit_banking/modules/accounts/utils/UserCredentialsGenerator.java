package com.orbit.orbit_banking.modules.accounts.utils;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserCredentialsGenerator {

    public Map<String, String> generateUserCredentials(
            String firstName, String lastName, String nationalID
    ) {

        String username = String.format("%s%s%s", firstName.charAt(0), lastName.charAt(0), nationalID.substring(0, 4));
        String password = String.format("%s%s%s", firstName.charAt(0), lastName.charAt(0), nationalID.hashCode());
        return Map.of("username", username, "password", password);
    }
}
