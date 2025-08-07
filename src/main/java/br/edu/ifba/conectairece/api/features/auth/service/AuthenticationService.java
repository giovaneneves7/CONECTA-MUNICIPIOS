package br.edu.ifba.conectairece.api.features.auth.service;

import br.edu.ifba.conectairece.api.features.auth.domain.dto.request.UserLoginRequestDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.dto.request.UserRegisterRequestDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.dto.response.UserLoginResponseDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.enums.UserStatus;
import br.edu.ifba.conectairece.api.features.auth.domain.model.User;
import br.edu.ifba.conectairece.api.features.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service responsible for user authentication.
 * Handles login and registration with JWT token generation.
 *
 * @author Jorge Roberto
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    /**
     * Logs in a user based on provided email and password.
     *
     * @param request object containing the user's credentials
     * @return DTO with JWT token and basic user data
     * @throws BadCredentialsException if email or password are incorrect
     */
    @Transactional
    public UserLoginResponseDTO login(UserLoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email ou senha inválidos."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Email ou senha inválidos.");
        }

        if (user.getStatus() == UserStatus.INACTIVE) {
            throw new BadCredentialsException("Usuário suspenso ou inativo.");
        }


        String token = tokenService.generateToken(user);

        return new UserLoginResponseDTO(user.getEmail(), token, user.getRole(), user.getStatus());
    }

    /**
     * Registers a new user in the system.
     *
     * @param request DTO with user registration data
     * @return DTO with JWT token and the new authenticated user's data
     */
    @Transactional
    public UserLoginResponseDTO register(UserRegisterRequestDTO request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("E-mail já cadastrado");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setStatus(UserStatus.ACTIVE);

        user = userRepository.save(user);

        String token = tokenService.generateToken(user);

        return new UserLoginResponseDTO(user.getEmail(), token, user.getRole(), user.getStatus());
    }
}
