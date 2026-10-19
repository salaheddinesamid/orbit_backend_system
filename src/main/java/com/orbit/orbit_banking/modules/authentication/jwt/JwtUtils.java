package com.orbit.orbit_banking.modules.authentication.jwt;

import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtils {

    private final String SECRET = "5367566859703373367639792F423F452848284D6251655468576D5A71347437";


    public String generateAccessToken(UserAccount userAccount) {
        return Jwts
                .builder()
                .setSubject(userAccount.getUsername())
                .setIssuer("orbit")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(10, ChronoUnit.MINUTES)))
                .claim("roles", userAccount.getRoles())
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
}
