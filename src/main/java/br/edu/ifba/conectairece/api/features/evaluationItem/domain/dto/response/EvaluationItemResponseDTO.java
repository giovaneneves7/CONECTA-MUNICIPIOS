package br.edu.ifba.conectairece.api.features.evaluationItem.domain.dto.response;

import br.edu.ifba.conectairece.api.features.evaluationItem.domain.enums.EvaluationItemStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for responding with the details of an evaluation item.
 *
 * @author Jorge Roberto
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EvaluationItemResponseDTO(
        @JsonProperty(value = "name")
        String name,

        @JsonProperty(value = "note")
        String note,

        @JsonProperty(value = "status")
        EvaluationItemStatus status,

        @JsonProperty(value = "blueprintType")
        String blueprintType
) {
}
