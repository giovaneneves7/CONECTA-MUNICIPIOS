package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for a Technical Responsible to approve a document (Analysis 1).
 * <p>
 * This DTO is used when a Technical Responsible needs to approve a document within the system.
 * It includes:
 * <ul>
 *   <li>{@code documentId} - The UUID of the document to approve (required).</li>
 *   <li>{@code registrationId} - The registration ID of the Technical Responsible (required).</li>
 * </ul>
 * </p>
 *
 * @param documentId     the unique identifier of the document to approve; must not be null
 * @param registrationId the registration ID of the Technical Responsible; must not be null
 * 
 * Author: Andesson Reis
 */
public record TechnicalResponsibleApproveDocumentRequestDTO_TEMP(
        @NotNull UUID documentId,
        @NotNull String registrationId
) {}
