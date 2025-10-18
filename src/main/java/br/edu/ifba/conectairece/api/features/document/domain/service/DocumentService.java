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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public DocumentDetailResponseDTO createDocument(Long requirementId, Document document) {
        var requirement = requirementRepository.findById(requirementId)
                .orElseThrow(() -> new BusinessException("Requirement with ID " + requirementId + " not found."));

        document.setRequirement(requirement);
        var savedDocument = documentRepository.save(document);

        return objectMapperUtil.mapToRecord(savedDocument, DocumentDetailResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentDetailResponseDTO findDocumentById(UUID documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new BusinessException("Document with ID " + documentId + " not found."));
        return objectMapperUtil.mapToRecord(document, DocumentDetailResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentDetailResponseDTO> findAllDocuments() {
        return documentRepository.findAll().stream()
                .map(doc -> objectMapperUtil.mapToRecord(doc, DocumentDetailResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DocumentDetailResponseDTO approveDocument(UUID documentId) {
        Document document = findDocumentEntityById(documentId);
        document.approve();

        Document savedDocument = documentRepository.save(document);
        return objectMapperUtil.mapToRecord(savedDocument, DocumentDetailResponseDTO.class);
    }

    @Override
    @Transactional
    public DocumentDetailResponseDTO rejectDocument(UUID documentId, DocumentRejectionDTO rejectionDto) {
        Document document = findDocumentEntityById(documentId);
        document.reject(rejectionDto.getJustification());

        Document savedDocument = documentRepository.save(document);
        return objectMapperUtil.mapToRecord(savedDocument, DocumentDetailResponseDTO.class);
    }

    /**
     * Retrieves a {@link Document} by its unique identifier or throws a standardized exception.
     *
     * @param documentId The UUID of the document to retrieve.
     * @return The corresponding {@link Document} entity.
     * @throws BusinessException if no document is found with the provided ID.
     */
    private Document findDocumentEntityById(UUID documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new BusinessException("Document with ID " + documentId + " not found."));
    }
}
