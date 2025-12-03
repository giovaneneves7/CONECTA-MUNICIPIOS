package br.com.cidadesinteligentes.infraestructure.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.request.UserLoginRequestDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.request.UserRegisterRequestDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UserLoginResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model.Cargo;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cidadao.model.Cidadao;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.Usuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.repository.ICargoRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUsuarioRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.model.Pessoa;
import br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.repository.IPersonRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.IPerfilRepository;
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
    private final IUsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final IPersonRepository personRepository;
    private final IPerfilRepository profileRepository;
    private final ICargoRepository roleRepository;

    /**
     * Logs in a user based on provided email and password.
     *
     * @param request object containing the user's credentials
     * @return DTO with JWT token and basic user data
     * @throws BadCredentialsException if email or password are incorrect
     */
    @Transactional
    public UserLoginResponseDTO login(UserLoginRequestDTO request) {
        Usuario user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.INVALID_CREDENTIALS.getMessage()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(BusinessExceptionMessage.INVALID_CREDENTIALS.getMessage());
        }

        if (user.getStatus() == StatusUsuario.INATIVO) {
            throw new BusinessException(BusinessExceptionMessage.INVALID_CREDENTIALS.getMessage());
        }

        if (user.getPerfis() != null && !user.getPerfis().isEmpty()) {
            user.setPerfilAtivo(user.getPerfis().get(0));
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

        Pessoa person = personRepository.save(objectMapperUtil.map(request.getPerson(), Pessoa.class));

        Usuario user = objectMapperUtil.map(request, Usuario.class);
        user.setSenha(passwordEncoder.encode(request.getPassword()));
        user.setStatus(StatusUsuario.ATIVO);
        user.setPessoa(person);
        user.setPerfis(new ArrayList<>());
        user = userRepository.save(user);

        Cargo role = roleRepository.findByNome("ROLE_CITIZEN")
                .orElseGet(() -> {
                    Cargo newRole = new Cargo();
                    newRole.setNome("ROLE_CITIZEN");
                    newRole.setDescricao("Role for citizen");
                    return roleRepository.save(newRole);
                });

        Perfil profile = new Cidadao();
        profile.setTipo("Cidadão");
        profile.setRole(role);
        profile.setUsuario(user);
        profile = profileRepository.save(profile);

        user.getPerfis().add(profile);
        user.setPerfilAtivo(profile);
        userRepository.save(user);

        String token = tokenService.generateToken(user);

        return new UserLoginResponseDTO(user.getId(), user.getEmail(), user.getUsername(), token, user.getStatus());
    }
}
