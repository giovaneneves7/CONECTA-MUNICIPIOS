package br.edu.ifba.conectairece.api.features.flow.domain.dto.request;

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
        UUID stepId
){
}
