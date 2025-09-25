package br.edu.ifba.conectairece.api.controller.v1.auth;

import br.edu.ifba.conectairece.api.features.auth.domain.dto.request.UserLoginRequestDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.dto.request.UserRegisterRequestDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.dto.response.UserLoginResponseDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.service.AuthenticationService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for authentication endpoints.
 * Includes user login and registration.
 *
 * @author Jorge Roberto
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    /**
     * Endpoint to authenticate a user with email and password.
     *
     * @param body DTO containing email and password.
     * @return Response with authenticated user data or appropriate error.
     */
    @PostMapping("/sessions/session")
    public ResponseEntity<?> loginUser(@RequestBody @Valid UserLoginRequestDTO body) {
        try {
            UserLoginResponseDTO response = authenticationService.login(body);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    /**
     * Endpoint to register a new user.
     *
     * @param body DTO containing user registration data.
     * @return Response with registered user data.
     */
    @PostMapping("/users/user")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegisterRequestDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(body));
    }
}
