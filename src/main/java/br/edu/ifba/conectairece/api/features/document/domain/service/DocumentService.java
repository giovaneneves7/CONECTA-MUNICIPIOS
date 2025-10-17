package br.edu.ifba.conectairece.api.features.document.domain.service;

import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentRejectionDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentDetailResponseDTO;
import br.edu.ifba.conectairece.api.features.document.domain.model.Document;
import br.edu.ifba.conectairece.api.features.document.domain.repository.DocumentRepository;
import br.edu.ifba.conectairece.api.features.requirement.domain.repository.RequirementRepository; 
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of {@link IDocumentService} responsible for handling the business logic
 * related to {@link Document} entities.
 * <p>
 * This service acts as the core of the document management domain,
 * orchestrating operations such as creation, approval, and rejection.
 * It enforces business rules, validates workflow states, and ensures
 * consistency between documents and their related requirements.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Coordinate the document review lifecycle (create, approve, reject).</li>
 *   <li>Delegate domain-specific state transitions to the {@link Document} entity itself.</li>
 *   <li>Ensure transactional consistency and map entities to DTOs.</li>
 *   <li>Handle {@link BusinessException} for missing or invalid entities.</li>
 * </ul>
 *
 * <p><b>Note:</b> This class is designed to evolve with future CRUD operations such as update,
 * delete, and list methods. When adding new features, ensure all operations remain transactional
 * and consistent with the domain model.</p>
 *
 * @author
 *   Andesson Reis
 * @since
 *   1.0
 * @see IDocumentService
 * @see Document
 * @see DocumentRepository
 * @see RequirementRepository
 */
@Service
@RequiredArgsConstructor
public class DocumentService implements IDocumentService {

    private final DocumentRepository documentRepository;
    private final ObjectMapperUtil objectMapperUtil;
    private final RequirementRepository requirementRepository;

    /**
     * Creates a new {@link Document} associated with an existing requirement.
     * <p>
     * This method retrieves the corresponding requirement entity, binds it to
     * the document, persists the document, and returns a DTO representation.
     * The created document will typically have the initial state {@code PENDING}.
     * </p>
     *
     * @param requirementId The numeric ID of the {@link br.edu.ifba.conectairece.api.features.requirement.domain.model.Requirement}.
     * @param document      The document entity to be created and linked to the requirement.
     * @return A {@link DocumentDetailResponseDTO} containing the persisted document details.
     * @throws BusinessException if the requirement with the given ID does not exist.
     */
    @Override
    @Transactional
    public DocumentDetailResponseDTO createDocument(Long requirementId, Document document) {
        var requirement = requirementRepository.findById(requirementId)
                .orElseThrow(() -> new BusinessException("Requirement with ID " + requirementId + " not found."));

        document.setRequirement(requirement);
        var savedDocument = documentRepository.save(document);

        return objectMapperUtil.mapToRecord(savedDocument, DocumentDetailResponseDTO.class);
    }

    /**
     * Approves a document by changing its state from {@code PENDING} to {@code APPROVED}.
     * <p>
     * This operation is transactional and ensures the document exists before updating.
     * The state transition logic is handled internally by the {@link Document} entity.
     * </p>
     *
     * @param documentId The unique identifier of the document to approve.
     * @return A {@link DocumentDetailResponseDTO} containing the updated document state.
     * @throws BusinessException if the document does not exist.
     * @throws IllegalStateException if the document is not in a valid state for approval.
     */
    @Override
    @Transactional
    public DocumentDetailResponseDTO approveDocument(UUID documentId) {
        Document document = findDocumentById(documentId);
        document.approve();

        Document savedDocument = documentRepository.save(document);
        return objectMapperUtil.mapToRecord(savedDocument, DocumentDetailResponseDTO.class);
    }

    /**
     * Rejects a document with a provided justification.
     * <p>
     * This operation transitions the document’s state from {@code PENDING} to {@code REJECTED}.
     * The rejection reason is stored in the document entity for auditing purposes.
     * </p>
     *
     * @param documentId   The unique identifier of the document to reject.
     * @param rejectionDto A DTO containing the rejection justification.
     * @return A {@link DocumentDetailResponseDTO} representing the rejected document.
     * @throws BusinessException if the document does not exist.
     * @throws IllegalStateException if the document is not in a {@code PENDING} state.
     */
    @Override
    @Transactional
    public DocumentDetailResponseDTO rejectDocument(UUID documentId, DocumentRejectionDTO rejectionDto) {
        Document document = findDocumentById(documentId);
        document.reject(rejectionDto.getJustification());

        Document savedDocument = documentRepository.save(document);
        return objectMapperUtil.mapToRecord(savedDocument, DocumentDetailResponseDTO.class);
    }

    /**
     * Retrieves a {@link Document} by its unique identifier or throws a standardized exception.
     * <p>
     * This helper method encapsulates lookup logic and ensures consistent error handling
     * throughout the service layer.
     * </p>
     *
     * @param documentId The UUID of the document to retrieve.
     * @return The corresponding {@link Document} entity.
     * @throws BusinessException if no document is found with the provided ID.
     */
    private Document findDocumentById(UUID documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new BusinessException("Document with ID " + documentId + " not found."));
    }
}
