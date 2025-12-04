package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilComCargoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.IPerfilRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UsuarioResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.Usuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUsuarioRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;

import java.util.List;
import java.util.UUID;

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
    public void updateStatusUsuario(UUID usuarioId, StatusUsuario newStatus) {
            Usuario user = this.userRepository.findById(usuarioId)
                    .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
            user.setStatus(newStatus);
            this.userRepository.save(user);
    }
}
