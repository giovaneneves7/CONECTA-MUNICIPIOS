package br.edu.ifba.conectairece.api.features.auth.domain.service;

import br.edu.ifba.conectairece.api.features.auth.domain.dto.request.UserLoginRequestDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.dto.request.UserRegisterRequestDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.dto.response.UserLoginResponseDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.enums.UserStatus;
import br.edu.ifba.conectairece.api.features.auth.domain.model.Role;
import br.edu.ifba.conectairece.api.features.citizen.domain.model.Citizen;
import br.edu.ifba.conectairece.api.features.user.domain.model.User;
import br.edu.ifba.conectairece.api.features.auth.domain.repository.RoleRepository;
import br.edu.ifba.conectairece.api.features.user.domain.repository.UserRepository;
import br.edu.ifba.conectairece.api.features.person.domain.model.Person;
import br.edu.ifba.conectairece.api.features.person.domain.repository.PersonRepository;
import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import br.edu.ifba.conectairece.api.features.profile.domain.repository.ProfileRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Service responsible for user authentication.
 * Handles login and registration with JWT token generation.
 *
 * @author Jorge Roberto
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private final ObjectMapperUtil objectMapperUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final PersonRepository personRepository;
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;

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
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.INVALID_CREDENTIALS.getMessage()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(BusinessExceptionMessage.INVALID_CREDENTIALS.getMessage());
        }

        if (user.getStatus() == UserStatus.INACTIVE) {
            throw new BusinessException(BusinessExceptionMessage.INVALID_CREDENTIALS.getMessage());
        }

        if (user.getProfiles() != null && !user.getProfiles().isEmpty()) {
            user.setActiveProfile(user.getProfiles().get(0));
        } else {
            throw new BusinessException(BusinessExceptionMessage.USER_WITHOUT_PROFILES.getMessage());
        }

        String token = tokenService.generateToken(user);

        return new UserLoginResponseDTO(user.getId(), user.getEmail(), user.getUsername(), token, user.getStatus());
    }

    /**
     * Registers a new user in the system.
     *
     * @param request DTO with user registration data
     * @return DTO with JWT token and the new authenticated user's data
     */
    public UserLoginResponseDTO register(UserRegisterRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException(BusinessExceptionMessage.ATTRIBUTE_VALUE_ALREADY_EXISTS.getMessage());
        }

        Person person = personRepository.save(objectMapperUtil.map(request.getPerson(), Person.class));

        User user = objectMapperUtil.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setPerson(person);
        user.setProfiles(new ArrayList<>());
        user = userRepository.save(user);

        Role role = roleRepository.findByName("ROLE_CITIZEN")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ROLE_CITIZEN");
                    newRole.setDescription("Role for citizen");
                    return roleRepository.save(newRole);
                });

        Profile profile = new Citizen();
        profile.setType("Cidadão");
        profile.setRole(role);
        profile.setUser(user);
        profile = profileRepository.save(profile);

        user.getProfiles().add(profile);
        user.setActiveProfile(profile);
        userRepository.save(user);

        String token = tokenService.generateToken(user);

        return new UserLoginResponseDTO(user.getId(), user.getEmail(), user.getUsername(), token, user.getStatus());
    }
}
