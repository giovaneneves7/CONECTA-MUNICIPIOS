package br.edu.ifba.conectairece.api.features.auth.domain.service;

import br.edu.ifba.conectairece.api.features.auth.domain.dto.request.UserLoginRequestDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.dto.request.UserRegisterRequestDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.dto.response.UserLoginResponseDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.enums.UserStatus;
import br.edu.ifba.conectairece.api.features.auth.domain.model.Role;
import br.edu.ifba.conectairece.api.features.auth.domain.model.User;
import br.edu.ifba.conectairece.api.features.auth.domain.repository.RoleRepository;
import br.edu.ifba.conectairece.api.features.auth.domain.repository.UserRepository;
import br.edu.ifba.conectairece.api.features.person.domain.model.Person;
import br.edu.ifba.conectairece.api.features.person.domain.repository.PersonRepository;
import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import br.edu.ifba.conectairece.api.features.profile.domain.repository.ProfileRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
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


        String token = tokenService.generateToken(user);

        return new UserLoginResponseDTO(user.getEmail(), token, user.getStatus());
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
            throw new BusinessException(BusinessExceptionMessage.ATTRIBUTE_VALUE_ALREADY_EXISTS.getMessage());
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setUsername(request.getUsername());

        Person person = new Person();
        person.setCpf(request.getPerson().getCpf());
        person.setFullName(request.getPerson().getFullName());
        person.setBirthDate(request.getPerson().getBirthDate());
        person.setGender(request.getPerson().getGender());
        //Salvando no banco de dados para receber o UUID
        person = personRepository.save(person);
        user.setPerson(person);

        /*
         * TODO: Implementar lógica de perfil
         *  Por enquanto está sem lógica para testes
         */
        Profile profile = new Profile();
        profile.setType("Teste");
        /*
         * TODO: Implementar lógica de Role
         *  Por enquanto está sem lógica para testes
         */
        Role role = new Role();
        role.setName("ROLE_CITIZEN");
        role.setDescription("citizen test");

        role = roleRepository.save(role);

        profile.setRole(role);
        user.setProfile(profileRepository.save(profile));

        //Salvando usuario
        user = userRepository.save(user);

        String token = tokenService.generateToken(user);

        return new UserLoginResponseDTO(user.getEmail(), token, user.getStatus());
    }
}
