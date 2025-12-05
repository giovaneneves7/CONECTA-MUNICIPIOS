package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.model.TechnicalResponsible;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilComCargoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.IPerfilRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.model.Pessoa;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UsuarioCompletoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UsuarioDetalheResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UsuarioResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.Usuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUsuarioRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.model.PublicServantProfile;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {

    private final ObjectMapperUtil objectMapperUtil;
    private final IPerfilRepository profileRepository;
    private final IUsuarioRepository userRepository;


    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO findById(final UUID id){

        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.INVALID_CREDENTIALS.getMessage()));

        return new UsuarioResponseDTO(
                user.getId(),
                user.getNomeUsuario(),
                user.getEmail(),
                user.getStatus()
        );

    }

    @Override
    @Transactional(readOnly = true)
    public List<PerfilComCargoResponseDTO> getUsuarioPerfis(final UUID id, final Pageable pageable) {

        Page<Perfil> profiles = this.profileRepository.findAllByUsuarioId(id, pageable);

        return profiles.stream()
                .map(profile -> new PerfilComCargoResponseDTO(
                        profile.getId(),
                        profile.getCargo().getNome(),
                        profile.getTipo(),
                        profile.getImagemUrl()
                ))
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public PerfilComCargoResponseDTO findPerfilAtivoByUsuarioId(final UUID id) {
        Usuario user = this.userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        if (user.getTipoAtivo() == null) {
            throw new BusinessException(BusinessExceptionMessage.USER_WITHOUT_PROFILES.getMessage());
        }

        Perfil profile = user.getTipoAtivo();

        return new PerfilComCargoResponseDTO(
                profile.getId(),
                profile.getCargo().getNome(),
                profile.getTipo(),
                profile.getImagemUrl()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findAll(final Pageable pageable) {

        Page<Usuario> users = userRepository.findAll(pageable);
        return users.stream()
                .map(user -> this.objectMapperUtil.mapToRecord(user, UsuarioResponseDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public UsuarioResponseDTO updateStatusUsuario(final UUID usuarioId, StatusUsuario newStatus) {
            Usuario user = this.userRepository.findById(usuarioId)
                    .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
            user.setStatus(newStatus);

            Usuario updatedUser = this.userRepository.save(user);
            
            return new UsuarioResponseDTO(
                updatedUser.getId(),
                updatedUser.getNomeUsuario(),
                updatedUser.getEmail(),
                updatedUser.getStatus()
            );
    }

    @Override @Transactional
    public Page<UsuarioCompletoResponseDTO> findAllDetalhesUsuarios(final Pageable pageable) {

        Page<Usuario> userPage = userRepository.findAll(pageable);

        return userPage.map(this::mapUserToAdminDetailDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioCompletoResponseDTO> findDetalhesUsuarioByNomeCargo(final String nomeCargo, Pageable pageable) {

        Page<Usuario> userPage = userRepository.findByPerfisCargoNome(nomeCargo, pageable);

        return userPage.map(this::mapUserToAdminDetailDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioCompletoResponseDTO> findDetalhesUsuariosByStatus(final StatusUsuario status, Pageable pageable) {
        Page<Usuario> userPage = userRepository.findByStatus(status, pageable);
        return userPage.map(this::mapUserToAdminDetailDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioCompletoResponseDTO> findDetalhesUsuarioByNomeOuCpf(final String termo, Pageable pageable) {
        Page<Usuario> userPage = userRepository.findByNomeCompletoOrCpfContaining(termo, pageable);
        return userPage.map(this::mapUserToAdminDetailDto);
    }

    @Override @Transactional(readOnly = true)
    public Page<UsuarioCompletoResponseDTO> findDetalhesUsuarioByNomeCargoEStatus(final String nomeCargo, StatusUsuario status, Pageable pageable){
        Page<Usuario> userPage = userRepository.findByPerfisCargoNomeAndStatus(nomeCargo, status, pageable);
        return userPage.map(this::mapUserToAdminDetailDto);
    }

    private UsuarioCompletoResponseDTO mapUserToAdminDetailDto(Usuario user) {
        Pessoa person = user.getPessoa();
        Perfil activeProfile = user.getTipoAtivo();

        UsuarioDetalheResponseDTO contentDto = new UsuarioDetalheResponseDTO(
                user.getId(),
                activeProfile != null ? activeProfile.getTipo() : null,
                activeProfile != null ? activeProfile.getImagemUrl() : null,
                person != null ? person.getNomeCompleto() : null,
                person != null ? person.getCpf() : null,
                user.getTelefone(),
                user.getEmail(),
                person != null && person.getGenero() != null ? person.getGenero().toString() : null,
                person != null ? person.getDataNascimento() : null,
                user.getStatus() != null ? user.getStatus().toString() : null
        );

        List<PerfilComCargoResponseDTO> profileListDto = user.getPerfis().stream()
                .map(profile -> {
                    
                        String roleName = profile.getCargo() != null ? profile.getCargo().getNome() : "SEM_ROLE";
                
                        String cargoReal = profile.getTipo();

                        return new PerfilComCargoResponseDTO(
                        profile.getId(),
                        cargoReal,             
                        roleName,             
                        profile.getImagemUrl() 
                        );
                })
                .collect(Collectors.toList());

        return new UsuarioCompletoResponseDTO(contentDto, profileListDto);
    }
}
