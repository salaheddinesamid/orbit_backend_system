package com.orbit.orbit_banking.modules.authentication.service.implementation;

import com.orbit.orbit_banking.modules.authentication.dto.LoginDto;
import com.orbit.orbit_banking.modules.authentication.dto.LoginResponseDto;
import com.orbit.orbit_banking.modules.authentication.exceptions.AccountNotFoundException;
import com.orbit.orbit_banking.modules.authentication.exceptions.InvalidCredentialsException;
import com.orbit.orbit_banking.modules.authentication.exceptions.MaximumAttemptsException;
import com.orbit.orbit_banking.modules.authentication.jwt.JwtUtils;
import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import com.orbit.orbit_banking.modules.authentication.repository.UserAccountRepository;
import com.orbit.orbit_banking.modules.authentication.service.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class PasswordFullAuthenticationServiceImpl implements AuthenticationService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public PasswordFullAuthenticationServiceImpl(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public LoginResponseDto authenticate(LoginDto loginDto) throws InvalidCredentialsException, AccountNotFoundException {
        // Fetch the user details from the database:
        UserAccount userAccount = userAccountRepository
                .findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new AccountNotFoundException(String.format("No account has been found with username: %s", loginDto.getUsername())));
        // Fetch how many attempts, and when the next attempt takes place:
        int attempts = userAccount.getFailedAttempts();
        Date nextAttempt = Date.from(userAccount.getLockedUntil());

        // If the user attempts to authenticate more than 5 times, raise an exception
        if(attempts > 5) {
            throw new MaximumAttemptsException();
        }

        if(nextAttempt.after(Date.from(Instant.now()))) {
            throw new MaximumAttemptsException();
        }

        // Compare the passwords
        if(!passwordEncoder.matches(loginDto.getPassword(), userAccount.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        // Generate JWT access token
        String accessToken = jwtUtils.generateAccessToken(
                userAccount
        );

        return new LoginResponseDto(
                accessToken,
                "Bearer",
                0,
                userAccount.getRoles()
        );
    }
}
