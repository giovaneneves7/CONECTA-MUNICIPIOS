package br.edu.ifba.conectairece.api.features.auth.service;

import br.edu.ifba.conectairece.api.features.auth.domain.dto.request.UserLoginRequestDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.dto.request.UserRegisterRequestDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.dto.response.UserLoginResponseDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.enums.Role;
import br.edu.ifba.conectairece.api.features.auth.domain.enums.UserStatus;
import br.edu.ifba.conectairece.api.features.auth.domain.model.User;
import br.edu.ifba.conectairece.api.features.auth.domain.repository.UserRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link AuthenticationService}.
 * These tests validate the authentication and registration flows using mocked dependencies.
 *
 * Technologies: JUnit 5 + Mockito
 *
 * @author Jorge Roberto
 */
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("Should login with valid credentials")
    void login_withValidCredentials_shouldReturnToken() {
        UserLoginRequestDTO request = new UserLoginRequestDTO("test@gmail.com", "123456");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword("encodedPassword");
        user.setRole(Role.CITIZEN);
        user.setStatus(UserStatus.ACTIVE);

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "encodedPassword")).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn("fake-jwt-token");

        UserLoginResponseDTO response = authenticationService.login(request);

        assertNotNull(response);
        assertEquals("test@gmail.com", response.getEmail());
        assertEquals("fake-jwt-token", response.getToken());
        assertEquals(Role.CITIZEN, response.getRole());
        assertEquals(UserStatus.ACTIVE, response.getUserStatus());
    }

    @Test
    @DisplayName("Should fail login with incorrect password")
    void login_withInvalidCredentials_shouldThrowException() {
        UserLoginRequestDTO request = new UserLoginRequestDTO("test@gmail.com", "wrong");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword("encodedPassword");
        user.setStatus(UserStatus.ACTIVE);

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encodedPassword")).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> authenticationService.login(request));
    }

    @Test
    @DisplayName("Should fail login for inactive user")
    void login_withInactiveUser_shouldThrowException() {
        UserLoginRequestDTO request = new UserLoginRequestDTO("test@gmail.com", "123456");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword("encodedPassword");
        user.setStatus(UserStatus.INACTIVE);

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "encodedPassword")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authenticationService.login(request));
    }

    @Test
    @DisplayName("Should fail login when user not found")
    void login_withNonExistentUser_shouldThrowException() {
        UserLoginRequestDTO request = new UserLoginRequestDTO("ghost@gmail.com", "123456");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class, () -> authenticationService.login(request));
    }

    @Test
    @DisplayName("Should register user successfully")
    void register_withValidUser_shouldSaveAndReturnToken() {
        UserRegisterRequestDTO request = new UserRegisterRequestDTO();
        request.setEmail("newuser@gmail.com");
        request.setPassword("123456");
        request.setName("New User");
        request.setRole(Role.CITIZEN);

        when(userRepository.findByEmail("newuser@gmail.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("encoded123");
        when(tokenService.generateToken(any(User.class))).thenReturn("token123");

        User savedUser = new User();
        savedUser.setEmail("newuser@gmail.com");
        savedUser.setPassword("encoded123");
        savedUser.setRole(Role.CITIZEN);
        savedUser.setStatus(UserStatus.ACTIVE);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserLoginResponseDTO response = authenticationService.register(request);

        assertNotNull(response);
        assertEquals("newuser@gmail.com", response.getEmail());
        assertEquals("token123", response.getToken());
    }

    @Test
    @DisplayName("Should fail registration with duplicate email")
    void register_withDuplicateEmail_shouldThrowException() {
        UserRegisterRequestDTO request = new UserRegisterRequestDTO();
        request.setEmail("existing@gmail.com");
        request.setPassword("123456");
        request.setName("Existing User");
        request.setRole(Role.CITIZEN);

        when(userRepository.findByEmail("existing@gmail.com")).thenReturn(Optional.of(new User()));

        assertThrows(IllegalStateException.class, () -> authenticationService.register(request));
    }
}