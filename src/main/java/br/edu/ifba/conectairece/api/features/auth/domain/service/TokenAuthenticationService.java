package br.edu.ifba.conectairece.api.features.auth.domain.service;


import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.conectairece.api.features.user.domain.model.User;
import br.edu.ifba.conectairece.api.features.user.domain.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Service responsible for validating the JWT token from the request
 * and authenticating the user based on it. Also handles CORS headers
 * manually in case authentication fails.
 *
 * Used as part of the JWT authentication filter process.
 *
 * @author Jorge Roberto
 */
@Service
public class TokenAuthenticationService {

    @Value("${api.security.token.secret}")
    private String secretKey;

    @Autowired
    private IUserRepository userRepository;

    /**
     * Extracts and validates the token from the HTTP request.
     * If valid, returns the user's authentication.
     *
     * @param request the HTTP request
     * @param response the HTTP response (used for CORS if necessary)
     * @return the user's authentication or null
     */
    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = getTokenFromRequest(request);

        if (token != null) {
            String username = validateToken(token);

            if (username != null) {
                UserDetails userDetails = loadUserByUsername(username);
                if (userDetails != null) {
                    return new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                } else {
                    throw new UsernameNotFoundException("User not found!");
                }
            }
        }

        allowCors(response);
        return null;
    }

    /**
     * Loads user details by email from the repository.
     *
     * @param email the user's email
     * @return user details as {@link UserDetails}
     */
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    /**
     * Extracts the token from the Authorization header in the request.
     *
     * @param request the HTTP request
     * @return the JWT token or null
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Validates the JWT token using the secret key and issuer.
     *
     * @param token the token to validate
     * @return the user's email if valid, otherwise null
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    /**
     * Finds a user by their email in the database.
     *
     * @param email the user's email
     * @return the user found
     */
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    /**
     * Adds CORS permissions to the HTTP response
     * when the request fails authentication.
     *
     * @param response the HTTP response
     */
    private void allowCors(HttpServletResponse response) {
        final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
        final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
        final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
        final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";

        List<String> headers = new ArrayList<>(List.of(
                ACCESS_CONTROL_ALLOW_ORIGIN,
                ACCESS_CONTROL_ALLOW_HEADERS,
                ACCESS_CONTROL_ALLOW_METHODS,
                ACCESS_CONTROL_REQUEST_HEADERS));

        headers.forEach(h -> {
            if (response.getHeader(h) == null)
                response.setHeader(h, "*");
        });
    }
}
