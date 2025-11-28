package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.dto.request;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.enums.GeneralEvaluationItemStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GeneralEvaluationItemUpdateRequestDTO(
        @JsonProperty(value = "note")
        @NotBlank(message = "Note is mandatory")
        String note,

        @JsonProperty(value = "status")
        @NotNull(message = "Status cannot be null")
        GeneralEvaluationItemStatus status
) {
}
