package br.edu.ifba.conectairece.api.features.publicservantprofile.domain.dto.request;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for requesting the rejection of a public servant's document.
 * <p>
 * This DTO is used when a public servant needs to reject a document within the system.
 * It includes:
 * <ul>
 *   <li>{@code documentId} - The UUID of the document to reject (required).</li>
 *   <li>{@code publicServantProfileId} - The UUID of the public servant profile (required).</li>
 *   <li>{@code justification} - The mandatory justification for rejection.</li>
 * </ul>
 * </p>
 * 
 * @param documentId             the unique identifier of the document to reject; must not be null
 * @param publicServantProfileId the unique identifier of the public servant profile; must not be null
 * @param justification          the mandatory justification for rejection; must not be blank
 * 
 * Author: Andesson Reis
 */
public record PublicServantRejectDocumentRequestDTO(
        @NotNull UUID documentId,
        @NotNull UUID publicServantProfileId,
        @NotBlank String justification
) {}
