package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.AssociationActionRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.ConstructionLicenseRequirementRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.RejectionRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response.ConstructionLicenseRequirementDetailDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response.ConstructionLicenseRequirementResponseDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.model.ConstructionLicenseRequirement;

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
    ConstructionLicenseRequirementDetailDTO findById(Long id);

    /**
     * Deletes a construction license requirement by its identifier.
     *
     * @param id requirement ID
     */
    void delete(Long id);

    ConstructionLicenseRequirementResponseDTO update(Long id, ConstructionLicenseRequirementRequestDTO dto);

    void approveAssociation(AssociationActionRequestDTO dto);

    void rejectAssociation(RejectionRequestDTO dto);

    List<ConstructionLicenseRequirementResponseDTO> findAllByTechnicalResponsible(UUID responsibleId);
    
    List<ConstructionLicenseRequirementResponseDTO> findAllByTechnicalResponsibleRegistrationId(String registrationId);

    /**
     * Retrieves a paginated list of construction license requirements filtered by the name of their associated RequirementType.
     *
     * @param typeName The name of the RequirementType to filter by (e.g., "Construção", "Reforma").
     * @param pageable Pagination and sorting information.
     * @return A Page containing ConstructionLicenseRequirementResponseDTO objects matching the specified type name.
     * @author Caio Alves
     */
    Page<ConstructionLicenseRequirementResponseDTO> findByRequirementTypeName(String typeName, Pageable pageable);
}
