package br.com.cidadesinteligentes.infraestructure.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.request.UserLoginRequestDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.request.UserRegisterRequestDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UserLoginResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.UserStatus;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model.Role;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cidadao.model.Citizen;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.User;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.repository.IRoleRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUserRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.model.Person;
import br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.repository.IPersonRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Profile;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.IProfileRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;

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
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final IPersonRepository personRepository;
    private final IProfileRepository profileRepository;
    private final IRoleRepository roleRepository;

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
