package br.edu.ifba.conectairece.api.features.document.domain.service;

import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentCorrectionSuggestionDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentRejectionDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentDetailResponseDTO;
import br.edu.ifba.conectairece.api.features.document.domain.model.Document;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.dto.request.PublicServantApproveDocumentRequestDTO;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.dto.request.PublicServantRejectDocumentRequestDTO;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request.TechnicalResponsibleApproveDocumentRequestDTO_TEMP;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request.TechnicalResponsibleRejectDocumentRequestDTO_TEMP;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing the business logic of {@link Document} entities.
 * <p>
 * Defines the contract for document management operations such as creation, retrieval,
 * approval, rejection, and any other document-related workflows.
 * Encapsulates domain logic and abstracts persistence or controller-specific concerns.
 * </p>
 *
 * @author Andesson
 * @since 1.0
 */

public interface IDocumentService {

    /**
     * Creates and persists a new {@link Document} associated with a specific requirement.
     * <p>
     * Initializes the document within the domain context, performs business validation,
     * and ensures referential integrity with the linked requirement.
     * The document is created in a <b>PENDING</b> state, awaiting manual review (approval or rejection).
     * </p>
     *
     * @param requirementId The unique identifier of the requirement to which the document belongs.
     * @param document      The {@link Document} entity to persist.
     * @return A {@link DocumentDetailResponseDTO} representing the created document with current state and metadata.
     * @throws br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException
     *         if the requirement does not exist or business rules are violated.
     * @throws IllegalArgumentException
     *         if the document entity contains invalid or missing mandatory fields.
     * @since 1.0
     * @see br.edu.ifba.conectairece.api.features.document.domain.model.Document
     */
    DocumentDetailResponseDTO createDocument(Long requirementId, Document document);

    /**
     * Retrieves a document by its unique identifier.
     *
     * @param documentId The UUID of the document to retrieve.
     * @return A {@link DocumentDetailResponseDTO} representing the document.
     * @throws br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException
     *         if the document does not exist.
     */
    DocumentDetailResponseDTO findDocumentById(UUID documentId);

    /**
     * Updates the data of an existing document.
     * <p>
     * This operation is restricted to documents in a PENDING state.
     * </p>
     *
     * @param documentId The UUID of the document to be updated.
     * @param documentUpdateData An entity containing the new data for the document.
     * @return A DTO representing the updated state of the document.
     * @throws br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException if the document is not found.
     * @throws IllegalStateException if the document is not in a PENDING state.
     */
    DocumentDetailResponseDTO updateDocument(UUID documentId, Document documentUpdateData);


    /**
     * Deletes a {@link Document} by its UUID.
     * <p>
     * This operation removes the document from persistent storage. If the document does not exist,
     * the implementation should handle the scenario appropriately (e.g., throwing an exception or returning an error response DTO).
     * </p>
     *
     * @param documentId The UUID of the document to delete.
     * @return A {@link DocumentDetailResponseDTO} containing details about the deletion operation.
     *         <ul>
     *             <li>{@code success = true} – When the document was found and deleted.</li>
     *             <li>{@code success = false} – When no document exists with the provided UUID, including an error message.</li>
     *         </ul>
     * @throws IllegalArgumentException If the provided UUID is null.
     * @throws DocumentNotFoundException If the document with the given UUID does not exist (implementation dependent).
     */
    DocumentDetailResponseDTO deleteDocument(UUID documentId);



    /**
     * Retrieves all documents in the system.
     *
     * @return A list of {@link DocumentDetailResponseDTO} representing all documents.
     */
    List<DocumentDetailResponseDTO> findAllDocuments();
    /**
     * Approves a document by its unique identifier.
     * <p>
     * Transitions a document from <b>PENDING</b> to <b>APPROVED</b> state after validation.
     * </p>
     *
     * @param documentId The UUID of the document to be approved.
     * @return A DTO representing the updated state of the approved document.
     *
     * @throws br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException
     *         if the document is not found.
     * @throws IllegalStateException
     *         if the document is not in a <b>PENDING</b> state.
     */
    DocumentDetailResponseDTO approveDocument(UUID documentId);

    /**
     * Rejects a document by its unique identifier, requiring a justification.
     * <p>
     * Transitions a document from <b>PENDING</b> to <b>REJECTED</b> state and stores the provided reason.
     * </p>
     *
     * @param documentId   The UUID of the document to be rejected.
     * @param rejectionDto DTO containing the mandatory justification for the rejection.
     * @return A DTO representing the updated state of the rejected document.
     *
     * @throws br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException
     *         if the document is not found.
     * @throws IllegalStateException
     *         if the document is not in a <b>PENDING</b> state.
     */
    DocumentDetailResponseDTO rejectDocument(UUID documentId, DocumentRejectionDTO rejectionDto);

    /**
     * Allows a Technical Responsible to suggest corrections for a document.
     * Sets the document status to CORRECTION_SUGGESTED and records the justification.
     * Ensures only the assigned Technical Responsible can perform this action.
     *
     * @param documentId The ID of the document to suggest corrections for.
     * @param responsibleId The ID of the Technical Responsible making the suggestion.
     * @param dto DTO containing the justification for the suggestion.
     * @return The updated document details.
     * @author Caio Alves
     */
    DocumentDetailResponseDTO suggestCorrection(DocumentCorrectionSuggestionDTO dto);

    /**
     * Approve a document as a Public Servant.
     *
     * @param dto DTO containing documentId, publicServantProfileId, and optional comment.
     * @return DocumentDetailResponseDTO with updated document status.
     */
    DocumentDetailResponseDTO approveDocumentByPublicServant(PublicServantApproveDocumentRequestDTO dto);

    /**
     * Reject a document as a Public Servant .
     *
     * @param dto DTO containing documentId, publicServantProfileId, and mandatory justification.
     * @return DocumentDetailResponseDTO with updated document status.
     */
    DocumentDetailResponseDTO rejectDocumentByPublicServant(PublicServantRejectDocumentRequestDTO dto);

    /**
     * Approve a document as a Technical Responsible.
     *
     * @param dto DTO containing documentId and registrationId of the Technical Responsible.
     * @return DocumentDetailResponseDTO with updated document status.
     */
    DocumentDetailResponseDTO approveDocumentByTechnicalResponsible(TechnicalResponsibleApproveDocumentRequestDTO_TEMP dto);

    /**
     * Reject a document as a Technical Responsible.
     *
     * @param dto DTO containing documentId, registrationId, and justification.
     * @return DocumentDetailResponseDTO with updated document status.
     */
    DocumentDetailResponseDTO rejectDocumentByTechnicalResponsible(TechnicalResponsibleRejectDocumentRequestDTO_TEMP dto);

}
