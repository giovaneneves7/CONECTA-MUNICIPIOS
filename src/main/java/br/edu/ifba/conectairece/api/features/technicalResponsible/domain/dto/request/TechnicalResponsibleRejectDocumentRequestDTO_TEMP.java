package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for a Technical Responsible to reject a document (Analysis 1).
 * <p>
 * This DTO is used when a Technical Responsible needs to reject a document within the system.
 * It includes:
 * <ul>
 *   <li>{@code documentId} - The UUID of the document to reject (required).</li>
 *   <li>{@code registrationId} - The registration/matriculation ID of the Technical Responsible (required).</li>
 *   <li>{@code justification} - The mandatory justification for rejecting the document.</li>
 * </ul>
 * </p>
 *
 * @param documentId     the unique identifier of the document to reject; must not be null
 * @param registrationId the registration ID of the Technical Responsible; must not be null
 * @param justification  mandatory justification for rejecting the document; must not be blank
 * 
 * Author: Andesson Reis
 */
public record TechnicalResponsibleRejectDocumentRequestDTO_TEMP(
        @NotNull UUID documentId,
        @NotNull String registrationId,
        @NotBlank String justification
) {}
