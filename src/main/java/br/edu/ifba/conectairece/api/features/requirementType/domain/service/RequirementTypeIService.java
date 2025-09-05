package br.edu.ifba.conectairece.api.features.requirementType.domain.service;

import java.util.List;

import br.edu.ifba.conectairece.api.features.requirementType.domain.dto.request.RequirementTypeRequestDTO;
import br.edu.ifba.conectairece.api.features.requirementType.domain.dto.response.RequirementTypeResponseDTO;
import br.edu.ifba.conectairece.api.features.requirementType.domain.model.RequirementType;

/**
 * Interface defining the contract for managing {@link RequirementType} entities.
 * Provides methods for creating, retrieving, and deleting requirement types.
 *
 * Main operations:
 * - Save new requirement types.
 * - Retrieve all requirement types.
 * - Find a requirement type by its identifier.
 * - Delete a requirement type by its identifier.
 *
 * Author: Caio Alves
 */

public interface RequirementTypeIService {

        /**
     * Saves a new requirement type.
     *
     * @param dto request data containing requirement type details
     * @return DTO with saved requirement type information
     */
    RequirementTypeResponseDTO save(RequirementTypeRequestDTO dto);

    /**
     * Retrieves all requirement types.
     *
     * @return list of requirement type DTOs
     */
    List<RequirementTypeResponseDTO> findAll();

    /**
     * Finds a requirement type by its identifier.
     *
     * @param id requirement type ID
     * @return the found requirement type entity
     */
    RequirementTypeResponseDTO findById(Integer id);

    /**
     * Deletes a requirement type by its identifier.
     *
     * @param id requirement type ID
     */
    void delete(Integer id);
}
