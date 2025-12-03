package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UserDataResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilComCargoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.IPerfilRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UserResponseDTO;
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

    /**
     * Searches for a user by the ID passed as a parameter
     *
     * @author Giovane Neves
     *
     * @param id The id of the user to be found
     * @return DTO with the found user data
     */
    @Transactional(readOnly = true)
    public UserDataResponseDTO getUserById(final UUID id){

        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.INVALID_CREDENTIALS.getMessage()));

        return objectMapperUtil.map(user, UserDataResponseDTO.class);

    }

    @Transactional(readOnly = true)
    public List<PerfilComCargoResponseDTO> getUserProfiles(final UUID id, final Pageable pageable) {

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
    public PerfilComCargoResponseDTO findActiveProfileByUserId(final UUID id) {
        Usuario user = this.userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        if (user.getPerfilAtivo() == null) {
            throw new BusinessException(BusinessExceptionMessage.USER_WITHOUT_PROFILES.getMessage());
        }

        Perfil profile = user.getPerfilAtivo();

        return new PerfilComCargoResponseDTO(
                profile.getId(),
                profile.getCargo().getNome(),
                profile.getTipo(),
                profile.getImagemUrl()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAllUsers(final Pageable pageable) {

        Page<Usuario> users = userRepository.findAll(pageable);
        return users.stream()
                .map(user -> this.objectMapperUtil.mapToRecord(user, UserResponseDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public void updateUserStatus(UUID userId, StatusUsuario newStatus) {
            Usuario user = this.findById(userId);
            user.setStatus(newStatus);
            this.userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Usuario findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
    }
}
