package br.edu.ifba.conectairece.api.features.profile.domain.service;

import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfileResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import br.edu.ifba.conectairece.api.features.profile.domain.repository.projection.ProfileProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing {@link Profile} entities.
 *
 * @author Jorge Roberto
 */
public interface ProfileIService {
    /**
     * Saves a new profile in the database.
     */
    ProfileResponseDTO save(Profile profile);

    /**
     * Updates an existing profile in the database.
     */
    ProfileResponseDTO update(Profile profile);

    /**
     * Deletes a profile by its identifier.
     */
    void delete(Long id);

    /**
     * Finds a profile by its identifier.
     */
    Profile findById(Long id);

    /**
     * Retrieves a paginated list of projected {@link ProfileProjection} entities.
     */
    Page<ProfileProjection> findAllProjectedBy(Pageable pageable);
}
