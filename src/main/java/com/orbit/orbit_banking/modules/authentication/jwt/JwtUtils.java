package com.orbit.orbit_banking.modules.authentication.jwt;

import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {

    private final String SECRET = "a20c4e8221d2a2c3d1f7309bf95a1a3c79f7fc5a3ace72833fc38206a0912353";

    /**
     *
     * @param token
     * @return
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     *
     * @param token
     * @return
     */
    public Claims extractClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     *
     * @param token
     * @param claimsResolver
     * @return
     * @param <T>
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    /** * Validates the JWT token.
     *
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try{
            Jwts
                    .parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e) {
            log.info("Invalid JWT token : {}", token);
            log.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            //throw new TokenExpiredException("Token has expired");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("{}{}", "JWT token compact of handler are invalid.", token);

            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     *
     * @param userAccount
     * @return
     */
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
