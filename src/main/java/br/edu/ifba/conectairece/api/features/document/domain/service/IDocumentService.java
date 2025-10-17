package br.edu.ifba.conectairece.api.features.document.domain.service;

import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentRejectionDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentDetailResponseDTO;
import br.edu.ifba.conectairece.api.features.document.domain.model.Document;

import java.util.UUID;

/**
 * Service interface for managing the business logic of {@link Document} entities.
 * <p>
 * Defines the contract for core document-related operations — creation, approval, and rejection —
 * encapsulating the domain logic and abstracting persistence or controller-specific concerns.
 * </p>
 *
 * @author
 *     Andesson Reis
 * @since
 *     1.0
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
}
