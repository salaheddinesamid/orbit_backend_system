package com.orbit.orbit_banking.modules.authentication.dto;

import com.orbit.orbit_banking.modules.authentication.model.Role;
import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import lombok.*;

import java.time.Instant;
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
    }
    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private final UserDetails user;
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
