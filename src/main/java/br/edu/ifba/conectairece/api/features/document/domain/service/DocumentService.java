package br.edu.ifba.conectairece.api.features.document.domain.service;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.model.ConstructionLicenseRequirement;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.repository.IConstructionLicenseRequirementRepository;
import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentCorrectionSuggestionDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentRejectionDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentDetailResponseDTO;
import br.edu.ifba.conectairece.api.features.document.domain.enums.DocumentStatus;
import br.edu.ifba.conectairece.api.features.document.domain.model.Document;
import br.edu.ifba.conectairece.api.features.document.domain.repository.IDocumentRepository;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.dto.request.PublicServantApproveDocumentRequestDTO;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.dto.request.PublicServantRejectDocumentRequestDTO;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.model.PublicServantProfile;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.repository.IPublicServantProfileRepository;
import br.edu.ifba.conectairece.api.features.requirement.domain.model.Requirement;
import br.edu.ifba.conectairece.api.features.requirement.domain.repository.IRequirementRepository;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request.TechnicalResponsibleApproveDocumentRequestDTO_TEMP;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request.TechnicalResponsibleRejectDocumentRequestDTO_TEMP;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.model.TechnicalResponsible;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.repository.ITechnicalResponsibleRepository;
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
 * Implementation of {@link IDocumentService} responsible for handling the
 * business logic
 * related to {@link Document} entities.
 * <p>
 * This service acts as the core of the document management domain,
 * orchestrating operations such as creation, approval, and rejection.
 * It enforces business rules, validates workflow states, and ensures
 * consistency between documents and their related requirements.
 * </p>
 *
 * <p>
 * <b>Responsibilities:</b>
 * </p>
 * <ul>
 * <li>Coordinate the document review lifecycle (create, approve, reject).</li>
 * <li>Delegate domain-specific state transitions to the {@link Document} entity
 * itself.</li>
 * <li>Ensure transactional consistency and map entities to DTOs.</li>
 * <li>Handle {@link BusinessException} for missing or invalid entities.</li>
 * </ul>
 *
 * <p>
 * <b>Note:</b> This class is designed to evolve with future CRUD operations
 * such as update,
 * delete, and list methods. When adding new features, ensure all operations
 * remain transactional
 * and consistent with the domain model.
 * </p>
 *
 * @author
 *         Andesson Reis
 * @since
 *        1.0
 * @see IDocumentService
 * @see Document
 * @see IDocumentRepository
 * @see IRequirementRepository
 */
@Service
@RequiredArgsConstructor
public class DocumentService implements IDocumentService {

    private final IDocumentRepository documentRepository;
    private final ObjectMapperUtil objectMapperUtil;
    private final IRequirementRepository requirementRepository;
    private final ITechnicalResponsibleRepository technicalResponsibleRepository;
    private final IConstructionLicenseRequirementRepository constructionLicenseRequirementRepository;
    private final IPublicServantProfileRepository publicServantProfileRepository;

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
        DocumentDetailResponseDTO deletedDocumentDTO = objectMapperUtil.mapToRecord(document,
                DocumentDetailResponseDTO.class);
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
     * Retrieves a {@link Document} by its unique identifier or throws a
     * standardized exception.
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
     * Sets the document status to CORRECTION_SUGGESTED and records the
     * justification.
     * Ensures only the assigned Technical Responsible can perform this action.
     *
     * @param documentId    The ID of the document to suggest corrections for.
     * @param responsibleId The ID of the Technical Responsible making the
     *                      suggestion.
     * @param dto           DTO containing the justification for the suggestion.
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
                .orElseThrow(() -> new BusinessException(
                        "Technical Responsible not found with registration ID: " + registrationId));

        Requirement requirementBase = document.getRequirement();

        ConstructionLicenseRequirement clr = constructionLicenseRequirementRepository.findById(requirementBase.getId())
                .orElseThrow(() -> new BusinessException(
                        "This operation is only supported for Construction License Requirements."));

        if (clr.getTechnicalResponsible() == null
                || !clr.getTechnicalResponsible().getId().equals(responsible.getId())) {
            throw new AccessDeniedException(
                    "You are not the designated Technical Responsible for this requirement's documents.");
        }

        document.setStatus(DocumentStatus.CORRECTION_SUGGESTED);
        document.setReviewNote(dto.justification());

        Document updatedDocument = documentRepository.save(document);
        return objectMapperUtil.mapToRecord(updatedDocument, DocumentDetailResponseDTO.class);
    }

    /**
     * Approves a document as a Public Servant (final approval step).
     * <p>
     * The document must be in status APPROVED or CORRECTION_SUGGESTED to be
     * eligible.
     * Optionally, a comment can be added as a review note.
     * </p>
     *
     * @param dto DTO containing documentId, publicServantProfileId, and optional
     *            comment.
     * @return DocumentDetailResponseDTO with updated document status.
     * @throws BusinessException     if the Public Servant profile is not found.
     * @throws IllegalStateException if the document is not in a valid status for SP
     *                               approval.
     * 
     *                               Author: Andesson Reis
     */
    @Override
    @Transactional
    public DocumentDetailResponseDTO approveDocumentByPublicServant(PublicServantApproveDocumentRequestDTO dto) {
        PublicServantProfile publicServant = publicServantProfileRepository.findById(dto.publicServantProfileId())
                .orElseThrow(() -> new BusinessException("Public Servant Profile not found."));

        Document document = findDocumentEntityById(dto.documentId());

        if (document.getStatus() != DocumentStatus.APPROVED
                && document.getStatus() != DocumentStatus.CORRECTION_SUGGESTED) {
            throw new IllegalStateException(
                    "Document must be in 'APPROVED' or 'CORRECTION_SUGGESTED' status to be analyzed by a Public Servant.");
        }

        document.setStatus(DocumentStatus.APPROVED);

        if (dto.comment() != null && !dto.comment().isBlank()) {
            document.setReviewNote(dto.comment());
        }

        Document savedDocument = documentRepository.save(document);
        return objectMapperUtil.mapToRecord(savedDocument, DocumentDetailResponseDTO.class);
    }

    /**
     * Rejects a document as a Public Servant (final approval step).
     * <p>
     * The document must be in status APPROVED or CORRECTION_SUGGESTED to be
     * eligible.
     * A justification is mandatory.
     * </p>
     *
     * @param dto DTO containing documentId, publicServantProfileId, and
     *            justification.
     * @return DocumentDetailResponseDTO with updated document status.
     * @throws BusinessException     if the Public Servant profile is not found.
     * @throws IllegalStateException if the document is not in a valid status for SP
     *                               rejection.
     * Author: Andesson Reis
     */
    @Override
    @Transactional
    public DocumentDetailResponseDTO rejectDocumentByPublicServant(PublicServantRejectDocumentRequestDTO dto) {
        PublicServantProfile publicServant = publicServantProfileRepository.findById(dto.publicServantProfileId())
                .orElseThrow(() -> new BusinessException("Public Servant Profile not found."));

        Document document = findDocumentEntityById(dto.documentId());

        if (document.getStatus() != DocumentStatus.APPROVED
                && document.getStatus() != DocumentStatus.CORRECTION_SUGGESTED) {
            throw new IllegalStateException(
                    "Document must be in 'APPROVED' or 'CORRECTION_SUGGESTED' status to be analyzed by a Public Servant.");
        }

        document.setStatus(DocumentStatus.REJECTED);
        document.setReviewNote(dto.justification());

        Document savedDocument = documentRepository.save(document);
        return objectMapperUtil.mapToRecord(savedDocument, DocumentDetailResponseDTO.class);
    }

    /**
     * Approves a document as a Technical Responsible (Analysis 1).
     * <p>
     * Security validation ensures that the Technical Responsible is the owner of
     * the document's requirement.
     * Only documents in PENDING status can be approved.
     * </p>
     *
     * @param dto DTO containing documentId and registrationId of the Technical
     *            Responsible.
     * @return DocumentDetailResponseDTO with updated document status.
     * @throws BusinessException     if the Technical Responsible or requirement is
     *                               not found.
     * @throws IllegalStateException if the document is not PENDING.
     * Author: Andesson Reis
     */
    @Override
    @Transactional
    public DocumentDetailResponseDTO approveDocumentByTechnicalResponsible(
            TechnicalResponsibleApproveDocumentRequestDTO_TEMP dto) {
        Document document = findDocumentEntityById(dto.documentId());

        validateTechnicalResponsibleForCLR(document, dto.registrationId());

        if (document.getStatus() != DocumentStatus.PENDING) {
            throw new IllegalStateException("Only PENDING documents can be approved by the Technical Responsible.");
        }

        document.approve();
        Document savedDocument = documentRepository.save(document);
        return objectMapperUtil.mapToRecord(savedDocument, DocumentDetailResponseDTO.class);
    }

    /**
     * Rejects a document as a Technical Responsible (Analysis 1).
     * <p>
     * Security validation ensures that the Technical Responsible is the owner of
     * the document's requirement.
     * Only documents in PENDING status can be rejected.
     * </p>
     *
     * @param dto DTO containing documentId, registrationId, and justification.
     * @return DocumentDetailResponseDTO with updated document status.
     * @throws BusinessException     if the Technical Responsible or requirement is
     *                               not found.
     * @throws IllegalStateException if the document is not PENDING.
     * Author: Andesson Reis
     */
    @Override
    @Transactional
    public DocumentDetailResponseDTO rejectDocumentByTechnicalResponsible(
            TechnicalResponsibleRejectDocumentRequestDTO_TEMP dto) {
        Document document = findDocumentEntityById(dto.documentId());

        validateTechnicalResponsibleForCLR(document, dto.registrationId());

        if (document.getStatus() != DocumentStatus.PENDING) {
            throw new IllegalStateException("Only PENDING documents can be rejected by the Technical Responsible.");
        }

        document.reject(dto.justification());
        Document savedDocument = documentRepository.save(document);
        return objectMapperUtil.mapToRecord(savedDocument, DocumentDetailResponseDTO.class);
    }

    /**
     * Validates that the given Technical Responsible is the owner of the
     * Construction License Requirement
     * for the document.
     *
     * @param document       Document to validate.
     * @param registrationId Registration ID of the Technical Responsible.
     * @throws BusinessException     if Technical Responsible or CLR not found.
     * @throws AccessDeniedException if the Technical Responsible does not own the
     *                               CLR.
     */
    private void validateTechnicalResponsibleForCLR(Document document, String registrationId) {
        TechnicalResponsible responsible = technicalResponsibleRepository.findByRegistrationId(registrationId)
                .orElseThrow(() -> new BusinessException(
                        "Technical Responsible not found with registration ID: " + registrationId));

        Requirement requirementBase = document.getRequirement();

        ConstructionLicenseRequirement clr = constructionLicenseRequirementRepository.findById(requirementBase.getId())
                .orElseThrow(() -> new BusinessException(
                        "This operation is only supported for Construction License Requirements."));

        if (clr.getTechnicalResponsible() == null
                || !clr.getTechnicalResponsible().getId().equals(responsible.getId())) {
            throw new AccessDeniedException(
                    "You are not the designated Technical Responsible for this requirement's documents.");
        }
    }
}