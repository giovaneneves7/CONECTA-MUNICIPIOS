package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.dto.response;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.enums.EvaluationItemStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Data Transfer Object for responding with the details of an evaluation item.
 *
 * @author Jorge Roberto
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EvaluationItemResponseDTO(

        @JsonProperty(value = "id")
        UUID id,

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
