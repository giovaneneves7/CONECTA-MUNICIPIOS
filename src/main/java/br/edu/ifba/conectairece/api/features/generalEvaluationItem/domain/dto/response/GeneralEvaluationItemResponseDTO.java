package br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.dto.response;

import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.enums.GeneralEvaluationItemStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public record GeneralEvaluationItemResponseDTO(
        @JsonProperty("note")
        String note,

        @JsonProperty("status")
        GeneralEvaluationItemStatus status
) {
}
