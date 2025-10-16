package br.edu.ifba.conectairece.api.features.document.domain.service;

import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentRejectionDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentDetailResponseDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentResponseDTO;

import java.util.UUID;

/**
 * Service interface for managing the business logic of {@link br.edu.ifba.conectairece.api.features.document.domain.model.Document} entities.
 * <p>
 * Defines the contract for document-related operations, such as approval and rejection,
 * abstracting the implementation details.
 * </p>
 *
 * @author Andesson Reis
 */
public interface IDocumentService {

    /**
     * Approves a document by its unique identifier.
     *
     * @param documentId The UUID of the document to be approved.
     * @return A DTO representing the updated state of the approved document.
     * @throws br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException if the document is not found.
     * @throws IllegalStateException if the document is not in a PENDING state.
     */
    DocumentDetailResponseDTO approveDocument(UUID documentId);

    /**
     * Rejects a document by its unique identifier, requiring a justification.
     *
     * @param documentId The UUID of the document to be rejected.
     * @param rejectionDto DTO containing the mandatory justification for the rejection.
     * @return A DTO representing the updated state of the rejected document.
     * @throws br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException if the document is not found.
     * @throws IllegalStateException if the document is not in a PENDING state.
     */
    DocumentDetailResponseDTO rejectDocument(UUID documentId, DocumentRejectionDTO rejectionDto);
}