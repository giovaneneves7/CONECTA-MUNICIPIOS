package br.edu.ifba.conectairece.api.features.document.domain.service;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.model.ConstructionLicenseRequirement;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.repository.ConstructionLicenseRequirementRepository;
import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentCorrectionSuggestionDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentRejectionDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentDetailResponseDTO;
import br.edu.ifba.conectairece.api.features.document.domain.enums.DocumentStatus;
import br.edu.ifba.conectairece.api.features.document.domain.model.Document;
import br.edu.ifba.conectairece.api.features.document.domain.repository.DocumentRepository;
import br.edu.ifba.conectairece.api.features.requirement.domain.model.Requirement;
import br.edu.ifba.conectairece.api.features.requirement.domain.repository.RequirementRepository;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.model.TechnicalResponsible;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.repository.TechnicalResponsibleRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
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
    private final TechnicalResponsibleRepository technicalResponsibleRepository;
    private final ConstructionLicenseRequirementRepository constructionLicenseRequirementRepository;

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
    public DocumentDetailResponseDTO updateDocument(UUID documentId, Document documentUpdateData) {
        Document existingDocument = findDocumentEntityById(documentId);
        if (existingDocument.getStatus() != DocumentStatus.PENDING) {
            throw new IllegalStateException("Apenas documentos com status PENDENTE podem ser atualizados.");
        }
        existingDocument.setName(documentUpdateData.getName());
        existingDocument.setFileExtension(documentUpdateData.getFileExtension());
        existingDocument.setFileUrl(documentUpdateData.getFileUrl());
        existingDocument.setReviewNote(documentUpdateData.getReviewNote());
        
        Document savedDocument = documentRepository.save(existingDocument);
        return objectMapperUtil.mapToRecord(savedDocument, DocumentDetailResponseDTO.class);
    }



@Override
    @Transactional
    public DocumentDetailResponseDTO deleteDocument(UUID documentId) {
        Document document = findDocumentEntityById(documentId);
        if (document.getStatus() != DocumentStatus.PENDING) {
            throw new IllegalStateException("Apenas documentos com status PENDENTE podem ser excluídos.");
        }
        DocumentDetailResponseDTO deletedDocumentDTO = objectMapperUtil.mapToRecord(document, DocumentDetailResponseDTO.class);
        documentRepository.delete(document);
        return deletedDocumentDTO;
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

    @Override
    @Transactional
    public DocumentDetailResponseDTO suggestCorrection(DocumentCorrectionSuggestionDTO dto) {

        UUID documentId = dto.documentId();
        String registrationId = dto.registrationId();

        Document document = findDocumentEntityById(documentId);

        if (document.getStatus() != DocumentStatus.PENDING) {
            throw new BusinessException("Correction can only be suggested for documents with PENDING status.");
        }

        TechnicalResponsible responsible = technicalResponsibleRepository.findByRegistrationId(registrationId)
            .orElseThrow(() -> new BusinessException("Technical Responsible not found with registration ID: " + registrationId));

        Requirement requirementBase = document.getRequirement();

        ConstructionLicenseRequirement clr = constructionLicenseRequirementRepository.findById(requirementBase.getId())
        .orElseThrow(() -> 
            new BusinessException("This operation is only supported for Construction License Requirements.")
        );

        if (clr.getTechnicalResponsible() == null || !clr.getTechnicalResponsible().getId().equals(responsible.getId())) {
        throw new AccessDeniedException("You are not the designated Technical Responsible for this requirement's documents.");
    }

        document.setStatus(DocumentStatus.CORRECTION_SUGGESTED);
        document.setReviewNote(dto.justification());

        Document updatedDocument = documentRepository.save(document);
        return objectMapperUtil.mapToRecord(updatedDocument, DocumentDetailResponseDTO.class);
    }
}
