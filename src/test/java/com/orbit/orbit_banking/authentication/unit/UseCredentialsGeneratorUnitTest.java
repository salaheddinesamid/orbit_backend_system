package com.orbit.orbit_banking.authentication.unit;

import com.orbit.orbit_banking.modules.accounts.utils.UserCredentialsGenerator;
import com.orbit.orbit_banking.modules.authentication.repository.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class UseCredentialsGeneratorUnitTest {


    @Mock
    private UserAccountRepository userAccountRepository;;

    @InjectMocks
    private UserCredentialsGenerator userCredentialsGenerator;

    @Test
    void testCreateNewCredentialsSuccess() {}

    @Test
    void testCreateNewCredentialsThrowsUserAlreadyExistsException() {}

    @Test
    void testCreateNewCredentialsThrowsUserNotFoundException() {}
}
