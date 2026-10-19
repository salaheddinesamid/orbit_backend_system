package com.orbit.orbit_banking.modules.authentication.service;

import com.orbit.orbit_banking.modules.authentication.dto.LoginDto;
import com.orbit.orbit_banking.modules.authentication.dto.LoginResponseDto;
import com.orbit.orbit_banking.modules.authentication.exceptions.AccountNotFoundException;
import com.orbit.orbit_banking.modules.authentication.exceptions.InvalidCredentialsException;

public interface AuthenticationService {

    LoginResponseDto authenticate(LoginDto loginDto) throws InvalidCredentialsException, AccountNotFoundException;
}
