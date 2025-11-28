package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.dto.response;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.enums.GeneralEvaluationItemStatus;
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
