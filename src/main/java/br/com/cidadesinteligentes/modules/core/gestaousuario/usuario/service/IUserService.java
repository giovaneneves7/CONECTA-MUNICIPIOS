package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UserDataResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.UserStatus;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.ProfileWithRoleResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UserResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    UserDataResponseDTO getUserById(final UUID id);
    List<ProfileWithRoleResponseDTO> getUserProfiles(final UUID id, final Pageable pageable);
    ProfileWithRoleResponseDTO findActiveProfileByUserId(final UUID id);
    List<UserResponseDTO> findAllUsers (final Pageable pageable);
    void updateUserStatus (UUID userId, UserStatus newStatus);
}
