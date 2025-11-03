package br.edu.ifba.conectairece.api.features.document.domain.dto.response;

import br.edu.ifba.conectairece.api.features.document.domain.enums.DocumentStatus;
import br.edu.ifba.conectairece.api.features.evaluationItem.domain.dto.response.EvaluationItemResponseDTO;

import java.util.List;
import java.util.UUID;

/**
 * A detailed Data Transfer Object (DTO) representing a Document.
 * <p>
 * This record provides a comprehensive, immutable view of a document's state and metadata,
 * suitable for detailed responses where the consumer needs all relevant information,
 * including its review status and any associated notes.
 * </p>
 *
 * @param id The unique identifier (UUID) of the document.
 * @param name The original name of the uploaded file (e.g., "identity_card.pdf").
 * @param fileExtension The extension of the file (e.g., "pdf", "jpg").
 * @param fileUrl The public URL to access the stored file.
 * @param status The current review status of the document.
 * @param reviewNote The justification or note provided during the review process, especially for REJECTED documents.
 *
 * @author Andesson Reis 
 */
public record DocumentDetailResponseDTO(
    UUID id,
    String name,
    String fileExtension,
    String fileUrl,
    DocumentStatus status,
    String reviewNote,
    List<EvaluationItemResponseDTO> evaluationItems
) {}