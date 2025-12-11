package br.com.cidadesinteligentes.infraestructure.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.request.UsuarioLoginRequestDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.request.UsuarioCadastrarRequestDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UsuarioLoginResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model.Cargo;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cidadao.model.Cidadao;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.Usuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.repository.ICargoRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUsuarioRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.model.Pessoa;
import br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.repository.IPessoaRepository;
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
 * Service responsável pela autenticação do usuário
 *
 * @author Jorge Roberto
 */
@Service
@RequiredArgsConstructor
public class AutenticacaoService {

    @Autowired
    private final ObjectMapperUtil objectMapperUtil;
    private final IUsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final IPessoaRepository personRepository;
    private final IPerfilRepository profileRepository;
    private final ICargoRepository roleRepository;

    /**
     * @param request objeto contendo credenciais do usuário
     * @return DTO com JWT token e informações básicas do usuário
     * @throws BadCredentialsException se o email ou senha estiverem incorretos
     */
    @Transactional
    public UsuarioLoginResponseDTO login(UsuarioLoginRequestDTO request) {
        Usuario user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.INVALID_CREDENTIALS.getMessage()));

        if (!passwordEncoder.matches(request.getSenha(), user.getPassword())) {
            throw new BusinessException(BusinessExceptionMessage.INVALID_CREDENTIALS.getMessage());
        }

        if (user.getStatus() == StatusUsuario.INATIVO) {
            throw new BusinessException(BusinessExceptionMessage.INVALID_CREDENTIALS.getMessage());
        }

        if (user.getPerfis() != null && !user.getPerfis().isEmpty()) {
            user.setTipoAtivo(user.getPerfis().get(0));
        } else {
            throw new BusinessException(BusinessExceptionMessage.USER_WITHOUT_PROFILES.getMessage());
        }

        String token = tokenService.generateToken(user);

        return new UsuarioLoginResponseDTO(user.getId(), user.getEmail(), user.getUsername(), token, user.getStatus());
    }

    /**
     * Registra um novo usuário no sistema
     *
     * @param request DTO com informações de cadastro no usuário
     * @return DTO com JWT token e infomrações do novo usuário autenticado
     */
    public UsuarioLoginResponseDTO register(UsuarioCadastrarRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException(BusinessExceptionMessage.ATTRIBUTE_VALUE_ALREADY_EXISTS.getMessage());
        }

        Pessoa person = personRepository.save(objectMapperUtil.map(request.getPessoa(), Pessoa.class));

        Usuario user = objectMapperUtil.map(request, Usuario.class);
        user.setSenha(passwordEncoder.encode(request.getSenha()));
        user.setStatus(StatusUsuario.ATIVO);
        user.setPessoa(person);
        user.setPerfis(new ArrayList<>());
        user = userRepository.save(user);

        Cargo role = roleRepository.findByNome("ROLE_CITIZEN")
                .orElseGet(() -> {
                    Cargo newRole = new Cargo();
                    newRole.setNome("ROLE_CITIZEN");
                    newRole.setDescricao("Cargo para cidadões");
                    return roleRepository.save(newRole);
                });

        Perfil profile = new Cidadao();
        profile.setTipo("Cidadão");
        profile.setCargo(role);
        profile.setUsuario(user);
        profile = profileRepository.save(profile);

        user.getPerfis().add(profile);
        user.setTipoAtivo(profile);
        userRepository.save(user);

        String token = tokenService.generateToken(user);

        return new UsuarioLoginResponseDTO(user.getId(), user.getEmail(), user.getUsername(), token, user.getStatus());
    }
}
