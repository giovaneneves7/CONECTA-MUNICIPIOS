package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.dto.request;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.enums.EvaluationItemStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Data Transfer Object for receiving evaluation item data in API **update** requests.
 * <p>
 * Used when updating an existing evaluation item via PUT/PATCH.
 * This DTO mirrors the {@link EvaluationItemRequestDTO} to ensure
 * compatibility with the service layer and validation.
 *
 * @author Andesson Reis 
 */
public record EvaluationItemUpdateRequestDTO(

        /**
         * The ID of the document associated with this evaluation item.
         */
        @JsonProperty(value = "documentId")
        @NotNull(message = "Document ID is mandatory")
        UUID documentId,

        /**
         * The name of the evaluation item.
         */
        @JsonProperty(value = "name")
        @NotBlank(message = "Name is mandatory")
        String name,

        /**
         * The new status of the evaluation item (e.g., PENDING, APPROVED, REJECTED).
         */
        @JsonProperty(value = "status")
        @NotNull(message = "Status is mandatory")
        EvaluationItemStatus status,

        /**
         * An optional note or observation for the evaluation.
         */
        @JsonProperty(value = "note")
        @NotBlank(message = "Note is mandatory")
        String note,

        /**
         * An optional string indicating the type of blueprint.
         */
        @JsonProperty(value = "blueprintType")
        String blueprintType
) {
}