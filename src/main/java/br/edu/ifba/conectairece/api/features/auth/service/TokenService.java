package br.edu.ifba.conectairece.api.features.auth.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import br.edu.ifba.conectairece.api.features.auth.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.annotation.PostConstruct;

/**
 * Service responsible for generating JWT tokens.
 *
 * Uses the Auth0 library to create tokens based on the user's email.
 * The secret key, expiration time, and timezone are configured via application properties.
 *
 * @author Jorge Roberto
 */

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secretKey;

    @Value("${api.security.token.expiration-hours}")
    private Long expirationHours;

    @Value("${api.security.token.timezone}")
    private String timeZone;

    private Algorithm algorithm;

    /**
     * Initializes the HMAC signing algorithm with the secret key.
     * This method runs after dependency injection is complete.
     */
    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    /**
     * Generates a JWT token for the provided user.
     *
     * @param user the authenticated user
     * @return the JWT token as {@link String}
     * @throws RuntimeException in case of generation failure
     */
    public String generateToken(User user) {
        try {
            Date issuedAt = new Date();
            Date expirationDate = generateExpirationDate();

            return JWT.create()
                    .withSubject(user.getEmail())
                    .withIssuer("login-auth-api")
                    .withIssuedAt(issuedAt)
                    .withExpiresAt(expirationDate)
                    .sign(algorithm);

        } catch (Exception e) {
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    /**
     * Generates the expiration date for the token based on current time
     * and the configured number of expiration hours.
     *
     * @return the expiration {@link Date}
     */
    private Date generateExpirationDate() {
        return Date.from(LocalDateTime.now()
                .plusHours(expirationHours)
                .toInstant(ZoneOffset.of(timeZone)));
    }
}
