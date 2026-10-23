package com.orbit.orbit_banking.authentication.unit;

import com.orbit.orbit_banking.modules.authentication.exceptions.UserAccountAlreadyExistsException;
import com.orbit.orbit_banking.modules.authentication.model.Role;
import com.orbit.orbit_banking.modules.authentication.model.RoleName;
import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import com.orbit.orbit_banking.modules.authentication.repository.RoleRepository;
import com.orbit.orbit_banking.modules.authentication.repository.UserAccountRepository;
import com.orbit.orbit_banking.modules.authentication.service.implementation.UserAccountCreationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserCreationUnitTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserAccountCreationServiceImpl userAccountCreationService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNewUserSuccess(){

        Role role = new Role();
        role.setRoleName(RoleName.ADMIN);

        when(userAccountRepository.existsByUsername(anyString()))
                .thenReturn(false);

        when(userAccountRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(roleRepository.findByRoleName(RoleName.ADMIN))
                .thenReturn(Optional.of(role));

        UserAccount result = userAccountCreationService
                .createUserAccount("test_user","test_password", List.of("ADMIN"));

        assertNotNull(result);
        assertEquals(result.getUsername(), "test_user");
        assertEquals(result.getRoles().size(), 1);
    }

    @Test
    void testCreateNewUserThrowsUserAlreadyExistsException(){

        when(userAccountRepository.existsByUsername(anyString()))
                .thenReturn(true);

        assertThrows(UserAccountAlreadyExistsException.class, ()-> userAccountCreationService
                .createUserAccount("test_user","test_password", List.of("ADMIN")));
    }

    @Test
    void testCreateNewUserThrowsRoleNotFoundException(){}
}
