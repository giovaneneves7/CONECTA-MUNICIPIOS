package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.service;

import java.util.List;
import java.util.UUID;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.ConstructionLicenseRequirementFinalizeRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response.*;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.AssociationActionRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.ConstructionLicenseRequirementRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.ConstructionLicenseRequirementUpdateRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.RejectionRequestDTO;
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

public interface IConstructionLicenseRequirementService {

    /**
     * Saves a new Construction License Requirement, processes associated documents,
     * and triggers the asynchronous creation of a corresponding Request entity.
     * <p>
     * After saving, it retrieves the {@link Request} created by the event listener
     * (using the most recent creation timestamp) and returns its ID.
     *
     * @param dto Data Transfer Object containing all requirement details.
     * @return A DTO containing the saved requirement details and the UUID of the newly created Request.
     * @throws br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException if any linked entity (User, Service, Responsible) is not found.
     */
    ConstructionLicenseRequirementWithRequestIDResponseDTO save(ConstructionLicenseRequirementRequestDTO dto);

    /**
     * Retrieves all construction license requirements.
     *
     * @return list of response DTOs
     */
    List<ConstructionLicenseRequirementResponseDTO> findAll(final Pageable pageable);

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

    ConstructionLicenseRequirementResponseDTO update(Long id, ConstructionLicenseRequirementUpdateRequestDTO dto);

    void approveAssociation(AssociationActionRequestDTO dto);

    ConstructionLicenseRequirementTechnicalResponsibleRejectDTO rejectAssociation(RejectionRequestDTO dto);

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

    /**
     * Finalizes the review process by approving a Construction License Requirement
     * and records the provided acceptance note (Comment) by the Public Servant.
     *
     * @param constructionLicenseRequirementId The ID of the requirement being approved.
     * @param dto DTO containing the public servant's ID and the acceptance note/comment.
     * @return A DTO confirming the approval details and final status (ACCEPTED).
     *
     * @throws BusinessException if the requirement or public servant profile is not found.
     * @throws BusinessException if Technical Responsible approval/association is still PENDING or REJECTED.
     * @throws BusinessException if 3 or more documents were not approved.
     */
    ConstructionLicenseRequirementFinalizeResponseDTO approveConstructionLicenseRequirement(Long constructionLicenseRequirementId, ConstructionLicenseRequirementFinalizeRequestDTO dto);

    /**
     * Finalizes the review process by rejecting a Construction License Requirement
     * and records the mandatory justification (Comment) provided by the Public Servant.
     *
     * @param constructionLicenseRequirementId The ID of the requirement being rejected.
     * @param dto DTO containing the public servant's ID and the rejection note/comment.
     * @return A DTO confirming the rejection details and final status (REJECTED).
     *
     * @throws BusinessException if the requirement or public servant profile is not found.
     * @throws BusinessException if Technical Responsible approval/association is still PENDING or REJECTED.
     */
    ConstructionLicenseRequirementFinalizeResponseDTO rejectConstructionLicenseRequirement(Long constructionLicenseRequirementId, ConstructionLicenseRequirementFinalizeRequestDTO dto);


    ConstructionLicenseRequirementFinalizedDetailDTO findFinalizedById(Long id);

}
