package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * @author Giovane Neves
 */
public record FlowStepRequestDTO (
        @JsonProperty("flowId")
        @NotNull(message = "flowId is required")
        UUID flowId,
        @NotNull(message = "stepId is required")
        @JsonProperty("stepId")
        UUID stepId,
        @NotNull(message = "'stepOrder' is required")
        @JsonProperty("stepOrder")
        long stepOrder
){
}
