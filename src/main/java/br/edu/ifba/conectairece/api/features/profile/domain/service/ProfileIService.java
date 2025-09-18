package br.edu.ifba.conectairece.api.features.profile.domain.service;

import br.edu.ifba.conectairece.api.features.profile.domain.dto.request.ProfileRequestDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfilePublicDataResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfileResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import br.edu.ifba.conectairece.api.features.profile.domain.repository.projection.ProfileProjection;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for managing {@link Profile} entities.
 *
 * @author Jorge Roberto
 */
public interface ProfileIService {
    /**
     * Saves a new profile in the database.
     */
    ProfileResponseDTO save(ProfileRequestDTO dto);

    /**
     * Updates an existing profile in the database.
     */
    ProfileResponseDTO update(Profile profile);

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
    Page<ProfileProjection> findAllProjectedBy(Pageable pageable);

    /**
     * Find all requests linked to the user id passed as a parameter
     *
     * @author Giovane Neves
     * @param userId The userId linked to the requests
     * @return A pageable list of requests linked to the user id passed as a parameter
     */
    Page<Request> findAllRequestsByProfileId(final UUID userId, Pageable pageable);
}
