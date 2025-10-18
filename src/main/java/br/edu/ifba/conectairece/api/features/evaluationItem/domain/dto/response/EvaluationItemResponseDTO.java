package br.edu.ifba.conectairece.api.features.evaluationItem.domain.dto.response;

import br.edu.ifba.conectairece.api.features.evaluationItem.domain.enums.EvaluationItemStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for responding with the details of an evaluation item.
 *
 * @author Jorge Roberto
 */
public record EvaluationItemResponseDTO(
        @JsonProperty(value = "note")
        String note,

        @JsonProperty(value = "status")
        EvaluationItemStatus status
) {
}
