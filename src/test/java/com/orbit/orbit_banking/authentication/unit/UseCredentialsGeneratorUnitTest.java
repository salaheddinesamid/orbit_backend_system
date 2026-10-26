package com.orbit.orbit_banking.authentication.unit;

import com.orbit.orbit_banking.modules.accounts.utils.UserCredentialsGenerator;
import com.orbit.orbit_banking.modules.authentication.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UseCredentialsGeneratorUnitTest {


    @Mock
    private UserAccountRepository userAccountRepository;;

    @InjectMocks
    private UserCredentialsGenerator userCredentialsGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNewCredentialsSuccess() {
        Map<String, String> result = userCredentialsGenerator
                .generateUserCredentials(
                        "John",
                        "Doe",
                        "TE209"
                );
        assertNotNull(result);
        assertEquals(result.get("username"), "JDTE20");
    }

    @Test
    void testCreateNewCredentialsThrowsUserAlreadyExistsException() {}

    @Test
    void testCreateNewCredentialsThrowsUserNotFoundException() {}
}
