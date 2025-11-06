package br.edu.ifba.conectairece.api.features.profile.domain.service;

import br.edu.ifba.conectairece.api.features.permission.domain.dto.response.PermissionResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfilePublicDataResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfileResponseCurrentType;
import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfileResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfileWithRoleResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import br.edu.ifba.conectairece.api.features.profile.domain.repository.projection.ProfileProjection;
import br.edu.ifba.conectairece.api.features.request.domain.dto.reposnse.RequestResponseDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing {@link Profile} entities.
 *
 * @author Jorge Roberto
 */
public interface ProfileIService {

    /**
     * Updates an existing profile in the database.
     */
    ProfileWithRoleResponseDTO update(Profile profile);

    /**
     * Deletes a profile by its identifier.
     */
    void delete(UUID id);

    /**
     * Finds a profile by its identifier.
     */
    ProfilePublicDataResponseDTO findById(UUID id);

    /**
     * Retrieves a paginated list of projected {@link ProfileProjection} entities.
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
    Page<RequestResponseDto> findAllRequestsByProfileId(UUID profileId, Pageable pageable);

    /**
     * Changes the active profile type for a specific user.
     * This method updates the user's active profile based on the provided user ID and the new profile type.
     *
     * @return A {@link ProfileResponseCurrentType} object containing the updated active profile information.
     */
    ProfileResponseCurrentType changeActiveProfile(UUID userId, String newActiveType);

    List<PermissionResponseDTO> findAllPermissionsByProfile(UUID profileId, Pageable pageable);

}
