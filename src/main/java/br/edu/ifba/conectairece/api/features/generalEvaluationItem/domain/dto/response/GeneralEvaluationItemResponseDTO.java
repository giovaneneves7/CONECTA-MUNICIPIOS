package br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.dto.response;

import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.enums.GeneralEvaluationItemStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public record GeneralEvaluationItemResponseDTO(
        @JsonProperty("id")
        Long id,

        @JsonProperty("note")
        String note,

        @JsonProperty(value = "name")
        String name,

        @JsonProperty("status")
        GeneralEvaluationItemStatus status
) {
}
