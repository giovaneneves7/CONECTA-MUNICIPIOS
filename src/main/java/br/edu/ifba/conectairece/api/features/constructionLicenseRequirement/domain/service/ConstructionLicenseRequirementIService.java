package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.service;

import java.util.List;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.ConstructionLicenseRequirementRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response.ConstructionLicenseRequirementResponseDTO;

/**
 * Interface defining the contract for managing {@link ConstructionLicenseRequirement} entities.
 * Provides methods for creating, retrieving, and deleting construction license requirements.
 *
 * Main operations:
 * - Save new construction license requirements with owner, property, and construction details.
 * - Retrieve all requirements or find by identifier.
 * - Delete requirements by identifier.
 *
 * This interface defines the service layer responsibilities, ensuring
 * separation between business logic and data access layers.
 *
 * Author: Caio Alves
 */

public interface ConstructionLicenseRequirementIService {

    /**
     * Saves a new construction license requirement.
     *
     * @param dto request data
     * @return response DTO with saved data
     */
    ConstructionLicenseRequirementResponseDTO save(ConstructionLicenseRequirementRequestDTO dto);

    /**
     * Retrieves all construction license requirements.
     *
     * @return list of response DTOs
     */
    List<ConstructionLicenseRequirementResponseDTO> findAll();

    /**
     * Finds a construction license requirement by its identifier.
     *
     * @param id requirement ID
     * @return response DTO
     */
    ConstructionLicenseRequirementResponseDTO findById(Integer id);

    /**
     * Deletes a construction license requirement by its identifier.
     *
     * @param id requirement ID
     */
    void delete(Integer id);

    ConstructionLicenseRequirementResponseDTO update(Integer id, ConstructionLicenseRequirementRequestDTO dto);
}
