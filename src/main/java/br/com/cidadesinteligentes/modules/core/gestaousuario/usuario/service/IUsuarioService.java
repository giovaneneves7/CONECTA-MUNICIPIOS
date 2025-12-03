package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UserDataResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilComCargoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UserResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IUsuarioService {
    UserDataResponseDTO getUserById(final UUID id);
    List<PerfilComCargoResponseDTO> getUserProfiles(final UUID id, final Pageable pageable);
    PerfilComCargoResponseDTO findActiveProfileByUserId(final UUID id);
    List<UserResponseDTO> findAllUsers (final Pageable pageable);
    void updateUserStatus (UUID userId, StatusUsuario newStatus);
}
