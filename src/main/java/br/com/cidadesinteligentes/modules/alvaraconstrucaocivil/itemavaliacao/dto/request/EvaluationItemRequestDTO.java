package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.dto.request;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.enums.EvaluationItemStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Data Transfer Object for requesting the creation or update of an evaluation item.
 *
 * @author Jorge Roberto
 */
public record EvaluationItemRequestDTO(
        @JsonProperty(value = "documentId")
        @NotNull(message = "Document ID is mandatory")
        UUID documentId,

        @JsonProperty(value = "name")
        @NotBlank(message = "Name is mandatory")
        String name,

        @JsonProperty(value = "status")
        @NotNull(message = "Status is mandatory")
        EvaluationItemStatus status,

        @JsonProperty(value = "note")
        @NotBlank(message = "Note is mandatory")
        String note,

        @JsonProperty(value = "blueprintType")
        String blueprintType
) {
}
