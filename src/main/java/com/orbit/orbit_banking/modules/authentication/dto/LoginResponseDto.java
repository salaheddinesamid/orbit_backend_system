package com.orbit.orbit_banking.modules.authentication.dto;

import com.orbit.orbit_banking.modules.authentication.model.Role;
import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import com.orbit.orbit_banking.modules.customer.model.Customer;
import com.orbit.orbit_banking.modules.customer.model.CustomerStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginResponseDto {
    public LoginResponseDto(
            String accessToken,
            String tokenType,
            long expiresIn,
            UserAccount user
    ){
        this.user = new UserDetails(user);
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.customer = new CustomerDetails(user.getCustomer());
    }
    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private final UserDetails user;
    private CustomerDetails customer;
}

@Data
class UserDetails {
    private Long userId;
    private String username;
    private Set<Role> roles;
    private boolean enabled = true;
    private int failedAttempts;
    private Instant lockedUntil;
    private boolean mfaEnabled;
    private String mfaSecretEncrypted;

    public UserDetails(UserAccount userAccount){
        this.userId = userAccount.getUserId();
        this.username = userAccount.getUsername();
        this.roles = userAccount.getRoles();
        this.enabled = userAccount.isEnabled();
        this.failedAttempts = userAccount.getFailedAttempts();
        this.lockedUntil = userAccount.getLockedUntil();
        this.mfaEnabled = userAccount.isMfaEnabled();
        this.mfaSecretEncrypted = userAccount.getMfaSecretEncrypted();
    }
}

@Data
class CustomerDetails{

    public CustomerDetails(Customer customer){
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.phone = customer.getPhone();
        this.address = customer.getAddress();
    }

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String nationalID;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;
    private String status;

}