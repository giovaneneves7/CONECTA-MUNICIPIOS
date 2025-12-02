package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.dto.response.PermissionResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.ProfilePublicDataResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.ProfileResponseCurrentTypeDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.ProfileWithRoleResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.projection.ProjecaoPerfil;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.RequestResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing {@link Perfil} entities.
 *
 * @author Jorge Roberto
 */
public interface IPerfilService {

    /**
     * Updates an existing profile in the database.
     */
    ProfileWithRoleResponseDTO update(Perfil profile);

    /**
     * Deletes a profile by its identifier.
     */
    void delete(UUID id);

    /**
     * Finds a profile by its identifier.
     */
    ProfilePublicDataResponseDTO findById(UUID id);

    /**
     * Retrieves a paginated list of projected {@link ProjecaoPerfil} entities.
     */
    List<ProfileWithRoleResponseDTO> getAllProfiles(Pageable pageable);

    /**
     * Find all requests linked to the user id passed as a parameter
     *
     * @author Giovane Neves
     * @param userId The userId linked to the requests
     * @return A pageable list of requests linked to the user id passed as a parameter
     */
    // No seu arquivo de interface de serviço
    Page<RequestResponseDTO> findAllRequestsByProfileId(UUID profileId, Pageable pageable);

    /**
     * Changes the active profile type for a specific user.
     * This method updates the user's active profile based on the provided user ID and the new profile type.
     *
     * @return A {@link ProfileResponseCurrentTypeDTO} object containing the updated active profile information.
     */
    ProfileResponseCurrentTypeDTO changeActiveProfile(UUID userId, String newActiveType);

    List<PermissionResponseDTO> findAllPermissionsByProfile(UUID profileId, Pageable pageable);

}
