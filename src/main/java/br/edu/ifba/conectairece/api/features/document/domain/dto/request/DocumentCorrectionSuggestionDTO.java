package br.edu.ifba.conectairece.api.features.document.domain.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for a Technical Responsible to suggest corrections for a document.
 * Contains the justification for the suggested changes.
 *
 * @author Caio Alves
 */
public record DocumentCorrectionSuggestionDTO(
    @NotNull(message = "Document ID cannot be null.")
    UUID documentId,

    @NotBlank(message = "Technical Responsible registration ID cannot be blank.")
    String registrationId,
    
    @NotBlank(message = "Suggestion justification cannot be blank.")
    @Size(min = 5, max = 500, message = "Suggestion must be between 5 and 500 characters.")
    String justification
) {

}
