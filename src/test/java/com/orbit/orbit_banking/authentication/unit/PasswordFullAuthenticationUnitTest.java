package com.orbit.orbit_banking.authentication.unit;

import com.orbit.orbit_banking.modules.authentication.dto.LoginDto;
import com.orbit.orbit_banking.modules.authentication.dto.LoginResponseDto;
import com.orbit.orbit_banking.modules.authentication.exceptions.MaximumAttemptsException;
import com.orbit.orbit_banking.modules.authentication.jwt.JwtUtils;
import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import com.orbit.orbit_banking.modules.authentication.repository.UserAccountRepository;
import com.orbit.orbit_banking.modules.authentication.service.implementation.PasswordFullAuthenticationServiceImpl;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class PasswordFullAuthenticationUnitTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private PasswordFullAuthenticationServiceImpl authenticationService;

    private UserAccount userAccount;
    private LoginDto loginDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        userAccount = new UserAccount();
        userAccount.setUsername("testUsername");
        userAccount.setHashedPassword(passwordEncoder.encode("testPassword"));
        userAccount.setFailedAttempts(0);
        userAccount.setLockedUntil(Date.from(Instant.now().minus(10, ChronoUnit.MINUTES)).toInstant());

        loginDto = new LoginDto();
        loginDto.setUsername("testUsername");
        loginDto.setPassword("testPassword");
    }

    @Test
    void testPasswordFullAuthenticationSuccess() {
        when(userAccountRepository.findByUsername(loginDto.getUsername()))
                .thenReturn(Optional.ofNullable(userAccount));

        when(passwordEncoder.matches(loginDto.getPassword(), userAccount.getPassword()))
                .thenReturn(true);

        LoginResponseDto responseDto = authenticationService
                .authenticate(loginDto);

        assertNotNull(responseDto);
    }

    @Test
    void testPasswordFullAuthenticationThrowsInvalidCredentials(){}

    @Test
    void testPasswordFullAuthenticationThrowsMaximumAttempts(){
        userAccount.setFailedAttempts(6);
        userAccount.setLockedUntil(Instant.now().plus(10, ChronoUnit.MINUTES));

        when(userAccountRepository.findByUsername(loginDto.getUsername()))
                .thenReturn(Optional.ofNullable(userAccount));

        when(passwordEncoder.matches(loginDto.getPassword(), userAccount.getPassword()))
                .thenReturn(true);

        assertThrows(MaximumAttemptsException.class, () -> authenticationService.authenticate(loginDto));
    }
}
